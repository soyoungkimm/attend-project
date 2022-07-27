package com.gamejigi.attend.controller;

import com.gamejigi.attend.model.dto.LectureDayDTO;
import com.gamejigi.attend.model.dto.NoticeDTO;
import com.gamejigi.attend.model.service.ADLecMoveServiceImpl;
import com.gamejigi.attend.model.service.NoticeServiceImpl;
import com.gamejigi.attend.model.service.StaffServiceImpl;
import com.gamejigi.attend.util.Pagination;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@WebServlet(name = "main", urlPatterns = { "/main/ad.do", "/main/notice-pagination.do", "/main/lec-pagination.do" })
public class MainController extends HttpServlet {
    NoticeServiceImpl noticeService = new NoticeServiceImpl();
    ADLecMoveServiceImpl adLecMoveService = new ADLecMoveServiceImpl();
    StaffServiceImpl staffService = new StaffServiceImpl();

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

        // 직원 메인
        if (action.equals("ad.do")) {

            // 임시
            int staff_id = 1;

            // 공지사항
            int perPageRows = 4; // 한 페이지에 나타날 행의 개수
            int perPagination = 5; // 한 화면에 나타날 페이지 번호 개수
            int totalRows = noticeService.getNoticeTotalNum(""); // 행의 총 개수
            Pagination pagination = new Pagination(1, perPageRows, perPagination, totalRows);
            ArrayList<NoticeDTO> noticeList = (ArrayList<NoticeDTO>) noticeService.getNoticeList(pagination, "");

            // 휴보강
            int depart_id = staffService.getDepartId(staff_id);
            int lec_perPageRows = 4; // 한 페이지에 나타날 행의 개수
            int lec_perPagination = 5; // 한 화면에 나타날 페이지 번호 개수
            int lec_totalRows = adLecMoveService.getRestTotalNum(depart_id); // 행의 총 개수
            Pagination lec_pagination = new Pagination(1, lec_perPageRows, lec_perPagination, lec_totalRows);
            List<LectureDayDTO> lectureDayList = adLecMoveService.readRestListByStateAndDepartId(lec_pagination, depart_id);

            // 포워딩
            req.setAttribute("pagination", pagination);
            req.setAttribute("lec_pagination", lec_pagination);
            req.setAttribute("noticeList", noticeList);
            req.setAttribute("lecturedayList", lectureDayList);
            req.getRequestDispatcher("/WEB-INF/views/main/ad_main.jsp").forward(req, resp);
        }
        else if (action.equals("notice-pagination.do")) {

            // 페이지네이션
            String pageNo = req.getParameter("page");
            int curPageNo = (pageNo != null)? Integer.parseInt(pageNo) : 1;
            int perPageRows = 4; // 한 페이지에 나타날 행의 개수
            int perPagination = 5; // 한 화면에 나타날 페이지 번호 개수
            int totalRows = noticeService.getNoticeTotalNum(""); // 행의 총 개수
            Pagination pagination = new Pagination(curPageNo, perPageRows, perPagination, totalRows);

            // 리스트 구해오기
            ArrayList<NoticeDTO> noticeList = (ArrayList<NoticeDTO>)noticeService.getNoticeList(pagination, "");

            HashMap<String, Object> map= new HashMap<>();
            map.put("noticeList", noticeList);
            map.put("pagination", pagination);

            // 리턴
            JSONObject json = new JSONObject(map);
            PrintWriter out = resp.getWriter();
            out.print(json);
        }
        else if (action.equals("lec-pagination.do")) {

            int staff_id = 1; // 임시
            int depart_id = staffService.getDepartId(staff_id);

            // 페이지네이션
            String pageNo = req.getParameter("page");
            int curPageNo = (pageNo != null)? Integer.parseInt(pageNo) : 1;
            int lec_perPageRows = 4; // 한 페이지에 나타날 행의 개수
            int lec_perPagination = 5; // 한 화면에 나타날 페이지 번호 개수
            int lec_totalRows = adLecMoveService.getRestTotalNum(depart_id); // 행의 총 개수
            Pagination pagination = new Pagination(curPageNo, lec_perPageRows, lec_perPagination, lec_totalRows);

            // 리스트 구해오기
            List<LectureDayDTO> lectureDayList = adLecMoveService.readRestListByStateAndDepartId(pagination, depart_id);

            HashMap<String, Object> map= new HashMap<>();
            map.put("lecList", lectureDayList);
            map.put("pagination", pagination);

            // 리턴
            JSONObject json = new JSONObject(map);
            PrintWriter out = resp.getWriter();
            out.print(json);
        }
    }
}
