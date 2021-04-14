<%-- 
    Document   : loginEllenorzo
    Created on : 2021.02.26., 8:50:53
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
    <h2>Ezt sosem láthatja senki.</h2>
    <% if(request.getParameter("username")==null) { %>
      <p>Az oldal tartalma csak bejelentkezés után érhető el.</p>
      <p><a href="index.jsp">Címlap</a></p>
    <% } else {
      String felhasznalonev=request.getParameter("username");
      String jelszo=request.getParameter("password");
      if(lekerdez.siker(felhasznalonev,jelszo)==1) { //true) { //false) {
        lekerdez.setLoginOK(true);
        session.setAttribute("felhasznalonev", felhasznalonev);
        
        response.sendRedirect("hierarchia1.jsp");
      } else {
        lekerdez.setLoginOK(false);
        response.sendRedirect("loginHiba.jsp");
      }
    } %>
    <p><%= lekerdez.siker(request.getParameter("username"),request.getParameter("password")) %></p>
  </body>
</html>
