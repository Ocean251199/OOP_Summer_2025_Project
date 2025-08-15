import java.util.ArrayList;
import java.util.List;

// Lớp đại diện cho người dùng trong hệ thống
public class User {
    private String userId;
    private String email;
    private String password;
    private List<String> borrowedBookIds;

    // Khởi tạo
    public User(String userId, String email, String password) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.borrowedBookIds = new ArrayList<>();
    }

    // Getters and setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getBorrowedBookIds() {
        return borrowedBookIds;
    }

    // Business methods
    public void borrowBook(String bookId) {
        borrowedBookIds.add(bookId);
    }

    public void returnBook(String bookId) {
        borrowedBookIds.remove(bookId);
    }

    // Phương thức chuyển đổi đối tượng thành chuỗi
    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", email='" + email + '\'' +
                ", borrowedBookIds=" + borrowedBookIds +
                '}';
    }
}
