package servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
        response.setHeader("Access-Control-Allow-Methods", "GET, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");

        try {
            // Get optional parameters
            String sort = request.getParameter("sort"); // "asc" or "desc"
            String limitParam = request.getParameter("limit");
            int limit = 20; // default Top 20
            if (limitParam != null) {
                try { limit = Integer.parseInt(limitParam); } catch (NumberFormatException ignored) {}
            }

            // Fetch top books
            List<BookDTO> books = libraryService.getTopBooks(limit);

            // Apply sorting if requested
            if ("asc".equalsIgnoreCase(sort)) {
                books.sort((b1, b2) -> Integer.compare(b1.getBorrowCount(), b2.getBorrowCount()));
            } else if ("desc".equalsIgnoreCase(sort)) {
                books.sort((b1, b2) -> Integer.compare(b2.getBorrowCount(), b1.getBorrowCount()));
            }

            // Send response
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
    protected void doOptions(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Allow CORS preflight
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
