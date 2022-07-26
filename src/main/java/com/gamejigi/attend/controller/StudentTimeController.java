package com.gamejigi.attend.controller;

import com.gamejigi.attend.model.dto.StudentDTO;
import com.gamejigi.attend.model.service.StudentServiceImpl;
import com.gamejigi.attend.model.service.TimetableServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "student-time", urlPatterns = { "/student-time/list.do" })
public class StudentTimeController extends HttpServlet {
    StudentServiceImpl studentService = new StudentServiceImpl();
    TimetableServiceImpl timetableService = new TimetableServiceImpl();

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

            int student_id = 1;

            // 학생 가져오기
            StudentDTO studentDTO = studentService.getStudent((long) student_id);

            // 시간표 가져오기
            List<String> timetable = timetableService.getDataByStudentId(student_id);

            req.setAttribute("student", studentDTO);
            req.setAttribute("timeTableList", timetable);
            req.getRequestDispatcher("/WEB-INF/views/stTime/list.jsp").forward(req, resp);
        }

    }
}
