package service;

import dao.BookDAO;
import dao.UserDAO;
import dao.RecordDAO;
// Chưa có UserDTO và RecordDTO
import dto.BookDto;
import dto.UserDto;
import dto.RecordD;
import type.ActionType;

import java.util.List;
import java.util.Optional;

// Lớp dịch vụ cho hoạt động Thư viện
public class LibraryService {

    private final UserDAO userDAO;
    private final BookDAO bookDAO;
    private final RecordDAO recordDAO;

    // Khởi tạo
    public LibraryService(UserDAO userDAO, BookDAO bookDAO, RecordDAO recordDAO) {
        this.userDAO = userDAO;
        this.bookDAO = bookDAO;
        this.recordDAO = recordDAO;
    }

    // Đăng ký người dùng
    public boolean registerUser(UserDTO user) {
        return userDAO.insertUser(user) > 0;
    }

    // Thêm sách
    public boolean addBook(BookDTO book) {
        return bookDAO.insertBook(book) > 0;
    }

    // Mượn sách
    public boolean borrowBook(String userId, String bookId) {
        Optional<UserDTO> userOpt = userDAO.getUserById(userId);
        Optional<BookDTO> bookOpt = bookDAO.getBookById(bookId);

        if (userOpt.isEmpty() || bookOpt.isEmpty()) {
            System.out.println("User or Book not found.");
            return false;
        }

        UserDTO user = userOpt.get();
        BookDTO book = bookOpt.get();

        // Kiểm tra xem người dùng đã mượn sách này chưa
        if (recordDAO.userHasBorrowedBook(userId, bookId)) {
            System.out.println("User already borrowed this book.");
            return false;
        }

        // Lưu mượn sách
        RecordDTO record = new RecordDTO(null, userId, bookId, ActionType.BORROW);
        recordDAO.insertRecord(record);

        // Tăng số lần mượn trong DB
        bookDAO.incrementBorrowCount(bookId);

        return true;
    }

    // Trả sách
    public boolean returnBook(String userId, String bookId) {
        Optional<UserDTO> userOpt = userDAO.getUserById(userId);
        Optional<BookDTO> bookOpt = bookDAO.getBookById(bookId);

        if (userOpt.isEmpty() || bookOpt.isEmpty()) {
            System.out.println("User or Book not found.");
            return false;
        }

        // Kiểm tra xem người dùng đã mượn sách này chưa
        if (!recordDAO.userHasBorrowedBook(userId, bookId)) {
            System.out.println("User hasn't borrowed this book.");
            return false;
        }

        // Lưu trả sách
        RecordDTO record = new RecordDTO(null, userId, bookId, ActionType.RETURN);
        recordDAO.insertRecord(record);

        // Tùy chọn: đánh dấu sách là có sẵn lại trong DB

        return true;
    }

    // Lấy tất cả các bản ghi
    public List<RecordDTO> getAllRecords() {
        return recordDAO.getAllRecords();
    }
}
