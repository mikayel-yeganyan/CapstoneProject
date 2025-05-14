package am.aua.resourcehub.DAO;

import am.aua.resourcehub.util.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDAO {
    public boolean authenticate(String username, String password) {
        String sql = "select * from admin_user where username = ? and pass = ?";
        try(Connection conn = ConnectionFactory.getInstance().getConnection();
            PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet rs = statement.executeQuery();

            return rs.next();
        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}
