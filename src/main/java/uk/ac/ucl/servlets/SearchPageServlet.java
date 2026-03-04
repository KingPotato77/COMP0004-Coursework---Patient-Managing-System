package uk.ac.ucl.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * The SearchPageServlet handles HTTP requests for displaying the search page.
 * It is mapped to the URL "/search".
 *
 * This servlet simply forwards to the search.jsp page.
 */
@WebServlet("/search")
public class SearchPageServlet extends HttpServlet
{

  /**
   * Handles HTTP GET requests.
   * Forwards the request to search.jsp.
   *
   * @param request  the HttpServletRequest object that contains the request the client has made of the servlet
   * @param response the HttpServletResponse object that contains the response the servlet sends to the client
   * @throws ServletException if the request for the GET could not be handled
   * @throws IOException      if an input or output error is detected when the servlet handles the GET request
   */
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
  {
    try {
      // Get the ServletContext to access the request dispatcher
      ServletContext context = getServletContext();
      RequestDispatcher dispatcher = context.getRequestDispatcher("/search.jsp");
      dispatcher.forward(request, response);
    } catch (Exception e) {
      request.setAttribute("errorMessage", "Error loading search page: " + e.getMessage());
      ServletContext context = getServletContext();
      RequestDispatcher dispatcher = context.getRequestDispatcher("/search.jsp");
      dispatcher.forward(request, response);
    }
  }
}

