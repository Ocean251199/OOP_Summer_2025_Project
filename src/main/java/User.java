import java.util.ArrayList;
import java.util.List;

// Abstract class representing a user in the library system
public abstract class User {
    private String userId;
    private String email;
    private String password;
    private List<String> borrowedBookIds;

    public User(String userId, String email, String password) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.borrowedBookIds = new ArrayList<>();
    }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public List<String> getBorrowedBookIds() {
        return borrowedBookIds;
    }

    public void setBorrowedBookIds(List<String> borrowedBookIds) {
        this.borrowedBookIds = borrowedBookIds;
    }

    // Force subclasses to define their role (e.g., "Admin" or "Member")
    public abstract String getRole();

    @Override
    public String toString() {
        return getRole() + "{" +
                "userId='" + userId + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
