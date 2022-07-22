package com.gamejigi.attend.controller;

import com.gamejigi.attend.model.dto.LectureDayDTO;
import com.gamejigi.attend.model.service.ADLecMoveServiceImpl;
import com.gamejigi.attend.model.service.LecMoveServiceImpl;
import com.gamejigi.attend.util.Pagination;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;


@WebServlet(name = "lecMove", urlPatterns = {"/lecMove/list.do", "/lecMove/edit.do"})
public class ADLecMoveController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    private void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        LecMoveServiceImpl lecMoveService = null;
        ADLecMoveServiceImpl adLecMoveService = null;

        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        req.setCharacterEncoding("UTF-8");

        String uri = req.getRequestURI();
        String contextPath = req.getContextPath();
        String command = uri.substring(contextPath.length() + 1);
        String action = command.substring(command.lastIndexOf("/") + 1);

        if(action.equals("list.do")){

            lecMoveService = new LecMoveServiceImpl();

            //검색
            LocalDate now = LocalDate.now();
            String term;
            if(now.getMonthValue()<=8){term = "1";}
            else {term = "2";}

            String sel1 = req.getParameter("sel1");
            String sel2 = req.getParameter("sel2");
            sel1 = (sel1 != null)? sel1 : "2022";
            sel2 = (sel2 != null)? sel2 : term;

            //페이지네이션
            String pageNo = req.getParameter("p"); // 현재 페이지 번호
            int curPageNo = (pageNo != null)? Integer.parseInt(pageNo) : 1;
            int perPageRows = 5; // 한 페이지에 나타날 데이터 행의 개수
            int perPagination = 5; // 한 화면에 나타날 페이지 번호 개수
            int totalNum = lecMoveService.getLecMoveTotalNum(sel1,sel2);
            Pagination pagination = new Pagination(curPageNo, perPageRows, perPagination, totalNum);
            ArrayList<LectureDayDTO> lecMoveList;
            lecMoveList = (ArrayList<LectureDayDTO>) lecMoveService.readLecMoveList(pagination,sel1,sel2);

            // 포워딩
            req.setAttribute("sel1", sel1);
            req.setAttribute("sel2", sel2);
            req.setAttribute("pagination", pagination);
            req.setAttribute("lecMoveList", lecMoveList);
            req.getRequestDispatcher("/WEB-INF/views/lecMove/ad_lecmove.jsp").forward(req, resp);
        }
        else if(action.equals("edit.do")){

            adLecMoveService = new ADLecMoveServiceImpl();

            int state = Integer.parseInt(req.getParameter("state"));
            Long id = Long.parseLong(req.getParameter("id"));
            String sel1 = req.getParameter("sel1");
            String sel2 = req.getParameter("sel2");

            adLecMoveService.updateLectureDayState(id,state);

            // 포워딩
            req.setAttribute("sel1", sel1);
            req.setAttribute("sel2", sel2);
            resp.sendRedirect("../lecMove/list.do");
        }
    }
}
