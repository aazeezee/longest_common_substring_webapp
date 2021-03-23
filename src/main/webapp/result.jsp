<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import ="java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Longest Common Substring Results</title>
    </head>
    <body>
        <center>
            <h1>
                LCS Industries, Inc.
            </h1>
            <h3>
                Here are your results:
            </h3>
            <p>
                <%
                    String result = (String) request.getAttribute("LCS");
                    out.println(result);
                %>
            </p>
            <br>
            <h3>
                Thank you!
            </h3>
            <br>
            <button onclick="window.location.href='/LongestCommonSubstringWebApp';">
                Return to Home
            </button>
    </body>
</html>