<%-- 
    Document   : loginHiba
    Created on : 2021.02.26., 9:10:38
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
    <h2>Bejelentkezés -> Hiba</h2>
    <p><%= session.getAttribute("felhasznalonev") %></p>
    <p><a href="index.jsp">Címlap</a></p>
  </body>
</html>
