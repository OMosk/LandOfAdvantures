package km23.loa.web.servlets;

/**
 * Created by mosk on 15.05.14.
 */
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import km23.loa.web.UserRegistration;
import km23.loa.web.util.GlobalParameters;
import km23.loa.web.util.Logger;

public class RegistrationServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
//        // Set response content type
//        response.setContentType("text/html");
//        // Actual logic goes here.
//        PrintWriter out = response.getWriter();
//        out.println("<h1>" + message + "</h1>");
        request.setAttribute("show_form", true);
        RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/WEB-INF/jsp/registration.jsp");
        requestDispatcher.forward(request, response);
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        GlobalParameters.setServletContext(getServletContext());
        UserRegistration userRegistration = new UserRegistration();

        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String rePassword = request.getParameter("re-password");

        Logger.log(request.getServletContext().getContextPath());

        if(!userRegistration.isLoginFree(login)){
            //log's not free
            System.out.println("Login is not free");
            request.setAttribute("show_form", true);
            request.setAttribute("login_problem", true);
            RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/WEB-INF/jsp/registration.jsp");
            requestDispatcher.forward(request, response);
        }
        else if(!password.equals(rePassword)){
            //pass mismatch
            System.out.println("pass mismatch");
            request.setAttribute("show_form", true);
            request.setAttribute("password_problem", true);
            RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/WEB-INF/jsp/registration.jsp");
            requestDispatcher.forward(request, response);
        }
        else if(!userRegistration.registerUser(login, password)){
            System.out.println("some registration problem");
            request.setAttribute("show_form", true);
            request.setAttribute("registration_problem", true);
            RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/WEB-INF/jsp/registration.jsp");
            requestDispatcher.forward(request, response);
        }
        else{
            System.out.println("registration success");
            request.setAttribute("registration_success", true);
            RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/WEB-INF/jsp/registration.jsp");
            requestDispatcher.forward(request, response);
        }
    }
}
