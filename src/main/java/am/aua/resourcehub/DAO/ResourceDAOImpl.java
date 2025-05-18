package am.aua.resourcehub.DAO;

import am.aua.resourcehub.model.Resource;
import am.aua.resourcehub.util.ConnectionFactory;
import am.aua.resourcehub.util.SynonymProvider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ResourceDAOImpl implements ResourceDAO {

    int foundResourceCount;

    public ResourceDAOImpl() {}

    public void updateResource(Resource resource) {
        String sql = "UPDATE resources SET title = ?, description = ? WHERE id = ?";
        String targetJoined = String.join(",", resource.getTarget());
        String keywordsJoined = String.join(",", resource.getKeywords());

        try (Connection conn = ConnectionFactory.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, resource.getId());
            stmt.setString(2, resource.getType());
            stmt.setString(3, resource.getTitle());
            stmt.setString(4,targetJoined);
            stmt.setString(5, resource.getDeveloper());
            stmt.setString(6, resource.getRegion());
            stmt.setString(7, resource.getLanguage());
            stmt.setString(8, keywordsJoined);
            stmt.setString(9, resource.getUrl());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<String> getAllKeywords() {
        Set<String> uniqueKeywords = new LinkedHashSet<>();
        String sql = "SELECT keywords FROM resources"; // No DISTINCT needed

        try (Connection conn = ConnectionFactory.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String keywordStr = rs.getString("keywords");
                if (keywordStr != null) {
                    String[] keywords = keywordStr.split(",");
                    for (String k : keywords) {
                        String trimmed = k.trim();
                        if (!trimmed.isEmpty()) {
                            uniqueKeywords.add(trimmed);
                        }
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new ArrayList<>(uniqueKeywords);
    }


    private List<Resource> mapResources(ResultSet rs, Connection connection) throws SQLException {
        List<Resource> resources = new ArrayList<>();

        String targetQuery =    "SELECT * FROM resource_has_target AS rht " +
                "LEFT JOIN target_audience AS ta ON rht.target_id = ta.id " +
                "WHERE resource_id = ?";
        PreparedStatement targetStmt = connection.prepareStatement(targetQuery);


        String domainQuery =    "SELECT * FROM resource_has_domain AS rhd " +
                "LEFT JOIN domains AS d ON rhd.domain_id = d.id " +
                "WHERE resource_id = ?";
        PreparedStatement domainStmt = connection.prepareStatement(domainQuery);

        while (rs.next()) {
            Resource resource = new Resource();
            resource.setId(rs.getInt("r.id"));
            resource.setTitle(rs.getString("title"));
            resource.setType(rs.getString("t.name"));
            resource.setDeveloper(rs.getString("developer"));
            resource.setRegion(rs.getString("region"));
            resource.setLanguage(rs.getString("resource_language"));
            resource.setUrl(rs.getString("link"));
            resource.setDescription(rs.getString("description"));
            resource.setKeywords(Arrays.stream(rs.getString("keywords").split(","))
                    .map(String::trim)
                    .collect(Collectors.toList()));

            targetStmt.setInt(1, resource.getId());
            ResultSet targetRs = targetStmt.executeQuery();
            List<String> targetAudiences = new ArrayList<>();
            while(targetRs.next()) {
                targetAudiences.add(targetRs.getString("name"));
            }
            resource.setTarget(targetAudiences);

            domainStmt.setInt(1, resource.getId());
            ResultSet domainRs = domainStmt.executeQuery();
            List<String> domains = new ArrayList<>();
            while (domainRs.next()) {
                domains.add(domainRs.getString("name"));
            }
            resource.setDomain(domains);


            resources.add(resource);
        }

        return resources.stream().distinct().collect(Collectors.toList());
    }

    @Override
    public List<Resource> search(String query, List<String> types, List<String> targets, List<String> regions, List<String> domains, List<String> languages, int limit, int offset) {
        List<Resource> result = null;
        SynonymProvider synonymProvider = null;
        Set<String> searchTerms = new HashSet<>();
        List<String> tokenizedQuery = preprocessQuery(query);
        String expandedQuery = "";

        //check if all the arguments are null or empty
        boolean noSearch =
                (query == null || query.isEmpty()) &&
                (types == null || types.isEmpty()) &&
                (targets == null || targets.isEmpty()) &&
                (regions == null || regions.isEmpty()) &&
                (domains == null || domains.isEmpty()) &&
                (languages == null || languages.isEmpty());

        //in case nothing was queried
        if(noSearch)
            return getAllResources(limit, offset);

        try {
            synonymProvider = new SynonymProvider();

            for (String t : tokenizedQuery) {
                searchTerms.addAll(synonymProvider.getSynonyms(t));
            }
        }
        catch (Exception e) {
            System.out.println("Synonym provider failed");
            System.out.println(e.getMessage());
        }

        if(!searchTerms.isEmpty())
            expandedQuery = String.join(" ", searchTerms);


        StringBuilder sql = new StringBuilder(
                        "SELECT SQL_CALC_FOUND_ROWS *, MATCH(title, developer, region, keywords, description) " +
                        "   AGAINST (? IN NATURAL LANGUAGE MODE) AS score " +
                        "FROM resources AS r LEFT JOIN resource_types AS t ON r.type = t.id " +
                        "LEFT JOIN resource_has_domain AS rd ON r.id = rd.resource_id " +
                        "LEFT JOIN domains AS d ON rd.domain_id = d.id " +
                        "LEFT JOIN resource_has_target as rt ON r.id = rt.resource_id " +
                        "LEFT JOIN target_audience as a ON rt.target_id = a.id " +
                        "WHERE ( ? = '' OR MATCH (title, developer, region, keywords, description) " +
                        "   AGAINST (? IN NATURAL LANGUAGE MODE)"
        );

        for (String t : tokenizedQuery) {
            if(t.length() >= 3) {
                sql.append(" OR r.title LIKE '%")
                        .append(t)
                        .append("%'");
                sql.append(" OR r.developer LIKE '%")
                        .append(t)
                        .append("%'");
                sql.append(" OR r.region LIKE '%")
                        .append(t)
                        .append("%'");
                sql.append(" OR r.keywords LIKE '%")
                        .append(t)
                        .append("%'");
                sql.append(" OR r.description LIKE '%")
                        .append(t)
                        .append("%'");
            }
        }
        sql.append(")");
        if (types != null && !types.isEmpty()) {
            sql.append(" AND t.name IN (")
            .append(types.stream().map(a -> "?").collect(Collectors.joining(","))).append(")");
        }
        if (domains != null && !domains.isEmpty()) {
            sql.append(" AND d.name IN (")
            .append(domains.stream().map(a -> "?").collect(Collectors.joining(","))).append(")");
        }
        if (targets != null && !targets.isEmpty()) {
            sql.append(" AND a.name IN (")
            .append(targets.stream().map(a -> "?").collect(Collectors.joining(","))).append(")");
        }
        if (languages != null && !languages.isEmpty()) {
            sql.append(" AND r.resource_language ")
                    .append("RLIKE CONCAT('\\\\b', ");
            for (int i = 0; i < languages.size(); i++) {
                sql.append("?");
                if(i < languages.size() - 1) {
                        sql.append(",")
                                .append("'")
                                .append("|")
                                .append("'")
                                .append(",");
                }
            }
            sql.append(", '\\\\b')");
        }

        sql.append(" GROUP BY r.id");
        sql.append(" ORDER BY score DESC LIMIT ? OFFSET ?");

        try (Connection connection = ConnectionFactory.getInstance().getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql.toString());
             PreparedStatement foundRowsStmt = connection.prepareStatement("SELECT FOUND_ROWS()")) {

            int i = 1;
            stmt.setString(i++, expandedQuery);
            stmt.setString(i++, expandedQuery);
            stmt.setString(i++, expandedQuery);

            if(types != null){
                for (String type : types) {
                    stmt.setString(i++, type);
                }
            }
            if(domains != null){
                for (String dom : domains) {
                    stmt.setString(i++, dom);
                }
            }
            if(targets != null){
                for (String target : targets) {
                    stmt.setString(i++, target);
                }
            }
            if(languages != null){
                for (String lang : languages) {
                    stmt.setString(i++, lang);
                }
            }

            stmt.setInt(i++, limit);
            stmt.setInt(i, offset);

            ResultSet rs = stmt.executeQuery();
            ResultSet count = foundRowsStmt.executeQuery();

            if (count.next())
                this.foundResourceCount = count.getInt(1);
            count.close();

            result = mapResources(rs, connection);

            rs.close();


        } catch (SQLException e) {
            System.out.println("Database Error Occurred");
            System.out.println(e.getMessage());
        }

        return result;
    }

    private static List<String> preprocessQuery(String raw) {
        if (raw == null || raw.isEmpty()) {
            return Collections.emptyList();
        }

        String clean = raw.toLowerCase().replaceAll("[^a-z0-9]+", " ").trim();

        String[] tokens = clean.split("\\s+");

        Set<String> stopWords = Stream.of("the", "of", "and", "a", "in", "to", "for", "on", "with", "are", "is", "am", "was", "were").collect(Collectors.toSet());
        List<String> result = new ArrayList<>();

        for (String token : tokens) {
            if (!stopWords.contains(token)) {
                result.add(token);
            }
        }
        return result;
    }

    @Override
    public List<Resource> getAllResources(int limit, int offset) {
        List<Resource> result = new ArrayList<>();

        String sql =    "SELECT SQL_CALC_FOUND_ROWS * FROM resources AS r " +
                        "LEFT JOIN resource_types AS t ON r.type = t.id " +
                        "LIMIT ? OFFSET ?";

        try (Connection connection = ConnectionFactory.getInstance().getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);
             PreparedStatement foundRowsStmt = connection.prepareStatement("SELECT FOUND_ROWS()"))
        {
            stmt.setInt(1, limit);
            stmt.setInt(2, offset);

            ResultSet rs = stmt.executeQuery();
            ResultSet count = foundRowsStmt.executeQuery();

            if (count.next())
                this.foundResourceCount = count.getInt(1);

            count.close();

            result = mapResources(rs, connection);

            rs.close();

        } catch (SQLException e) {
            System.out.println("Database Error Occurred");
            System.out.println(e.getMessage());
        }

        return result;
    }

    public void insertResources(List<Resource> resources) {
        if (resources == null || resources.isEmpty()) {
            System.out.println("Nothing to add");
            return;
        }
        StringBuilder sql1 = new StringBuilder("INSERT INTO resources (title, type, developer, description, region, resource_language, keywords, link) VALUES ");
        StringBuilder sql2 = new StringBuilder("INSERT INTO resource_has_target (resource_id, target_id) VALUES ");
        StringBuilder sql3 = new StringBuilder("INSERT INTO resource_has_domain (resource_id, domain_id) VALUES ");

        try(Connection connection = ConnectionFactory.getInstance().getConnection()) {

            for (int i=0; i < resources.size(); i++) {
                sql1.append("(?, (SELECT id FROM resource_types WHERE name = ?), ?, ?, ?, ?, ?, ?)");

                for (int j=0; j < resources.get(i).getTarget().size(); j++) {
                    sql2.append("((SELECT id FROM resources WHERE link = ?), (SELECT id FROM target_audience WHERE name = ?))");
                    if (j != resources.get(i).getTarget().size() - 1)
                        sql2.append(", ");
                }
                for (int j=0; j < resources.get(i).getDomain().size(); j++) {
                    sql3.append("((SELECT id FROM resources WHERE link = ?), (SELECT id FROM domains WHERE name = ?))");
                    if (j != resources.get(i).getDomain().size() - 1)
                        sql3.append(", ");
                }

                if (i != resources.size() - 1) {
                    sql1.append(", ");
                    sql2.append(", ");
                    sql3.append(", ");
                }
            }

            PreparedStatement resourceStmt = connection.prepareStatement(sql1.toString());
            PreparedStatement targetStmt = connection.prepareStatement(sql2.toString());
            PreparedStatement domainStmt = connection.prepareStatement(sql3.toString());


            int i = 1;
            int j = 1;
            int k = 1;
            for (Resource r : resources) {
                resourceStmt.setString(i++, r.getTitle());
                resourceStmt.setString(i++, r.getType());
                resourceStmt.setString(i++, r.getDeveloper());
                resourceStmt.setString(i++, r.getDescription());
                resourceStmt.setString(i++, r.getRegion());
                resourceStmt.setString(i++, r.getLanguage());
                resourceStmt.setString(i++, String.join(", ", r.getKeywords()));
                resourceStmt.setString(i++, r.getUrl());

                for (String t : r.getTarget()) {
                    targetStmt.setString(j++, r.getUrl());
                    targetStmt.setString(j++, t);
                }
                for (String d : r.getDomain()) {
                    domainStmt.setString(k++, r.getUrl());
                    domainStmt.setString(k++, d);
                }


            }
            resourceStmt.executeUpdate();
            targetStmt.executeUpdate();
            domainStmt.executeUpdate();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getFoundResourceCount() {
        return foundResourceCount;
    }

}
