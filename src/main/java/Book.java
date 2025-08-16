import java.util.List;

public class Book {
    private String bookId;
    private String title;
    private String author;
    private String publisher;
    private int yearPublished;
    private List<String> genres;
    private int borrowCount;
    private String imgUrl;

    // Constructor
    public Book(String bookId, String title, String author, String publisher, int yearPublished, List<String> genres, String imgUrl) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.yearPublished = yearPublished;
        this.genres = genres;
        this.borrowCount = 0; // start at 0
        this.imgUrl = imgUrl;
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

    public void incrementBorrowCount() {
        this.borrowCount++;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
    
    // Optional: toString for debugging
    @Override
    public String toString() {
        return "Book{" +
                "bookId='" + bookId + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", publisher='" + publisher + '\'' +
                ", yearPublished=" + yearPublished +
                ", genres=" + genres +
                ", borrowCount=" + borrowCount +
                '}';
    }
    
}