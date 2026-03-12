package uk.ac.ucl.model;

import java.io.Reader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class DataLoader {

    private static final Logger logger = Logger.getLogger(DataLoader.class.getName());

    public DataFrame loadData(String fileName) {
        DataFrame df = new DataFrame();

        try (Reader reader = new FileReader(fileName);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader()))
        {
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
            logger.warning("Error loading file: " + e.getMessage());
        }

        return df;
    }
}
