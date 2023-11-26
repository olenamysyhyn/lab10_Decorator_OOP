package ua.edu.ucu.apps;

import lombok.AllArgsConstructor;

import java.io.IOException;
import java.sql.SQLException;

@AllArgsConstructor
public class TimedDocument implements Document {
    public String gcsPath;

    @Override
    public String parse() throws IOException, SQLException {
        String smartDocumentText = measureParsingTime(new SmartDocument(gcsPath));
        String cachedDocumentText = measureParsingTime(new CachedDocument(gcsPath));

        return cachedDocumentText != null ? cachedDocumentText : smartDocumentText;
    }

    private String measureParsingTime(Document document) throws IOException, SQLException {
        long startTime = System.currentTimeMillis();
        String documentText = document.parse();
        long endTime = System.currentTimeMillis();

        String documentType = document instanceof SmartDocument ? "SmartDocument" : "CachedDocument";
        System.out.println("Time of " + documentType + ": " + (endTime - startTime) / 1000.0);

        return documentText;
    }
}
