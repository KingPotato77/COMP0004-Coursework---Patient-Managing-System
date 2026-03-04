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

  // ========== REQUIREMENT 7: OPERATIONS ==========

  /**
   * Find the oldest living person in the dataset.
   * Returns a map with the patient's name and age.
   */
  public Map<String, String> findOldestPerson() {
    Map<String, String> oldest = new LinkedHashMap<>();
    int maxAge = -1;
    String oldestName = null;
    String oldestBirthDate = null;

    for (int i = 0; i < df.getRowCount(); i++) {
      String deathDate = df.getValue("DEATHDATE", i);
      // Only consider living people (no death date)
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

  /**
   * Find the youngest person in the dataset.
   */
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

  /**
   * Count how many people live in each city.
   */
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

  /**
   * Count how many people live in each state.
   */
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

  /**
   * Get gender distribution.
   */
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

  /**
   * Get race distribution.
   */
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

  /**
   * Get marital status distribution.
   */
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

  /**
   * Get ethnicity distribution.
   */
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

  /**
   * Get total patient count.
   */
  public int getTotalPatientCount() {
    return df.getRowCount();
  }

  /**
   * Get count of deceased patients.
   */
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

  /**
   * Get count of living patients.
   */
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

  /**
   * Get average age of living patients.
   */
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

  /**
   * Get people in a specific city.
   */
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
}
