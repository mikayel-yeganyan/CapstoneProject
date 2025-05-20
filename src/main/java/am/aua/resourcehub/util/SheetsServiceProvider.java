package am.aua.resourcehub.util;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

/**
 * Utility class used for providing a service to communicate with Google sheets, uses Google Sheets API
 */
public class SheetsServiceProvider {
    private static final String APPLICATION_NAME = "OTTERS Resource Hub";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

    private static final List<String> SCOPES =
            Collections.singletonList(SheetsScopes.SPREADSHEETS);
    private static final String CREDENTIALS_FILE_PATH = "src/main/resources/credentials.json";


    /**
     * Get a Sheets object to manipulate the Google sheets
     * @return Sheets object
     * @throws GeneralSecurityException if no access to the spreadsheet
     * @throws IOException if cannot read credentials.json
     */
    public static Sheets getSheetsService() throws GeneralSecurityException, IOException {
        InputStream in = SheetsServiceProvider.class.getClassLoader().getResourceAsStream("credentials.json");
        if(in == null) {
            throw new FileNotFoundException("Resource not found: credentials.json");
        }
        GoogleCredentials credentials = GoogleCredentials.fromStream(in)
                .createScoped(SCOPES);

        // Build a new authorized API client service.
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();

        return new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY,
                new HttpCredentialsAdapter(credentials))
                .setApplicationName(APPLICATION_NAME)
                .build();
    }
}
