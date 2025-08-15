package dao;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RecordDAO {

    public void addRecord(Record record) {
        String sql = "INSERT INTO records(recordId, timestamp, userId, bookId, action) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, record.getRecordId());
            pstmt.setString(2, record.getTimestamp().toString());
            pstmt.setString(3, record.getUserId());
            pstmt.setString(4, record.getBookId());
            pstmt.setString(5, record.getAction().name());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Record> getAllRecords() {
        List<Record> records = new ArrayList<>();
        String sql = "SELECT * FROM records";

        try (Connection conn = DBHelper.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Record r = new Record(
                        rs.getString("recordId"),
                        rs.getString("userId"),
                        rs.getString("bookId"),
                        ActionType.valueOf(rs.getString("action"))
                );
                r.setTimestamp(LocalDateTime.parse(rs.getString("timestamp")));
                records.add(r);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return records;
    }
}
