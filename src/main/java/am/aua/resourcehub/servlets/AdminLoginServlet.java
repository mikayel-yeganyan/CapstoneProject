package am.aua.resourcehub.servlets;

import am.aua.resourcehub.DAO.AdminDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.*;
import java.io.IOException;

    @WebServlet("/adminLogin")
    public class AdminLoginServlet extends HttpServlet {
        protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
            String username = request.getParameter("username");
            String password = request.getParameter("pass");

            AdminDAO adminDAO = new AdminDAO();
            if (adminDAO.validate(username, password)) {
                HttpSession session = request.getSession();
                session.setAttribute("adminUser", username);
                response.sendRedirect("adminDashboard.jsp");
            } else {
                request.setAttribute("errorMessage", "Invalid credentials");
                request.getRequestDispatcher("adminLogin.jsp").forward(request, response);
            }
        }
    }

