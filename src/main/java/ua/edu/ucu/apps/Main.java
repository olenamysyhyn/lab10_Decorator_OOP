package ua.edu.ucu.apps;

import java.io.IOException;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException, IOException {
        System.out.println(new CachedDocument("gs://cv-examples/wiki.png").parse());
        new TimedDocument("gs://cv-examples/wiki.png").parse();
    }
}