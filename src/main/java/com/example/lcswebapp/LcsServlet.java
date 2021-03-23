package com.example.lcswebapp;

import org.json.simple.parser.ParseException;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.*;

@WebServlet(
        name = "lcsservlet",
        urlPatterns = "/LCS"
)
public class LcsServlet extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        JsonService jsonService = new JsonService();
        LcsProcessor lcsProcessor = new LcsProcessor();
        String userStrings = request.getParameter("userStrings");
        List<String> values;
        try {
            values = jsonService.getJsonValuesAsList(userStrings);
        } catch (ParseException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            handleRequest(request,
                    response,
                    "error",
                    "Invalid input; unable to parse input as it was not in JSON format",
                    "error.jsp");
            return;
        }

        if (values.size() == 0) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            handleRequest(request,
                    response,
                    "error",
                    "Invalid input; The \"setOfStrings\" JSON element must be present and must not be an empty set of strings",
                    "error.jsp");
            return;
        }

        Set<String> valueSet = new HashSet<>(values);
        if (valueSet.size() < values.size()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            handleRequest(request,
                    response,
                    "error",
                    "Invalid input; input must be a set of unique strings with no duplicates",
                    "error.jsp");
            return;
        }

        List<String> lcsList = lcsProcessor.handler(values);
        handleRequest(request,
                response,
                "LCS",
                jsonService.getListAsJson(lcsList),
                "result.jsp");
    }

    public void handleRequest(HttpServletRequest request,
                              HttpServletResponse response,
                              String attrib,
                              Object attribValue,
                              String destination) throws ServletException, IOException
    {
        request.setAttribute(attrib, attribValue);
        RequestDispatcher view = request.getRequestDispatcher(destination);
        view.forward(request, response);
    }
}