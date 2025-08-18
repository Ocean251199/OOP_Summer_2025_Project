package dto;

import java.util.List;

public class UserDTO {
    private String userId;
    private String email;
    private String role;
    private List<String> borrowedBookIds; // keep as List for JSON

    // Constructor
    public UserDTO(String userId, String email, String role, List<String> borrowedBookIds) {
        this.userId = userId;
        this.email = email;
        this.role = role;
        this.borrowedBookIds = borrowedBookIds;
    }

    // Getters and setters
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public List<String> getBorrowedBookIds() { return borrowedBookIds; }
    public void setBorrowedBookIds(List<String> borrowedBookIds) { this.borrowedBookIds = borrowedBookIds; }
}
