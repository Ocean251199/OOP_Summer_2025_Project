package service;

import dao.BookDAO;
import dao.UserDAO;
import dao.RecordDAO;
import dto.BookDTO;
import dto.UserDTO;
import dto.RecordDTO;
import model.ActionType;

import java.util.List;
import java.util.Optional;

public class LibraryService {

    private final UserDAO userDAO;
    private final BookDAO bookDAO;
    private final RecordDAO recordDAO;

    public LibraryService(UserDAO userDAO, BookDAO bookDAO, RecordDAO recordDAO) {
        this.userDAO = userDAO;
        this.bookDAO = bookDAO;
        this.recordDAO = recordDAO;
    }

    public boolean registerUser(UserDTO user) {
        return userDAO.insertUser(user) > 0;
    }

    public boolean addBook(BookDTO book) {
        return bookDAO.insertBook(book) > 0;
    }

    public boolean borrowBook(String userId, String bookId) {
        Optional<UserDTO> userOpt = userDAO.getUserById(userId);
        Optional<BookDTO> bookOpt = bookDAO.getBookById(bookId);

        if (userOpt.isEmpty() || bookOpt.isEmpty()) {
            System.out.println("User or Book not found.");
            return false;
        }

        UserDTO user = userOpt.get();
        BookDTO book = bookOpt.get();

        // Check if already borrowed
        if (recordDAO.userHasBorrowedBook(userId, bookId)) {
            System.out.println("User already borrowed this book.");
            return false;
        }

        // Save borrow record
        RecordDTO record = new RecordDTO(null, userId, bookId, ActionType.BORROW);
        recordDAO.insertRecord(record);

        // Increment borrow count in DB
        bookDAO.incrementBorrowCount(bookId);

        return true;
    }

    public boolean returnBook(String userId, String bookId) {
        Optional<UserDTO> userOpt = userDAO.getUserById(userId);
        Optional<BookDTO> bookOpt = bookDAO.getBookById(bookId);

        if (userOpt.isEmpty() || bookOpt.isEmpty()) {
            System.out.println("User or Book not found.");
            return false;
        }

        // Check if they actually borrowed it
        if (!recordDAO.userHasBorrowedBook(userId, bookId)) {
            System.out.println("User hasn't borrowed this book.");
            return false;
        }

        // Save return record
        RecordDTO record = new RecordDTO(null, userId, bookId, ActionType.RETURN);
        recordDAO.insertRecord(record);

        // Optionally: mark book as available again in DB

        return true;
    }

    public List<RecordDTO> getAllRecords() {
        return recordDAO.getAllRecords();
    }
}
