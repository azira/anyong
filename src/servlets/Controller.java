package servlets;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Controller
 */

public class Controller extends HttpServlet {
  private static final long serialVersionUID = 1L;
  private static String SEARCH_JSP = "/search.jsp";

  
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String forward="";
    // Get a map of the request parameters
    @SuppressWarnings("unchecked")
    String query = request.getParameter("search");
   
    forward = SEARCH_JSP + "?query=" + query;
    
    response.sendRedirect(forward);
    //view.forward(request, response);
  }
} 