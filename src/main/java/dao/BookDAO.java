package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Book;
import service.DBHelper;

public class BookDAO {

    // Insert a Book into the database
    public void addBook(Book book) {
        String sql = "INSERT INTO books(bookId, isbn, book_title, book_author, publisher, year_of_publication, borrowCount, image_url_m) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, book.getBookId());
            pstmt.setString(2, book.getIsbn());
            pstmt.setString(3, book.getTitle());
            pstmt.setString(4, book.getAuthor());
            pstmt.setString(5, book.getPublisher());
            pstmt.setInt(6, book.getYearPublished());
            pstmt.setInt(7, book.getBorrowCount());
            pstmt.setString(8, book.getImgUrl());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Remove a book by bookId
    public void removeBookFromDB(String bookId) {
        String sql = "DELETE FROM books WHERE bookId = ?";

        try (Connection conn = DBHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, bookId);
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                System.out.println("No book found with ID: " + bookId);
            } else {
                System.out.println("Book with ID " + bookId + " has been removed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Get top N books sorted by borrowCount descending
    public List<Book> getTopBooks(int limit) {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books ORDER BY borrowCount DESC LIMIT ?";

        try (Connection conn = DBHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, limit);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    books.add(new Book(
                            rs.getString("bookId"),
                            rs.getString("isbn"),
                            rs.getString("book_title"),
                            rs.getString("book_author"),
                            rs.getString("publisher"),
                            rs.getInt("year_of_publication"),
                            rs.getInt("borrowCount"),
                            rs.getString("image_url_m")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    // Load all books from the database
    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books";

        try (Connection conn = DBHelper.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                books.add(new Book(
                        rs.getString("bookId"),
                        rs.getString("isbn"),
                        rs.getString("book_title"),
                        rs.getString("book_author"),
                        rs.getString("publisher"),
                        rs.getInt("year_of_publication"),
                        rs.getInt("borrowCount"),
                        rs.getString("image_url_m")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    // Update borrow count
    public void updateBorrowCount(String bookId, int borrowCount) {
        String sql = "UPDATE books SET borrowCount = ? WHERE bookId = ?";

        try (Connection conn = DBHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, borrowCount);
            pstmt.setString(2, bookId);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Get a book by its ID
    public Book getBookById(String bookId) {
        String sql = "SELECT * FROM books WHERE bookId = ?";
        Book book = null;

        try (Connection conn = DBHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, bookId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    book = new Book(
                            rs.getString("bookId"),
                            rs.getString("isbn"),
                            rs.getString("book_title"),
                            rs.getString("book_author"),
                            rs.getString("publisher"),
                            rs.getInt("year_of_publication"),
                            rs.getInt("borrowCount"),
                            rs.getString("image_url_m")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return book; // null if not found
    }

    // Update all fields of a book (except bookId which is the primary key)
    public void updateBook(Book book) {
        String sql = "UPDATE books SET isbn = ?, book_title = ?, book_author = ?, publisher = ?, year_of_publication = ?, borrowCount = ?, image_url_m = ? WHERE bookId = ?";

        try (Connection conn = DBHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, book.getIsbn());
            pstmt.setString(2, book.getTitle());
            pstmt.setString(3, book.getAuthor());
            pstmt.setString(4, book.getPublisher());
            pstmt.setInt(5, book.getYearPublished());
            pstmt.setInt(6, book.getBorrowCount());
            pstmt.setString(7, book.getImgUrl());
            pstmt.setString(8, book.getBookId());

            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated == 0) {
                System.out.println("No book found with ID: " + book.getBookId());
            } else {
                System.out.println("Book with ID " + book.getBookId() + " has been updated.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Book> searchBooks(String term, String filter) {
        List<Book> books = new ArrayList<>();
        String sql;

        switch (filter != null ? filter.toLowerCase() : "all") {
            case "title":
                sql = "SELECT * FROM books WHERE LOWER(book_title) LIKE ?";
                break;
            case "author":
                sql = "SELECT * FROM books WHERE LOWER(book_author) LIKE ?";
                break;
            case "publisher":
                sql = "SELECT * FROM books WHERE LOWER(publisher) LIKE ?";
                break;
            case "all":
            default:
                sql = "SELECT * FROM books WHERE LOWER(book_title) LIKE ? OR LOWER(book_author) LIKE ? OR LOWER(publisher) LIKE ?";
                break;
        }

        try (Connection conn = DBHelper.connect();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            String searchTerm = "%" + term + "%";

            if ("all".equals(filter) || filter == null) {
                stmt.setString(1, searchTerm);
                stmt.setString(2, searchTerm);
                stmt.setString(3, searchTerm);
            } else {
                stmt.setString(1, searchTerm);
            }

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Book book = new Book(
                        rs.getString("bookId"),
                        rs.getString("isbn"),
                        rs.getString("book_title"),
                        rs.getString("book_author"),
                        rs.getString("publisher"),
                        rs.getInt("year_of_publication"),
                        rs.getInt("borrowCount"),
                        rs.getString("image_url_m")
                );
                books.add(book);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return books;
    }
}
