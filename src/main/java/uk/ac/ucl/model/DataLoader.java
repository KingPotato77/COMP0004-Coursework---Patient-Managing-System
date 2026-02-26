package uk.ac.ucl.model;

import java.io.Reader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class DataLoader {
    public DataFrame loadData(String fileName) {
        DataFrame df = new DataFrame();

        try (Reader reader = new FileReader(fileName);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader()))
        {
            // creating columns with names set as headers of csv
            List<String> headers = csvParser.getHeaderNames();
            for (String header : headers) {
                df.addColumn(new Column(header));
            }

            for (CSVRecord record : csvParser) {
                for (String header : headers) {
                    df.addValue(header, record.get(header));
                }
            }

        } catch (IOException e) {
//            e.printStackTrace();
            System.out.println("Error loading file: "+e.getMessage());
        }

        return df;
    }
}
