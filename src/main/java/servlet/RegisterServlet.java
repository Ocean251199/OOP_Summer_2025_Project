package servlet;

import dto.UserDTO;
import service.LibraryService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

@WebServlet("/api/register")
public class RegisterServlet extends HttpServlet {

    private final LibraryService libraryService = LibraryService.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();

        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String confirmPassword = req.getParameter("confirmPassword");

        if (email == null || password == null || confirmPassword == null || !password.equals(confirmPassword)) {
            out.println("{\"success\":false,\"message\":\"Thông tin không hợp lệ hoặc mật khẩu không khớp.\"}");
            return;
        }

        // Create UserDTO with password
        UserDTO userDTO = new UserDTO(
                "USER-" + UUID.randomUUID().toString().substring(0, 8),
                email,
                password,       // <-- include the password here
                "MEMBER",
                null            // borrowedBookIds empty initially
        );

        boolean success = libraryService.registerUser(userDTO);

        if (success) {
            out.println("{\"success\":true,\"message\":\"Đăng ký thành công!\"}");
        } else {
            out.println("{\"success\":false,\"message\":\"Email đã được đăng ký hoặc lỗi hệ thống.\"}");
        }
    }
}
