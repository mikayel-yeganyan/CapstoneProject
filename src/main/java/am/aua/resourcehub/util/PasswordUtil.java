package am.aua.resourcehub.util;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Utility class for encoding a password
 */
public class PasswordUtil {
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    /**
     * used for hashing a raw password string
     * @param plainPassword raw password String
     * @return hashed password String
     */
    public static String hashPassword(String plainPassword) {
        return encoder.encode(plainPassword);
    }
}