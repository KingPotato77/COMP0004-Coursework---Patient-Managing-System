package uk.ac.ucl.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import uk.ac.ucl.model.Model;
import uk.ac.ucl.model.ModelFactory;

import java.io.IOException;

/**
 * ExportServlet handles saving the current patient data to a CSV file.
 * It is mapped to the URL "/export".
 */
@WebServlet("/export")
public class ExportServlet extends HttpServlet
{
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
  {
    try {
      Model model = ModelFactory.getModel();
      model.saveToCSV("data/patients100.csv");
      request.setAttribute("message", "Data saved to data/patients100.csv successfully.");
    } catch (Exception e) {
      request.setAttribute("message", "Error saving CSV: " + e.getMessage());
    }

    RequestDispatcher dispatcher = request.getRequestDispatcher("/exportCSV.jsp");
    dispatcher.forward(request, response);
  }
}
