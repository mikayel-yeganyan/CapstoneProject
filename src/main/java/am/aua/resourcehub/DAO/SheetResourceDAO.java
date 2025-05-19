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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SheetResourceDAO {

    public SheetResourceDAO() { }

    public List<Resource> getUnapprovedResourcesFromForm(String spreadsheetId, String range, Map<String, Integer> headerIndices) throws IllegalArgumentException, GeneralSecurityException, IOException{
        List<String> requiredHeaders = Arrays.asList("title", "type", "developer", "target", "region", "language", "domain", "url", "keywords", "description", "approved");
        for (String header : requiredHeaders) {
            if (!headerIndices.containsKey(header)) {
                throw new IllegalArgumentException("Missing required column: " + header);
            }
        }
        List<List<Object>> rows = null;
        Sheets service = SheetsServiceProvider.getSheetsService();
        ValueRange response = service.spreadsheets().values().get(spreadsheetId, range).execute();
        rows = response.getValues();

        Integer approvedIndex = headerIndices.get("approved");
        int startingIndex = Integer.parseInt(String.valueOf(range.charAt(range.indexOf('!') + 2)));
        List<Resource> resources = new ArrayList<>();
        if (rows != null && !rows.isEmpty()){
            for (int i = 0; i < rows.size(); i++) {
                List<Object> row = rows.get(i);
                int rowIndex = i + startingIndex;
                String approvedValue = (String) row.get(approvedIndex);

                if (!approvedValue.isEmpty() && !approvedValue.equalsIgnoreCase("false"))
                    continue;

                Resource r = mapSheetRowToResource(headerIndices, row);
                if (r != null) {
                    r.setSheetRowIndex(rowIndex);
                    resources.add(r);
                }
            }

            System.out.println("Inside getResourcesFromSheet, will return " + resources.size() + " items");
        }

        return resources;
    }

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
                Resource r = mapSheetRowToResource(headerIndices, row);
                if (r != null)
                    resources.add(r);
            }

            System.out.println("Inside getResourcesFromSheet, will return " + resources.size() + " items");
        }

        return resources;
    }
    private Resource mapSheetRowToResource(Map<String, Integer> headerIndices, List<Object> row) {
        Resource r = new Resource();

        if (row.size() <= Collections.max(headerIndices.values())) return null;

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

        return r;
    }

    public void markResourceAsApproved(String spreadsheetId, String sheetName, int rowIndex, int approvedColIndex)
            throws GeneralSecurityException, IOException {

        Sheets service = SheetsServiceProvider.getSheetsService();

        // Convert column index (0-based) to letter (e.g., 7 -> "H")
        String colLetter = columnIndexToLetter(approvedColIndex);
        String cell = sheetName + "!" + colLetter + rowIndex;  // e.g., "Sheet1!H5"

        ValueRange body = new ValueRange()
                .setValues(Collections.singletonList(Collections.singletonList("true")));

        service.spreadsheets().values()
                .update(spreadsheetId, cell, body)
                .setValueInputOption("RAW")
                .execute();

        System.out.println("Marked row " + rowIndex + " as approved.");
    }

    private String columnIndexToLetter(int index) {
        StringBuilder result = new StringBuilder();
        while (index >= 0) {
            result.insert(0, (char) ('A' + (index % 26)));
            index = (index / 26) - 1;
        }
        return result.toString();
    }

    public Resource getResourceByRowIndex(String spreadsheetId,  String sheetName, Map<String, Integer> headerIndices, int rowIndex) throws GeneralSecurityException, IOException {
        Sheets service = SheetsServiceProvider.getSheetsService();

        String rowRange = sheetName + "!A" + rowIndex + ":Z" + rowIndex;
        List<List<Object>> values = service.spreadsheets().values()
                .get(spreadsheetId, rowRange)
                .execute().getValues();

        if (values == null || values.isEmpty()) return null;

        return mapSheetRowToResource(headerIndices, values.get(0));
    }
}

