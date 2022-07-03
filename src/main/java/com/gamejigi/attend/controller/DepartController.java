package com.gamejigi.attend.controller;

import com.gamejigi.attend.model.dto.DepartDTO;
import com.gamejigi.attend.model.service.DepartService;
import com.gamejigi.attend.model.service.DepartServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "depart", urlPatterns = {"/depart/list.do", "/depart/new.do", "/depart/edit.do", "/depart/delete.do"})
public class DepartController extends HttpServlet {
    DepartService ds = null;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ds = new DepartServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    private void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");

        String uri = req.getRequestURI();
        String contextPath = req.getContextPath();
        String command = uri.substring(contextPath.length() + 1);
        String action = command.substring(command.lastIndexOf('/') + 1);

        if (action.equals("list.do")) {
            String keyword = req.getParameter("text1");
            keyword = keyword != null ? keyword : "";

            String pageStr = req.getParameter("page");
            int page = pageStr != null ? Integer.valueOf(pageStr) : 1;
            int pageRows = 5;
            int pageNums = 5;
            int totalRows = ds.countByName(keyword);

            System.out.println(keyword);
            System.out.println(totalRows);

            int startRow = (page - 1) * pageRows + 1;
            startRow = startRow <= totalRows ? startRow : totalRows - totalRows % pageRows + 1;
            page = startRow / pageRows + 1;
            int count = pageRows + startRow - 1 <= totalRows ? pageRows : totalRows % pageRows;

            System.out.println(count);

            List<DepartDTO> departs = new ArrayList<>();
            if (count != 0)
                departs = ds.searchByName(keyword, startRow - 1, count);
            int pageBegin = page - pageNums/2;
            pageBegin = Math.max(pageBegin, 1);
            int pageEnd = page + pageNums/2 - (pageNums+1)%2;
            pageEnd = Math.min(pageEnd, (totalRows + pageRows - 1) / pageRows);
            List<Integer> pageList = new ArrayList<>();
            for (int i = pageBegin; i <= pageEnd; i++) {
                pageList.add(i);
            }

            req.setAttribute("departs", departs);
            req.setAttribute("page", page);
            req.setAttribute("pageBegin", pageBegin);
            req.setAttribute("pageEnd", pageEnd);
            req.setAttribute("pageList", pageList);
            req.setAttribute("text1", keyword);
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/views/depart/list.jsp");
            requestDispatcher.forward(req, resp);
        }
        else if (action.equals("new.do")) {
            String idStr = req.getParameter("id");
            String name = req.getParameter("name");
            String classNumStr = req.getParameter("classnum");
            String gradeSystemStr = req.getParameter("gradesystem");
            if (idStr == null) {
                RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/views/depart/create.jsp");
                requestDispatcher.forward(req, resp);
                return;
            }

            DepartDTO depart = new DepartDTO();
            depart.setId(Integer.valueOf(idStr));
            depart.setName(name);
            depart.setClassNum(Integer.valueOf(classNumStr));
            depart.setGradeSystem(Integer.valueOf(gradeSystemStr));
            ds.create(depart);

            RequestDispatcher requestDispatcher = req.getRequestDispatcher("/depart/list.do");
            requestDispatcher.forward(req, resp);
        }
        else if (action.equals("edit.do")) {
            String idStr = req.getParameter("id");
            String name = req.getParameter("name");
            String classNumStr = req.getParameter("classnum");
            String gradeSystemStr = req.getParameter("gradesystem");
            if (idStr == null) {
                RequestDispatcher requestDispatcher = req.getRequestDispatcher("/depart/list.do");
                requestDispatcher.forward(req, resp);
                return;
            }
            else if (name == null) {
                DepartDTO depart = ds.findById(Integer.valueOf(idStr));
                req.setAttribute("depart", depart);
                RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/views/depart/edit.jsp");
                requestDispatcher.forward(req, resp);
                return;
            }

            DepartDTO depart = new DepartDTO();
            depart.setId(Integer.valueOf(idStr));
            depart.setName(name);
            depart.setClassNum(Integer.valueOf(classNumStr));
            depart.setGradeSystem(Integer.valueOf(gradeSystemStr));
            ds.update(depart);

            RequestDispatcher requestDispatcher = req.getRequestDispatcher("/depart/list.do");
            requestDispatcher.forward(req, resp);
        }
        else if (action.equals("delete.do")) {
            String idStr = req.getParameter("id");
            if (idStr == null) {
                RequestDispatcher requestDispatcher = req.getRequestDispatcher("/depart/list.do");
                requestDispatcher.forward(req, resp);
                return;
            }

            ds.delete(Integer.valueOf(idStr));
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("/depart/list.do");
            requestDispatcher.forward(req, resp);
        }
    }
}