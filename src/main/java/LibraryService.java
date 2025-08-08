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
<<<<<<< HEAD
                             Arrays.asList("Cổ điển", "Thơ ca"), "https://www.nxbtre.com.vn/Images/Book/NXBTreStoryFull_03462015_104616.jpg");
        Book book2 = new Book("B002", "Số đỏ", "Vũ Trọng Phụng", "NXB Kim Đồng", 1936, 
                             Arrays.asList("Tiểu thuyết", "Hiện thực"), "https://product.hstatic.net/200000017360/product/bia_sodo3-b1_b32d805ef78846fab8d0d6c1c7fc887b_master.jpg");
        Book book3 = new Book("B003", "Tắt đèn", "Ngô Tất Tố", "NXB Văn học", 1939, 
                             Arrays.asList("Tiểu thuyết", "Hiện thực"), "https://images-na.ssl-images-amazon.com/images/S/compressed.photo.goodreads.com/books/1479993956i/13147425.jpg");

=======
                             Arrays.asList("Cổ điển", "Thơ ca"));
        Book book2 = new Book("B002", "Số đỏ", "Vũ Trọng Phụng", "NXB Kim Đồng", 1936, 
                             Arrays.asList("Tiểu thuyết", "Hiện thực"));
        Book book3 = new Book("B003", "Tắt đèn", "Ngô Tất Tố", "NXB Văn học", 1939, 
                             Arrays.asList("Tiểu thuyết", "Hiện thực"));
        
>>>>>>> 55e6436a5a282ac3ad80809b1ba72066886e3305
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
<<<<<<< HEAD
        String defaultImgUrl = "https://via.placeholder.com/150x200?text=No+Image";
        return addBook(title, author, publisher, yearPublished, genres, defaultImgUrl);
    }

    public boolean addBook(String title, String author, String publisher, int yearPublished, List<String> genres, String imgUrl) {
        try {
            String bookId = generateBookId();
            Book book = new Book(bookId, title, author, publisher, yearPublished, genres, imgUrl);
=======
        try {
            String bookId = generateBookId();
            Book book = new Book(bookId, title, author, publisher, yearPublished, genres);
>>>>>>> 55e6436a5a282ac3ad80809b1ba72066886e3305
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
<<<<<<< HEAD
                isBookAvailable(book.getBookId()),
                book.getImgUrl()
=======
                isBookAvailable(book.getBookId())
>>>>>>> 55e6436a5a282ac3ad80809b1ba72066886e3305
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
