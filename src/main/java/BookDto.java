import java.util.List;

public class BookDto {
    private String bookId;
    private String title;
    private String author;
    private String publisher;
    private int yearPublished;
    private List<String> genres;
    private int borrowCount;
    private boolean isAvailable;
<<<<<<< HEAD
    private String imgUrl;
=======
>>>>>>> 55e6436a5a282ac3ad80809b1ba72066886e3305

    // Constructors
    public BookDto() {}

    public BookDto(String bookId, String title, String author, String publisher, 
<<<<<<< HEAD
                   int yearPublished, List<String> genres, int borrowCount, 
                   boolean isAvailable, String imgUrl) {
=======
                   int yearPublished, List<String> genres, int borrowCount, boolean isAvailable) {
>>>>>>> 55e6436a5a282ac3ad80809b1ba72066886e3305
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.yearPublished = yearPublished;
        this.genres = genres;
        this.borrowCount = borrowCount;
        this.isAvailable = isAvailable;
<<<<<<< HEAD
        this.imgUrl = imgUrl;
=======
>>>>>>> 55e6436a5a282ac3ad80809b1ba72066886e3305
    }

    // Getters and Setters
    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getYearPublished() {
        return yearPublished;
    }

    public void setYearPublished(int yearPublished) {
        this.yearPublished = yearPublished;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public int getBorrowCount() {
        return borrowCount;
    }

    public void setBorrowCount(int borrowCount) {
        this.borrowCount = borrowCount;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
<<<<<<< HEAD

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
=======
>>>>>>> 55e6436a5a282ac3ad80809b1ba72066886e3305
}
