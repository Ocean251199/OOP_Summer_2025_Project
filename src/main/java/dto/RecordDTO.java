package dto;

import java.time.LocalDateTime;

public class RecordDTO {
    private String recordId;
    private LocalDateTime timestamp;
    private String userId;
    private String bookId;
    private String action; // Could keep ActionType string here

    // Constructor
    public RecordDTO(String recordId, LocalDateTime timestamp, String userId,
                     String bookId, String action) {
        this.recordId = recordId;
        this.timestamp = timestamp;
        this.userId = userId;
        this.bookId = bookId;
        this.action = action;
    }

    // Getters and setters
    public String getRecordId() { return recordId; }
    public void setRecordId(String recordId) { this.recordId = recordId; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getBookId() { return bookId; }
    public void setBookId(String bookId) { this.bookId = bookId; }

    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }
}
