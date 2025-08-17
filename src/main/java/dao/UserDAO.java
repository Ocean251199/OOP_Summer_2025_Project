package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import service.DBHelper;
import model.User;
import model.Admin;
import model.Member;

public class UserDAO {

    public void addUser(User user) {
        String sql = "INSERT INTO users(userId, email, password, role) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user.getUserId());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, user.getPassword());
            pstmt.setString(4, user.getRole());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";

        try (Connection conn = DBHelper.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String role = rs.getString("role");
                if (role.equals("ADMIN")) {
                    users.add(new Admin(rs.getString("userId"), rs.getString("email"), rs.getString("password")));
                } else if (role.equals("MEMBER")) {
                    users.add(new Member(rs.getString("userId"), rs.getString("email"), rs.getString("password")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public User getUserById(String userId) {
        String sql = "SELECT * FROM users WHERE userId = ?";
        User user = null;

        try (Connection conn = DBHelper.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String role = rs.getString("role");
                    if (role.equals("ADMIN")) {
                        user = new Admin(
                                rs.getString("userId"),
                                rs.getString("email"),
                                rs.getString("password")
                        );
                    } else if (role.equals("MEMBER")) {
                        user = new Member(
                                rs.getString("userId"),
                                rs.getString("email"),
                                rs.getString("password")
                        );
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user; // null if not found
    }

}
