package service;

import dao.*;
import dto.*;
import model.*;
import model.Record;
import type.*;

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
        model.Record record = new Record(generateRecordId(), userId, bookId, ActionType.RETURN);
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
    private UserDTO mapToDTO(User user) {
        return new UserDTO(
            user.getUserId(),
            user.getEmail(),
            user.getRole(),  // now the 3rd argument
            new ArrayList<>(user.getBorrowedBookIds())  // 4th argument
        );
    }

    private User mapToDomain(UserDTO dto) {
        // Choose subclass based on role
        User user;
        switch (dto.getRole().toUpperCase()) {
            case "ADMIN":
                user = new Admin(dto.getUserId(), dto.getEmail(), "TEMP_PASSWORD");  // default password
                break;
            default:
                user = new Member(dto.getUserId(), dto.getEmail(), "TEMP_PASSWORD");
                break;
        }
        // Copy borrowed books
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
            record.getTimestamp(),         // copy timestamp
            record.getUserId(),
            record.getBookId(),
            record.getAction().name()      // convert enum to String
        );
    }

    private Record mapToDomain(RecordDTO dto) {
        return new Record(
            dto.getRecordId(),
            dto.getUserId(),
            dto.getBookId(),
            ActionType.valueOf(dto.getAction())  // convert String to enum
        );
    }
}
