package com.gamejigi.attend.controller;


import com.gamejigi.attend.model.dto.DepartDTO;
import com.gamejigi.attend.model.dto.TeacherDTO;
import com.gamejigi.attend.model.service.DepartServiceImpl;
import com.gamejigi.attend.model.service.TeacherServiceImpl;
import com.gamejigi.attend.model.service.TimetableServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.*;

@WebServlet(name = "teTime" ,urlPatterns = {"/time/te_time.do"})
public class TETimeController extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    private void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");

        String uri = req.getRequestURI();
        String contextPath = req.getContextPath();
        String command = uri.substring(contextPath.length() + 1);
        String action = command.substring(command.lastIndexOf('/') + 1);

        if(action.equals("te_time.do")){
            int teId = 1; //로그인시 구현
            int departId=1; //로그인시 구현

            LocalDate now = LocalDate.now();
            int year = now.getYear();
            int term = 0;
            if(now.getMonthValue()<=8){
                term = 1;
            }
            else {
                term = 2;
            }

            TimetableServiceImpl timetableService = new TimetableServiceImpl();
            DepartServiceImpl departService = new DepartServiceImpl();
            TeacherServiceImpl teacherService = new TeacherServiceImpl();

            List<String> timeTableList = timetableService.loadDataUseTeId(departId,year,term,teId);
            DepartDTO departDTO = departService.findById(departId);
            TeacherDTO teacherDTO = teacherService.getTeacher(teId);

            req.setAttribute("timeTableList",timeTableList);
            req.setAttribute("departName",departDTO.getName());
            req.setAttribute("teacherName",teacherDTO.getName());

            RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/views/time/te_time.jsp");
            requestDispatcher.forward(req, resp);

            System.out.println(year);
            System.out.println(term);
            for (String time : timeTableList){
                System.out.println(time);
            }
        }
    }
}
