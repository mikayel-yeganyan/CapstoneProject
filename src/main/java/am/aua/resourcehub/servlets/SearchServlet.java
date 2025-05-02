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

@WebServlet(name = "Search", value = "/search-resources" )
public class SearchServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String search = req.getParameter("search-box");

        ResourceDAOImpl resourceDAO = new ResourceDAOImpl();
        FilterOptionsDAO filterDAO = new FilterOptionsDAO();

        if ( search == null || search.isEmpty() ) {
            //populate the search results
            req.setAttribute("searchResult", resourceDAO.getAllResources());

            HttpSession session = req.getSession();
            //populate the filtering options
            session.setAttribute("types", filterDAO.getAllTypes());
            session.setAttribute("domains", filterDAO.getAllDomains());
            session.setAttribute("targets", filterDAO.getAllTargets());
            session.setAttribute("languages", filterDAO.getAllLanguages());
        }

        req.getRequestDispatcher("WEB-INF/view/searchPage.jsp").forward(req, resp);
    }
}
