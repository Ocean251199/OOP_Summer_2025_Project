package service;

import model.*;
import type.ActionType;
import java.util.UUID;
import dao.*;

public class LibraryManager {
    private UserDAO userDAO;
    private BookDAO bookDAO;
    private RecordDAO recordDAO;

    public LibraryManager(UserDAO userDAO, BookDAO bookDAO, RecordDAO recordDAO) {
        this.userDAO = userDAO;
        this.bookDAO = bookDAO;
        this.recordDAO = recordDAO;
    }

    public void registerUser(User user) {
        userDAO.addUser(user);
    }

    public void removeBook(Book book) {
        bookDAO.removeBookFromDB(book.getBookId());
    }

    public void addBook(Book book) {
        bookDAO.addBook(book);
    }

    public void borrowBook(String userId, String bookId) {
        User user = userDAO.getUserById(userId);
        Book book = bookDAO.getBookById(bookId);

        if (user == null || book == null) {
            System.out.println("User or Book not found.");
            return;
        }

        if (user.getBorrowedBookIds().contains(bookId)) {
            System.out.println("Already borrowed.");
            return;
        }

        user.getBorrowedBookIds().add(bookId);
        book.incrementBorrowCount();

        userDAO.updateUser(user);
        bookDAO.updateBook(book);

        model.Record record = new model.Record(generateRecordId(), userId, bookId, ActionType.BORROW);
        recordDAO.addRecord(record);

        System.out.println("Borrow successful: " + record);
    }

    public void returnBook(String userId, String bookId) {
        User user = userDAO.getUserById(userId);
        Book book = bookDAO.getBookById(bookId);

        if (user == null || book == null) {
            System.out.println("User or Book not found.");
            return;
        }

        if (!user.getBorrowedBookIds().contains(bookId)) {
            System.out.println("Not borrowed.");
            return;
        }

        user.getBorrowedBookIds().remove(bookId);

        userDAO.updateUser(user);

        model.Record record = new model.Record(generateRecordId(), userId, bookId, ActionType.RETURN);
        recordDAO.addRecord(record);

        System.out.println("Return successful: " + record);
    }

    public void printAllRecords() {
        for (model.Record r : recordDAO.getAllRecords()) {
            System.out.println(r);
        }
    }

    private String generateRecordId() {
        return "REC-" + UUID.randomUUID().toString().substring(0, 8);
    }
}
