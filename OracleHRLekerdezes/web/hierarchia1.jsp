
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="lekerdez" class="lekerdez.AdatbazisLekerdezBean" scope="session"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="hierarhia.css" rel="stylesheet" type="text/css"/>
        <title>JSP Page</title>
    </head>
    <body>
         <% if(session.getAttribute("felhasznalonev")==null) { %>
      <h2>Privát lap</h2>
      <p>Az oldal tartalma csak bejelentkezés után érhető el.</p>
      <p><a href="index.jsp">Címlap</a></p>
    <% } else { %>
    <!--todo alkalamzott mezők kiirni last name job tiitle, hierarhia elő állitása,-->
      <h2>Privát lap (bejelentkezve: <%= session.getAttribute("felhasznalonev") %>, munkakör: <%= session.getAttribute("jobTitle")%>) </h2>
      <p><%= lekerdez.hierarhia() %><p>
      <p><a href="privatDolgozok.jsp">Részlegek és alkalmazottak alapadatai</a></p>
      <p>...<br>...</p>
      <p><a href="logout.jsp">Kilép</a></p>
    <% } %>
    </body>
</html>
