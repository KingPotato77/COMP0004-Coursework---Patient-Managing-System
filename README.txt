PATIENT DATA WEB APPLICATION

OVERVIEW
This Java web application manages and analyzes patient data from the provided CSV files using a DataFrame structure.
Built with Tomcat, Servlets, and JSPs following the MVC pattern. Allows for viewing patient list, adding/editing/removing
patients from database, viewing statistics, and updating database (in CSV or JSON).
Boilerplate code generated with the help of GitHub Copilot.

FEATURES IMPLEMENTED
Requirements 1-4: Core Data Structure
- Column class with name and ArrayList of row values
- DataFrame class managing collections of columns
- DataLoader class for reading CSV files into DataFrame
- Model class implementing Singleton pattern for centralized data management

Requirement 5: Web Application Interface
- Patient List: View all patients with clickable names linking to details
- Patient Details: information display for individual patients
- Navigation: Clean interface with header/footer on all pages
- Styled with CSS (mainly inline)

Requirement 6: Search Functionality
- Case-insensitive keyword search across all patient fields
- Search implementation in Model class (not servlets/JSPs)
- Results display patient names with matched fields highlighted, link to patient detail pages, and show match count

Requirements 7 & 10: Statistical Operations
- Age analysis: oldest and youngest patients with calculated ages
- Geographic distribution: Patients grouped by city and state
- Demographics distribution by gender, race, ethnicity, marital status
- Graphs to help visualize distribution

Requirement 8: Data Modification & CSV Export
- Add new patients with auto generated unique IDs
- Edit existing patient information with validation
- Delete patients with proper cleanup
- Export entire dataset to CSV file (saved to data/patients100.csv)

Requirement 9: JSON Export
- JSONWriter class for DataFrame to JSON conversion
- Export option available in web interface (saves to patients_export.json)

HOW TO RUN
(need Java and Maven installed)
1. Run: mvn clean package
2. Run Main.java from your IDE or: java -cp target/classes uk.ac.ucl.main.Main
3. Open browser to: http://localhost:8080

*The application currently uses patients100.csv, but it works with any of the patient CSV files in the correct format.
To change it, replace 'patients100.csv' in:
- ModelFactory.java (line 24)
- EditPatientServlet.java (line 68)
- ExportServlet.java (line 25)
- AddPatientServlet.java (line 63)
