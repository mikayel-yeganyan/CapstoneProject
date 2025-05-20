package am.aua.resourcehub.servlets;

import am.aua.resourcehub.DAO.AdminDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "Login", value = "/admin-login")
public class AdminLoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //username and the password to be checked for admin login
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        AdminDAO adminDAO = new AdminDAO();
        if (adminDAO.authenticate(username, password)) {
            HttpSession session = req.getSession();
            //store the admin username in session scope to prevent unauthorized access
            session.setAttribute("adminUser", username);
            //redirect the admin to the dashboard
            req.getRequestDispatcher("WEB-INF/admin/adminDashboard.jsp").forward(req, resp);

        } else {
            //authentication failed
            req.setAttribute("errorMessage", "Invalid username or password");
            req.getRequestDispatcher("WEB-INF/admin/adminLogin.jsp").forward(req, resp);
        }
    }
}


