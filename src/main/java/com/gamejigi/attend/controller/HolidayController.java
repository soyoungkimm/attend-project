package com.gamejigi.attend.controller;

import com.gamejigi.attend.model.dto.HolidayDTO;
import com.gamejigi.attend.model.service.HolidayServiceImpl;
import com.gamejigi.attend.util.Pagination;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name="holidays", urlPatterns = {"/holidays/","/holidays/list.do", "/holidays/create.do","/holidays/create-action.do",
            "/holidays/detail.do", "/holidays/delete.do", "/holidays/edit.do", "/holidays/edit-action.do"})
public class HolidayController extends HttpServlet {

    HolidayServiceImpl holidayService = new HolidayServiceImpl();

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
        if(action.equals("list.do") || action.isEmpty()){

            // 검색키워드
            String search = req.getParameter("s"); // 검색어
            search = (search != null)? search : "";

            // 페이지네이션
            String pageNo = req.getParameter("p"); // 현재 페이지 번호
            int curPageNo = (pageNo != null)? Integer.parseInt(pageNo) : 1;
            int perPageRows = 4; // 한 페이지에 나타날 데이터 행의 개수
            int perPagination = 5; // 한 화면에 나타날 페이지 번호 개수
            int totalRows = holidayService.getHolidayTotalNum(search);
            Pagination pagination = new Pagination(curPageNo, perPageRows, perPagination, totalRows);

            //holiday리스트 저장
            ArrayList<HolidayDTO> holidayList = new ArrayList<HolidayDTO>();
            holidayList = (ArrayList<HolidayDTO>) holidayService.getHolidayList(pagination, search);

            // 포워딩
            req.setAttribute("search", search);
            req.setAttribute("pagination", pagination);
            req.setAttribute("holidayList", holidayList);
            req.getRequestDispatcher("/WEB-INF/views/holidays/list.jsp").forward(req, resp);
        
        }
        //휴일 생성 폼
        else if(action.equals("create.do")){
            req.getRequestDispatcher("/WEB-INF/views/holidays/create.jsp").forward(req, resp);
        }
        //휴일 DB생성
        else if(action.equals("create-action.do")){

            HolidayDTO holidayDTO = new HolidayDTO();
            holidayDTO.setYyyy(req.getParameter("yyyy"));
            holidayDTO.setHoliday(req.getParameter("holiday"));
            holidayDTO.setReason(req.getParameter("reason"));
            int rowNum = (int)holidayService.createHoliday(holidayDTO);

            if(rowNum > 0) {
                req.setAttribute("holiday", holidayDTO);
                req.getRequestDispatcher("/WEB-INF/views/holidays/detail.jsp").forward(req, resp);
            }
        
        }
        //휴일 상세
        else if(action.equals("detail.do")){
            HolidayDTO holidayDTO = new HolidayDTO();
            holidayDTO = holidayService.getHoliday(Long.parseLong(req.getParameter("id")));
            if(holidayDTO != null) {
                req.setAttribute("holiday", holidayDTO);
                req.getRequestDispatcher("/WEB-INF/views/holidays/detail.jsp").forward(req, resp);
            }

        }
        //휴일 삭제
        else if(action.equals("delete.do")){
            int row_num = holidayService.deleteHoliday(Long.parseLong(req.getParameter("id")));

            if(row_num > 0) {
                req.getRequestDispatcher("/holidays/list.do").forward(req, resp);
            }

        }
        //휴일 수정 폼
        else if(action.equals("edit.do")){
            HolidayDTO holidayDTO = new HolidayDTO();
            holidayDTO = holidayService.getHoliday(Long.parseLong(req.getParameter("id")));

            if(holidayDTO != null) {
                req.setAttribute("holiday", holidayDTO);
                req.getRequestDispatcher("/WEB-INF/views/holidays/edit.jsp").forward(req, resp);
            }

        }
        //휴일 DB수정
        else if(action.equals("edit-action.do")){
            HolidayDTO holidayDTO = new HolidayDTO();
            holidayDTO.setId(Long.parseLong(req.getParameter("id")));
            holidayDTO.setYyyy(req.getParameter("yyyy"));
            holidayDTO.setHoliday(req.getParameter("holiday"));
            holidayDTO.setReason(req.getParameter("reason"));
            int rowNum = (int)holidayService.updateHoliday(holidayDTO);

            if(rowNum > 0) {
                req.setAttribute("holiday", holidayDTO);
                req.getRequestDispatcher("/WEB-INF/views/holidays/detail.jsp").forward(req, resp);
            }

        }

    }

}
