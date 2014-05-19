<%--
  Created by IntelliJ IDEA.
  User: mosk
  Date: 15.05.14
  Time: 1:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%

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
        <% if(false){ %>
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
        <% } %>
    </div>
</header>
<%
    //String message = request.getAttribute("message");
    String message = (String)request.getAttribute("message");
    Boolean showForm = (Boolean) request.getAttribute("show_form");

    String login = request.getParameter("login");
    if(login==null)login = "Логін";
    String password = request.getParameter("password");
    if(password==null)password = "Пароль";
    String rePassword = request.getParameter("re-password");
    if(rePassword==null)rePassword = "Повторіть пароль";

%>
    <% if(showForm!=null && showForm) { %>
    <form method="POST" action="registration">
        <table align="center">
            <tr>
                <td align="right">Логін:</td>
                <td><input type="text" name="login" placeholder="Логін" value="<%= login%>"></td>
            </tr>
            <tr>
                <td align="right">Пароль:</td>
                <td><input type="text" name="password" placeholder="Пароль" value="<%= password%>"></td>
            </tr>
            <tr>
                <td align="right">Повторіть пароль:</td>
                <td ><input type="text" name="re-password" placeholder="Повторіть пароль" value="<%= rePassword%>"></td>
            </tr>
            <tr>
                <td></td>
                <td><input type="submit" value="Зареєструвати"></td>
            </tr>
        </table>
    </form>
    <% } %>
</body>
</html>