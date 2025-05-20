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

/**
 * DAO layer used for manipulating resources in Google Spreadsheet using Google Sheets API
 */
public class SheetResourceDAO {

    public SheetResourceDAO() { }

    /**
     * Gets all the resources marked Approved = false from the Google Sheet
     * @param spreadsheetId Google Spreadsheet id
     * @param range Range of rows and columns including the sheet name
     * @param headerIndices A mapping of column names to their indices in the spreadsheet
     * @return A list of unapproved resources populated from the spreadsheet
     * @throws IllegalArgumentException If mapping of columns is wrong
     * @throws GeneralSecurityException If service account couldn't access the given spreadsheet
     * @throws IOException If problems reading the credentials.json file
     */
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

    /**
     * Gets all the resources from the Google Sheet
     * @param spreadsheetId Google Spreadsheet id
     * @param range Range of rows and columns including the sheet name
     * @param headerIndices A mapping of column names to their indices in the spreadsheet
     * @return A list of resources populated from the spreadsheet
     * @throws IllegalArgumentException If mapping of columns is wrong
     * @throws GeneralSecurityException If service account couldn't access the given spreadsheet
     * @throws IOException If problems reading the credentials.json file
     */
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

    /**
     * Utility method for mapping a single row of a spreadsheet to actual resource object
     * @param headerIndices A mapping of column names to their indices in the spreadsheet
     * @param row The single row to be mapped to a single Resource
     * @return The new Resource
     */
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

    /**
     * Marks the given resource as approved in the given spreadsheet
     * @param spreadsheetId The id of the spreadsheet to be updated
     * @param sheetName the name of the sheet
     * @param rowIndex row index of the resource to be approved
     * @param approvedColIndex the column index of the approved field
     * @throws GeneralSecurityException If problems accessing the spreadsheet
     * @throws IOException If problems reading the credentials.json
     */
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

    /**
     * Returns the letter column of the given coulumn's index (e.g. 3->C)
     * @param index the index of the column starting from 0
     * @return the letter corresponding to the given index in the sheets
     */
    private String columnIndexToLetter(int index) {
        StringBuilder result = new StringBuilder();
        while (index >= 0) {
            result.insert(0, (char) ('A' + (index % 26)));
            index = (index / 26) - 1;
        }
        return result.toString();
    }

    /**
     * Get a single resource given the row index of the resource in a spreadsheet
     * @param spreadsheetId the spreadsheet id to be accessed
     * @param sheetName the name of the sheet
     * @param headerIndices mapping of headers to indices
     * @param rowIndex the index row index of the resource to be accessed
     * @return a constructed Resource object
     * @throws GeneralSecurityException If problems accessing the spreadsheet
     * @throws IOException If problems reading the credentials.json
     */
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

