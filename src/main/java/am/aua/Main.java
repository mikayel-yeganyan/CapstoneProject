package am.aua;


import am.aua.resourcehub.DAO.ResourceDAOImpl;
import am.aua.resourcehub.DAO.SheetResourceDAO;
import am.aua.resourcehub.model.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    //This method is used to populate the database once with a custom defined Google Sheets (the service user should be granted a view access)
    //We mainly used it for testing and to ensure consistent data across other machines
    public static void main(String[] args) {

        /*SheetResourceDAO sheetResource = new SheetResourceDAO();

        String spreadsheetId = "1nGqNhOx-BNxReNAuNrQ414Z82UqRSXAiYT4w6CMkpMI"; // Link to manually edited "Resource hub material" spreadsheet
        String range = "Resource hub materials!A4:Z"; //the name of the sheet
        List<Resource> resources = new ArrayList<>();

        Map<String, Integer> manualSpreadSheet = new HashMap<>();
        manualSpreadSheet.put("title", 1);
        manualSpreadSheet.put("type", 2);
        manualSpreadSheet.put("developer", 3);
        manualSpreadSheet.put("target", 4);
        manualSpreadSheet.put("region", 5);
        manualSpreadSheet.put("language", 6);
        manualSpreadSheet.put("domain", 7);
        manualSpreadSheet.put("url", 8);
        manualSpreadSheet.put("keywords", 9);
        manualSpreadSheet.put("description", 10);

        try{
            resources = sheetResource.getResourcesFromSheet(spreadsheetId, range, manualSpreadSheet);
            System.out.println("Back from getResourcesFromSheet, received " + resources.size());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        ResourceDAOImpl dao = new ResourceDAOImpl();
        dao.insertResources(resources);
*/
    }
}