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
            System.out.println("DEBUG: Calling LibraryManager.borrowBook()... - LibraryService.java:86"); 
            libraryManager.borrowBook(userId, bookId);
            return true;
        } catch (Exception e) {
            System.err.println("DEBUG: Exception caught in LibraryService: - LibraryService.java:90" + e.getMessage()); 
            e.printStackTrace();
            return false;
        }
    }

    // Trả sách
    public boolean returnBook(String userId, String bookId) {
        try {
            System.out.println("DEBUG: Calling LibraryManager.returnBook()... - LibraryService.java:99");
            libraryManager.returnBook(userId, bookId);
            return true;
        } catch (Exception e) {
            System.err.println("DEBUG: Exception caught in LibraryService: - LibraryService.java:103" + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public List<BookDto> searchBooks(String query) {
        if (query == null || query.trim().isEmpty()) {
            return getAllBooks();
        }
        
        String lowercaseQuery = query.toLowerCase();
        
        return libraryManager.getBooks().values().stream()
                .filter(book -> 
                    book.getTitle().toLowerCase().contains(lowercaseQuery) ||
                    book.getAuthor().toLowerCase().contains(lowercaseQuery) ||
                    book.getPublisher().toLowerCase().contains(lowercaseQuery)
                )
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public boolean deleteBook(String bookId) {
    // Kiểm tra xem sách có tồn tại không
        if (!libraryManager.getBooks().containsKey(bookId)) {
            System.err.println("Book not found for deletion: - LibraryService.java:129" + bookId);
            return false;
        }
        // Kiểm tra xem sách có đang được mượn không
        if (!isBookAvailable(bookId)) {
            System.err.println("Cannot delete book: Book is currently borrowed: - LibraryService.java:134" + bookId);
            return false;
        }
        // Xóa sách khỏi LibraryManager
        return libraryManager.deleteBook(bookId);
    }

    // Kiểm tra sách có không
    private boolean isBookAvailable(String bookId) {
        // Kiểm tra xem có người dùng nào đã mượn sách này không
        return libraryManager.getUsers().values().stream()
                .noneMatch(user -> user.getBorrowedBookIds().contains(bookId));
    }

    public boolean registerUser(String email, String password) {
        if (isEmailExists(email)) {
            return false; 
        }

        String userId = generateUserId();
        User newUser = new User(userId, email, password);
        libraryManager.registerUser(newUser);

        return true; // Đăng ký thành công
    }

    //kiểm tra email
    private boolean isEmailExists(String email) {
        return libraryManager.getUsers().values().stream()
                .anyMatch(user -> user.getEmail().equalsIgnoreCase(email));
    }

    // Tạo ID người dùng mới
    private String generateUserId() {
        int nextId = libraryManager.getUsers().size() + 1;
        return "U" + String.format("%03d", nextId);
    }

    public UserDto validateLogin(String email, String password) {
        User user = libraryManager.getUsers().values().stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(email) && u.getPassword().equals(password))
                .findFirst()
                .orElse(null);

        if (user != null) {
            return new UserDto(user.getUserId(), user.getEmail());
        }
        return null; 
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
