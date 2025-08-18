import java.util.Arrays;

import model.Book;

// Kiểm thử thư viện
public class LibTest {

    // Phương thức chính
    public static void main(String[] args) {
        Book book = new Book(
            "B001",
            "The Great Gatsby",
            "F. Scott Fitzgerald",
            "Scribner",
            1925,
            Arrays.asList("Classic", "Fiction"),
            "https://example.com/great_gatsby.jpg"
        );

        System.out.println(book); // Print initial state

        book.incrementBorrowCount();
        book.incrementBorrowCount();

        System.out.println("After borrowing twice: - LibTest.java:20");
        System.out.println(book);
    }
}
