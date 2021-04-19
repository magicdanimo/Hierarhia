<%-- 
    Document   : logout
    Created on : 2021.02.26., 9:35:12
    Author     : Kaczur
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="lekerdez" class="lekerdez.AdatbazisLekerdezBean" scope="session"/>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Oracle HR lekérdező 1.0</title>
  </head>
  <body>
    <h2>Kilép</h2>
    <p>privát elköszönő szöveg</p>
    <p><a href="index.jsp">Címlap</a></p>
    <%
      lekerdez.setLoginOK(false);
      session.removeAttribute("felhasznalonev");
      session.invalidate();
    %>
  </body>
</html>
