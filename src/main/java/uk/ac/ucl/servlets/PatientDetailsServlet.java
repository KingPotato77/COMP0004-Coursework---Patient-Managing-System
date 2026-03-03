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
 * The PatientDetailsServlet handles HTTP requests for displaying information about a specific patient when their name is clicked on in patientList.jsp
 * It's mapped to the URL "/patientDetails".
 *
 *
 */
@WebServlet("/patientDetails")
public class PatientDetailsServlet extends HttpServlet {

  /**
   * Handles HTTP GET requests.
   * Retrieves patient details based on the ID parameter.
   *
   * @param request  the HttpServletRequest object that contains the request the client has made of the servlet
   * @param response the HttpServletResponse object that contains the response the servlet sends to the client
   * @throws ServletException if the request for the GET could not be handled
   * @throws IOException      if an input or output error is detected when the servlet handles the GET request
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    // Use patientID to get patient information (since it's primary key of DataFrame) to retrive information with Model.getPatientDetails
    String patientId = request.getParameter("id");

    try {
      Model model = ModelFactory.getModel();

      // trim() makes sure no errors could be caused by whitespace
      if (patientId == null || patientId.trim().isEmpty()) {
        request.setAttribute("errorMessage", "No patient ID provided.");
      } else {
        Map<String, String> patientDetails = model.getPatientDetails(patientId);

        if (patientDetails.isEmpty()) {
          request.setAttribute("errorMessage", "Patient not found.");
        } else {
          request.setAttribute("patientDetails", patientDetails);
        }
      }

      ServletContext context = getServletContext();
      RequestDispatcher dispatch = context.getRequestDispatcher("/patientDetails.jsp");
      dispatch.forward(request, response);

    } catch (IOException e) {
      request.setAttribute("errorMessage", "Error loading patient data: " + e.getMessage());
      ServletContext context = getServletContext();
      RequestDispatcher dispatch = context.getRequestDispatcher("/patientDetails.jsp");
      dispatch.forward(request, response);
    }
  }

  /**
   * Handles HTTP POST requests.
   * Redirects to doGet as viewing patient details is typically an idempotent operation.
   *
   * @param request  the HttpServletRequest object that contains the request the client has made of the servlet
   * @param response the HttpServletResponse object that contains the response the servlet sends to the client
   * @throws ServletException if the request for the POST could not be handled
   * @throws IOException      if an input or output error is detected when the servlet handles the POST request
   */
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    doGet(request, response);
  }
}

