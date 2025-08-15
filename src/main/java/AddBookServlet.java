import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

import com.fasterxml.jackson.databind.ObjectMapper;
/**
@WebServlet("/api/books/add")*/
public class AddBookServlet extends HttpServlet {
    private LibraryService libraryService;
    private ObjectMapper objectMapper;

    /**
     * Khởi tạo servlet và phiên bản dịch vụ thư viện.
     */
    @Override
    public void init() throws ServletException {
        libraryService = LibraryService.getInstance();
        objectMapper = new ObjectMapper();
    }

    /**
     * Xử lý yêu cầu POST để thêm sách mới.
     * @param request HttpServletRequest chứa thông tin yêu cầu.
     * @param response HttpServletResponse để gửi phản hồi.
     * @throws ServletException nếu có lỗi trong quá trình xử lý servlet.
     * @throws IOException nếu có lỗi trong quá trình đọc/ghi dữ liệu.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
    
        // THÊM DEBUG REQUEST INFO
        System.out.println("🚀 AddBookServlet.doPost() called! - AddBookServlet.java:27");
        System.out.println("Request URL: - AddBookServlet.java:28" + request.getRequestURL());
        System.out.println("ContentType: - AddBookServlet.java:29" + request.getContentType());
        System.out.println("ContentLength: - AddBookServlet.java:30" + request.getContentLength());
        
        // In ra tất cả parameter
        System.out.println("Parameter names received: - AddBookServlet.java:33");
        java.util.Enumeration<String> paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = paramNames.nextElement();
            System.out.println("" + paramName + ": " + request.getParameter(paramName));
        }
        
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        // Enable CORS
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");

        try {
            // Lấy parameters từ request
            String title = request.getParameter("title");
            String author = request.getParameter("author");
            String publisher = request.getParameter("publisher");
            String yearPublishedStr = request.getParameter("yearPublished");
            String genresStr = request.getParameter("genres");
            String imgUrl = request.getParameter("imgUrl");

            System.out.println("=== SERVLET RECEIVED === - AddBookServlet.java:58");
            System.out.println("title: ' - AddBookServlet.java:59" + title + "' (null? " + (title == null) + ")");
            System.out.println("author: ' - AddBookServlet.java:60" + author + "' (null? " + (author == null) + ")");
            System.out.println("publisher: ' - AddBookServlet.java:61" + publisher + "' (null? " + (publisher == null) + ")");
            System.out.println("yearPublished: ' - AddBookServlet.java:62" + yearPublishedStr + "' (null? " + (yearPublishedStr == null) + ")");
            System.out.println("genres: ' - AddBookServlet.java:63" + genresStr + "' (null? " + (genresStr == null) + ")");
            System.out.println("imgUrl: ' - AddBookServlet.java:64" + imgUrl + "' (null? " + (imgUrl == null) + ")");
            System.out.println("==================== - AddBookServlet.java:65");


            // Kiểm tra input thiếu và không đền, bắt buộc điền tất cả input (trừ URL hình ảnh)
            if (title == null || title.trim().isEmpty() ||
                author == null || author.trim().isEmpty() ||
                publisher == null || publisher.trim().isEmpty() ||
                yearPublishedStr == null || yearPublishedStr.trim().isEmpty() ||
                genresStr == null || genresStr.trim().isEmpty()) {

                System.out.println("❌ VALIDATION FAILED! - AddBookServlet.java:75");
                if (title == null || title.trim().isEmpty()) System.out.println("Title is invalid: ' - AddBookServlet.java:76" + title + "'");
                if (author == null || author.trim().isEmpty()) System.out.println("Author is invalid: ' - AddBookServlet.java:77" + author + "'");
                if (publisher == null || publisher.trim().isEmpty()) System.out.println("Publisher is invalid: ' - AddBookServlet.java:78" + publisher + "'");
                if (yearPublishedStr == null || yearPublishedStr.trim().isEmpty()) System.out.println("Year is invalid: ' - AddBookServlet.java:79" + yearPublishedStr + "'");
                if (genresStr == null || genresStr.trim().isEmpty()) System.out.println("Genres is invalid: ' - AddBookServlet.java:80" + genresStr + "'");

                ApiResponse<Object> errorResponse = ApiResponse.error("Tất cả các trường đều bắt buộc (trừ URL hình ảnh)");
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
                return;
            }

            // Ảnh gốc thêm vào nếu không có ảnh
            if (imgUrl == null || imgUrl.trim().isEmpty()) {
                imgUrl = "https://plpsoft.vn/ckfinder/connector?command=Proxy&lang=vi&type=Files&currentFolder=%2FBaivietIT%2FJava%2F&hash=c245c263ce0eced480effe66bbede6b4d46c15ae&fileName=Plpsoft-Java.jpg";
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

            // Parse genres
            String[] genreArray = genresStr.split(",");
            for (int i = 0; i < genreArray.length; i++) {
                genreArray[i] = genreArray[i].trim();
            }

            // Thêm sách vào kho
            boolean success = libraryService.addBook(title, author, publisher, yearPublished, Arrays.asList(genreArray), imgUrl);
            
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
