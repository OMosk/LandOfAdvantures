package km23.loa.web.servlets;

/**
 * Created by mosk on 15.05.14.
 */
import km23.loa.web.UserAuthorization;
import km23.loa.web.util.GlobalParameters;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;
import javax.servlet.*;
import javax.servlet.http.*;


public class AuthorizationServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        if(request.getParameter("do").equals("logout")){

            GlobalParameters.setServletContext(getServletContext());
            UserAuthorization userAuthorization = new UserAuthorization();

            String session_hash = null;

            Cookie[] cookies = request.getCookies();
            for(Cookie cookie: cookies){
                if(cookie.getName().equals("session_hash")) session_hash = cookie.getValue();
            }
            userAuthorization.checkUserAuthorization(request.getSession().getId(), session_hash);
            userAuthorization.logout();

            response.sendRedirect(request.getHeader("referer")!=null?request.getHeader("referer"): "http://"+request.getServerName() + ":" + request.getLocalPort()  + getServletContext().getContextPath() + "/");
            System.out.println("http://"+request.getServerName() + ":" + request.getLocalPort()  + getServletContext().getContextPath() + "/");

        }
        else{
            response.sendRedirect(request.getHeader("referer")!=null?request.getHeader("referer"): "http://"+request.getServerName() + ":" + request.getLocalPort()  + getServletContext().getContextPath() + "/");
        }
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        GlobalParameters.setServletContext(getServletContext());
        UserAuthorization userAuthorization = new UserAuthorization();

        String login = request.getParameter("login");
        String password = request.getParameter("password");

        if(login!=null && password!=null){
            if(userAuthorization.checkUserLoginPassword(login, password)){
                String sessionId = request.getSession().getId();
                String sessionHash = getMD5Hash( String.valueOf(System.currentTimeMillis()) );
                userAuthorization.authorizeUser(login, sessionId, sessionHash);
                if(userAuthorization.checkUserAuthorization(sessionId, sessionHash)){
                    Cookie sessionHashCookie = new Cookie("session_hash", sessionHash);
                    response.addCookie(sessionHashCookie);
                    //getServletContext().getRequestDispatcher("/-.jsp").forward(request, response);
                    response.sendRedirect(request.getHeader("referer"));
                }
                else {
                    response.sendRedirect(request.getHeader("referer"));
                }
            }
            else {
                request.setAttribute("wrong_loginpassword", true);
                getServletContext().getRequestDispatcher("/WEB-INF/jsp/authorization.jsp").forward(request, response);
            }
        }
        else{
            response.sendRedirect(request.getHeader("referer"));
        }
    }
    protected String getMD5Hash(String string){
        String md5Hash = null;
        byte[] stringBytes = null;

        try {
            stringBytes = string.getBytes("UTF-8");
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hashDigits = md.digest(stringBytes);
            StringBuilder sb = new StringBuilder(2*hashDigits.length);
            for(byte b : hashDigits){
                sb.append(String.format("%02x", b&0xff));
            }
            md5Hash = sb.toString();
        }
        catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        catch(UnsupportedEncodingException e){
            e.printStackTrace();
        }
        return md5Hash;
    }
}
