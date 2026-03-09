package uk.ac.ucl.model;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class JSONWriter {

    public void writeToJSON(DataFrame df, String fileName) {
        try (FileWriter writer = new FileWriter(fileName)) {
            ArrayList<String> columnNames = df.getColumnNames();

            writer.write("[\n");

            int rowCount = df.getRowCount();
            int writtenRows = 0;

            // first pass: count non-empty rows for correct comma placement
            int nonEmptyCount = 0;
            for (int row = 0; row < rowCount; row++) {
                String id = df.getValue("ID", row);
                if (id != null && !id.isEmpty()) {
                    nonEmptyCount++;
                }
            }

            for (int row = 0; row < rowCount; row++) {
                // skip deleted (empty) rows
                String id = df.getValue("ID", row);
                if (id == null || id.isEmpty()) {
                    continue;
                }

                writer.write("  {\n");

                for (int i = 0; i < columnNames.size(); i++) {
                    String colName = columnNames.get(i);
                    String value = df.getValue(colName, row);
                    if (value == null) {
                        value = "";
                    }
                    // escape any quotes in the value
                    value = value.replace("\"", "\\\"");

                    writer.write("    \"" + colName + "\": \"" + value + "\"");
                    if (i < columnNames.size() - 1) {
                        writer.write(",");
                    }
                    writer.write("\n");
                }

                writtenRows++;
                writer.write("  }");
                if (writtenRows < nonEmptyCount) {
                    writer.write(",");
                }
                writer.write("\n");
            }

            writer.write("]\n");

        } catch (IOException e) {
            System.out.println("Error writing JSON file: " + e.getMessage());
        }
    }
}
