package com.gamejigi.attend.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "error", urlPatterns = {"/error/404.do", "/error/500.do", "/error/permission.do"})
public class ErrorController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    private void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");

        String uri = req.getRequestURI();
        String contextPath = req.getContextPath();
        String command = uri.substring(contextPath.length() + 1);
        String action = command.substring(command.lastIndexOf('/') + 1);

        if (action.equals("404.do")) {
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/views/error/404error.jsp");
            requestDispatcher.forward(req, resp);
        }
        else if (action.equals("500.do")) {
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/views/error/500error.jsp");
            requestDispatcher.forward(req, resp);
        }
        else if (action.equals("permission.do")) {
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/views/error/permission.jsp");
            requestDispatcher.forward(req, resp);
        }
    }
}
