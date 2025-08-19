package servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import dto.*;
import service.LibraryService;

@WebServlet("/api/books")
public class BooksServlet extends HttpServlet {
    private LibraryService libraryService;
    private ObjectMapper objectMapper;

    @Override
    public void init() throws ServletException {
        libraryService = LibraryService.getInstance();
        objectMapper = new ObjectMapper();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        // Enable CORS
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");

        try {
            HttpSession session = request.getSession(false);
            String type = request.getParameter("type"); // check if "borrowed"
            String sort = request.getParameter("sort");
            String limitParam = request.getParameter("limit");
            int limit = 20;
            if (limitParam != null) {
                try { limit = Integer.parseInt(limitParam); } catch (NumberFormatException ignored) {}
            }

            List<BookDTO> books;

            if ("borrowed".equalsIgnoreCase(type)) {
                // Must be logged in
                if (session == null || session.getAttribute("userId") == null) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().write(objectMapper.writeValueAsString(
                            ApiResponse.error("Bạn chưa đăng nhập")
                    ));
                    return;
                }
                String userId = (String) session.getAttribute("userId");
                books = libraryService.getBorrowedBooksByUser(userId);
            } else {
                // Top books or all books
                books = libraryService.getTopBooks(limit);
            }

            if ("asc".equalsIgnoreCase(sort)) {
                books.sort((b1, b2) -> Integer.compare(b1.getBorrowCount(), b2.getBorrowCount()));
            } else if ("desc".equalsIgnoreCase(sort)) {
                books.sort((b1, b2) -> Integer.compare(b2.getBorrowCount(), b1.getBorrowCount()));
            }

            ApiResponse<List<BookDTO>> successResponse = ApiResponse.success("Danh sách sách", books);
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write(objectMapper.writeValueAsString(successResponse));

        } catch (Exception e) {
            ApiResponse<Object> errorResponse = ApiResponse.error("Lỗi server: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.getWriter().write(objectMapper.writeValueAsString(
                    ApiResponse.error("Bạn chưa đăng nhập")
            ));
            return;
        }

        String userId = (String) session.getAttribute("userId");
        String bookId = request.getParameter("bookId");

        if (bookId == null) {
            response.getWriter().write(objectMapper.writeValueAsString(
                    ApiResponse.error("Book ID missing.")
            ));
            return;
        }

        boolean success = libraryService.borrowBook(userId, bookId);

        if (success) {
            response.getWriter().write(objectMapper.writeValueAsString(
                    ApiResponse.success("Bạn đã mượn sách thành công!", null)
            ));
        } else {
            response.getWriter().write(objectMapper.writeValueAsString(
                    ApiResponse.error("Không thể mượn sách. Kiểm tra User hoặc Book.")
            ));
        }
    }

    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
