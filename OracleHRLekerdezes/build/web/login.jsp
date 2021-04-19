<%-- 
    Document   : login
    Created on : 2021.02.26., 8:38:31
    Author     : Kaczur
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Oracle HR lekérdező 1.0</title>
  </head>
  <body>
    <h2>Bejelentkezés</h2>
    <form action="loginEllenorzo.jsp" method="post">
      Felhasználónév: <input id="username" type="text" name="username"><br>
      Jelszó: <input id="password" type="password" name="password"><br>
      <input type="submit" value="Belép">
    </form>
    <p><a href="index.jsp">Címlap</a></p>
  </body>
</html>
