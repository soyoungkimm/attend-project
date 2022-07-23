package com.gamejigi.attend.controller;

import com.gamejigi.attend.model.dto.*;
import com.gamejigi.attend.model.service.*;
import com.gamejigi.attend.util.Pagination;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@WebServlet(name = "TeLecMoveController", urlPatterns = {"/TeLecMove/list.do", "/TeLecMove/norm.do",
        "/TeLecMove/rest.do", "/TeLecMove/new.do", "/TeLecMove/cancel.do"})
public class TeLecMoveController extends HttpServlet {

    TeLecMoveService teLecMoveService = new TeLecMoveServiceImpl();
    TimetableService timetableService = new TimetableServiceImpl();

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

        int id = 1;
        int depart = 1;

        DepartDTO departDTO = (new DepartServiceImpl()).findById(depart);
        req.setAttribute("departName", departDTO.getName());

        TeacherDTO teacherDTO = (new TeacherServiceImpl()).getTeacher(id);
        req.setAttribute("teacherName", teacherDTO.getName());


        // 리스트
        if (action.equals("list.do")) {

            // 페이지네이션
            int totalRows = teLecMoveService.getTotalRowsByTeacher(id); // 행의 총 개수
            int perPageRows = 4; // 한 페이지에 나타날 행의 개수
            int perPagination = 5; // 한 화면에 나타날 페이지 번호 개수
            String pageNo = req.getParameter("p"); // 현재 페이지 번호
            int curPageNo = 1;
            if (pageNo != null) {
                curPageNo = Integer.parseInt(pageNo);
                if (curPageNo <= 0 || ((totalRows-1) / perPageRows+1) < curPageNo)
                    curPageNo = 1;
            }
            Pagination pagination = new Pagination(curPageNo, perPageRows, perPagination, totalRows);

            // 리스트 구해오기
            List<LectureDayDTO> lectureDays = new ArrayList<LectureDayDTO>();
            lectureDays = teLecMoveService.readListUsePaginationByTeacher(pagination, id);

            // 포워딩
            req.setAttribute("pagination", pagination);
            req.setAttribute("lectureDays", lectureDays);
            req.getRequestDispatcher("/WEB-INF/views/teLecMove/te_lecmove.jsp").forward(req, resp);
        }
        else if (action.equals("norm.do")) {
            int year = LocalDate.now().getYear();
            int month = LocalDate.now().getMonthValue();
            int term = (month < 8) ? 1 : 2;

            // 교수 시간표 가져오기
            List<String> timetable = timetableService.loadDataUseTeId(depart, year, term, id);

            // 포워딩
            req.setAttribute("timetable", timetable);
            req.getRequestDispatcher("/WEB-INF/views/teLecMove/te_lecmovenorm.jsp").forward(req, resp);
        }
        else if (action.equals("rest.do")) {
            String lectureStr = req.getParameter("sellecture");
            String[] lectureArr = lectureStr.split("\\^");

            int year = LocalDate.now().getYear();
            int month = LocalDate.now().getMonthValue();
            int term = (month < 8) ? 1 : 2;

            // 해당 학기 시간표 가져오기
            List<Integer[]> timetable = teLecMoveService.getTimetable(depart, year, term);

            String roomName = lectureArr[7];
            String buildingName = teLecMoveService.getBuildingNameByRoomName(roomName);

            List<Map<String, String>> buildingList = teLecMoveService.getBuildingListByDepart(depart);
            List<Map<String, String>> roomList = teLecMoveService.getRoomListByDepart(depart);

            // 포워딩
            req.setAttribute("depart", depart);
            req.setAttribute("sellecture", lectureStr);
            req.setAttribute("timetable", timetable);
            req.setAttribute("buildingName", buildingName);
            req.setAttribute("roomName", roomName);
            req.setAttribute("buildingList", buildingList);
            req.setAttribute("roomList", roomList);
            req.setAttribute("normdate", req.getParameter("normdate"));
            req.setAttribute("normweek", req.getParameter("normweek"));
            req.setAttribute("normstart", req.getParameter("normstart"));
            req.setAttribute("normhour", req.getParameter("normhour"));
            req.getRequestDispatcher("/WEB-INF/views/teLecMove/te_lecmoverest.jsp").forward(req, resp);
        }
        else if (action.equals("new.do")) {
            int roomno = Integer.parseInt(req.getParameter("room_no"));
            String normdate = req.getParameter("normdate");
            int normstart = Integer.parseInt(req.getParameter("normstart"));
            int normhour = Integer.parseInt(req.getParameter("normhour"));
            String restdate = req.getParameter("restdate");
            int reststart = Integer.parseInt(req.getParameter("reststart"));
            int resthour = Integer.parseInt(req.getParameter("resthour"));

            LectureDayDTO lectureDay = new LectureDayDTO();
            lectureDay.setRoomId(roomno);
            lectureDay.setNormstate(3);
            lectureDay.setReststate(0);
            lectureDay.setState(0);
            lectureDay.setNormdate(normdate);
            lectureDay.setNormhour(normhour);
            lectureDay.setNormstart(normstart);
            lectureDay.setRestdate(restdate);
            lectureDay.setResthour(resthour);
            lectureDay.setReststart(reststart);
            teLecMoveService.applyLecMove(lectureDay);

            // 포워딩
            req.getRequestDispatcher("/TeLecMove/list.do").forward(req, resp);
        }
        else if (action.equals("cancel.do")) {
            int lecDayId = Integer.parseInt(req.getParameter("id"));
            teLecMoveService.cancelLecMove(lecDayId);

            // 포워딩
            req.getRequestDispatcher("/TeLecMove/list.do").forward(req, resp);
        }
    }
}
