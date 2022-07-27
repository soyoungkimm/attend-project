package com.gamejigi.attend.controller;


import com.gamejigi.attend.model.dto.MylectureDTO;
import com.gamejigi.attend.model.service.TeLecQaServiceImpl;
import com.gamejigi.attend.util.Pagination;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "teLecQa", urlPatterns = {"/lecQa/te_list.do", "/lecQa/te_edit.do","/lecQa/te_edit-action.do"})
public class TeLecQaController extends HttpServlet {
    TeLecQaServiceImpl teLecQaService = new TeLecQaServiceImpl();

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

        if(action.equals("te_list.do")){

            String pageNo = req.getParameter("p"); // 현재 페이지 번호
            int curPageNo = (pageNo != null)? Integer.parseInt(pageNo) : 1;
            int perPageRows = 5; // 한 페이지에 나타날 데이터 행의 개수
            int perPagination = 5; // 한 화면에 나타날 페이지 번호 개수
            int totalNum = teLecQaService.getLecQaListTotalNum();
            Pagination pagination = new Pagination(curPageNo, perPageRows, perPagination, totalNum);

            ArrayList<MylectureDTO> mylectureList = (ArrayList<MylectureDTO>) teLecQaService.getLecQaList(pagination);

            req.setAttribute("pagination", pagination);
            req.setAttribute("mylectureList", mylectureList);

            req.getRequestDispatcher("/WEB-INF/views/lecQa/te_lecqalist.jsp").forward(req, resp);
        }
        else if(action.equals("te_edit.do")){

            MylectureDTO mylectureDTO = new MylectureDTO();
            mylectureDTO.setId(Integer.parseInt(req.getParameter("id")));

            MylectureDTO mylecture = teLecQaService.getLecQa(mylectureDTO);

            req.setAttribute("mylecture",mylecture);
            req.getRequestDispatcher("/WEB-INF/views/lecQa/te_lecqaedit.jsp").forward(req, resp);
        }
        else if(action.equals("te_edit-action.do")){

            MylectureDTO mylectureDTO = new MylectureDTO();
            mylectureDTO.setId(Integer.parseInt(req.getParameter("id")));

            if((req.getParameter("qatxt2") != null)) {
                mylectureDTO.setQanswer(req.getParameter("qatxt2"));
                mylectureDTO.setQkind(2);
                if(teLecQaService.editLecQaList(mylectureDTO)>0){
                    resp.sendRedirect("../lecQa/te_list.do");
                }
            }
        }
    }
}
