package com.gamejigi.attend.controller;

import com.gamejigi.attend.model.dto.LectureDayDTO;
import com.gamejigi.attend.model.service.ADLecMoveServiceImpl;
import com.gamejigi.attend.util.Pagination;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "asLecMove", urlPatterns = {"/asLecMove/main.do", "/asLecMove/approve.do", "/asLecMove/denial.do"})
public class ASLecMoveController extends HttpServlet {

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
        if(action.equals("main.do") || action.isEmpty()){

            ADLecMoveServiceImpl adLecMoveService = new ADLecMoveServiceImpl();

            // 페이지네이션
            String pageNo = req.getParameter("p"); // 현재 페이지 번호
            int curPageNo = (pageNo != null)? Integer.parseInt(pageNo) : 1;
            int perPageRows = 3; // 한 페이지에 나타날 데이터 행의 개수
            int perPagination = 5; // 한 화면에 나타날 페이지 번호 개수
            int totalRows = adLecMoveService.getRestTotalNum(1);
            Pagination pagination = new Pagination(curPageNo, perPageRows, perPagination, totalRows);

            //임시 - depart_id는 로그인된 정보에서 가져오도록 수정
            List<LectureDayDTO> lectureDayList = adLecMoveService.readRestListByStateAndDepartId(pagination,1);

            req.setAttribute("lectureDayList", lectureDayList);
            req.setAttribute("pagination", pagination);
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/views/lecMove/as_lecmove.jsp");
            requestDispatcher.forward(req, resp);
        }
        else if (action.equals("approve.do")){

            Long id = Long.parseLong(req.getParameter("id"));

            ADLecMoveServiceImpl adLecMoveService = new ADLecMoveServiceImpl();
            LectureDayDTO lectureDayDTO = adLecMoveService.getLectureDayDTO(id);
            if(lectureDayDTO.getState() == 0) {
                adLecMoveService.updateLectureDayState(id, 2);
            }

            resp.sendRedirect("../asLecMove/main.do");

        }
        else if (action.equals("denial.do")){

            Long id = Long.parseLong(req.getParameter("id"));

            ADLecMoveServiceImpl adLecMoveService = new ADLecMoveServiceImpl();
            LectureDayDTO lectureDayDTO = adLecMoveService.getLectureDayDTO(id);
            if(lectureDayDTO.getState() == 0) {
                adLecMoveService.updateLectureDayState(id, 3);
            }

            resp.sendRedirect("../asLecMove/main.do");
        }
    }
}
