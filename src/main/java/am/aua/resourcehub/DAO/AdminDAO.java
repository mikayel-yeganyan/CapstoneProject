package am.aua.resourcehub.DAO;

import am.aua.resourcehub.util.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDAO {
    public boolean validate(String username, String pass) {
        String sql = "SELECT * FROM admin_user WHERE username = ? AND password = ?";
        try(Connection conn = ConnectionFactory.getInstance().getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);
        ){
          statement.setString(1, username);
          statement.setString(2, pass);
          ResultSet rs = statement.executeQuery();
          return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

