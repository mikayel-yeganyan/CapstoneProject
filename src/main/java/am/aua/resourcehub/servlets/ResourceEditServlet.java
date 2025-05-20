package am.aua.resourcehub.servlets;

import am.aua.resourcehub.DAO.ResourceDAO;
import am.aua.resourcehub.DAO.ResourceDAOImpl;
import am.aua.resourcehub.model.Resource;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Edit", value = "/edit-resource")
public class ResourceEditServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Resource resource = new Resource();
        ResourceDAOImpl dao = new ResourceDAOImpl();
        dao.updateResource(resource);
        request.getRequestDispatcher("WEB-INF/admin/adminDashboard.jsp").forward(request, response);

    }
}