# 🚀 QUICK START GUIDE

## Chạy ngay trong 2 phút!

### Bước 1: Kiểm tra yêu cầu hệ thống
- ✅ Java JDK 17+ đã cài đặt
- ✅ Maven đã cài đặt và có trong PATH

### Bước 2: Chạy ứng dụng
Chọn một trong các cách sau:

**🎯 Cách 1: Dùng script (Dễ nhất)**
1. Double-click file `run-simple.bat`
2. Chọn "y" khi được hỏi
3. Đợi server khởi động

**⚡ Cách 2: Command line (Nhanh nhất)**
```bash
cd "c:\Users\noina\Downloads\OOP_Summer_2025_Project-main (2)\OOP_Summer_2025_Project-main"
mvn clean compile jetty:run
```

### Bước 3: Truy cập ứng dụng
Mở trình duyệt và vào: **http://localhost:8080/btl/**

---

## 🎮 Hướng dẫn sử dụng

### Trang chủ
- Hiển thị 3 tính năng chính của hệ thống
- Click vào từng tính năng để truy cập

### Thêm sách mới
1. Click "Thêm sách" từ trang chủ
2. Điền đầy đủ thông tin sách
3. Click "Thêm Sách"

### Xem sách chưa mượn
1. Click "Sách chưa mượn" từ trang chủ
2. Xem danh sách sách có sẵn
3. Click "Mượn Sách" để mượn

### Xem sách đã mượn
1. Click "Sách đã mượn" từ trang chủ
2. Xem danh sách sách đang được mượn
3. Click "Trả Sách" để trả lại

---

## 🛠️ Khắc phục sự cố

**Nếu gặp lỗi compilation:**
1. Chạy `test-build.bat` để kiểm tra môi trường
2. Đảm bảo Java và Maven đã được cài đặt đúng

**Nếu không thể truy cập web:**
1. Đợi cho đến khi thấy message "Started ServerConnector"
2. Kiểm tra port 8080 có bị chiếm không

**Cần hỗ trợ thêm:**
- Xem file `DEPLOYMENT_GUIDE.md` để có hướng dẫn chi tiết
- Kiểm tra phần Troubleshooting

---

## 🏗️ Kiến trúc hệ thống

```
Frontend (HTML/CSS/JS) 
       ↕️
API Layer (Servlets)    ← ĐÂY LÀ MIDDLEWARE
       ↕️
Service Layer 
       ↕️
Data Layer (Models)
```

**Middleware layer** bao gồm:
- `AddBookServlet.java` - API thêm sách
- `BooksServlet.java` - API lấy danh sách  
- `BorrowBookServlet.java` - API mượn sách
- `ReturnBookServlet.java` - API trả sách

---

🎉 **Chúc bạn sử dụng thành công!**
