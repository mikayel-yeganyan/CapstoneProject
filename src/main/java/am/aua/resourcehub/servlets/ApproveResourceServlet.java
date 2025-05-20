package am.aua.resourcehub.servlets;

import am.aua.resourcehub.DAO.ResourceDAOImpl;
import am.aua.resourcehub.DAO.SheetResourceDAO;
import am.aua.resourcehub.model.Resource;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "Approve Resource", value = "/approve-resource")
public class ApproveResourceServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("adminUser") == null) {
            req.getRequestDispatcher("admin/adminLogin.jsp").forward(req, resp);
        }
        ResourceDAOImpl resourceDAO = new ResourceDAOImpl();
        SheetResourceDAO sheetResourceDAO = new SheetResourceDAO();

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

        int rowIndex = Integer.parseInt(req.getParameter("rowIndex")); //the row index of the resource to be marked approved
        String sheetName = "Form Responses 1"; // the name of the spreadsheet connected to google forms
        String sheetId = "1EJXdbsqOlEV3OUwCJkQli7aYpr79jjE71K18klNaZ_4"; // the id of the spreadsheet connected to google forms
        Resource resource = null;

        try{
           resource = sheetResourceDAO.getResourceByRowIndex(sheetId, sheetName, formSpreadsheet, rowIndex);
        }catch (Exception e){
            req.setAttribute("msg", "something went wrong during insertion of the resource.");
            e.printStackTrace();
        }

        if (resource != null) {
            resourceDAO.insertResources(Collections.singletonList(resource));
            req.setAttribute("msg", "successfully inserted into the database.");
        }
        else
            req.setAttribute("msg", "something went wrong during insertion of the resource.");

        try {
            sheetResourceDAO.markResourceAsApproved(sheetId, sheetName, rowIndex, formSpreadsheet.get("approved"));
        } catch (GeneralSecurityException e) {
            req.setAttribute("msg", "something went with spreadsheet access.");
            throw new RuntimeException(e);
        }



        req.getRequestDispatcher("admin/adminDashboard.jsp").forward(req, resp);


    }
}
