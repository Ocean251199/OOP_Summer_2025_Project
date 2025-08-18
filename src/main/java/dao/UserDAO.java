package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import service.DBHelper;
import model.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class UserDAO {

    private static final ObjectMapper mapper = new ObjectMapper();

    public void addUser(User user) {
        String sql = "INSERT INTO users(userId, email, password, role, borrowedBookIds) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user.getUserId());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, user.getPassword());
            pstmt.setString(4, user.getRole());
            pstmt.setString(5, serializeBorrowedBooks(user.getBorrowedBookIds()));

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
                List<String> borrowedBooks = deserializeBorrowedBooks(rs.getString("borrowedBookIds"));

                User user;
                if ("ADMIN".equals(role)) {
                    user = new Admin(rs.getString("userId"), rs.getString("email"), rs.getString("password"));
                } else {
                    user = new Member(rs.getString("userId"), rs.getString("email"), rs.getString("password"));
                }

                user.setBorrowedBookIds(borrowedBooks);
                users.add(user);
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
                    List<String> borrowedBooks = deserializeBorrowedBooks(rs.getString("borrowedBookIds"));

                    if ("ADMIN".equals(role)) {
                        user = new Admin(rs.getString("userId"), rs.getString("email"), rs.getString("password"));
                    } else {
                        user = new Member(rs.getString("userId"), rs.getString("email"), rs.getString("password"));
                    }

                    user.setBorrowedBookIds(borrowedBooks);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user; // null if not found
    }

    public void printBorrowedBooks(User user) {
        List<String> borrowed = user.getBorrowedBookIds();
        System.out.println("Borrowed books: " + borrowed);
    }

    public void updateUser(User user) {
        String sql = "UPDATE users SET email = ?, password = ?, role = ?, borrowedBookIds = ? WHERE userId = ?";

        try (Connection conn = DBHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user.getEmail());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getRole());
            pstmt.setString(4, serializeBorrowedBooks(user.getBorrowedBookIds()));
            pstmt.setString(5, user.getUserId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUser(String userId) {
        String sql = "DELETE FROM users WHERE userId = ?";

        try (Connection conn = DBHelper.connect();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, userId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Convert list -> JSON string
    private String serializeBorrowedBooks(List<String> borrowedBookIds) {
        try {
            return mapper.writeValueAsString(borrowedBookIds);
        } catch (Exception e) {
            e.printStackTrace();
            return "[]"; // default empty list
        }
    }

    // Convert JSON string -> list
    private List<String> deserializeBorrowedBooks(String json) {
        try {
            if (json == null) return new ArrayList<>();
            return mapper.readValue(json, new TypeReference<List<String>>() {});
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
