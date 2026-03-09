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
 * SaveJSONServlet handles saving the current patient data to a JSON file.
 * It is mapped to the URL "/saveJSON".
 */
@WebServlet("/saveJSON")
public class SaveJSONServlet extends HttpServlet
{
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
  {
    try {
      Model model = ModelFactory.getModel();
      model.saveToJSON("patients_export.json");
      request.setAttribute("message", "Data saved to patients_export.json successfully.");
    } catch (Exception e) {
      request.setAttribute("message", "Error saving JSON: " + e.getMessage());
    }

    RequestDispatcher dispatcher = request.getRequestDispatcher("/saveJSON.jsp");
    dispatcher.forward(request, response);
  }
}

