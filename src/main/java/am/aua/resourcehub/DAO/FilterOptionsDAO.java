package am.aua.resourcehub.DAO;


import am.aua.resourcehub.util.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * DAO layer used for fetching filter options to be put in the sidebar as checkboxes
 */
public class FilterOptionsDAO {

    /**
     * Empty constructor
     */
    public FilterOptionsDAO() {}

    /**
     * Get all types by which the results can be filtered
     * @return a List of String types from the database
     */
    public List<String> getAllTypes() {
        return getSingleColumn("SELECT name FROM resource_types");
    }

    /**
     * Get all domains by which the results can be filtered
     * @return a List of String domains from the database
     */
    public List<String> getAllDomains() {
        return getSingleColumn("SELECT name FROM domains");
    }

    /**
     * Get all target audiences by which the results can be filtered
     * @return a List of String target audiences from the database
     */
    public List<String> getAllTargets() {
        return getSingleColumn("SELECT name FROM target_audience");
    }

    /**
     * Get all languages by which the results can be filtered
     * @return a List of String languages from the database
     */
    public List<String> getAllLanguages() {
        return getSingleColumn("SELECT resource_language FROM resources");
    }

    /**
     * Get all regions by which the results can be filtered
     * @return a List of String regions from the database
     */
    public List<String> getAllRegions() { return getSingleColumn("SELECT region FROM resources"); }

    /**
     * Utility method to construct the list of values from the query
     * @param query a query that will return a single column of values from the database
     * @return a List of Strings from one column of the database
     */
    private List<String> getSingleColumn(String query) {
        List<String> values = new ArrayList<>();
        try (Connection connection = ConnectionFactory.getInstance().getConnection();
             PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                values.add(rs.getString(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return values.stream()
                .flatMap(s -> Arrays.stream(s.split(",")))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .distinct()
                .collect(Collectors.toList());
    }
}