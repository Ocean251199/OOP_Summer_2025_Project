# Library Management System

Hệ thống quản lý thư viện với Java backend và HTML/JavaScript frontend.

## Tính năng

- **Thêm sách mới**: Thêm sách với thông tin chi tiết
- **Xem sách chưa mượn**: Hiển thị danh sách sách có sẵn để mượn
- **Xem sách đã mượn**: Hiển thị danh sách sách đang được mượn
- **Mượn sách**: Mượn sách từ thư viện
- **Trả sách**: Trả sách về thư viện

## Cấu trúc dự án

```
├── src/main/java/
│   ├── Book.java                 # Model cho sách
│   ├── User.java                 # Model cho người dùng
│   ├── Record.java               # Model cho bản ghi mượn/trả
│   ├── ActionType.java           # Enum cho loại hành động
│   ├── LibraryManager.java       # Class quản lý chính
│   ├── LibraryService.java       # Business Logic Layer  
│   ├── BookDto.java              # Data Transfer Object
│   ├── ApiResponse.java          # API Response Wrapper
│   └── 🔌 MIDDLEWARE SERVLETS:
│       ├── AddBookServlet.java     # POST /api/books/add
│       ├── BooksServlet.java       # GET  /api/books
│       ├── BorrowBookServlet.java  # POST /api/books/borrow
│       └── ReturnBookServlet.java  # POST /api/books/return
├── src/main/webapp/              # Frontend files
│   ├── Index.html               # Trang chủ
│   ├── AddBook.html             # Trang thêm sách
│   ├── AvailableBooks.html      # Trang sách chưa mượn
│   ├── BorrowedBooks.html       # Trang sách đã mượn
│   └── WEB-INF/web.xml          # Cấu hình servlet
├── pom.xml                      # Maven configuration
├── run-simple.bat               # Script chạy nhanh
└── DEPLOYMENT_GUIDE.md          # Hướng dẫn deploy
```

## API Endpoints

### 1. Thêm sách
- **URL**: `/api/books/add`
- **Method**: POST
- **Parameters**: 
  - `title`: Tiêu đề sách
  - `author`: Tác giả
  - `publisher`: Nhà xuất bản
  - `yearPublished`: Năm xuất bản
  - `genres`: Thể loại (cách nhau bởi dấu phẩy)

### 2. Lấy danh sách sách
- **URL**: `/api/books`
- **Method**: GET
- **Parameters**: 
  - `type`: `available` (sách chưa mượn), `borrowed` (sách đã mượn), hoặc để trống (tất cả)

### 3. Mượn sách
- **URL**: `/api/books/borrow`
- **Method**: POST
- **Parameters**: 
  - `userId`: ID người dùng (dùng "default" cho user test)
  - `bookId`: ID sách cần mượn

### 4. Trả sách
- **URL**: `/api/books/return`
- **Method**: POST
- **Parameters**: 
  - `userId`: ID người dùng (dùng "default" cho user test)
  - `bookId`: ID sách cần trả

## Cách chạy

### 🚀 Cách nhanh nhất:

**Option 1: Dùng script**
- Double-click file `run-simple.bat` và chọn "y"

**Option 2: Chạy thủ công**
1. Mở Command Prompt hoặc PowerShell
2. Copy và paste lệnh sau:
   ```bash
   cd "c:\Users\noina\Downloads\OOP_Summer_2025_Project-main (2)\OOP_Summer_2025_Project-main"
   mvn clean compile jetty:run
   ```
3. Truy cập: http://localhost:8080/btl/

### 🔧 Nếu gặp lỗi:
1. Chạy `test-build.bat` để kiểm tra môi trường
2. Xem file `DEPLOYMENT_GUIDE.md` để có hướng dẫn chi tiết

### 🏗️ Deploy trên Tomcat:
1. Build WAR file: `mvn clean compile war:war`
2. Copy `target/btl.war` vào thư mục `webapps` của Tomcat
3. Khởi động Tomcat và truy cập: http://localhost:8080/btl/

## Công nghệ sử dụng

- **Backend**: Java, Servlet API, Jackson (JSON processing)
- **Frontend**: HTML, CSS, JavaScript
- **Build Tool**: Maven
- **Server**: Apache Tomcat

## Kiến trúc hệ thống

Hệ thống được thiết kế theo mô hình 3 tầng:

1. **Presentation Layer** (Frontend): HTML/CSS/JavaScript
2. **Business Logic Layer** (Service): LibraryService.java
3. **Data Access Layer** (Model): LibraryManager.java, Book.java, User.java, Record.java

**API Layer** (Servlet) đóng vai trò là **middleware** kết nối giữa frontend và backend, xử lý:
- HTTP requests/responses
- Data validation
- JSON serialization/deserialization
- CORS handling
- Error handling
