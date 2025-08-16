package model;
import java.time.LocalDateTime;

import type.ActionType;

public class Record {
    private String recordId;
    private LocalDateTime timestamp;
    private String userId;
    private String bookId;
    private ActionType action;

    // Constructor
    public Record(String recordId, String userId, String bookId, ActionType action) {
        this.recordId = recordId;
        this.timestamp = LocalDateTime.now();  // record when it happens
        this.userId = userId;
        this.bookId = bookId;
        this.action = action;
    }

    // Getters and Setters
    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public ActionType getAction() {
        return action;
    }

    public void setAction(ActionType action) {
        this.action = action;
    }

    public boolean isReturned() {
        return this.action == ActionType.RETURN;
    }

    // Optional toString
    @Override
    public String toString() {
        return "Record{" +
                "recordId='" + recordId + '\'' +
                ", timestamp=" + timestamp +
                ", userId='" + userId + '\'' +
                ", bookId='" + bookId + '\'' +
                ", action=" + action +
                '}';
    }
}
