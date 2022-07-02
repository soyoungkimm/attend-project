package com.gamejigi.attend.controller;

import com.gamejigi.attend.model.dto.NoticeDTO;
import com.gamejigi.attend.model.service.NoticeServiceImpl;
import com.gamejigi.attend.util.Pagination;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "notices", urlPatterns = { "/notices/list.do", "/notices/create.do", "/notices/create-action.do",
        "/notices/detail.do", "/notices/delete.do", "/notices/edit.do", "/notices/edit-action.do" })
public class NoticeController extends HttpServlet {

    NoticeServiceImpl noticeService = new NoticeServiceImpl();

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

        // 리스트
        if (action.equals("list.do")) {

            // 검색
            String search = req.getParameter("s"); // 검색어
            search = (search != null)? search : "";

            // 페이지네이션
            String pageNo = req.getParameter("p"); // 현재 페이지 번호
            int curPageNo = (pageNo != null)? Integer.parseInt(pageNo) : 1;
            int perPageRows = 4; // 한 페이지에 나타날 행의 개수
            int perPagination = 5; // 한 화면에 나타날 페이지 번호 개수
            int totalRows = noticeService.getNoticeTotalNum(search); // 행의 총 개수
            Pagination pagination = new Pagination(curPageNo, perPageRows, perPagination, totalRows);

            // 리스트 구해오기
            ArrayList<NoticeDTO> noticeList = new ArrayList<NoticeDTO>();
            noticeList = (ArrayList<NoticeDTO>)noticeService.getNoticeList(pagination, search);

            // 포워딩
            req.setAttribute("search", search);
            req.setAttribute("pagination", pagination);
            req.setAttribute("noticeList", noticeList);
            req.getRequestDispatcher("/WEB-INF/views/notices/list.jsp").forward(req, resp);
        }
        // 새로 만들기 폼
        else if (action.equals("create.do")) {
            req.getRequestDispatcher("/WEB-INF/views/notices/create.jsp").forward(req, resp);
        }
        // DB에 새로 생성
        else if (action.equals("create-action.do")) {

            NoticeDTO noticeDTO = new NoticeDTO();
            noticeDTO.setTitle(req.getParameter("title"));
            noticeDTO.setWriteday(req.getParameter("writeday"));
            noticeDTO.setContent(req.getParameter("content"));
            int rowNum = (int)noticeService.createNotice(noticeDTO);

            if(rowNum > 0) {
                req.setAttribute("notice", noticeDTO);
                req.getRequestDispatcher("/WEB-INF/views/notices/detail.jsp").forward(req, resp);
            }
        }
        // 자세히 보기
        else if (action.equals("detail.do")) {
            NoticeDTO noticeDTO = new NoticeDTO();
            noticeDTO = noticeService.getNotice(Long.parseLong(req.getParameter("id")));

            if(noticeDTO != null) {
                req.setAttribute("notice", noticeDTO);
                req.getRequestDispatcher("/WEB-INF/views/notices/detail.jsp").forward(req, resp);
            }
        }
        // 삭제
        else if (action.equals("delete.do")) {
            int row_num = noticeService.deleteNotice(Long.parseLong(req.getParameter("id")));

            if(row_num == 0) {
                req.getRequestDispatcher("/notices/list.do").forward(req, resp);
            }
        }
        // 수정하기 폼
        else if (action.equals("edit.do")) {
            NoticeDTO noticeDTO = new NoticeDTO();
            noticeDTO = noticeService.getNotice(Long.parseLong(req.getParameter("id")));

            if(noticeDTO != null) {
                req.setAttribute("notice", noticeDTO);
                req.getRequestDispatcher("/WEB-INF/views/notices/edit.jsp").forward(req, resp);
            }
        }
        // 공지사항 DB 수정
        else if (action.equals("edit-action.do")) {
            NoticeDTO noticeDTO = new NoticeDTO();
            noticeDTO.setId(Long.parseLong(req.getParameter("id")));
            noticeDTO.setTitle(req.getParameter("title"));
            noticeDTO.setWriteday(req.getParameter("writeday"));
            noticeDTO.setContent(req.getParameter("content"));
            int rowNum = (int)noticeService.updateNotice(noticeDTO);

            if(rowNum > 0) {
                req.setAttribute("notice", noticeDTO);
                req.getRequestDispatcher("/WEB-INF/views/notices/detail.jsp").forward(req, resp);
            }
        }
    }
}
