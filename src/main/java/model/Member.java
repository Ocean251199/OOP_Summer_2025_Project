package model;
import java.util.List;

public class Member extends User {

    public Member(String userId, String email, String password) {
        super(userId, email, password);
    }

    public void borrowBook(String bookId) {
        List<String> borrowed = getBorrowedBookIds();  // ✅ use getter
        if (!borrowed.contains(bookId)) {
            borrowed.add(bookId);
        } else {
            throw new IllegalStateException("This book is already borrowed by the member.");
        }
    }

    public void returnBook(String bookId) {
        getBorrowedBookIds().remove(bookId);   // ✅ safe access
    }

    @Override
    public String getRole() {
        return "MEMBER";
    }

    @Override
    public String toString() {
        return super.toString() + ", borrowedBookIds=" + getBorrowedBookIds() + "}";
    }
}
