COMP0004 Coursework - Patient Data Web Application

Overview

This Java web application loads, displays, and manages synthetic patient data from CSV files.
It's built using embedded Tomcat, Java Servlets, and JSPs, following the Model-View-Controller
pattern. The data is held in a custom DataFrame structure made up of Column objects, loaded by
a DataLoader class, and managed through a Model accessed via the Singleton pattern.

Data and Search

Patient data is read from patients100.csv at startup. The DataFrame stores all fields as
strings, each Column holding one CSV column across all rows. Search is implemented
entirely in the Model class, with case-insensitive matching across every field. Results
include the patient name, the fields that matched, and a link to the full patient details page.

Web Interface

The application provides a patient list, individual patient detail pages, an add-patient form,
and an edit/delete form. Each page links back to the previous one. Servlets handle all requests
and pass data to JSPs as request attributes. JSPs contain only the display logic needed to
render the response.

Statistics and Analytics

The statistics page shows total, living, and deceased patient counts, average age, and the
oldest and youngest living patients. It also shows distributions by city, state, gender, race,
ethnicity, and marital status. Supported visually with inline bar charts using HTML and CSS.

Data Export

The full dataset can be saved back to CSV using a dedicated export button, which overwrites
patients100.csv. A separate button saves the data to patients_export.json, written by a custom
JSONWriter class that produces well-formed JSON.

How to Run

Requires Java and Maven.
1. Run: mvn clean package
2. Run Main.java from the IDE, or: java -cp target/classes uk.ac.ucl.main.Main
3. Open browser to: http://localhost:8080

The application reads from data/patients100.csv. To use a larger dataset, place the chosen
CSV file in the data/ folder and update the filename in
- ModelFactory.java (line 24)
- EditPatientServlet.java (line 68)
- AddPatientServlet.java (line 63)
- ExportServlet.java (line 25)
