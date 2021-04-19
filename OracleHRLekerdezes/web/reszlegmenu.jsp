<%-- 
    Document   : reszlegmenu
    Created on : Apr 13, 2021, 4:28:09 PM
    Author     : win7
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="lekerdez" class="lekerdez.AdatbazisLekerdezBean" scope="session"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="reszlegCSS.css" rel="stylesheet" type="text/css"/>
        <title>Részleg menü</title>
    </head>
    <body>
        <!--mega menü linkek legenerálása sqlböl-->
        <p><%= lekerdez.megaMenu() %><p>
    </body>
</html>
