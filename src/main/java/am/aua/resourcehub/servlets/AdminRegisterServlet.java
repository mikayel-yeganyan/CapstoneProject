package am.aua.resourcehub.servlets;

import am.aua.resourcehub.DAO.AdminDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/admin-register")
public class AdminRegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        AdminDAO adminDAO = new AdminDAO();
        boolean success = adminDAO.createAdmin(username, password);

        if (success) {
            resp.sendRedirect("admin/adminDashboard.jsp");
        } else {
            req.setAttribute("errorMessage", "Failed to create admin user");
            req.getRequestDispatcher("admin/adminRegister.jsp").forward(req, resp);
        }
    }
}

