package am.aua.resourcehub.DAO;

import am.aua.resourcehub.model.Resource;
import am.aua.resourcehub.util.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ResourceDAOImpl implements ResourceDAO {

    public ResourceDAOImpl() {}

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

        StringBuilder sql = new StringBuilder(
                        "SELECT *, MATCH(title, developer, region, keywords, description) " +
                        "   AGAINST (? IN NATURAL LANGUAGE MODE) AS score " +
                        "FROM resources AS r LEFT JOIN resource_types AS t ON r.type = t.id " +
                        "LEFT JOIN resource_has_domain AS rd ON r.id = rd.resource_id " +
                        "LEFT JOIN domains AS d ON rd.domain_id = d.id " +
                        "LEFT JOIN resource_has_target as rt ON r.id = rt.resource_id " +
                        "LEFT JOIN target_audience as a ON rt.target_id = a.id " +
                        "WHERE ( ? = '' OR MATCH (title, developer, region, keywords, description) " +
                        "   AGAINST (? IN NATURAL LANGUAGE MODE))"
        );

        if(types != null && !types.isEmpty()) {
            sql.append(" AND t.name IN (")
            .append(types.stream().map(a -> "?").collect(Collectors.joining(","))).append(")");
        }
        if(domains != null && !domains.isEmpty()) {
            sql.append(" AND d.name IN (")
            .append(domains.stream().map(a -> "?").collect(Collectors.joining(","))).append(")");
        }
        if(targets != null && !targets.isEmpty()) {
            sql.append(" AND a.name IN (")
            .append(targets.stream().map(a -> "?").collect(Collectors.joining(","))).append(")");
        }
        if(languages != null && !languages.isEmpty()) {
            sql.append(" AND r.resource_language IN (")
            .append(languages.stream().map(a -> "?").collect(Collectors.joining(","))).append(")");
        }

        sql.append(" GROUP BY r.id");
        sql.append(" ORDER BY score DESC LIMIT ? OFFSET ?");

        try (Connection connection = ConnectionFactory.getInstance().getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql.toString())) {

            int i = 1;
            stmt.setString(i++, query);
            stmt.setString(i++, query);
            stmt.setString(i++, query);

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
            result = mapResources(rs, connection);

            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public Resource getResourceById(int id) {
        List<Resource> result = null;

        String sql =    "SELECT * FROM resources AS r " +
                        "LEFT JOIN resource_types AS t ON r.type = t.id " +
                        "WHERE r.id = ?";

        try (Connection connection = ConnectionFactory.getInstance().getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            result = mapResources(rs, connection);

            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (result != null) {
            return result.get(0);
        }

        return null;
    }

    @Override
    public List<Resource> getAllResources(int limit, int offset) {
        List<Resource> result = new ArrayList<>();

        String sql =    "SELECT * FROM resources AS r " +
                        "LEFT JOIN resource_types AS t ON r.type = t.id " +
                        "LIMIT ? OFFSET ?";

        try (Connection connection = ConnectionFactory.getInstance().getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)
             ) {
            stmt.setInt(1, limit);
            stmt.setInt(2, offset);

            ResultSet rs = stmt.executeQuery();

            result = mapResources(rs, connection);

            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public List<Resource> searchResourcesByName(String search) {
        List<Resource> result = new ArrayList<>();

        String sql =    "SELECT * FROM resources AS r " +
                        "LEFT JOIN resource_types AS t ON r.type = t.id " +
                        "WHERE r.title LIKE 1";

        try (Connection connection = ConnectionFactory.getInstance().getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, "'%" + search + "%'");
            ResultSet rs = stmt.executeQuery();

            result = mapResources(rs, connection);

            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public List<Resource> searchResourcesByKeyword(String keyword) {
        //TODO
        return Collections.emptyList();
    }
    public void updateResource(Resource resource) {
        String sql = "UPDATE resources SET title = ?, description = ? WHERE id = ?";
        try (Connection conn = ConnectionFactory.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, resource.getTitle());
            stmt.setString(2, resource.getDescription());
            stmt.setInt(3, resource.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
