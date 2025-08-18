<<<<<<< Updated upstream
=======
<<<<<<< Updated upstream
<<<<<<< Updated upstream
=======
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes
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
<<<<<<< Updated upstream
<<<<<<< Updated upstream
        // Use singleton instance of LibraryService
=======
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes
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
<<<<<<< Updated upstream
<<<<<<< Updated upstream
            String sort = request.getParameter("sort"); // optional, e.g., "asc" or "desc"
            List<BookDTO> books = libraryService.getAllBooks(); // get all books

            // Sort by borrowCount if requested
=======
=======
>>>>>>> Stashed changes
            String sort = request.getParameter("sort"); // "asc" or "desc"
            String limitParam = request.getParameter("limit");
            int limit = 20;
            if (limitParam != null) {
                try { limit = Integer.parseInt(limitParam); } catch (NumberFormatException ignored) {}
            }

            List<BookDTO> books = libraryService.getTopBooks(limit);

<<<<<<< Updated upstream
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes
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
<<<<<<< Updated upstream
<<<<<<< Updated upstream
    
    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Allow CORS preflight
=======
=======
>>>>>>> Stashed changes

    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
<<<<<<< Updated upstream
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
<<<<<<< Updated upstream
<<<<<<< Updated upstream
=======
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes
>>>>>>> Stashed changes
