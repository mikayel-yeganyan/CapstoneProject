package am.aua.resourcehub.servlets;

import am.aua.resourcehub.DAO.AdminDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "Admin", value = "/admin-login" )
public class AdminLoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        AdminDAO adminDAO = new AdminDAO();
        if (adminDAO.authenticate(username, password)) {
            HttpSession session = req.getSession();
            session.setAttribute("adminUser", username);
            resp.sendRedirect("admin/adminDashboard.jsp");
        } else {
            req.setAttribute("errorMessage", "Invalid username or password");
            req.getRequestDispatcher("admin/adminLogin.jsp").forward(req, resp);
        }
    }

}
