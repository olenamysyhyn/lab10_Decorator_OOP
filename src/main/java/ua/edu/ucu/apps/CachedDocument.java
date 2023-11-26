package ua.edu.ucu.apps;

import lombok.AllArgsConstructor;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@AllArgsConstructor
public class CachedDocument implements Document {
    private final String gcsPath;

    @Override
    public String parse() throws IOException, SQLException {
        String text = retrieveTextFromDatabase();

        if (text == null) {
            text = new SmartDocument(gcsPath).parse();
            saveTextToDatabase(text);
        }

        return text;
    }

    private String retrieveTextFromDatabase() throws SQLException {
        String selectQuery = "SELECT text FROM documents WHERE gcsPath = ?";
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:identifier.sqlite");
             PreparedStatement selectStatement = conn.prepareStatement(selectQuery)) {
            selectStatement.setString(1, gcsPath);

            try (ResultSet resultSet = selectStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("text");
                }
            }
        }
        return null;
    }

    private void saveTextToDatabase(String text) throws SQLException {
        String insertQuery = "INSERT INTO documents (gcsPath, text) VALUES (?, ?)";
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:identifier.sqlite");
             PreparedStatement insertStatement = conn.prepareStatement(insertQuery)) {
            insertStatement.setString(1, gcsPath);
            insertStatement.setString(2, text);
            insertStatement.executeUpdate();
        }
    }
}
