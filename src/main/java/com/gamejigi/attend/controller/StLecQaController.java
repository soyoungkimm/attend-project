package com.gamejigi.attend.controller;

import com.gamejigi.attend.model.dao.MyLectureDAO;
import com.gamejigi.attend.model.dto.*;
import com.gamejigi.attend.model.service.*;
import com.gamejigi.attend.util.Pagination;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

@WebServlet(name = "stLecQa", urlPatterns = {"/lecQa/st_list.do", "/lecQa/st_create.do", "/lecQa/st_create-action.do", "/lecQa/st_edit.do",
        "/lecQa/st_edit-action.do", "/lecQa/teacher_name.do"})

public class StLecQaController extends HttpServlet {

    StLecQaServiceImpl stLecQaService = new StLecQaServiceImpl();
    StudentServiceImpl studentService = new StudentServiceImpl();
    StLecServiceImpl stLecService = new StLecServiceImpl();
    DepartServiceImpl departService = new DepartServiceImpl();
    LectureServiceImpl lectureService = new LectureServiceImpl();
    TeacherServiceImpl teacherService = new TeacherServiceImpl();
    LocalDate now = LocalDate.now();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    public void process(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        HttpSession session = req.getSession();

        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        req.setCharacterEncoding("UTF-8");

        String uri = req.getRequestURI();
        String contextPath = req.getContextPath();
        String command = uri.substring(contextPath.length() + 1);
        String action = command.substring(command.lastIndexOf("/") + 1);

        if (action.equals("st_list.do")) {

            LoginDTO loginDTO = new LoginDTO();
            Object o = session.getAttribute("logined");     // 세션 정보 저장
            loginDTO = (LoginDTO) o;
            int student_id = (loginDTO != null) ? loginDTO.getId() : 1;     // 세션 정보 없을때

            int year = now.getYear();                                                   // 년
            int term = (now.getMonthValue() > 1 && now.getMonthValue() < 8) ? 1 : 2;    // 학기


            String pageNo = req.getParameter("p"); // 현재 페이지 번호
            int curPageNo = (pageNo != null) ? Integer.parseInt(pageNo) : 1;
            int perPageRows = 5; // 한 페이지에 나타날 데이터 행의 개수
            int perPagination = 5; // 한 화면에 나타날 페이지 번호 개수
            int totalNum = stLecQaService.getLecQaListTotalNum(student_id);
            Pagination pagination = new Pagination(curPageNo, perPageRows, perPagination, totalNum);

            ArrayList<MylectureDTO> mylectureList = (ArrayList<MylectureDTO>) stLecQaService.getLecQaList(pagination, student_id);


            req.setAttribute("year", year);
            req.setAttribute("term", term);
            req.setAttribute("pagination", pagination);
            req.setAttribute("mylectureList", mylectureList);
            req.getRequestDispatcher("/WEB-INF/views/lecQa/st_lecqa_list.jsp").forward(req, resp);
        }

        else if (action.equals("st_create.do")) {

            LoginDTO loginDTO = new LoginDTO();
            Object o = session.getAttribute("logined");     // 세션 정보 저장
            loginDTO = (LoginDTO) o;
            int student_id = (loginDTO != null) ? loginDTO.getId() : 1;     // 세션 정보 없을때

            int year = now.getYear();
            int month = now.getMonthValue();
            int day = now.getDayOfMonth();
            int term = (month > 1 && month < 8) ? 1 : 2;

            StudentDTO studentDTO = studentService.getStudent((long) student_id);

            DepartDTO departDTO = departService.findById(studentDTO.getDepart_id());

            ArrayList<MylectureDTO> mylectureList;
            mylectureList = (ArrayList<MylectureDTO>) stLecService.getMyLecList(student_id);

            req.setAttribute("mylectureList", mylectureList);

            req.setAttribute("year", year);
            req.setAttribute("month", month);
            req.setAttribute("day", day);
            req.setAttribute("term", term);
            req.setAttribute("student", studentDTO);
            req.setAttribute("depart", departDTO);
            req.setAttribute("mylectureList", mylectureList);
            req.getRequestDispatcher("/WEB-INF/views/lecQa/st_lecqa_create.jsp").forward(req, resp);
        }

        else if (action.equals("st_create-action.do")) {

            LoginDTO loginDTO = new LoginDTO();
            Object o = session.getAttribute("logined");     // 세션 정보 저장
            loginDTO = (LoginDTO) o;
            int student_id = (loginDTO != null) ? loginDTO.getId() : 1;     // 세션 정보 없을때

            int year = now.getYear();
            int month = now.getMonthValue();
            int day = now.getDayOfMonth();
            int term = (month > 1 && month < 8) ? 1 : 2;




            MylectureDTO mylectureDTO = new MylectureDTO();
            mylectureDTO.setStudent_id(student_id);
            mylectureDTO.setLecture_id(Integer.parseInt(req.getParameter("subject")));
            mylectureDTO.setQday(req.getParameter("qawriteday"));
            mylectureDTO.setQtitle(req.getParameter("qatitle"));
            mylectureDTO.setQask(req.getParameter("qatxt1"));
            mylectureDTO.setQkind(1);

            if (stLecQaService.createLecQa(mylectureDTO) > 0) {
                resp.sendRedirect("st_list.do");
            }
        }

        else if (action.equals("st_edit.do")) {

            LoginDTO loginDTO = new LoginDTO();
            Object o = session.getAttribute("logined");     // 세션 정보 저장
            loginDTO = (LoginDTO) o;
            int student_id = (loginDTO != null) ? loginDTO.getId() : 1;     // 세션 정보 없을때

            int year = now.getYear();                                                   // 년
            int term = (now.getMonthValue() > 1 && now.getMonthValue() < 8) ? 1 : 2;    // 학기

            int mylecture_id = Integer.parseInt(req.getParameter("id"));
            MylectureDTO mylecture = stLecQaService.getLecQa(mylecture_id, student_id);

            StudentDTO studentDTO = studentService.getStudent((long) student_id);

            req.setAttribute("year", year);
            req.setAttribute("term", term);
            req.setAttribute("mylecture", mylecture);
            req.setAttribute("student", studentDTO);
            req.getRequestDispatcher("/WEB-INF/views/lecQa/st_lecqa_edit.jsp").forward(req, resp);
        }

        else if (action.equals("st_edit-action.do")) {

            MylectureDTO mylectureDTO = new MylectureDTO();
            mylectureDTO.setId(Integer.parseInt(req.getParameter("id")));
            mylectureDTO.setQtitle(req.getParameter("qatitle"));
            mylectureDTO.setQask(req.getParameter("qatxt1"));

            if (stLecQaService.updateLecQa(mylectureDTO) > 0) {
                resp.sendRedirect("st_list.do");
            }
        }

        else if (action.equals("teacher_name.do")) {

            int lecture_id = Integer.parseInt(req.getParameter("lecture_id"));
            if (lecture_id == 0)
            {
                HashMap<String, String> map = new HashMap<>();
                map.put("name", "");

                JSONObject json = new JSONObject(map);
                PrintWriter out = resp.getWriter();
                out.print(json);
            }
            else
            {
                LectureDTO lectureDTO = lectureService.getLecture(lecture_id);
                TeacherDTO teacherDTO = teacherService.getTeacher(lectureDTO.getTeacher_id());

                HashMap<String, String> map = new HashMap<>();
                map.put("name", teacherDTO.getName());

                JSONObject json = new JSONObject(map);
                PrintWriter out = resp.getWriter();
                out.print(json);
            }
        }
    }
}


