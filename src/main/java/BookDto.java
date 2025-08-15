import java.util.List;

// Đại diện cho thông tin sách được truyền qua API
public class BookDto {
    private String bookId;
    private String title;
    private String author;
    private String publisher;
    private int yearPublished;
    private List<String> genres;
    private int borrowCount;
    private boolean isAvailable;
    private String imgUrl;

    // Khởi tạo
    public BookDto() {}

    public BookDto(String bookId, String title, String author, String publisher, 
                   int yearPublished, List<String> genres, int borrowCount, 
                   boolean isAvailable, String imgUrl) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.yearPublished = yearPublished;
        this.genres = genres;
        this.borrowCount = borrowCount;
        this.isAvailable = isAvailable;
        this.imgUrl = imgUrl;
    }

    // Getters and setters
    public String getBookId() { return bookId; }
    public void setBookId(String bookId) { this.bookId = bookId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getPublisher() { return publisher; }
    public void setPublisher(String publisher) { this.publisher = publisher; }

    public int getYearPublished() { return yearPublished; }
    public void setYearPublished(int yearPublished) { this.yearPublished = yearPublished; }

    public List<String> getGenres() { return genres; }
    public void setGenres(List<String> genres) { this.genres = genres; }

    public int getBorrowCount() { return borrowCount; }
    public void setBorrowCount(int borrowCount) { this.borrowCount = borrowCount; }

    public boolean isAvailable() { return isAvailable; }
    public void setAvailable(boolean available) { isAvailable = available; }

    public String getImgUrl() { return imgUrl; }
    public void setImgUrl(String imgUrl) { this.imgUrl = imgUrl; }
}
