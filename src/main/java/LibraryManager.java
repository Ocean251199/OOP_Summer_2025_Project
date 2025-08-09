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
            System.out.println("User or Book not found. - LibraryManager.java:30");
            return;
        }

        if (user.getBorrowedBookIds().contains(bookId)) {
            System.out.println("User already borrowed this book. - LibraryManager.java:35");
            return;
        }

        user.getBorrowedBookIds().add(bookId);
        book.incrementBorrowCount();

        Record record = new Record(generateRecordId(), userId, bookId, ActionType.BORROW);
        records.add(record);

        System.out.println("Borrow successful: - LibraryManager.java:45" + record);
    }

    // Handle returning a book
    public void returnBook(String userId, String bookId) {
        User user = users.get(userId);
        Book book = books.get(bookId);

        if (user == null || book == null) {
            System.out.println("User or Book not found. - LibraryManager.java:54");
            return;
        }

        if (!user.getBorrowedBookIds().contains(bookId)) {
            System.out.println("User hasn't borrowed this book. - LibraryManager.java:59");
            return;
        }

        user.getBorrowedBookIds().remove(bookId);

        Record record = new Record(generateRecordId(), userId, bookId, ActionType.RETURN);
        records.add(record);

        System.out.println("Return successful: - LibraryManager.java:68" + record);
    }

    // View logs
    public void printAllRecords() {
        for (Record r : records) {
            System.out.println(r);
        }
    }

    // Getters for accessing data
    public Map<String, User> getUsers() {
        return users;
    }

    public Map<String, Book> getBooks() {
        return books;
    }

    public List<Record> getRecords() {
        return records;
    }

    // --- Utility ---
    private String generateRecordId() {
        return "REC-" + UUID.randomUUID().toString().substring(0, 8);
    }
}
