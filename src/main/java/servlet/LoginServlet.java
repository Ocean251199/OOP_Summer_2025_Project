package servlet;

import dto.UserDTO;
import service.LibraryService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/api/login")
public class LoginServlet extends HttpServlet {

    private final LibraryService libraryService = LibraryService.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();

        String email = req.getParameter("email");
        String password = req.getParameter("password");

        if (email == null || password == null) {
            out.println("{\"success\":false,\"message\":\"Vui lòng điền đầy đủ thông tin.\"}");
            return;
        }

        // Check user exists
        UserDTO userDTO = libraryService.getAllUsers().stream() // you might want a getUserByEmail() helper later
                .filter(u -> u.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);

        if (userDTO != null && "TEMP_PASSWORD".equals(password)) { // TEMP_PASSWORD placeholder for now
            HttpSession session = req.getSession(true);
            session.setAttribute("userId", userDTO.getUserId());
            out.println("{\"success\":true,\"message\":\"Đăng nhập thành công!\"}");
        } else {
            out.println("{\"success\":false,\"message\":\"Email hoặc mật khẩu không đúng.\"}");
        }
    }
}
