package model;
import java.util.ArrayList;
import java.util.List;

public class Member extends User {
    private List<String> borrowedBookIds;

    public Member(String userId, String email, String password) {
        super(userId, email, password);
        this.borrowedBookIds = new ArrayList<>();
    }

    public void borrowBook(String bookId) {
        if (!borrowedBookIds.contains(bookId)) {
            borrowedBookIds.add(bookId);
        } else {
            throw new IllegalStateException("This book is already borrowed by the member.");
        }
    }

    public void returnBook(String bookId) {
        borrowedBookIds.remove(bookId);
    }

    public List<String> getBorrowedBookIds() {
        return borrowedBookIds;
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
