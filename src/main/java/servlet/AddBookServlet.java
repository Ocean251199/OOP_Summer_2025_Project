package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import dto.*;
import service.LibraryService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

public class AddBookServlet extends HttpServlet {

    private LibraryService libraryService;
    private ObjectMapper objectMapper;

    @Override
    public void init() throws ServletException {
        libraryService = LibraryService.getInstance(); // ensure singleton or service instance
        objectMapper = new ObjectMapper();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        // Enable CORS
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");

        try {
            // Read parameters
            String title = request.getParameter("title");
            String author = request.getParameter("author");
            String publisher = request.getParameter("publisher");
            String yearPublishedStr = request.getParameter("yearPublished");
            String imgUrl = request.getParameter("imgUrl");

            // Validate input
            if (title == null || title.trim().isEmpty() ||
                author == null || author.trim().isEmpty() ||
                publisher == null || publisher.trim().isEmpty() ||
                yearPublishedStr == null || yearPublishedStr.trim().isEmpty()) {

                ApiResponse<Object> errorResponse = ApiResponse.error("Tất cả các trường đều bắt buộc (trừ URL hình ảnh)");
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
                return;
            }

            // Set default image if empty
            if (imgUrl == null || imgUrl.trim().isEmpty()) {
                imgUrl = "https://via.placeholder.com/150x200?text=No+Image";
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

            // Generate book ID
            String bookId = "BOOK-" + UUID.randomUUID().toString().substring(0, 8);

            // Create BookDTO
            BookDTO bookDTO = new BookDTO(
                    bookId,
                    "", // ISBN empty if not used
                    title,
                    author,
                    publisher,
                    yearPublished,
                    0,  // borrowCount default 0
                    imgUrl
            );

            // Add book using service
            boolean success = libraryService.addBook(bookDTO);

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
