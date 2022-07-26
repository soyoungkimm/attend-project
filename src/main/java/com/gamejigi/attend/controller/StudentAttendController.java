package com.gamejigi.attend.controller;

import com.gamejigi.attend.model.dto.*;
import com.gamejigi.attend.model.service.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@WebServlet(name = "student-attend", urlPatterns = { "/student-attend/list.do" })

public class StudentAttendController extends HttpServlet {

    StudentAttendServiceImpl studentAttendService = new StudentAttendServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    public void process(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {



        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        req.setCharacterEncoding("UTF-8");

        String uri = req.getRequestURI();
        String contextPath = req.getContextPath();
        String command = uri.substring(contextPath.length() + 1);
        String action = command.substring(command.lastIndexOf("/") + 1);



        // 리스트
        if (action.equals("list.do")) {

            int student_id = 1;
            int term = 1;

            String year = String.valueOf(LocalDate.now().getYear());
            int month = Integer.valueOf(LocalDate.now().getMonthValue());

            if (month < 8 && month > 1)
            {
                term = 1;
            }
            else
            {
                term = 2;
            }

            List<StudentAttendDTO> studentAttendList = new ArrayList<StudentAttendDTO>();
            studentAttendList = studentAttendService.getStudentAttendList(student_id, term);
            studentAttendService.setRestLecture(studentAttendList);

            req.setAttribute("year", year);
            req.setAttribute("term", term);
            req.setAttribute("studentAttendList", studentAttendList);
            req.getRequestDispatcher("/WEB-INF/views/stAttends/list.jsp").forward(req, resp);

        }

    }
}
