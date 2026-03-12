package uk.ac.ucl.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import uk.ac.ucl.model.Model;
import uk.ac.ucl.model.ModelFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * AddPatientServlet handles displaying the add-patient form and saving a new patient.
 * Mapped to "/addPatient".
 */
@WebServlet("/addPatient")
public class AddPatientServlet extends HttpServlet
{

  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
  {
    try {
      Model model = ModelFactory.getModel();
      request.setAttribute("columnNames", model.getColumnNames());

      ServletContext context = getServletContext();
      RequestDispatcher dispatcher = context.getRequestDispatcher("/addPatient.jsp");
      dispatcher.forward(request, response);

    } catch (Exception e) {
      request.setAttribute("errorMessage", "Error loading form: " + e.getMessage());
      ServletContext context = getServletContext();
      RequestDispatcher dispatcher = context.getRequestDispatcher("/addPatient.jsp");
      dispatcher.forward(request, response);
    }
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
  {
    try {
      Model model = ModelFactory.getModel();

      // Collect all form data
      Map<String, String> patientData = new HashMap<>();
      for (String colName : model.getColumnNames()) {
        String value = request.getParameter(colName);
        patientData.put(colName, value != null ? value : "");
      }

      // generate ID if none provided (most likely)
      if (patientData.get("ID") == null || patientData.get("ID").isEmpty()) {
        patientData.put("ID", UUID.randomUUID().toString());
      }

      model.addNewPatient(patientData);

      model.saveToCSV("data/patients100.csv");

      // redirect to patient list
      response.sendRedirect("/patientList");

    } catch (Exception e) {
      request.setAttribute("errorMessage", "Error adding patient: " + e.getMessage());
      ServletContext context = getServletContext();
      RequestDispatcher dispatcher = context.getRequestDispatcher("/addPatient.jsp");
      dispatcher.forward(request, response);
    }
  }
}

