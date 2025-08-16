package dao;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import type.ActionType;
import model.Record;
import service.DBHelper;

// Lớp DAO cho bản ghi
public class RecordDAO {

    // Thêm bản ghi
    public void addRecord(Record record) {
        String sql = "INSERT INTO records(recordId, timestamp, userId, bookId, action) VALUES (?, ?, ?, ?, ?)";

        // Thực hiện thêm bản ghi
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

    // Lấy tất cả bản ghi
    public List<Record> getAllRecords() {
        List<Record> records = new ArrayList<>();
        String sql = "SELECT * FROM records";

        // Thực hiện truy vấn
        try (Connection conn = DBHelper.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Record r = new Record();
                r.setRecordId(rs.getString("recordId"));
                r.setUserId(rs.getString("userId"));
                r.setBookId(rs.getString("bookId"));
                r.setAction(ActionType.valueOf(rs.getString("action")));
                r.setTimestamp(LocalDateTime.parse(rs.getString("timestamp")));
                records.add(r);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return records;
    }

    // Lấy tất cả bản ghi theo userId
    public List<Record> getRecordsByUserId(String userId) {
        List<Record> records = new ArrayList<>();
        String sql = "SELECT * FROM records WHERE userId = ?";

        // Thực hiện truy vấn
        try (Connection conn = DBHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, userId);
            ResultSet rs = pstmt.executeQuery();

            // Duyệt qua kết quả và thêm vào danh sách
            while (rs.next()) {
                Record r = new Record();
                r.setRecordId(rs.getString("recordId"));
                r.setUserId(rs.getString("userId"));
                r.setBookId(rs.getString("bookId"));
                r.setAction(ActionType.valueOf(rs.getString("action")));
                r.setTimestamp(LocalDateTime.parse(rs.getString("timestamp")));
                records.add(r);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return records;
    }   
}
