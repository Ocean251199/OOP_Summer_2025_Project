package model;

import dto.BookDTO;
import service.LibraryService;
public class Admin extends User {

    public Admin(String userId, String email, String password) {
        super(userId, email, password);
    }

    @Override
    public String getRole() {
        return "ADMIN";
    }

    public void addBook(BookDTO book, LibraryService manager) {
        manager.addBook(book);
    }

    public void removeUser(String userId, LibraryService manager) {
        manager.removeUser(userId);
    }

    public void removeBook(Book book, LibraryService manager) {
        manager.removeBook(book.getBookId());
    }

    public void viewBorrowedBooks(Member member) {
        System.out.println(member.getBorrowedBookIds());
    }
}
