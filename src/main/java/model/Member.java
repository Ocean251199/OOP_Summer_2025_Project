package model;
import java.util.ArrayList;
import java.util.List;

// Thành viên thư viện
public class Member extends User {
    private List<String> borrowedBookIds;

    // Khởi tạo
    public Member(String userId, String email, String password) {
        super(userId, email, password);
        this.borrowedBookIds = new ArrayList<>();
    }

    // Mượn sách
    public void borrowBook(String bookId) {
        if (!borrowedBookIds.contains(bookId)) {
            borrowedBookIds.add(bookId);
        } else {
            throw new IllegalStateException("This book is already borrowed by the member.");
        }
    }

    // Trả sách
    public void returnBook(String bookId) {
        borrowedBookIds.remove(bookId);
    }

    // Lấy danh sách sách đã mượn
    public List<String> getBorrowedBookIds() {
        return borrowedBookIds;
    }

    // Đặt danh sách sách đã mượn
    public void setBorrowedBookIds(List<String> borrowedBookIds) {
        this.borrowedBookIds = borrowedBookIds;
    }

    @Override
    public String getRole() {
        return "MEMBER";
    }

    @Override
    public String toString() {
        return super.toString() + ", borrowedBookIds=" + borrowedBookIds + "}";
    }
}
