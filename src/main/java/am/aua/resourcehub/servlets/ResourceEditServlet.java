package am.aua.resourcehub.servlets;

import am.aua.resourcehub.DAO.ResourceDAO;
import am.aua.resourcehub.model.Resource;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/editResource")
public class ResourceEditServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String title = request.getParameter("title");
        String description = request.getParameter("description");

       /* Resource resource = new Resource(id, title, description);
        ResourceDAO dao = new ResourceDAO();
        dao.updateResource(resource);
        response.sendRedirect("adminDashboard.jsp");

        */
    }
}