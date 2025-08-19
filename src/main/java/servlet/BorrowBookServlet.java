package servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import dto.ApiResponse;
import service.LibraryService;

@WebServlet("/api/books/borrow")
public class BorrowBookServlet extends HttpServlet {
    private LibraryService libraryService;
    private ObjectMapper objectMapper;

    @Override
    public void init() throws ServletException {
        libraryService = LibraryService.getInstance();
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
            // Get userId from session (must be logged in)
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("userId") == null) {
                ApiResponse<Object> errorResponse = ApiResponse.error("Bạn chưa đăng nhập");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
                return;
            }

            String userId = (String) session.getAttribute("userId");

            // Get bookId from URL-encoded form
            String bookId = request.getParameter("bookId");
            if (bookId == null || bookId.trim().isEmpty()) {
                ApiResponse<Object> errorResponse = ApiResponse.error("Book ID là bắt buộc");
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
                return;
            }

            // Attempt to borrow book
            boolean success = libraryService.borrowBook(userId, bookId);

            if (success) {
                ApiResponse<Object> successResponse = ApiResponse.success("Mượn sách thành công", null);
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write(objectMapper.writeValueAsString(successResponse));
            } else {
                ApiResponse<Object> errorResponse = ApiResponse.error(
                    "Không thể mượn sách. Có thể sách đã được mượn trước đó."
                );
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
}
