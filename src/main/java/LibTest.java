import java.util.Arrays;

public class LibTest {
    public static void main(String[] args) {
        Book book = new Book(
            "B001",
            "The Great Gatsby",
            "F. Scott Fitzgerald",
            "Scribner",
            1925,
            Arrays.asList("Classic", "Fiction")
        );

        System.out.println(book); // Print initial state

        book.incrementBorrowCount();
        book.incrementBorrowCount();

        System.out.println("After borrowing twice:");
        System.out.println(book);
    }
}
