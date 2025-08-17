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

    public void removeBook(Book book, LibraryManager manager) {
        manager.removeBook(book);
    }

    public void viewBorrowedBooks(Member member) {
        System.out.println(member.getBorrowedBookIds());
    }
}
