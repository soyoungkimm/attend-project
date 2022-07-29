package com.gamejigi.attend.controller;


import com.gamejigi.attend.model.dto.DepartDTO;
import com.gamejigi.attend.model.dto.TeacherDTO;
import com.gamejigi.attend.model.dto.TimeTeacherDTO;
import com.gamejigi.attend.model.service.*;
import com.gamejigi.attend.util.Pagination;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@WebServlet(name = "timeTeacher" ,urlPatterns = {"/TimeTeacher/list.do"})
public class TimeTeacher extends HttpServlet{
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

        if(action.equals("list.do")) {
            int year, term, depart, page;

            String yearStr = req.getParameter("year");
            if (yearStr == null) {
                LocalDate now = LocalDate.now();
                year = now.getYear();
                term = now.getMonthValue() < 7 ? 1 : 2;
                depart = 0;
                page = 1;
            }
            else {
                year = Integer.parseInt(yearStr);
                term = Integer.parseInt(req.getParameter("term"));
                depart = Integer.parseInt(req.getParameter("depart"));
                page = Integer.parseInt(req.getParameter("page"));
            }
            req.setAttribute("year", year);
            req.setAttribute("term", term);
            req.setAttribute("depart", depart);

            TimeTeacherService timeTeacherService = new TimeTeacherServiceImpl();
            DepartService departService = new DepartServiceImpl();
            int perPageRows = 4; // 한 페이지에 나타날 데이터 행의 개수
            int perPagination = 5; // 한 화면에 나타날 페이지 번호 개수
            int totalRows = timeTeacherService.count(depart);
            Pagination pagination = new Pagination(page, perPageRows, perPagination, totalRows);

            List<DepartDTO> departsList = departService.getDepartList();
            List<TimeTeacherDTO> timeTableList = timeTeacherService.readListUsePagination(pagination, year, term, depart);
            List<Integer> yearList = timeTeacherService.getYearList(depart);

            req.setAttribute("timeTableList",timeTableList);
            req.setAttribute("yearList", yearList);
            req.setAttribute("teacherKindList", new String[]{"교수","겸임","시간"});
            req.setAttribute("departsList",departsList);
            req.setAttribute("pagination", pagination);

            RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/views/adTimeTeacher/timeteacher.jsp");
            requestDispatcher.forward(req, resp);
        }
    }
}
