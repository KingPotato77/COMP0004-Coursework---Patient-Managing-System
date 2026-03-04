package uk.ac.ucl.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class Model {
  private DataFrame df;

  public void readFile(String fileName) {
    DataLoader loader = new DataLoader();
    df = loader.loadData(fileName);
  }

  public DataFrame getDf() {
    return df;
  }

  public List<String> searchFor(String keyword) {
    List<String> results = new ArrayList<>();

    for (int row = 0; row < df.getRowCount(); row++) {
      for (String colName : df.getColumnNames()) {
        String cellValue = df.getValue(colName, row);

        if (cellValue != null && cellValue.toLowerCase().contains(keyword)) {
          String firstName = df.getValue("FIRST", row);
          String lastName = df.getValue("LAST", row);
          results.add(firstName + " " + lastName);
          break;
        }
      }
    }

    return results;
  }

  public List<String> getPatientNames() {
    List<String> names = new ArrayList<>();

    for (int i = 0; i < df.getRowCount(); i++) {
      names.add(df.getValue("FIRST", i) + " " + df.getValue("LAST", i));
    }

    return names;
  }

  public Map<String, String> getPatientDetails(String id) {
    Map<String, String> details = new LinkedHashMap<>();
    for (int row = 0; row < df.getRowCount(); row++) {
      if (df.getValue("ID", row).equals(id)) {
        for (String colName : df.getColumnNames()) {
          details.put(colName, df.getValue(colName, row));
        }
        break;
      }
    }
    return details;
  }
}
  /*
  // The example code in this class should be replaced by your Model class code.
  // The data should be stored in a suitable data structure.
  public List<String> getPatientNames()
  {
    return readFile("data/patients100.csv");
  }

  // This method illustrates how to read csv data from a file.
  // The data files are stored in the root directory of the project (the directory your project is in),
  // in the directory named data.
  public List<String> readFile(String fileName)
  {
    List<String> data = new ArrayList<>();

    try (Reader reader = new FileReader(fileName);
         CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT))
    {
      for (CSVRecord csvRecord : csvParser)
      {
        // The first row of the file contains the column headers, so is not actual data.
        data.add(csvRecord.get(0));
      }
    } catch (IOException e)
    {
      e.printStackTrace();
    }
    return data;
  }

  // This also returns dummy data. The real version should use the keyword parameter to search
  // the data and return a list of matching items.
  public List<String> searchFor(String keyword)
  {
    return List.of("Search keyword is: "+ keyword, "result1", "result2", "result3");
  }
}
*/
