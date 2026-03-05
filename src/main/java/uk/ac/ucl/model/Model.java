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


  // methods for requirement 7

  public Map<String, String> findOldestPerson() {
    Map<String, String> oldest = new LinkedHashMap<>();
    int maxAge = -1;
    String oldestName = null;
    String oldestBirthDate = null;

    for (int i = 0; i < df.getRowCount(); i++) {
      String deathDate = df.getValue("DEATHDATE", i);
      // Only consider alive people (no death date)
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


  public Map<String, Integer> getPeopleByCity() {
    Map<String, Integer> cityCount = new LinkedHashMap<>();

    for (int i = 0; i < df.getRowCount(); i++) {
      String city = df.getValue("CITY", i);
      if (city != null && !city.isEmpty()) {
        cityCount.put(city, cityCount.getOrDefault(city, 0) + 1);
      }
    }

    // Sort by count (descending)
    return cityCount.entrySet()
      .stream()
      .sorted((a, b) -> b.getValue().compareTo(a.getValue()))
      .collect(Collectors.toMap(
        Map.Entry::getKey,
        Map.Entry::getValue,
        (e1, e2) -> e1,
        LinkedHashMap::new
      ));
  }


  public Map<String, Integer> getPeopleByState() {
    Map<String, Integer> stateCount = new LinkedHashMap<>();

    for (int i = 0; i < df.getRowCount(); i++) {
      String state = df.getValue("STATE", i);
      if (state != null && !state.isEmpty()) {
        stateCount.put(state, stateCount.getOrDefault(state, 0) + 1);
      }
    }

    // Sort by count (descending)
    return stateCount.entrySet()
      .stream()
      .sorted((a, b) -> b.getValue().compareTo(a.getValue()))
      .collect(Collectors.toMap(
        Map.Entry::getKey,
        Map.Entry::getValue,
        (e1, e2) -> e1,
        LinkedHashMap::new
      ));
  }


  public Map<String, Integer> getGenderDistribution() {
    Map<String, Integer> genderCount = new LinkedHashMap<>();

    for (int i = 0; i < df.getRowCount(); i++) {
      String gender = df.getValue("GENDER", i);
      if (gender != null && !gender.isEmpty()) {
        String genderLabel = gender.equals("M") ? "Male" : gender.equals("F") ? "Female" : "Other";
        genderCount.put(genderLabel, genderCount.getOrDefault(genderLabel, 0) + 1);
      }
    }

    return genderCount;
  }


  public Map<String, Integer> getRaceDistribution() {
    Map<String, Integer> raceCount = new LinkedHashMap<>();

    for (int i = 0; i < df.getRowCount(); i++) {
      String race = df.getValue("RACE", i);
      if (race != null && !race.isEmpty()) {
        raceCount.put(race, raceCount.getOrDefault(race, 0) + 1);
      }
    }

    // Sort by count (descending)
    return raceCount.entrySet()
      .stream()
      .sorted((a, b) -> b.getValue().compareTo(a.getValue()))
      .collect(Collectors.toMap(
        Map.Entry::getKey,
        Map.Entry::getValue,
        (e1, e2) -> e1,
        LinkedHashMap::new
      ));
  }


  public Map<String, Integer> getMaritalStatusDistribution() {
    Map<String, Integer> maritalCount = new LinkedHashMap<>();

    for (int i = 0; i < df.getRowCount(); i++) {
      String marital = df.getValue("MARITAL", i);
      if (marital != null && !marital.isEmpty()) {
        maritalCount.put(marital, maritalCount.getOrDefault(marital, 0) + 1);
      } else {
        maritalCount.put("Unknown", maritalCount.getOrDefault("Unknown", 0) + 1);
      }
    }

    // Sort by count (descending)
    return maritalCount.entrySet()
      .stream()
      .sorted((a, b) -> b.getValue().compareTo(a.getValue()))
      .collect(Collectors.toMap(
        Map.Entry::getKey,
        Map.Entry::getValue,
        (e1, e2) -> e1,
        LinkedHashMap::new
      ));
  }


  public Map<String, Integer> getEthnicityDistribution() {
    Map<String, Integer> ethnicityCount = new LinkedHashMap<>();

    for (int i = 0; i < df.getRowCount(); i++) {
      String ethnicity = df.getValue("ETHNICITY", i);
      if (ethnicity != null && !ethnicity.isEmpty()) {
        ethnicityCount.put(ethnicity, ethnicityCount.getOrDefault(ethnicity, 0) + 1);
      } else {
        ethnicityCount.put("Unknown", ethnicityCount.getOrDefault("Unknown", 0) + 1);
      }
    }

    // Sort by count (descending)
    return ethnicityCount.entrySet()
      .stream()
      .sorted((a, b) -> b.getValue().compareTo(a.getValue()))
      .collect(Collectors.toMap(
        Map.Entry::getKey,
        Map.Entry::getValue,
        (e1, e2) -> e1,
        LinkedHashMap::new
      ));
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

  /**
   * Calculate age from birth date string (YYYY-MM-DD format).
   */
  private int calculateAge(String birthDateStr) {
    try {
      LocalDate birthDate = LocalDate.parse(birthDateStr);
      LocalDate today = LocalDate.now();
      return Period.between(birthDate, today).getYears();
    } catch (Exception e) {
      return 0;
    }
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


  public List<String> getPeopleInCity(String cityName) {
    List<String> people = new ArrayList<>();
    for (int i = 0; i < df.getRowCount(); i++) {
      String city = df.getValue("CITY", i);
      if (city != null && city.equalsIgnoreCase(cityName)) {
        people.add(df.getValue("FIRST", i) + " " + df.getValue("LAST", i));
      }
    }
    return people;
  }

  // ========== REQUIREMENT 8: ADD/EDIT/DELETE ==========

  /**
   * Update a patient's field value.
   */
  public void updatePatient(String patientId, String columnName, String value) {
    for (int row = 0; row < df.getRowCount(); row++) {
      if (df.getValue("ID", row).equals(patientId)) {
        df.putValue(columnName, row, value);
        break;
      }
    }
  }

  /**
   * Delete a patient by ID.
   */
  public void deletePatient(String patientId) {
    for (int row = 0; row < df.getRowCount(); row++) {
      if (df.getValue("ID", row).equals(patientId)) {
        // Actually remove the row from the DataFrame
        df.removeRow(row);
        break;
      }
    }
  }

  /**
   * Add a new patient with the provided data.
   */
  public void addNewPatient(Map<String, String> patientData) {
    for (String colName : df.getColumnNames()) {
      String value = patientData.getOrDefault(colName, "");
      df.addValue(colName, value);
    }
  }

  /**
   * Save DataFrame to CSV file.
   */
  public void saveToCSV(String fileName) {
    try {
      java.io.FileWriter writer = new java.io.FileWriter(fileName);

      // Write header (column names)
      ArrayList<String> columnNames = df.getColumnNames();
      for (int i = 0; i < columnNames.size(); i++) {
        writer.write(columnNames.get(i));
        if (i < columnNames.size() - 1) {
          writer.write(",");
        }
      }
      writer.write("\n");

      // Write data rows (skip empty rows that were deleted)
      for (int row = 0; row < df.getRowCount(); row++) {
        boolean isEmpty = true;
        for (String colName : columnNames) {
          String value = df.getValue(colName, row);
          if (value != null && !value.isEmpty()) {
            isEmpty = false;
            break;
          }
        }

        // Skip empty rows (deleted patients)
        if (isEmpty) {
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

      writer.close();
    } catch (Exception e) {
      System.err.println("Error saving CSV: " + e.getMessage());
    }
  }
}

