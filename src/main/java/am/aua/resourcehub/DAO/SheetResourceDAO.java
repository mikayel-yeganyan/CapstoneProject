package am.aua.resourcehub.DAO;

import am.aua.resourcehub.model.Resource;
import am.aua.resourcehub.util.SheetsServiceProvider;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class SheetResourceDAO {

    public SheetResourceDAO() { }

    //returns resources from the remote Google Sheets spreadsheet given the spreadsheet id, value range and a map of column names with their indices in a row
    //throws an exception if unable to access the Google Sheets

    public List<Resource> getResourcesFromSheet(String spreadsheetId, String range, Map<String, Integer> headerIndices) throws IllegalArgumentException, GeneralSecurityException, IOException {

        List<String> requiredHeaders = Arrays.asList("title", "type", "developer", "target", "region", "language", "domain", "url", "keywords", "description");
        for (String header : requiredHeaders) {
            if (!headerIndices.containsKey(header)) {
                throw new IllegalArgumentException("Missing required column: " + header);
            }
        }
        List<List<Object>> rows = null;
        Sheets service = SheetsServiceProvider.getSheetsService();
        ValueRange response = service.spreadsheets().values().get(spreadsheetId, range).execute();
        rows = response.getValues();

        List<Resource> resources = new ArrayList<>();
        if (rows != null && !rows.isEmpty()){

            for (List<Object> row : rows) {
                Resource r = new Resource();

                if (row.size() <= Collections.max(headerIndices.values())) continue; // Skip incomplete row

                r.setTitle(row.get(headerIndices.get("title")).toString().trim());
                r.setType(row.get(headerIndices.get("type")).toString().trim());
                r.setDeveloper(row.get(headerIndices.get("developer")).toString().trim());
                r.setTarget(Arrays.asList(row.get(headerIndices.get("target")).toString().split("\\s*,\\s*")));
                r.setRegion(row.get(headerIndices.get("region")).toString().trim());
                r.setLanguage(row.get(headerIndices.get("language")).toString().trim());
                r.setDomain(row.get(headerIndices.get("domain")).toString().equalsIgnoreCase("both") ? Arrays.asList("marine", "freshwater") : Collections.singletonList(row.get(headerIndices.get("domain")).toString().trim()));
                r.setUrl(row.get(headerIndices.get("url")).toString().trim());
                r.setKeywords(Arrays.asList(row.get(headerIndices.get("keywords")).toString().split("\\s*,\\s*")));
                r.setDescription(row.get(headerIndices.get("description")).toString().trim());

                resources.add(r);

            }

            System.out.println("Inside getResourcesFromSheet, will return " + resources.size() + " items");
        }

        return resources;
    }
}
