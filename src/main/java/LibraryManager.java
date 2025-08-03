import java.util.*;

public class LibraryManager {
    private Map<String, User> users;
    private Map<String, Book> books;
    private List<Record> records;

    public LibraryManager() {
        this.users = new HashMap<>();
        this.books = new HashMap<>();
        this.records = new ArrayList<>();
    }

    // Add user or book to system
    public void registerUser(User user) {
        users.put(user.getUserId(), user);
    }

    public void addBook(Book book) {
        books.put(book.getBookId(), book);
    }

    // Handle borrowing a book
    public void borrowBook(String userId, String bookId) {
        User user = users.get(userId);
        Book book = books.get(bookId);

        if (user == null || book == null) {
            System.out.println("User or Book not found.");
            return;
        }

        if (user.getBorrowedBookIds().contains(bookId)) {
            System.out.println("User already borrowed this book.");
            return;
        }

        user.getBorrowedBookIds().add(bookId);
        book.incrementBorrowCount();

        Record record = new Record(generateRecordId(), userId, bookId, ActionType.BORROW);
        records.add(record);

        System.out.println("Borrow successful: " + record);
    }

    // Handle returning a book
    public void returnBook(String userId, String bookId) {
        User user = users.get(userId);
        Book book = books.get(bookId);

        if (user == null || book == null) {
            System.out.println("User or Book not found.");
            return;
        }

        if (!user.getBorrowedBookIds().contains(bookId)) {
            System.out.println("User hasn't borrowed this book.");
            return;
        }

        user.getBorrowedBookIds().remove(bookId);

        Record record = new Record(generateRecordId(), userId, bookId, ActionType.RETURN);
        records.add(record);

        System.out.println("Return successful: " + record);
    }

    // View logs
    public void printAllRecords() {
        for (Record r : records) {
            System.out.println(r);
        }
    }

    // --- Utility ---
    private String generateRecordId() {
        return "REC-" + UUID.randomUUID().toString().substring(0, 8);
    }
}
