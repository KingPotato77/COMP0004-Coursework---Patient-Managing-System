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
 * The StatisticsServlet handles HTTP requests for displaying patient statistics and analytics.
 * It is mapped to the URL "/statistics".
 *
 * This servlet demonstrates:
 * 1. Retrieving various statistical data from the Model.
 * 2. Passing aggregated data to a JSP for display.
 * 3. Error handling for data retrieval.
 */
@WebServlet("/statistics")
public class StatisticsServlet extends HttpServlet
{

  /**
   * Handles HTTP GET requests.
   * Retrieves various statistics from the Model and forwards to statistics.jsp.
   *
   * @param request  the HttpServletRequest object that contains the request the client has made of the servlet
   * @param response the HttpServletResponse object that contains the response the servlet sends to the client
   * @throws ServletException if the request for the GET could not be handled
   * @throws IOException      if an input or output error is detected when the servlet handles the GET request
   */
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
  {
    try {
      // Get the singleton instance of the Model
      Model model = ModelFactory.getModel();

      // Retrieve various statistics and add them to request attributes
      request.setAttribute("totalPatients", model.getTotalPatientCount());
      request.setAttribute("livingPatients", model.getLivingCount());
      request.setAttribute("deceasedPatients", model.getDeceasedCount());
      request.setAttribute("averageAge", String.format("%.2f", model.getAverageAge()));

      request.setAttribute("oldestPerson", model.findOldestPerson());
      request.setAttribute("youngestPerson", model.findYoungestPerson());

      request.setAttribute("peopleByCity", model.getPeopleByCity());
      request.setAttribute("peopleByState", model.getPeopleByState());
      request.setAttribute("genderDistribution", model.getGenderDistribution());
      request.setAttribute("raceDistribution", model.getRaceDistribution());
      request.setAttribute("maritalStatusDistribution", model.getMaritalStatusDistribution());
      request.setAttribute("ethnicityDistribution", model.getEthnicityDistribution());

      // Forward to the statistics JSP
      ServletContext context = getServletContext();
      RequestDispatcher dispatcher = context.getRequestDispatcher("/statistics.jsp");
      dispatcher.forward(request, response);

    } catch (Exception e) {
      request.setAttribute("errorMessage", "Error loading statistics: " + e.getMessage());
      ServletContext context = getServletContext();
      RequestDispatcher dispatcher = context.getRequestDispatcher("/statistics.jsp");
      dispatcher.forward(request, response);
    }
  }
}

