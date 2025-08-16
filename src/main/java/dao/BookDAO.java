package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Book;

public class BookDAO {

    // Insert a Book into the database
    public void addBook(Book book) {
        String sql = "INSERT INTO books(bookId, title, author, publisher, yearPublished, genres, borrowCount, imgUrl) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, book.getBookId());
            pstmt.setString(2, book.getTitle());
            pstmt.setString(3, book.getAuthor());
            pstmt.setString(4, book.getPublisher());
            pstmt.setInt(5, book.getYearPublished());
            pstmt.setString(6, String.join(",", book.getGenres())); // store as CSV
            pstmt.setInt(7, book.getBorrowCount());
            pstmt.setString(8, book.getImgUrl());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("publisher"),
                        rs.getInt("yearPublished"),
                        List.of(rs.getString("genres").split(",")),
                        rs.getString("imgUrl")
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
}
