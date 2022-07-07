package com.gamejigi.attend.controller;

import com.gamejigi.attend.model.dto.DepartDTO;
import com.gamejigi.attend.model.dto.StaffDTO;
import com.gamejigi.attend.model.service.DepartServiceImpl;
import com.gamejigi.attend.model.service.StaffServiceImpl;
import com.gamejigi.attend.util.Pagination;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.annotation.MultipartConfig;

@WebServlet(name = "staff", urlPatterns = {"/staff/list.do", "/staff/create.do", "/staff/create-action.do",
        "/staff/detail.do", "/staff/delete.do", "/staff/edit.do", "/staff/edit-action.do"})

@MultipartConfig(
        fileSizeThreshold = 1024*1024,
        maxFileSize = 1024*1024*50, //50메가
        maxRequestSize = 1024*1024*50*5 // 50메가 5개까지
)

public class StaffController extends HttpServlet {

    StaffServiceImpl staffService = new StaffServiceImpl();
    DepartServiceImpl departService = new DepartServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        req.setCharacterEncoding("UTF-8");

        String uri = req.getRequestURI();
        String contextPath = req.getContextPath();
        String command = uri.substring(contextPath.length() + 1);
        String action = command.substring(command.lastIndexOf("/") + 1);
        String absolutePath = req.getServletContext().getRealPath("/image/staff");

        // 리스트
        if (action.equals("list.do")) {

            // 검색
            String search = req.getParameter("s"); // 검색어
            search = (search != null)? search : "";

            // 페이지네이션
            String pageNo = req.getParameter("p"); // 현재 페이지 번호
            int curPageNo = (pageNo != null) ? Integer.parseInt(pageNo) : 1;
            int perPageRows = 4; // 한 페이지에 나타날 데이터 행의 개수
            int perPagination = 5; // 한 화면에 나타날 페이지 번호 개수
            int totalRows = staffService.getStaffTotalNum(search); // 행의 총 개수
            Pagination pagination = new Pagination(curPageNo, perPageRows, perPagination, totalRows);

            // 교수 리스트 저장
            ArrayList<StaffDTO> staffList;
            staffList = (ArrayList<StaffDTO>) staffService.getStaffList(pagination, search);

            // 포워딩
            req.setAttribute("search", search);
            req.setAttribute("pagination", pagination);
            req.setAttribute("staffList", staffList);
            req.getRequestDispatcher("/WEB-INF/views/staff/list.jsp").forward(req, resp);

        }

        //생성 폼
        else if (action.equals("create.do")) {

            ArrayList<DepartDTO> departList = new ArrayList<DepartDTO>();
            departList = (ArrayList<DepartDTO>)departService.getDepartList();

            req.setAttribute("departList", departList);

            req.getRequestDispatcher("/WEB-INF/views/staff/create.jsp").forward(req, resp);
        }
        //DB 생성
        else if (action.equals("create-action.do")) {

            StaffDTO staffDTO = new StaffDTO();
            staffDTO.setDepart_id(Integer.parseInt(req.getParameter("depart_id")));
            staffDTO.setUid(req.getParameter("uid"));
            staffDTO.setPwd(req.getParameter("pwd"));
            staffDTO.setName(req.getParameter("name"));
            String tel = req.getParameter("tel1") + req.getParameter("tel2") + req.getParameter("tel3");
            staffDTO.setTel(tel);
            String phone = req.getParameter("phone1") + req.getParameter("phone2") + req.getParameter("phone3");
            staffDTO.setPhone(phone);
            staffDTO.setEmail(req.getParameter("email"));
            Part part = req.getPart("pic");

            Boolean isSuccess = staffService.createStaff(staffDTO, part, absolutePath);

            if(isSuccess) {
                req.setAttribute("absolutePath", absolutePath);
                req.setAttribute("staff", staffDTO);
                req.getRequestDispatcher("/WEB-INF/views/staff/detail.jsp").forward(req, resp);
            }

        }
        //상세
        else if (action.equals("detail.do")) {
            StaffDTO staffDTO;
            staffDTO = staffService.getStaff(Integer.parseInt(req.getParameter("id")));
            if (staffDTO != null) {
                req.setAttribute("staff", staffDTO);
                req.getRequestDispatcher("/WEB-INF/views/staff/detail.jsp").forward(req, resp);
            }

        }

        // 삭제
        else if (action.equals("delete.do")) {
            int row_num = staffService.deleteStaff(Integer.parseInt(req.getParameter("id")), absolutePath);

            if(row_num == 0) {
                req.getRequestDispatcher("/staff/list.do").forward(req, resp);
            }
        }

        // 수정하기 폼
        else if (action.equals("edit.do")) {
            StaffDTO staffDTO;
            staffDTO = staffService.getStaff(Integer.parseInt(req.getParameter("id")));

            // 학과 리스트 구해오기
            ArrayList<DepartDTO> departList = new ArrayList<DepartDTO>();
            departList = (ArrayList<DepartDTO>)departService.getDepartList();

            if(staffDTO != null) {
                req.setAttribute("departList", departList);
                req.setAttribute("staff", staffDTO);
                req.getRequestDispatcher("/WEB-INF/views/staff/edit.jsp").forward(req, resp);
            }
        }
        // 수정
        else if (action.equals("edit-action.do")) {

            StaffDTO staffDTO = new StaffDTO();
            staffDTO.setId(Integer.parseInt(req.getParameter("id")));
            staffDTO.setDepart_id(Integer.parseInt(req.getParameter("depart_id")));
            staffDTO.setUid(req.getParameter("uid"));
            staffDTO.setPwd(req.getParameter("pwd"));
            staffDTO.setName(req.getParameter("name"));
            String tel = req.getParameter("tel1") + req.getParameter("tel2") + req.getParameter("tel3");
            staffDTO.setTel(tel);
            String phone = req.getParameter("phone1") + req.getParameter("phone2") + req.getParameter("phone3");
            staffDTO.setPhone(phone);
            staffDTO.setEmail(req.getParameter("email"));
            Part part = req.getPart("pic");

            Boolean isSuccess = staffService.updateStaff(staffDTO, part, absolutePath);

            if(isSuccess) {
                req.setAttribute("absolutePath", absolutePath);
                req.setAttribute("staff", staffDTO);
                req.getRequestDispatcher("/WEB-INF/views/staff/detail.jsp").forward(req, resp);
            }
        }

    }


}
