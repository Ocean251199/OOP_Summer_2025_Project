package service;

import dao.*;
import dto.*;
import model.*;
import model.Record;
import type.ActionType;

import java.util.*;
import java.util.stream.Collectors;

public class LibraryService {

    private final UserDAO userDAO;
    private final BookDAO bookDAO;
    private final RecordDAO recordDAO;

    public LibraryService(UserDAO userDAO, BookDAO bookDAO, RecordDAO recordDAO) {
        this.userDAO = userDAO;
        this.bookDAO = bookDAO;
        this.recordDAO = recordDAO;
    }

    // ---------------- User Management ----------------
    public boolean registerUser(UserDTO userDTO) {
        try {
            User user = mapToDomain(userDTO);
            userDAO.addUser(user);  // assume void
            return true;            // success if no exception
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean removeUser(String userId) {
        try {
            userDAO.removeUser(userId); // assume void
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Optional<UserDTO> getUser(String userId) {
        User user = userDAO.getUserById(userId); // returns User or null
        return user != null ? Optional.of(mapToDTO(user)) : Optional.empty();
    }

    // ---------------- Book Management ----------------
    public boolean addBook(BookDTO bookDTO) {
        try {
            Book book = mapToDomain(bookDTO);
            bookDAO.addBook(book); // assume void
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean removeBook(String bookId) {
        try {
            bookDAO.removeBookFromDB(bookId); // assume void
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

        // Update domain objects
        user.getBorrowedBookIds().add(bookId);
        book.incrementBorrowCount();

        // Persist changes
        userDAO.updateUser(user);
        bookDAO.updateBook(book);

        // Record the action
        model.Record record = new model.Record(generateRecordId(), userId, bookId, ActionType.BORROW);
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

        // Update domain objects
        user.getBorrowedBookIds().remove(bookId);

        // Persist changes
        userDAO.updateUser(user);

        // Record the action
        Record record = new Record(generateRecordId(), userId, bookId, ActionType.RETURN);
        recordDAO.addRecord(record);

        return true;
    }

    // ---------------- Records ----------------
    public List<RecordDTO> getAllRecords() {
    List<Record> records = recordDAO.getAllRecords(); // List<Record>
    return records.stream()
                  .map(this::mapToDTO)      // Record → RecordDTO
                  .collect(Collectors.toList());
    }

    // ---------------- ID Generation ----------------
    private String generateRecordId() {
        return "REC-" + UUID.randomUUID().toString().substring(0, 8);
    }

    // ---------------- Mapping ----------------
    private User mapToDomain(UserDTO dto) {
        User user = new User(dto.getUserId(), dto.getName());
        user.getBorrowedBookIds().addAll(dto.getBorrowedBookIds());
        return user;
    }

    private UserDTO mapToDTO(User user) {
        return new UserDTO(user.getUserId(), user.getName(), user.getBorrowedBookIds());
    }

    private Book mapToDomain(BookDTO dto) {
        Book book = new Book(dto.getBookId(), dto.getTitle(), dto.getAuthor());
        book.setBorrowCount(dto.getBorrowCount());
        return book;
    }

    private BookDTO mapToDTO(Book book) {
        return new BookDTO(book.getBookId(), book.getTitle(), book.getAuthor(), book.getBorrowCount());
    }

    private RecordDTO mapToDTO(Record record) {
        return new RecordDTO(record.getRecordId(), record.getUserId(), record.getBookId(), record.getAction());
    }
}
