package am.aua.resourcehub.servlets;

import am.aua.resourcehub.DAO.AdminDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
            resp.sendRedirect("admin/adminDashboard.jsp");
        } else {
            //authentication failed
            req.setAttribute("errorMessage", "Invalid username or password");
            req.getRequestDispatcher("admin/adminLogin.jsp").forward(req, resp);
        }
    }
}


