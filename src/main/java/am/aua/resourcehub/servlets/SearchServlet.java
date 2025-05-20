package am.aua.resourcehub.servlets;

import am.aua.resourcehub.DAO.FilterOptionsDAO;
import am.aua.resourcehub.DAO.ResourceDAOImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebServlet(name = "Search", value = "/search-resources" )
public class SearchServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String search = req.getParameter("query");
        //variables used for pagination
        String pageString = req.getParameter("page");
        int    page  = pageString == null || pageString.isEmpty() ? 1 : Math.max(1, Integer.parseInt(req.getParameter("page")));
        int pageSize = 20;
        int offset = (page - 1) * pageSize;

        //get the filtering options from checked by the user
        List<String> filterTypes = req.getParameterValues("types") == null ? null : Arrays.asList(req.getParameterValues("types"));
        List<String> filterDomains = req.getParameterValues("domains") == null ? null : Arrays.asList(req.getParameterValues("domains"));
        List<String> filterTargets = req.getParameterValues("targets") == null ? null : Arrays.asList(req.getParameterValues("targets"));
        List<String> filterLanguages = req.getParameterValues("languages") == null ? null : Arrays.asList(req.getParameterValues("languages"));
        List<String> filterRegions = req.getParameterValues("regions") == null ? null : Arrays.asList(req.getParameterValues("regions"));


        ResourceDAOImpl resourceDAO = new ResourceDAOImpl();
        FilterOptionsDAO filterDAO = new FilterOptionsDAO();

        HttpSession session = req.getSession();
        //populate the filtering options
        session.setAttribute("types", filterDAO.getAllTypes());
        session.setAttribute("domains", filterDAO.getAllDomains());
        session.setAttribute("targets", filterDAO.getAllTargets());
        session.setAttribute("languages", filterDAO.getAllLanguages());
        session.setAttribute("regions", filterDAO.getAllRegions());

        //populate the search results with matching resources
        req.setAttribute("searchResult",
                resourceDAO.search(search, filterTypes, filterTargets, filterRegions, filterDomains, filterLanguages, pageSize, offset));

        //update pagination variables
        int resourceCount = resourceDAO.getFoundResourceCount();
        int noOfPages = (int) Math.ceil(resourceCount * 1.0 / pageSize);
        req.setAttribute("totalResources", resourceCount);
        req.setAttribute("noOfPages", noOfPages);
        req.setAttribute("currentPage", page);

        req.getRequestDispatcher("WEB-INF/view/searchPage.jsp").forward(req, resp);
    }
}
