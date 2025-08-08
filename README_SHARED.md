# 📚 Library Management System - Shared Version

> **Chào bạn!** Đây là dự án hệ thống quản lý thư viện mình vừa làm xong. Bạn có thể chạy thử và xem code nhé! 😊

## 🎯 Quick Start (2 phút)

### 📋 Cần có:
- **Java 17+** ([Download here](https://adoptium.net/)) 
- **Maven** ([Install guide](https://maven.apache.org/install.html))

### 🚀 Chạy ngay:
1. **Extract** file ZIP này
2. **Double-click** `run-simple.bat`  
3. Chọn **"y"** và đợi server khởi động
4. Mở browser: **http://localhost:8080/btl/**

## 🎮 Demo Features:

| Tính năng | Mô tả |
|-----------|-------|
| 📖 **Add Book** | Thêm sách mới với đầy đủ thông tin |
| 📋 **Browse Books** | Xem danh sách sách có sẵn/đã mượn |
| 🔄 **Borrow/Return** | Mượn và trả sách real-time |
| 🔍 **Search & Filter** | Tìm kiếm theo thể loại |

## 🏗️ Tech Stack:
- **Backend**: Java, Servlets, Maven
- **Frontend**: HTML/CSS/JavaScript  
- **Architecture**: 3-tier với middleware API
- **Server**: Jetty (embedded)

## 📖 Tài liệu:
- **`FOR_FRIENDS.md`** - Hướng dẫn chi tiết cho bạn bè
- **`QUICK_START.md`** - Setup nhanh
- **`DEPLOYMENT_GUIDE.md`** - Deploy production

## 🛠️ Troubleshooting:
```bash
# Nếu gặp lỗi, chạy:
test-build.bat

# Hoặc manual:
mvn clean compile jetty:run
```

## 🎨 Want to Hack?
- **Frontend**: Edit `src/main/webapp/*.html`
- **API**: Check `*Servlet.java` files
- **Business Logic**: See `LibraryService.java`
- **Models**: `Book.java`, `User.java`, etc.

---

**🚀 Enjoy exploring! Có questions cứ ping mình nhé!** 

---

*Made with ❤️ for OOP Course (INT2204)*
