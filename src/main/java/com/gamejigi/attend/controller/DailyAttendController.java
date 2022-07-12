package com.gamejigi.attend.controller;

import com.gamejigi.attend.model.dto.LectureDayDTO;
import com.gamejigi.attend.model.dto.StudentDTO;
import com.gamejigi.attend.model.dto.TeacherDTO;
import com.gamejigi.attend.model.service.DailyAttendServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.gamejigi.attend.model.service.TeacherServiceImpl;
import org.json.JSONObject;

@WebServlet(name = "daily-attend", urlPatterns = { "/daily-attend/list.do", "/daily-attend/detail.do",
        "/daily-attend/ajax-update-attend.do", "/daily-attend/update-all-attend.do" })

public class DailyAttendController extends HttpServlet {

    DailyAttendServiceImpl dailyAttendService = new DailyAttendServiceImpl();
    TeacherServiceImpl teacherService = new TeacherServiceImpl();

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
            // 날짜
            String date = req.getParameter("date");
            String today = LocalDate.now().toString(); // 오늘 날짜
            date = (date != null)? date : today;

            // 교수정보 가져오기
            int teacher_id = 1; // 임시
            TeacherDTO teacherDTO = teacherService.getTeacher(teacher_id);

            // 리스트 구해오기
            List<LectureDayDTO> dailyAttendList = new ArrayList<LectureDayDTO>();
            dailyAttendList = dailyAttendService.getDailyAttendList(date, teacher_id);

            // 포워딩
            req.setAttribute("date", date);
            req.setAttribute("dailyAttendList", dailyAttendList);
            req.setAttribute("teacher", teacherDTO);
            req.getRequestDispatcher("/WEB-INF/views/dailyAttends/list.jsp").forward(req, resp);
        }
        // 출첵
        else if (action.equals("detail.do")) {
            Long lectureday_id = Long.parseLong(req.getParameter("id"));
            LectureDayDTO lectureDayDTO = new LectureDayDTO();
            List<StudentDTO> studentList = new ArrayList<>();
            HashMap<String,Integer> map = new HashMap<>();
            int appear, late, absent;

            // 강의 정보 가져오기
            lectureDayDTO = dailyAttendService.getLectureDayById(lectureday_id);

            // 강의 듣는 학생 가져오기
            studentList = dailyAttendService.getStudentAttend(lectureday_id);

            // 전체 출결 상황 가져오기
            map = dailyAttendService.getAllAttend(lectureday_id);
            appear = map.get("appear");
            late = map.get("late");
            absent = map.get("absent");

            req.setAttribute("lecture", lectureDayDTO);
            req.setAttribute("studentList", studentList);
            req.setAttribute("appear", appear);
            req.setAttribute("late", late);
            req.setAttribute("absent", absent);
            req.getRequestDispatcher("/WEB-INF/views/dailyAttends/detail.jsp").forward(req, resp);

        }
        // 출석체크 ajax
        else if (action.equals("ajax-update-attend.do")) {
            int lecture_id = Integer.parseInt(req.getParameter("lecture_id"));
            Long lectureday_id = Long.parseLong(req.getParameter("lectureday_id"));
            int student_id = Integer.parseInt(req.getParameter("student_id"));
            int h = Integer.parseInt(req.getParameter("h"));
            int v = Integer.parseInt(req.getParameter("v"));
            HashMap<String,Integer> map = new HashMap<>();

            // 학생 출석 변경
            dailyAttendService.updateAttend(lecture_id, student_id, h, v);

            // 전체 출결 상황 변경
            map = dailyAttendService.getAllAttend(lectureday_id);

            // 리턴
            JSONObject json = new JSONObject(map);
            PrintWriter out = resp.getWriter();
            out.print(json);
        }
        // 전체 출석으로
        else if (action.equals("update-all-attend.do")) {
            int lectureday_id = Integer.parseInt(req.getParameter("lectureday_id"));

            // 학생 출석 변경
            int result = dailyAttendService.updateAllAttend(lectureday_id);

            if (result > 0) resp.sendRedirect("detail.do?id=" + lectureday_id);
        }
    }
}
