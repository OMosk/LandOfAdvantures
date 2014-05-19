package km23.loa.web.servlets;

/**
 * Created by mosk on 15.05.14.
 */
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;


public class AuthorizationServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
//        // Set response content type
//        response.setContentType("text/html");
//        // Actual logic goes here.
//        PrintWriter out = response.getWriter();
//        out.println("<h1>" + message + "</h1>");
        //RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/WEB-INF/jsp/registration.jsp");
        //requestDispatcher.forward(request, response);
        response.setContentType("text/html");
        PrintWriter out =  response.getWriter();
        out.println("Hello world");
        out.flush();
    }
}
