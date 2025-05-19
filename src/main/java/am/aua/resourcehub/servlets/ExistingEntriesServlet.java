package am.aua.resourcehub.servlets;

import am.aua.resourcehub.DAO.FilterOptionsDAO;
import am.aua.resourcehub.DAO.ResourceDAOImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebServlet(name = "Existing", value = "/existing-resources" )
public class ExistingEntriesServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("adminUser") == null) {
            req.getRequestDispatcher("admin/adminLogin.jsp").forward(req, resp);
        }

        ResourceDAOImpl resourceDAO = new ResourceDAOImpl();

        req.setAttribute("resources",
                resourceDAO.getAllResources(-1, -1));
        req.setAttribute("action", "remove");

        req.getRequestDispatcher("admin/adminDashboard.jsp").forward(req, resp);
    }
}
