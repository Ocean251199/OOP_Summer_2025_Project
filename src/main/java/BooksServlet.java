import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

// Servlet để xử lý các yêu cầu liên quan đến sách
@WebServlet("/api/books")
public class BooksServlet extends HttpServlet {
    private LibraryService libraryService;
    private ObjectMapper objectMapper;

    // Khởi tạo
    @Override
    public void init() throws ServletException {
        libraryService = LibraryService.getInstance();
        objectMapper = new ObjectMapper();
    }

    // Xử lý yêu cầu GET để lấy danh sách sách
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("application/json; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        // Enable CORS
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");

        // Lấy tham số từ yêu cầu
        try {
            String type = request.getParameter("type");
            List<BookDto> books;

            if ("available".equals(type)) {
                books = libraryService.getAvailableBooks();
            } else if ("borrowed".equals(type)) {
                books = libraryService.getBorrowedBooks();
            } else {
                books = libraryService.getAllBooks();
            }

            ApiResponse<List<BookDto>> successResponse = ApiResponse.success(books);
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write(objectMapper.writeValueAsString(successResponse));

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
        response.setHeader("Access-Control-Allow-Methods", "GET, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
