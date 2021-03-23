<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import ="java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Error</title>
    </head>
    <body>
        <center>
            <h1>
                There was a problem
            </h1>
            <h2>
                <%
                    out.println("HTTP Status Code: " + response.getStatus());
                %>
            </h2>
            <p>
                <%
                    String result = (String) request.getAttribute("error");
                    out.println(result);
                %>
            </p>
            <br>
            <br>
            <button onclick="window.location.href='/LongestCommonSubstringWebApp';">
                Return to Home
            </button>
    </body>
</html>