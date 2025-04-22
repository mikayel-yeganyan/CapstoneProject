package am.aua.resourcehub.servlets;

import am.aua.resourcehub.DAO.ResourceDAOImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Search", value = "/search-resources" )
public class SearchServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String search = req.getParameter("search-box");

        ResourceDAOImpl dao = new ResourceDAOImpl();
        if ( search == null || search.isEmpty() ) {
            req.setAttribute("searchResult", dao.getAllResources());
        }

        req.getRequestDispatcher("WEB-INF/view/searchPage.jsp").forward(req, resp);
    }
}
