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

        return resources;
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
    public List<Resource> getAllResources() {
        List<Resource> result = new ArrayList<>();

        String sql =    "SELECT * FROM resources AS r " +
                        "LEFT JOIN resource_types AS t ON r.type = t.id ";

        try (Connection connection = ConnectionFactory.getInstance().getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            result = mapResources(rs, connection);

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
}
