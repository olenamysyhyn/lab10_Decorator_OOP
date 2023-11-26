// package ua.edu.ucu.apps;
// import lombok.AllArgsConstructor;

// import java.io.IOException;
// import java.sql.SQLException;

// @AllArgsConstructor
// public class TimedDocument implements Document {
//     public String gcsPath;
//     @Override
//     public String parse() throws IOException, SQLException {
//         long start_smart = System.currentTimeMillis();
//         String text = new SmartDocument(gcsPath).parse();
//         long end_smart = System.currentTimeMillis();
//         System.out.println("SmartDocument time (s): " + (end_smart - start_smart) / 1000.0);

//         long start_cached = System.currentTimeMillis();
//         new CachedDocument(gcsPath).parse();
//         long end_cached = System.currentTimeMillis();
//         System.out.println("CachedDocument time (s): " + (end_cached - start_cached) / 1000.0);

//         return text;
//     }
// }


// package ua.edu.ucu.apps;

// import lombok.AllArgsConstructor;

// import java.io.IOException;
// import java.sql.SQLException;

// @AllArgsConstructor
// public class TimedDocument implements Document {
//     public String gcsPath;

//     @Override
//     public String parse() throws IOException, SQLException {
//         long smartStartTime = System.currentTimeMillis();
//         String smartDocumentText = new SmartDocument(gcsPath).parse();
//         long smartEndTime = System.currentTimeMillis();
//         System.out.println("Time of SmartDocument : " + (smartEndTime - smartStartTime) / 1000.0);

//         long cachedStartTime = System.currentTimeMillis();
//         String cachedDocumentText = new CachedDocument(gcsPath).parse();
//         long cachedEndTime = System.currentTimeMillis();
//         System.out.println("Time of CachedDocument: " + (cachedEndTime - cachedStartTime) / 1000.0);

//         return cachedDocumentText != null ? cachedDocumentText : smartDocumentText;
//     }
// }
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
