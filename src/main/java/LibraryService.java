import java.util.*;
import java.util.stream.Collectors;

// Dịch vụ thư viện
public class LibraryService {
    private LibraryManager libraryManager;
    private static LibraryService instance;

    // Khởi tạo
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

    // Khởi tạo với một số dữ liệu thử nghiệm
    private void initializeTestData() {
        // Thêm một số sách thử nghiệm
        Book book1 = new Book("B001", "Truyện Kiều", "Nguyễn Du", "NXB Văn học", 1820,
                             Arrays.asList("Cổ điển", "Thơ ca"), "https://www.nxbtre.com.vn/Images/Book/NXBTreStoryFull_03462015_104616.jpg");
        Book book2 = new Book("B002", "Số đỏ", "Vũ Trọng Phụng", "NXB Kim Đồng", 1936, 
                             Arrays.asList("Tiểu thuyết", "Hiện thực"), "https://product.hstatic.net/200000017360/product/bia_sodo3-b1_b32d805ef78846fab8d0d6c1c7fc887b_master.jpg");
        Book book3 = new Book("B003", "Tắt đèn", "Ngô Tất Tố", "NXB Văn học", 1939, 
                             Arrays.asList("Tiểu thuyết", "Hiện thực"), "https://images-na.ssl-images-amazon.com/images/S/compressed.photo.goodreads.com/books/1479993956i/13147425.jpg");

        libraryManager.addBook(book1);
        libraryManager.addBook(book2);
        libraryManager.addBook(book3);

        // Thêm người dùng thử nghiệm
        User testUser = new User("U001", "test@example.com", "password");
        libraryManager.registerUser(testUser);
    }

    // Lấy danh sách tất cả sách
    public List<BookDto> getAllBooks() {
        return libraryManager.getBooks().values().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // Lấy danh sách sách trong kho
    public List<BookDto> getAvailableBooks() {
        return libraryManager.getBooks().values().stream()
                .filter(book -> isBookAvailable(book.getBookId()))
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // Lấy danh sách sách đã mượn
    public List<BookDto> getBorrowedBooks() {
        return libraryManager.getBooks().values().stream()
                .filter(book -> !isBookAvailable(book.getBookId()))
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // Thêm sách mới không ảnh
    public boolean addBook(String title, String author, String publisher, int yearPublished, List<String> genres) {
        String defaultImgUrl = "https://gemini.google.com/app/74d50a86f5e0c098";
        return addBook(title, author, publisher, yearPublished, genres, defaultImgUrl);
    }

    // Thêm sách mới
    public boolean addBook(String title, String author, String publisher, int yearPublished, List<String> genres, String imgUrl) {
        try {
            String bookId = generateBookId();
            Book book = new Book(bookId, title, author, publisher, yearPublished, genres, imgUrl);
            libraryManager.addBook(book);
            return true;
        } catch (Exception e) {
            
            return false;
        }
    }

    // Mượn sách
    public boolean borrowBook(String userId, String bookId) {
        try {
            System.out.println("DEBUG: Calling LibraryManager.borrowBook()... - LibraryService.java:79"); 
            libraryManager.borrowBook(userId, bookId);
            return true;
        } catch (Exception e) {
            System.err.println("DEBUG: Exception caught in LibraryService: - LibraryService.java:83" + e.getMessage()); 
            e.printStackTrace();
            return false;
        }
    }

    // Trả sách
    public boolean returnBook(String userId, String bookId) {
        try {
            System.out.println("DEBUG: Calling LibraryManager.returnBook()... - LibraryService.java:91");
            libraryManager.returnBook(userId, bookId);
            return true;
        } catch (Exception e) {
            System.err.println("DEBUG: Exception caught in LibraryService: - LibraryService.java:95" + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Kiểm tra sách có không
    private boolean isBookAvailable(String bookId) {
        // Kiểm tra xem có người dùng nào đã mượn sách này không
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
                isBookAvailable(book.getBookId()),
                book.getImgUrl()
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
