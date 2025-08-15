import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

// Servlet để xử lý yêu cầu mượn sách
@WebServlet("/api/books/borrow")
public class BorrowBookServlet extends HttpServlet {
    private LibraryService libraryService;
    private ObjectMapper objectMapper;

    // Khởi tạo
    @Override
    public void init() throws ServletException {
        libraryService = LibraryService.getInstance();
        objectMapper = new ObjectMapper();
    }

    // Xử lý yêu cầu POST để mượn sách
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
                System.out.println("DEBUG: Entering BorrowBookServlet.doPost() - BorrowBookServlet.java:25");

        response.setContentType("application/json; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        // Enable CORS
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, String> requestBody = objectMapper.readValue(request.getInputStream(), Map.class);
            String userId = requestBody.get("userId");
            String bookId = requestBody.get("bookId");
    
            System.out.println("DEBUG: Servlet nhận được  userId: ' - BorrowBookServlet.java:41" + userId + "', bookId: '" + bookId + "'");
            // Kiểm tra tham số bắt buộc
            if (userId == null || userId.trim().isEmpty() ||
                bookId == null || bookId.trim().isEmpty()) {

                ApiResponse<Object> errorResponse = ApiResponse.error("User ID và Book ID là bắt buộc");
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
                return;
            }

            if (userId == null || userId.trim().isEmpty()) {
                System.out.println("User ID không được để trống. - BorrowBookServlet.java:55");
            }

            if (bookId == null || bookId.trim().isEmpty()) {
                System.out.println("Book ID không được để trống. - BorrowBookServlet.java:59");
            }
            // Dùng người dùng mặc định nếu không được cung cấp
            if ("default".equals(userId.trim())) {
                userId = "U001";
            }

            // Mượn sách thành công và thất bại
            boolean success = libraryService.borrowBook(userId, bookId);
            
            if (success) {
                ApiResponse<Object> successResponse = ApiResponse.success("Mượn sách thành công", null);
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write(objectMapper.writeValueAsString(successResponse));
            } else {
                ApiResponse<Object> errorResponse = ApiResponse.error("Không thể mượn sách");
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
            }

        } catch (Exception e) {
            ApiResponse<Object> errorResponse = ApiResponse.error("Lỗi server: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
        }
    }

    // Xử lý yêu cầu OPTIONS để hỗ trợ CORS
    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
