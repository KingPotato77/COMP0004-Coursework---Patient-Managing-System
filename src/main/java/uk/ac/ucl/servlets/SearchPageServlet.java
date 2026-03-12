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
 * SearchPageServlet serves the search form. Mapped to "/search".
 */
@WebServlet("/search")
public class SearchPageServlet extends HttpServlet
{
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
  {
    ServletContext context = getServletContext();
    RequestDispatcher dispatcher = context.getRequestDispatcher("/search.jsp");
    dispatcher.forward(request, response);
  }
}

