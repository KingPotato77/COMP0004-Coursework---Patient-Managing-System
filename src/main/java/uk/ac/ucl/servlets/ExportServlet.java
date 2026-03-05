package uk.ac.ucl.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import uk.ac.ucl.model.Model;
import uk.ac.ucl.model.ModelFactory;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * ExportServlet handles CSV file exports.
 * mapped to the URL "/export".
 */
@WebServlet("/export")
public class ExportServlet extends HttpServlet
{

  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
  {
    try {
      Model model = ModelFactory.getModel();

      // set response headers for file download
      response.setContentType("text/csv");
      response.setHeader("Content-Disposition", "attachment; filename=\"patients_export.csv\"");

      PrintWriter writer = response.getWriter();

      // write header
      java.util.ArrayList<String> columnNames = model.getDf().getColumnNames();
      for (int i = 0; i < columnNames.size(); i++) {
        writer.write(columnNames.get(i));
        if (i < columnNames.size() - 1) {
          writer.write(",");
        }
      }
      writer.write("\n");

      // Write data rows
      for (int row = 0; row < model.getDf().getRowCount(); row++) {
        boolean isEmpty = true;
        for (String colName : columnNames) {
          String value = model.getDf().getValue(colName, row);
          if (value != null && !value.isEmpty()) {
            isEmpty = false;
            break;
          }
        }

        // Skip empty rows (deleted patients)
        if (isEmpty) {
          continue;
        }

        for (int i = 0; i < columnNames.size(); i++) {
          String value = model.getDf().getValue(columnNames.get(i), row);
          if (value != null) {
            writer.write(value);
          }
          if (i < columnNames.size() - 1) {
            writer.write(",");
          }
        }
        writer.write("\n");
      }

      writer.flush();
      writer.close();

    } catch (Exception e) {
      response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error exporting data: " + e.getMessage());
    }
  }
}

