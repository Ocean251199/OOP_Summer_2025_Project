import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebServlet("/api/users/register")
public class RegisterUserServlet extends HttpServlet {
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
            Map<String, String> requestBody = objectMapper.readValue(request.getInputStream(), Map.class);
            String email = requestBody.get("email");
            String password = requestBody.get("password");

            if (email == null || email.trim().isEmpty() || password == null || password.trim().isEmpty()) {
                ApiResponse<Object> errorResponse = ApiResponse.error("Email và mật khẩu không được để trống.");
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
                return;
            }

            boolean success = libraryService.registerUser(email, password);

            if (success) {
                ApiResponse<Object> successResponse = ApiResponse.success("Đăng ký thành công", null);
                response.setStatus(HttpServletResponse.SC_CREATED);
                response.getWriter().write(objectMapper.writeValueAsString(successResponse));
            } else {
                ApiResponse<Object> errorResponse = ApiResponse.error("Email đã tồn tại.");
                response.setStatus(HttpServletResponse.SC_CONFLICT);
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