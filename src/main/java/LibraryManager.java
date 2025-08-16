import java.util.*;

// Quản lý thư viện
public class LibraryManager {
    private Map<String, User> users;
    private Map<String, Book> books;
    private List<Record> records;

    // Khởi tạo
    public LibraryManager() {
        this.users = new HashMap<>();
        this.books = new HashMap<>();
        this.records = new ArrayList<>();
    }

    // Thêm người dùng hoặc sách vào hệ thống
    public void registerUser(User user) {
        users.put(user.getUserId(), user);
    }

    public void addBook(Book book) {
        books.put(book.getBookId(), book);
    }
    

    // Xử lý mượn sách
    public boolean borrowBook(String userId, String bookId) {
        User user = users.get(userId);
        Book book = books.get(bookId);

        System.out.println("DEBUG: Looking for user ' - LibraryManager.java:31" + userId + "'. Found: " + (user != null));
        System.out.println("DEBUG: Looking for book ' - LibraryManager.java:32" + bookId + "'. Found: " + (book != null));

        if (user == null) {
            System.out.println("User not found. - LibraryManager.java:35");
            return false; // Thất bại
        }

        if (book == null) {
            System.out.println("Book not found. - LibraryManager.java:40");
            return false; // Thất bại
        }

        System.out.println("DEBUG: User - LibraryManager.java:44" + userId + " has " + user.getBorrowedBookIds().size() + " books borrowed.");
        System.out.println("DEBUG: Does user - LibraryManager.java:45" + userId + " already have book " + bookId + "? " + user.getBorrowedBookIds().contains(bookId));

        if (user.getBorrowedBookIds().contains(bookId)) {
            System.out.println("User already borrowed this book. - LibraryManager.java:48");
            return false; // Thất bại
        }

        

        user.getBorrowedBookIds().add(bookId);
        book.incrementBorrowCount();

        Record record = new Record(generateRecordId(), userId, bookId, ActionType.BORROW);
        records.add(record);

        System.out.println("Borrow successful: - LibraryManager.java:60" + record);
        return true; // Thành công
    }

    // Xử lý hoàn trả sách
    public boolean returnBook(String userId, String bookId) {
        User user = users.get(userId);
        Book book = books.get(bookId);

        
        System.out.println("DEBUG: Looking for user ' - LibraryManager.java:70" + userId + "'. Found: " + (user != null));
        System.out.println("DEBUG: Looking for book ' - LibraryManager.java:71" + bookId + "'. Found: " + (book != null));
        if (user == null || book == null) {
            System.out.println("User or Book not found. - LibraryManager.java:73");
            return false;
        }

        System.out.println("DEBUG: User - LibraryManager.java:77" + userId + " has " + user.getBorrowedBookIds().size() + " books borrowed.");
        System.out.println("DEBUG: Does user - LibraryManager.java:78" + userId + " already have book " + bookId + "? " + user.getBorrowedBookIds().contains(bookId));
        
        if (!user.getBorrowedBookIds().contains(bookId)) {
            System.out.println("User hasn't borrowed this book. - LibraryManager.java:81");
            return false;
        }

        user.getBorrowedBookIds().remove(bookId);

        Record record = new Record(generateRecordId(), userId, bookId, ActionType.RETURN);
        records.add(record);

        
        System.out.println("Return successful: - LibraryManager.java:91" + record);
        return true; // Thành công
    }

    public boolean deleteBook(String bookId) {
        if (books.containsKey(bookId)) {
            books.remove(bookId);
            System.out.println("Book deleted: - LibraryManager.java:98" + bookId);
            return true;
        }
        return false;
    }
    
    // Xem nhật ký
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
