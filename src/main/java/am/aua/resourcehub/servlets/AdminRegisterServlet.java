package am.aua.resourcehub.servlets;

import am.aua.resourcehub.DAO.AdminDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Admin Register", value = "/admin-register")
public class AdminRegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Disable registration permanently
//        resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Registration is disabled.");

        //username and password of the admin to be registered
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        AdminDAO adminDAO = new AdminDAO();
        boolean success = adminDAO.createAdmin(username, password); // true if the admin was successfully registered

        if (success) {
            req.getRequestDispatcher("WEB-INF/admin/adminLogin.jsp").forward(req, resp);
        } else {
            //something went wrong try again
            req.setAttribute("errorMessage", "Failed to create admin user");
            req.getRequestDispatcher("WEB-INF/admin/adminRegister.jsp").forward(req, resp);
        }

    }
}

