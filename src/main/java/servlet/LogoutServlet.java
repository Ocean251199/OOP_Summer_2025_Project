package servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/api/logout")
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();

        // Lấy session hiện tại, không tạo session mới nếu chưa tồn tại
        HttpSession session = req.getSession(false);

        if (session != null) {
            // Hủy session để xóa tất cả các thuộc tính của nó
            session.invalidate();
            out.println("{\"success\":true,\"message\":\"Đăng xuất thành công.\"}");
        } else {
            // Trường hợp session không tồn tại (người dùng chưa đăng nhập)
            out.println("{\"success\":false,\"message\":\"Không có phiên làm việc nào để đăng xuất.\"}");
        }
    }
}