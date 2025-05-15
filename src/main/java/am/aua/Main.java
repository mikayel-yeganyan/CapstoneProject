package am.aua;

import am.aua.resourcehub.DAO.ResourceDAO;
import am.aua.resourcehub.DAO.ResourceDAOImpl;
import am.aua.resourcehub.model.Resource;
import am.aua.resourcehub.util.ConnectionFactory;
import am.aua.resourcehub.util.SheetsServiceProvider;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.Sheet;
import com.google.api.services.sheets.v4.model.ValueRange;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        /*ResourceDAOImpl dao = new ResourceDAOImpl();
        List<Resource> resources = dao.search(null, null, null, null, null, null, 20, 0);


        for (Resource resource : resources) {
            System.out.println(resource.getId() + " | " + resource.getTitle() + " | " + resource.getTarget() + " | " + resource.getDomain() + " | " + resource.getType());
        }*/

        final String spreadsheetId = "1nGqNhOx-BNxReNAuNrQ414Z82UqRSXAiYT4w6CMkpMI"; // Link to manually edited "Resource hub material" spreadsheet
        final String range = "Resource hub materials";

        List<List<Object>> rows = null;
        try{
            Sheets service = SheetsServiceProvider.getSheetsService();
            ValueRange response = service.spreadsheets().values().get(spreadsheetId, range).execute();
            rows = response.getValues();
        }catch (Exception e){
            e.printStackTrace();
        }

        System.out.println(rows.get(0));
        System.out.println(rows.get(1));
        System.out.println(rows.get(2));
        System.out.println(rows.get(3));
        System.out.println(rows.get(4));


        List<Resource> resources = new ArrayList<>();

        for(int i = 3; i < rows.size(); i++){
            List<Object> row = rows.get(i);
            Resource r = new Resource();
            r.setTitle(row.get(1).toString().trim());
            r.setType(row.get(2).toString().trim());
            r.setDeveloper(row.get(3).toString().trim());
            r.setTarget(Arrays.asList(row.get(4).toString().split("\\s*,\\s*")));
            System.out.println(r.getTarget());
            r.setRegion(row.get(5).toString().trim());
            r.setLanguage(row.get(6).toString().trim());
            r.setDomain(row.get(7).toString().equalsIgnoreCase("both") ? Arrays.asList("marine", "freshwater") : Arrays.asList(row.get(7).toString().trim()));
            r.setUrl(row.get(8).toString().trim());
            r.setKeywords(Arrays.asList(row.get(9).toString().split("\\s*,\\s*")));
            r.setDescription(row.get(10).toString().trim());

            resources.add(r);
        }




        List<String> target = Arrays.asList("students", "teachers");
        List<String> domain = Collections.singletonList("freshwater");
        Resource resource = new Resource(7777777, "publication", "test", "test", target, "test", "test", target, "test");
        resource.setDescription("test");
        resource.setDomain(domain);

        ResourceDAOImpl dao = new ResourceDAOImpl();
        dao.insertResources(Collections.singletonList(resource));
        dao.insertResources(resources);

    }
}