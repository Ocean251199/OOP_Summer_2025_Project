package model;

import service.LibraryManager;
public class Admin extends User {

    public Admin(String userId, String email, String password) {
        super(userId, email, password);
    }

    @Override
    public String getRole() {
        return "ADMIN";
    }

    public void addBook(Book book, LibraryManager manager) {
        manager.addBook(book);
    }

    public void removeBook(String bookId, LibraryManager manager) {
        manager.removeBook(bookId);
    }

    public void viewBorrowedBooks(LibraryManager manager) {
        System.out.println(manager.getBorrowedBooks());
    }
}
