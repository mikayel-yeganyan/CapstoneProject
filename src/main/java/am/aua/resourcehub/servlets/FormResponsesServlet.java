package am.aua.resourcehub.servlets;

import am.aua.resourcehub.DAO.SheetResourceDAO;
import am.aua.resourcehub.model.Resource;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "Admin Resources", value = "/form-resources")
public class FormResponsesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("adminUser") == null) {
            req.getRequestDispatcher("WEB-INF/admin/adminLogin.jsp").forward(req, resp);
        }
        SheetResourceDAO sheetResource = new SheetResourceDAO();

        String spreadsheetId = "1EJXdbsqOlEV3OUwCJkQli7aYpr79jjE71K18klNaZ_4"; // Link to the spreadsheet populated by the form
        String range = "Form Responses 1!A2:Z"; //the name of the sheet
        List<Resource> resources = new ArrayList<>();

        // a mapping of columns to indices corresponding to our spreadsheet connected to google forms
        Map<String, Integer> formSpreadsheet = new HashMap<>();
        formSpreadsheet.put("title", 1);
        formSpreadsheet.put("developer", 2);
        formSpreadsheet.put("type", 3);
        formSpreadsheet.put("target", 4);
        formSpreadsheet.put("region", 5);
        formSpreadsheet.put("language", 6);
        formSpreadsheet.put("domain", 7);
        formSpreadsheet.put("keywords", 8);
        formSpreadsheet.put("description", 9);
        formSpreadsheet.put("url", 10);
        formSpreadsheet.put("approved", 11);

        try{
            resources = sheetResource.getUnapprovedResourcesFromForm(spreadsheetId, range, formSpreadsheet);
            System.out.println("Back from getResourcesFromSheet, received " + resources.size());

        } catch (Exception e) {
            req.setAttribute("msg", "fetching from the sheet failed " + e.getMessage());
        }

        req.setAttribute("resources", resources);
        req.setAttribute("action", "approve");

        req.getRequestDispatcher("WEB-INF/admin/adminDashboard.jsp").forward(req, resp);
    }
}
