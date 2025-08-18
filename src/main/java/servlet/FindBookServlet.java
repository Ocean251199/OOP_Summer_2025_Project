package servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import dto.ApiResponse;
import service.LibraryService;

@WebServlet("/api/books/borrow")
public class FindBookServlet extends HttpServlet {
    private LibraryService libraryService;
    private ObjectMapper objectMapper;

    @Override
    public void init() throws ServletException {
        libraryService = LibraryService.getInstance(); // singleton instance
        objectMapper = new ObjectMapper();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        response.setContentType("application/json; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        // Enable CORS
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");

        try {
            // Parse JSON body
            BorrowRequest borrowRequest = objectMapper.readValue(request.getReader(), BorrowRequest.class);
            String userId = borrowRequest.getUserId();
            String bookId = borrowRequest.getBookId();

            // Validate input
            if (userId == null || userId.trim().isEmpty() ||
                bookId == null || bookId.trim().isEmpty()) {

                ApiResponse<Object> errorResponse = ApiResponse.error("User ID và Book ID là bắt buộc");
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
                return;
            }

            // Default test user
            if ("default".equalsIgnoreCase(userId.trim())) {
                userId = "U001";
            }

            boolean success = libraryService.borrowBook(userId, bookId);

            if (success) {
                ApiResponse<Object> successResponse = ApiResponse.success("Mượn sách thành công", null);
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write(objectMapper.writeValueAsString(successResponse));
            } else {
                ApiResponse<Object> errorResponse = ApiResponse.error("Không thể mượn sách. Có thể sách đã được mượn trước đó.");
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
            }

        } catch (Exception e) {
            ApiResponse<Object> errorResponse = ApiResponse.error("Lỗi server: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
        }
    }

    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setStatus(HttpServletResponse.SC_OK);
    }

    // --- Helper DTO for parsing JSON ---
    public static class BorrowRequest {
        private String userId;
        private String bookId;

        public String getUserId() { return userId; }
        public void setUserId(String userId) { this.userId = userId; }

        public String getBookId() { return bookId; }
        public void setBookId(String bookId) { this.bookId = bookId; }
    }
}
