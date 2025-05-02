package am.aua.resourcehub.DAO;


import am.aua.resourcehub.util.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FilterOptionsDAO {
    private Connection connection;

    public FilterOptionsDAO(Connection connection) {
        this.connection = connection;
    }
    public FilterOptionsDAO() {
        try {
            this.connection = ConnectionFactory.getInstance().getConnection();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> getAllTypes() {
        return getSingleColumn("SELECT name FROM resource_types");
    }

    public List<String> getAllDomains() {
        return getSingleColumn("SELECT name FROM domains");
    }

    public List<String> getAllTargets() {
        return getSingleColumn("SELECT name FROM target_audience");
    }

    public List<String> getAllLanguages() {
        return getSingleColumn("SELECT resource_language FROM resources");
    }

    private List<String> getSingleColumn(String query) {
        List<String> values = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                values.add(rs.getString(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return values.stream().distinct().collect(Collectors.toList());
    }
}