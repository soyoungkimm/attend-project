package com.gamejigi.attend.controller;

import com.gamejigi.attend.model.dao.DepartDAO;
import com.gamejigi.attend.model.dto.*;
import com.gamejigi.attend.model.service.*;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@WebServlet(name = "time", urlPatterns = {"/astime/time.do", "/astime/timeall.do", "/astime/search.do", "/astime/save.do"})
public class ASTimeController extends HttpServlet {
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

        if (action.equals("time.do")) {
            //조교ID를 가져온다.
            int staffId = 1; //로그인 부분 완성되면 구현

            //학과, 건물, 강의실 정보 가져오기
            StaffService staffService = new StaffServiceImpl();
            DepartService departService = new DepartServiceImpl();
            int departId = staffService.getStaff(staffId).getDepart_id();
            String departName = departService.findById(departId).getName();

            TimetableService timetableService = new TimetableServiceImpl();
            int gradeSystem = timetableService.getGradeSystemByStaffId(staffId);
            List<RoomDTO> rooms = timetableService.getRoomsByStaffId(staffId);
            List<BuildingDTO> buildings = timetableService.getBuildingsByRooms(rooms);
            List<List<Integer>> indexList = new ArrayList<>(buildings.size());
            for (int b = 0; b < buildings.size(); b++) {
                indexList.add(new ArrayList<>());
                for (int r = 0; r < rooms.size(); r++)
                    if (rooms.get(r).getBuilding_id() == buildings.get(b).getId())
                        indexList.get(b).add(r);
            }
            List<String> lectures = timetableService.getLecturesByStaffId(staffId);
            List<String> lectureNames = new ArrayList<>();
            for (String lecture : lectures) {
                String[] str = lecture.split("\\^");
                lectureNames.add(str[2]+"-"+str[3]+" ("+str[4]+"h) : "+str[8]);
            }

            req.setAttribute("gradeSystem", gradeSystem);
            req.setAttribute("rooms", rooms);
            req.setAttribute("buildings", buildings);
            req.setAttribute("indexList", indexList);
            req.setAttribute("departName", departName);
            req.setAttribute("lectures", lectures);
            req.setAttribute("lectureNames", lectureNames);
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/views/time/as_time.jsp");
            requestDispatcher.forward(req, resp);
        }
        else if (action.equals("timeall.do")) {
            //학과 id를 가져온다. 로그인 구현하고 구현
            int departId = 1;

            //학과정보 가져오기
            DepartService departService = new DepartServiceImpl();
            String departName = departService.findById(departId).getName();
            List<DepartDTO> departLists = departService.getDepartList();

            req.setAttribute("departName", departName);
            req.setAttribute("departLists", departLists);
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/views/time/as_timeall.jsp");
            requestDispatcher.forward(req, resp);
        }
        else if (action.equals("search.do")) {
            String termStr = req.getParameter("term");
            String[] termArr = termStr.split("\\^");
            int year = Integer.parseInt(termArr[0]);
            int term = Integer.parseInt(termArr[1]);
            int departId = Integer.parseInt(req.getParameter("departId"));

            //시간표 가져오기
            TimetableService timetableService = new TimetableServiceImpl();
            List<String> timetable = timetableService.loadData(departId, year, term);

            //시간표 전송
            resp.setContentType("text/plain");
            resp.setCharacterEncoding("UTF-8");
            PrintWriter out = resp.getWriter();
            out.print(String.join("/", timetable));
        }
        else if (action.equals("save.do")) {
            String saveStr = req.getParameter("save");
            String[] saveArr = saveStr.split("/");
            String termStr = req.getParameter("term");
            String[] termArr = termStr.split("\\^");
            int year = Integer.parseInt(termArr[0]);
            int term = Integer.parseInt(termArr[1]);

            //조교ID를 가져온다.
            int staffId = 1; //로그인 부분 완성되면 구현

            //저장하기
            TimetableService timetableService = new TimetableServiceImpl();
            timetableService.saveData(Arrays.asList(saveArr), staffId, year, term);

            //저장 완료 전송
            resp.setContentType("text/plain");
            resp.setCharacterEncoding("UTF-8");
            PrintWriter out = resp.getWriter();
            out.println("success!");
        }
    }
}
