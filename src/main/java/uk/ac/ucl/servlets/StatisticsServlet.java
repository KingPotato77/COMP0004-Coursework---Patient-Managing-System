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

/**
 * StatisticsServlet gathers all analytics data from the Model and forwards it
 * to statistics.jsp for display. Mapped to "/statistics".
 */
@WebServlet("/statistics")
public class StatisticsServlet extends HttpServlet
{
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
  {
    try {
      Model model = ModelFactory.getModel();

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

