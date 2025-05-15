package am.aua.resourcehub.DAO;

import am.aua.resourcehub.util.ConnectionFactory;
import am.aua.resourcehub.util.PasswordUtil;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDAO {
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public boolean authenticate(String username, String password) {
        String sql = "SELECT pass FROM admin_user WHERE username = ?";

        try (Connection conn = ConnectionFactory.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String storedHash = rs.getString("pass");
                return encoder.matches(password, storedHash);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
    public boolean createAdmin(String username, String plainPassword) {
        String sql = "INSERT INTO admin_user (username, pass) VALUES (?, ?)";
        String hashedPassword = PasswordUtil.hashPassword(plainPassword);

        try (Connection conn = ConnectionFactory.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, hashedPassword);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
