package service;

import dao.*;
import dto.*;
import model.*;
import model.Record;
import type.*;

import java.util.*;
import java.util.stream.Collectors;

public class LibraryService {

    private static volatile LibraryService instance;

    private final UserDAO userDAO;
    private final BookDAO bookDAO;
    private final RecordDAO recordDAO;

    // Private constructor for singleton
    private LibraryService() {
        this.userDAO = new UserDAO();
        this.bookDAO = new BookDAO();
        this.recordDAO = new RecordDAO();
    }

    // Thread-safe singleton accessor
    public static LibraryService getInstance() {
        if (instance == null) {
            synchronized (LibraryService.class) {
                if (instance == null) {
                    instance = new LibraryService();
                }
            }
        }
        return instance;
    }

    // ---------------- User Management ----------------
    public boolean registerUser(UserDTO userDTO) {
        try {
            User user = mapToDomain(userDTO);
            userDAO.addUser(user);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean removeUser(String userId) {
        try {
            userDAO.removeUser(userId);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Optional<UserDTO> getUser(String userId) {
        User user = userDAO.getUserById(userId);
        return user != null ? Optional.of(mapToDTO(user)) : Optional.empty();
    }

    // ---------------- User Listing ----------------
    public List<UserDTO> getAllUsers() {
        List<User> users = userDAO.getAllUsers();
        return users.stream()
                    .map(this::mapToDTO)
                    .collect(Collectors.toList());
    }

    // ---------------- Book Management ----------------
    public boolean addBook(BookDTO bookDTO) {
        try {
            Book book = mapToDomain(bookDTO);
            bookDAO.addBook(book);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean removeBook(String bookId) {
        try {
            bookDAO.removeBookFromDB(bookId);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Optional<BookDTO> getBook(String bookId) {
        Book book = bookDAO.getBookById(bookId);
        return book != null ? Optional.of(mapToDTO(book)) : Optional.empty();
    }

    // ---------------- Book Listing ----------------
    public List<BookDTO> getTopBooks(int limit) {
        return bookDAO.getTopBooks(limit)
                      .stream()
                      .map(this::mapToDTO)
                      .collect(Collectors.toList());
    }

    public List<BookDTO> getAllBooks() {
        List<Book> books = bookDAO.getAllBooks();
        return books.stream()
                    .map(this::mapToDTO)
                    .collect(Collectors.toList());
    }

    // ---------------- Borrow / Return ----------------
    public boolean borrowBook(String userId, String bookId) {
        User user = userDAO.getUserById(userId);
        Book book = bookDAO.getBookById(bookId);

        if (user == null || book == null) {
            System.out.println("User or Book not found.");
            return false;
        }

        if (user.getBorrowedBookIds().contains(bookId)) {
            System.out.println("User already borrowed this book.");
            return false;
        }

        user.getBorrowedBookIds().add(bookId);
        book.incrementBorrowCount();

        userDAO.updateUser(user);
        bookDAO.updateBook(book);

        Record record = new Record(generateRecordId(), userId, bookId, ActionType.BORROW);
        recordDAO.addRecord(record);

        return true;
    }

    public boolean returnBook(String userId, String bookId) {
        User user = userDAO.getUserById(userId);
        Book book = bookDAO.getBookById(bookId);

        if (user == null || book == null) {
            System.out.println("User or Book not found.");
            return false;
        }

        if (!user.getBorrowedBookIds().contains(bookId)) {
            System.out.println("User has not borrowed this book.");
            return false;
        }

        user.getBorrowedBookIds().remove(bookId);
        book.decrementBorrowCount();

        userDAO.updateUser(user);
        bookDAO.updateBook(book);

        Record record = new Record(generateRecordId(), userId, bookId, ActionType.RETURN);
        recordDAO.addRecord(record);

        return true;
    }

    // ---------------- Records ----------------
    public List<RecordDTO> getAllRecords() {
        List<Record> records = recordDAO.getAllRecords();
        return records.stream()
                      .map(this::mapToDTO)
                      .collect(Collectors.toList());
    }

    // ---------------- ID Generation ----------------
    private String generateRecordId() {
        return "REC-" + UUID.randomUUID().toString().substring(0, 8);
    }

    // ---------------- Mapping ----------------
    private UserDTO mapToDTO(User user) {
        return new UserDTO(
                user.getUserId(),
                user.getEmail(),
                user.getRole(),
                new ArrayList<>(user.getBorrowedBookIds())
        );
    }

    private User mapToDomain(UserDTO dto) {
        User user;
        switch (dto.getRole().toUpperCase()) {
            case "ADMIN":
                user = new Admin(dto.getUserId(), dto.getEmail(), "TEMP_PASSWORD");
                break;
            default:
                user = new Member(dto.getUserId(), dto.getEmail(), "TEMP_PASSWORD");
                break;
        }
        if (dto.getBorrowedBookIds() != null) {
            user.getBorrowedBookIds().addAll(dto.getBorrowedBookIds());
        }
        return user;
    }

    private BookDTO mapToDTO(Book book) {
        return new BookDTO(
                book.getBookId(),
                book.getIsbn(),
                book.getTitle(),
                book.getAuthor(),
                book.getPublisher(),
                book.getYearPublished(),
                book.getBorrowCount(),
                book.getImgUrl()
        );
    }

    private Book mapToDomain(BookDTO dto) {
        return new Book(
                dto.getBookId(),
                dto.getIsbn(),
                dto.getTitle(),
                dto.getAuthor(),
                dto.getPublisher(),
                dto.getYearPublished(),
                dto.getBorrowCount(),
                dto.getImgUrl()
        );
    }

    private RecordDTO mapToDTO(Record record) {
        return new RecordDTO(
                record.getRecordId(),
                record.getTimestamp(),
                record.getUserId(),
                record.getBookId(),
                record.getAction().name()
        );
    }

    private Record mapToDomain(RecordDTO dto) {
        return new Record(
                dto.getRecordId(),
                dto.getUserId(),
                dto.getBookId(),
                ActionType.valueOf(dto.getAction())
        );
    }
}
