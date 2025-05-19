package am.aua.resourcehub.servlets;

import am.aua.resourcehub.DAO.ResourceDAO;
import am.aua.resourcehub.DAO.ResourceDAOImpl;
import am.aua.resourcehub.model.Resource;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Edit", value = "/edit-resource")
public class ResourceEditServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Resource resource = new Resource();
        ResourceDAOImpl dao = new ResourceDAOImpl();
        dao.updateResource(resource);
        response.sendRedirect("adminDashboard.jsp");
    }
}