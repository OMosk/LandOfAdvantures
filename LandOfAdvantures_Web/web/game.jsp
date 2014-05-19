<%--
  Created by IntelliJ IDEA.
  User: mosk
  Date: 15.05.14
  Time: 6:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>LandOfAdvantures</title>
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
        <a href="/">LandOfAdvantures</a>
    </span>
    <div id="subheader_section">
        <% if(true){ %>
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
                    <td align="right"><a href="/registration">Зареєструватись</a></td>
                    <td><input type="submit" value="Увійти"></td>
                </tr>
            </table>
        </form>
        <% } %>
    </div>
</header>
<div id="game_section">

</div>
</body>
</html>