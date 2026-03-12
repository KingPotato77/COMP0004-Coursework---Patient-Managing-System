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
import java.util.Map;

/**
 * EditPatientServlet handles viewing, editing, and deleting a patient record.
 * Mapped to "/editPatient".
 */
@WebServlet("/editPatient")
public class EditPatientServlet extends HttpServlet
{

  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
  {
    try {
      String patientId = request.getParameter("id");
      Model model = ModelFactory.getModel();

      // Get patient details for display
      Map<String, String> patientDetails = model.getPatientDetails(patientId);
      request.setAttribute("patientDetails", patientDetails);
      request.setAttribute("patientId", patientId);

      ServletContext context = getServletContext();
      RequestDispatcher dispatcher = context.getRequestDispatcher("/editPatient.jsp");
      dispatcher.forward(request, response);

    } catch (Exception e) {
      request.setAttribute("errorMessage", "Error loading patient: " + e.getMessage());
      ServletContext context = getServletContext();
      RequestDispatcher dispatcher = context.getRequestDispatcher("/editPatient.jsp");
      dispatcher.forward(request, response);
    }
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
  {
    try {
      String action = request.getParameter("action");
      String patientId = request.getParameter("patientId");
      Model model = ModelFactory.getModel();

      if ("delete".equals(action)) {
        model.deletePatient(patientId);
        request.setAttribute("successMessage", "Patient deleted successfully.");
      } else if ("save".equals(action)) {
        // Get all column names and update each field
        for (String colName : model.getColumnNames()) {
          String value = request.getParameter(colName);
          if (value != null) {
            model.updatePatient(patientId, colName, value);
          }
        }
        request.setAttribute("successMessage", "Patient updated successfully.");
      }

      model.saveToCSV("data/patients100.csv");

      // Redirect back to patient list
      response.sendRedirect("/patientList");

    } catch (Exception e) {
      request.setAttribute("errorMessage", "Error processing request: " + e.getMessage());
      ServletContext context = getServletContext();
      RequestDispatcher dispatcher = context.getRequestDispatcher("/editPatient.jsp");
      dispatcher.forward(request, response);
    }
  }
}

