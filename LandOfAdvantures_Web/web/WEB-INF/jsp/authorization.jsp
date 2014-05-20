<%@ page import="com.sun.org.apache.xpath.internal.operations.Bool" %>
<%@ page import="km23.loa.web.util.*" %>
<%@ page import="km23.loa.web.UserAuthorization" %>
<%--
  Created by IntelliJ IDEA.
  User: mosk
  Date: 20.05.14
  Time: 2:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    GlobalParameters.setServletContext(request.getServletContext());
    UserAuthorization userAuthorization = new UserAuthorization();
    String session_hash = null;

    Cookie[] cookies = request.getCookies();
    for(Cookie cookie: cookies){
        if(cookie.getName().equals("session_hash")) session_hash = cookie.getValue();
    }
    userAuthorization.checkUserAuthorization(request.getSession().getId(), session_hash);

%>
<!DOCTYPE HTML>
<html>
<head>
    <title>loa</title>
    <script src="resources/scripts/jquery-2.0.2.min.js"></script>
    <script src="resources/scripts/pixi.js"></script>
    <script src="resources/scripts/keydrown.min.js"></script>
    <script src="resources/scripts/jquery.json-2.4.min.js"></script>
    <script src="resources/scripts/game.js"></script>
    <link rel="stylesheet" href="resources/styles/main.css" >

</head>
<body>
<header>
    <span class="logo_caption">
        LandOfAdvantures
    </span>
    <div id="subheader_section">
        <% if(!userAuthorization.isAuthorized()){ %>
        <form>
            <table align="center">
                <tr>
                    <td align="right">Логін</td>
                    <td><input type="text" name="login" placeholder="Логін"></td>
                </tr>
                <tr>
                    <td align="right">Пароль</td>
                    <td><input type="text" name="password" placeholder="Пароль"></td>
                </tr>
                <tr>
                    <td align="right"><a href="">Зареєструватись</a></td>
                    <td><input type="submit" value="Увійти"></td>
                </tr>
            </table>
        </form>
        <% }
            else{
        %>
            <b>Привіт, <%= userAuthorization.getLogin() %></b>
        <%
            }

        %>
    </div>
</header>
<%
    Boolean error = (Boolean)request.getAttribute("wrong_loginpassword");
    if(error!=null && error){
%>
        <p>Неправильно введений логін та/або пароль.</p>
<%
    }
%>
</body>
</html>