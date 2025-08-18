package dto;

public class BookDTO {
    private String bookId;
    private String title;
    private String author;
    private String publisher;
    private int yearPublished;
    private String genres;
    private int borrowCount;
    private String imgUrl;

    // Constructor
    public BookDTO(String bookId, String title, String author, String publisher,
                   int yearPublished, String genres, int borrowCount, String imgUrl) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.yearPublished = yearPublished;
        this.genres = genres;
        this.borrowCount = borrowCount;
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

    public String getGenres() { return genres; }
    public void setGenres(String genres) { this.genres = genres; }

    public int getBorrowCount() { return borrowCount; }
    public void setBorrowCount(int borrowCount) { this.borrowCount = borrowCount; }

    public String getImgUrl() { return imgUrl; }
    public void setImgUrl(String imgUrl) { this.imgUrl = imgUrl; }
}
