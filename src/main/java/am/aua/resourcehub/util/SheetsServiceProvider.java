package am.aua.resourcehub.util;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

public class SheetsServiceProvider {
    private static final String APPLICATION_NAME = "OTTERS Resource Hub";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private static final List<String> SCOPES =
            Collections.singletonList(SheetsScopes.SPREADSHEETS_READONLY);
    private static final String CREDENTIALS_FILE_PATH = "src/main/resources/credentials.json";



    public static Sheets getSheetsService() throws GeneralSecurityException, IOException {
        GoogleCredentials credentials = GoogleCredentials.fromStream(Files.newInputStream(Paths.get(CREDENTIALS_FILE_PATH)))
                .createScoped(Collections.singleton(SheetsScopes.SPREADSHEETS_READONLY));

        // Build a new authorized API client service.
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();

        final String spreadsheetId = "1nGqNhOx-BNxReNAuNrQ414Z82UqRSXAiYT4w6CMkpMI"; // Link to manually edited "Resource hub material" spreadsheet
        final String range = "Resource hub materials";

        return new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY,
                new HttpCredentialsAdapter(credentials))
                .setApplicationName(APPLICATION_NAME)
                .build();
    }
}
