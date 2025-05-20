package am.aua.resourcehub.servlets;

import am.aua.resourcehub.DAO.ResourceDAOImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Remove", value = "/remove-resource" )
public class RemoveResourceServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("adminUser") == null) {
            req.getRequestDispatcher("WEB-INF/admin/adminLogin.jsp").forward(req, resp);
        }
        ResourceDAOImpl resourceDAO = new ResourceDAOImpl();
        int id = Integer.parseInt(req.getParameter("resourceId"));

        req.setAttribute("msg", resourceDAO.removeResourceWithId(id));

        req.getRequestDispatcher("WEB-INF/admin/adminDashboard.jsp").forward(req, resp);

    }


}
