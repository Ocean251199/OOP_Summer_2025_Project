import java.util.*;
import java.util.stream.Collectors;

public class LibraryService {
    private LibraryManager libraryManager;
    private static LibraryService instance;

    private LibraryService() {
        this.libraryManager = new LibraryManager();
        initializeTestData();
    }

    public static LibraryService getInstance() {
        if (instance == null) {
            instance = new LibraryService();
        }
        return instance;
    }

    // Initialize with some test data
    private void initializeTestData() {
        // Add some test books
        Book book1 = new Book("B001", "Truyện Kiều", "Nguyễn Du", "NXB Văn học", 1820, 
                             Arrays.asList("Cổ điển", "Thơ ca"));
        Book book2 = new Book("B002", "Số đỏ", "Vũ Trọng Phụng", "NXB Kim Đồng", 1936, 
                             Arrays.asList("Tiểu thuyết", "Hiện thực"));
        Book book3 = new Book("B003", "Tắt đèn", "Ngô Tất Tố", "NXB Văn học", 1939, 
                             Arrays.asList("Tiểu thuyết", "Hiện thực"));
        
        libraryManager.addBook(book1);
        libraryManager.addBook(book2);
        libraryManager.addBook(book3);

        // Add test user
        User testUser = new User("U001", "test@example.com", "password");
        libraryManager.registerUser(testUser);
    }

    public List<BookDto> getAllBooks() {
        return libraryManager.getBooks().values().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<BookDto> getAvailableBooks() {
        return libraryManager.getBooks().values().stream()
                .filter(book -> isBookAvailable(book.getBookId()))
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<BookDto> getBorrowedBooks() {
        return libraryManager.getBooks().values().stream()
                .filter(book -> !isBookAvailable(book.getBookId()))
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public boolean addBook(String title, String author, String publisher, int yearPublished, List<String> genres) {
        try {
            String bookId = generateBookId();
            Book book = new Book(bookId, title, author, publisher, yearPublished, genres);
            libraryManager.addBook(book);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean borrowBook(String userId, String bookId) {
        try {
            libraryManager.borrowBook(userId, bookId);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean returnBook(String userId, String bookId) {
        try {
            libraryManager.returnBook(userId, bookId);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean isBookAvailable(String bookId) {
        // Check if any user has borrowed this book
        return libraryManager.getUsers().values().stream()
                .noneMatch(user -> user.getBorrowedBookIds().contains(bookId));
    }

    private BookDto convertToDto(Book book) {
        return new BookDto(
                book.getBookId(),
                book.getTitle(),
                book.getAuthor(),
                book.getPublisher(),
                book.getYearPublished(),
                book.getGenres(),
                book.getBorrowCount(),
                isBookAvailable(book.getBookId())
        );
    }

    private String generateBookId() {
        return "B" + String.format("%03d", libraryManager.getBooks().size() + 1);
    }

    // Getter for accessing LibraryManager if needed
    public LibraryManager getLibraryManager() {
        return libraryManager;
    }
}
