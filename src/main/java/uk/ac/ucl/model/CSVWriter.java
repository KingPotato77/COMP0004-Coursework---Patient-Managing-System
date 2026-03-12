package uk.ac.ucl.model;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

public class CSVWriter {

    private static final Logger logger = Logger.getLogger(CSVWriter.class.getName());

    public void writeToCSV(DataFrame df, String fileName) {
        try (FileWriter writer = new FileWriter(fileName)) {
            ArrayList<String> columnNames = df.getColumnNames();

            // write header row
            for (int i = 0; i < columnNames.size(); i++) {
                writer.write(columnNames.get(i));
                if (i < columnNames.size() - 1) {
                    writer.write(",");
                }
            }
            writer.write("\n");

            // write data rows
            for (int row = 0; row < df.getRowCount(); row++) {
                String id = df.getValue("ID", row);
                if (id == null || id.isEmpty()) {
                    continue;
                }

                for (int i = 0; i < columnNames.size(); i++) {
                    String value = df.getValue(columnNames.get(i), row);
                    if (value != null) {
                        writer.write(value);
                    }
                    if (i < columnNames.size() - 1) {
                        writer.write(",");
                    }
                }
                writer.write("\n");
            }

        } catch (IOException e) {
            logger.warning("Error writing CSV file: " + e.getMessage());
        }
    }
}

