<%-- 
    Document   : index
    Created on : 2021.02.24., 10:03:32
    Author     : Kaczur
--%>

<%@page import="lekerdez.AdatbazisLekerdezBean"
        contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="lekerdez" class="lekerdez.AdatbazisLekerdezBean" scope="session"/>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
    <title>Oracle HR lekérdező 1.0</title>
  </head>
  <body>
    <h2>Részlegek és alkalmazottak alapadatai</h2>
    <% if(!lekerdez.isLoginOK()) { %>
      <p>Az oldal tartalma csak bejelentkezés után érhető el.</p>
      <p><a href="index.jsp">Címlap</a></p>
    <% } else { %>
      <p>Kinyitható/becsukható lista</p>
      <%= lekerdez.getNyithatoCsukhatoLista() %>
      <p><a href="loginOK.jsp">Privát lap</a></p>
    <% } %>
  </body>
</html>
