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
    public boolean borrowBook(String userId, String bookId) {
        User user = users.get(userId);
        Book book = books.get(bookId);

        System.out.println("DEBUG: Looking for user ' - LibraryManager.java:29" + userId + "'. Found: " + (user != null));
        System.out.println("DEBUG: Looking for book ' - LibraryManager.java:30" + bookId + "'. Found: " + (book != null));

        if (user == null) {
            System.out.println("User not found. - LibraryManager.java:33");
            return false; // Thất bại
        }

        if (book == null) {
            System.out.println("Book not found. - LibraryManager.java:38");
            return false; // Thất bại
        }

        System.out.println("DEBUG: User - LibraryManager.java:42" + userId + " has " + user.getBorrowedBookIds().size() + " books borrowed.");
        System.out.println("DEBUG: Does user - LibraryManager.java:43" + userId + " already have book " + bookId + "? " + user.getBorrowedBookIds().contains(bookId));

        if (user.getBorrowedBookIds().contains(bookId)) {
            System.out.println("User already borrowed this book. - LibraryManager.java:46");
            return false; // Thất bại
        }

        

        user.getBorrowedBookIds().add(bookId);
        book.incrementBorrowCount();

        Record record = new Record(generateRecordId(), userId, bookId, ActionType.BORROW);
        records.add(record);

        System.out.println("Borrow successful: - LibraryManager.java:58" + record);
        return true; // Thành công
    }

    // Handle returning a book
    public boolean returnBook(String userId, String bookId) {
        User user = users.get(userId);
        Book book = books.get(bookId);

        
        System.out.println("DEBUG: Looking for user ' - LibraryManager.java:68" + userId + "'. Found: " + (user != null));
        System.out.println("DEBUG: Looking for book ' - LibraryManager.java:69" + bookId + "'. Found: " + (book != null));
        if (user == null || book == null) {
            System.out.println("User or Book not found. - LibraryManager.java:71");
            return false;
        }

        System.out.println("DEBUG: User - LibraryManager.java:75" + userId + " has " + user.getBorrowedBookIds().size() + " books borrowed.");
        System.out.println("DEBUG: Does user - LibraryManager.java:76" + userId + " already have book " + bookId + "? " + user.getBorrowedBookIds().contains(bookId));
        
        if (!user.getBorrowedBookIds().contains(bookId)) {
            System.out.println("User hasn't borrowed this book. - LibraryManager.java:79");
            return false;
        }

        user.getBorrowedBookIds().remove(bookId);

        Record record = new Record(generateRecordId(), userId, bookId, ActionType.RETURN);
        records.add(record);

        
        System.out.println("Return successful: - LibraryManager.java:89" + record);
        return true; // Thành công
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
