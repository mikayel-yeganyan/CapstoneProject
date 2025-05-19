package am.aua.resourcehub.servlets;

import am.aua.resourcehub.DAO.ResourceDAOImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Remove", value = "/remove-resource" )
public class RemoveResourceServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("adminUser") == null) {
            req.getRequestDispatcher("admin/adminLogin.jsp").forward(req, resp);
        }
        ResourceDAOImpl resourceDAO = new ResourceDAOImpl();
        int id = Integer.parseInt(req.getParameter("resourceId"));

        req.setAttribute("msg", resourceDAO.removeResourceWithId(id));

        req.getRequestDispatcher("admin/adminDashboard.jsp").forward(req, resp);

    }


}
