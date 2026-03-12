package uk.ac.ucl.model;

import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;


public class Model {
  private DataFrame df;

  public void readFile(String fileName) {
    DataLoader loader = new DataLoader();
    df = loader.loadData(fileName);
  }


  public List<String> getColumnNames() {
    return df.getColumnNames();
  }


  public void saveToCSV(String fileName) {
    CSVWriter csvWriter = new CSVWriter();
    csvWriter.writeToCSV(df, fileName);
  }


  public void saveToJSON(String fileName) {
    JSONWriter jsonWriter = new JSONWriter();
    jsonWriter.writeToJSON(df, fileName);
  }


  public List<Map<String, String>> searchForDetailed(String keyword) {
    List<Map<String, String>> results = new ArrayList<>();

    for (int row = 0; row < df.getRowCount(); row++) {
      List<String> matchedFields = new ArrayList<>();

      for (String colName : df.getColumnNames()) {
        String cellValue = df.getValue(colName, row);
        if (cellValue != null && cellValue.toLowerCase().contains(keyword.toLowerCase())) {
          matchedFields.add(colName);
        }
      }

      // Include this patient in results only when at least one field matched
      if (!matchedFields.isEmpty()) {
        Map<String, String> result = new HashMap<>();
        result.put("id", df.getValue("ID", row));
        result.put("name", df.getValue("FIRST", row) + " " + df.getValue("LAST", row));
        result.put("matchedFields", String.join(", ", matchedFields));
        results.add(result);
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

  public List<String> getPatientIds() {
    List<String> ids = new ArrayList<>();
    for (int i = 0; i < df.getRowCount(); i++) {
      ids.add(df.getValue("ID", i));
    }
    return ids;
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


  // Statistics and analytics (Requirement 7)

  public Map<String, String> findOldestPerson() {
    Map<String, String> oldest = new LinkedHashMap<>();
    int maxAge = -1;
    String oldestName = null;
    String oldestBirthDate = null;

    for (int i = 0; i < df.getRowCount(); i++) {
      String deathDate = df.getValue("DEATHDATE", i);
      // Only consider alive people
      if (deathDate == null || deathDate.isEmpty()) {
        String birthDateStr = df.getValue("BIRTHDATE", i);
        if (birthDateStr != null && !birthDateStr.isEmpty()) {
          int age = calculateAge(birthDateStr);
          if (age > maxAge) {
            maxAge = age;
            oldestName = df.getValue("FIRST", i) + " " + df.getValue("LAST", i);
            oldestBirthDate = birthDateStr;
          }
        }
      }
    }

    if (oldestName != null) {
      oldest.put("Name", oldestName);
      oldest.put("Age", String.valueOf(maxAge));
      oldest.put("Birth Date", oldestBirthDate);
    }
    return oldest;
  }


  public Map<String, String> findYoungestPerson() {
    Map<String, String> youngest = new LinkedHashMap<>();
    int minAge = Integer.MAX_VALUE;
    String youngestName = null;
    String youngestBirthDate = null;

    for (int i = 0; i < df.getRowCount(); i++) {
      String birthDateStr = df.getValue("BIRTHDATE", i);
      if (birthDateStr != null && !birthDateStr.isEmpty()) {
        int age = calculateAge(birthDateStr);
        if (age < minAge) {
          minAge = age;
          youngestName = df.getValue("FIRST", i) + " " + df.getValue("LAST", i);
          youngestBirthDate = birthDateStr;
        }
      }
    }

    if (youngestName != null) {
      youngest.put("Name", youngestName);
      youngest.put("Age", String.valueOf(minAge));
      youngest.put("Birth Date", youngestBirthDate);
    }
    return youngest;
  }


  // Counts occurrences of each value in the given column, sorted by count descending.
  // If includeUnknown is true, blank values are counted under "Unknown".
  private Map<String, Integer> sortedCountMap(String columnName, boolean includeUnknown) {
    Map<String, Integer> counts = new LinkedHashMap<>();

    for (int i = 0; i < df.getRowCount(); i++) {
      String value = df.getValue(columnName, i);
      if (value != null && !value.isEmpty()) {
        counts.put(value, counts.getOrDefault(value, 0) + 1);
      } else if (includeUnknown) {
        counts.put("Unknown", counts.getOrDefault("Unknown", 0) + 1);
      }
    }

    return counts.entrySet()
            .stream()
            .sorted((a, b) -> b.getValue().compareTo(a.getValue()))
            .collect(Collectors.toMap(
                    Map.Entry::getKey,
                    Map.Entry::getValue,
                    (e1, e2) -> e1,
                    LinkedHashMap::new
            ));
  }


  public Map<String, Integer> getPeopleByCity() {
    return sortedCountMap("CITY", false);
  }


  public Map<String, Integer> getPeopleByState() {
    return sortedCountMap("STATE", false);
  }


  public Map<String, Integer> getGenderDistribution() {
    Map<String, Integer> genderCount = new LinkedHashMap<>();

    for (int i = 0; i < df.getRowCount(); i++) {
      String gender = df.getValue("GENDER", i);
      if (gender != null && !gender.isEmpty()) {
        String label = gender.equals("M") ? "Male" : gender.equals("F") ? "Female" : "Other";
        genderCount.put(label, genderCount.getOrDefault(label, 0) + 1);
      }
    }

    return genderCount;
  }


  public Map<String, Integer> getRaceDistribution() {
    return sortedCountMap("RACE", false);
  }


  public Map<String, Integer> getMaritalStatusDistribution() {
    return sortedCountMap("MARITAL", true);
  }


  public Map<String, Integer> getEthnicityDistribution() {
    return sortedCountMap("ETHNICITY", true);
  }


  public int getTotalPatientCount() {
    return df.getRowCount();
  }


  public int getDeceasedCount() {
    int count = 0;
    for (int i = 0; i < df.getRowCount(); i++) {
      String deathDate = df.getValue("DEATHDATE", i);
      if (deathDate != null && !deathDate.isEmpty()) {
        count++;
      }
    }
    return count;
  }


  public int getLivingCount() {
    return getTotalPatientCount() - getDeceasedCount();
  }


  public double getAverageAge() {
    int totalAge = 0;
    int count = 0;

    for (int i = 0; i < df.getRowCount(); i++) {
      String deathDate = df.getValue("DEATHDATE", i);
      // Only consider living people
      if (deathDate == null || deathDate.isEmpty()) {
        String birthDateStr = df.getValue("BIRTHDATE", i);
        if (birthDateStr != null && !birthDateStr.isEmpty()) {
          totalAge += calculateAge(birthDateStr);
          count++;
        }
      }
    }

    return count > 0 ? (double) totalAge / count : 0.0;
  }


  // Add, edit, delete patients (Requirement 8)

  public void updatePatient(String patientId, String columnName, String value) {
    for (int row = 0; row < df.getRowCount(); row++) {
      if (df.getValue("ID", row).equals(patientId)) {
        df.putValue(columnName, row, value);
        break;
      }
    }
  }

  public void deletePatient(String patientId) {
    for (int row = 0; row < df.getRowCount(); row++) {
      if (df.getValue("ID", row).equals(patientId)) {
        df.removeRow(row);
        break;
      }
    }
  }


  public void addNewPatient(Map<String, String> patientData) {
    for (String colName : df.getColumnNames()) {
      df.addValue(colName, patientData.getOrDefault(colName, ""));
    }
  }


  // Returns 0 if the date string cannot be parsed (e.g. malformed or missing data).
  private int calculateAge(String birthDateStr) {
    try {
      LocalDate birthDate = LocalDate.parse(birthDateStr);
      return Period.between(birthDate, LocalDate.now()).getYears();
    } catch (Exception e) {
      return 0;
    }
  }
}
