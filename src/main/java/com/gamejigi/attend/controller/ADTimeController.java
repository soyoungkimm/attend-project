package com.gamejigi.attend.controller;

import com.gamejigi.attend.model.dto.DepartDTO;
import com.gamejigi.attend.model.service.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "adTimes", urlPatterns = {"/adTimes/timeAll.do", "/adTimes/search.do"})
public class ADTimeController extends HttpServlet {
    DepartServiceImpl departService = new DepartServiceImpl();

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

        //휴일 목록
        if(action.equals("timeAll.do") || action.isEmpty()){

            List<DepartDTO> departLists = departService.getDepartList();

            req.setAttribute("departLists", departLists);
            req.getRequestDispatcher("/WEB-INF/views/adTime/ad_timeall.jsp").forward(req, resp);

        }else if(action.equals("search.do")){

            int term = Integer.parseInt(req.getParameter("term"));
            int yyyy = Integer.parseInt(req.getParameter("yyyy"));
            int departId = Integer.parseInt(req.getParameter("depart"));

            //시간표 가져오기
            TimetableService timetableService = new TimetableServiceImpl();
            List<String> timetable = timetableService.loadData(departId, yyyy, term);

            //시간표 전송
            resp.setContentType("text/plain;charset=UTF-8");
            PrintWriter out = resp.getWriter();
            out.print(String.join("/", timetable));
            out.close();
        }
    }
}
