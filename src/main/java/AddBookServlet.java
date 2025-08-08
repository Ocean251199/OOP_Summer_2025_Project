import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebServlet("/api/books/add")
public class AddBookServlet extends HttpServlet {
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
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");

        try {
            // Get parameters from request
            String title = request.getParameter("title");
            String author = request.getParameter("author");
            String publisher = request.getParameter("publisher");
            String yearPublishedStr = request.getParameter("yearPublished");
            String genresStr = request.getParameter("genres");
<<<<<<< HEAD
            String imgUrl = request.getParameter("imgUrl"); 
=======
>>>>>>> 55e6436a5a282ac3ad80809b1ba72066886e3305

            // Validate input
            if (title == null || title.trim().isEmpty() ||
                author == null || author.trim().isEmpty() ||
                publisher == null || publisher.trim().isEmpty() ||
                yearPublishedStr == null || yearPublishedStr.trim().isEmpty() ||
                genresStr == null || genresStr.trim().isEmpty()) {
<<<<<<< HEAD

=======
                
>>>>>>> 55e6436a5a282ac3ad80809b1ba72066886e3305
                ApiResponse<Object> errorResponse = ApiResponse.error("Tất cả các trường đều bắt buộc");
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
                return;
            }

            // Parse year
            int yearPublished;
            try {
                yearPublished = Integer.parseInt(yearPublishedStr.trim());
            } catch (NumberFormatException e) {
                ApiResponse<Object> errorResponse = ApiResponse.error("Năm xuất bản không hợp lệ");
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
                return;
            }

            // Parse genres (split by comma)
            String[] genreArray = genresStr.split(",");
            for (int i = 0; i < genreArray.length; i++) {
                genreArray[i] = genreArray[i].trim();
            }
<<<<<<< HEAD
            
            if (imgUrl == null || imgUrl.trim().isEmpty()) {
                imgUrl = "https://via.placeholder.com/150x200?text=No+Image";
            }

            // Add book using service
            boolean success = libraryService.addBook(title, author, publisher, yearPublished, Arrays.asList(genreArray), imgUrl);
=======

            // Add book using service
            boolean success = libraryService.addBook(title, author, publisher, yearPublished, Arrays.asList(genreArray));
>>>>>>> 55e6436a5a282ac3ad80809b1ba72066886e3305
            
            if (success) {
                ApiResponse<Object> successResponse = ApiResponse.success("Thêm sách thành công", null);
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write(objectMapper.writeValueAsString(successResponse));
            } else {
                ApiResponse<Object> errorResponse = ApiResponse.error("Không thể thêm sách");
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
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
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
