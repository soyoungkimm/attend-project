package com.gamejigi.attend.controller;

import com.gamejigi.attend.model.dto.*;
import com.gamejigi.attend.model.service.DailyAttendServiceImpl;
import com.gamejigi.attend.model.service.SubjectAttendServiceImpl;
import com.gamejigi.attend.model.service.TeacherServiceImpl;
import org.json.JSONObject;

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

@WebServlet(name = "subject-attend", urlPatterns = { "/subject-attend/list.do", "/subject-attend/detail.do",
        "/subject-attend/ajax-update-attend.do", "/subject-attend/update-all-attend.do" })
public class SubjectAttendController extends HttpServlet {

    SubjectAttendServiceImpl subjectAttendService = new SubjectAttendServiceImpl();

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

            int teacher_id = 1;// 임시

            // 과목 가져오기
            List<SubjectDTO> subjectList = subjectAttendService.getSubject(teacher_id);

            // 연도
            String year = req.getParameter("year");
            String today = String.valueOf(LocalDate.now().getYear()); // 오늘 날짜
            year = (year == null)? today : year;

            // 학기
            String term = req.getParameter("term");
            term = (term == null)? "1" : term;

            // 학년
            String grade = req.getParameter("grade");
            grade = (grade == null)? "1" : grade;

            // 반
            String ban = req.getParameter("ban");
            ban = (ban == null)? "A" : ban;

            // 과목
            String subject = req.getParameter("subject");
            subject = (subject == null)? String.valueOf(subjectList.get(0).getId()) : subject;

            // 교수정보 가져오기
            TeacherDTO teacherDTO = teacherService.getTeacher(teacher_id);

            // lecture_id 가져오기
            int lecture_id = subjectAttendService.getLectureId(year, Integer.parseInt(term), Integer.parseInt(grade), ban, Integer.parseInt(subject), teacher_id);

            // 학생 출결 리스트 가져오기
            List<SubjectAttendDTO> subjectAttendList = new ArrayList<SubjectAttendDTO>();
            subjectAttendList = subjectAttendService.getStudentAttendList(lecture_id);

            // 학생 출결에서 보강날 세팅
            subjectAttendService.setRestLecture(subjectAttendList, lecture_id);

            // 시작교시, 진행시간, 시작 날짜 가져오기
            int normstart, normhour;
            String normdate;
            if (lecture_id == 0) {
                normstart = 1;
                normhour = 1;
                normdate = "";
            }
            else {
                LectureDayDTO lectureDayDTO = subjectAttendService.getStartTeachingAndHourAndStartDate(lecture_id);
                normstart = lectureDayDTO.getNormstart();
                normhour = lectureDayDTO.getNormhour();
                normdate = lectureDayDTO.getNormdate();
            }

            // 날짜 가져오기
            List<String> dateList = lecture_id == 0 ? null : subjectAttendService.getDateList(normdate);

            // 보강 날짜 가져오기
            List<LectureDayDTO> restList = lecture_id == 0 ? null : subjectAttendService.getRestList(lecture_id);

            // 포워딩
            req.setAttribute("year", year);
            req.setAttribute("term", term);
            req.setAttribute("grade", grade);
            req.setAttribute("ban", ban);
            req.setAttribute("sub", subject);
            req.setAttribute("teacher", teacherDTO);
            req.setAttribute("start", normstart);
            req.setAttribute("hour", normhour);
            req.setAttribute("dateList", dateList);
            req.setAttribute("lecture_id", lecture_id);
            req.setAttribute("subjectList", subjectList);
            req.setAttribute("studentList", subjectAttendList);
            req.setAttribute("restList", restList);
            req.getRequestDispatcher("/WEB-INF/views/subjectAttends/list.jsp").forward(req, resp);
        }
        // 출석체크 ajax
        else if (action.equals("ajax-update-attend.do")) {
            int lecture_id = Integer.parseInt(req.getParameter("lecture_id"));
            int student_id = Integer.parseInt(req.getParameter("student_id"));
            int h = Integer.parseInt(req.getParameter("h"));
            int v = Integer.parseInt(req.getParameter("v"));
            HashMap<String,Integer> map = new HashMap<>();

            // 학생 출석 변경(재사용)
            dailyAttendService.updateAttend(lecture_id, student_id, h, v);

            // 전체 출결 상황 가져오기
            MyLectureDTO2 myLectureDTO = subjectAttendService.getLateAbsentScore(lecture_id, student_id);
            map.put("late", myLectureDTO.getIlate());
            map.put("absent", myLectureDTO.getIxhour());
            map.put("score", myLectureDTO.getIattend());

            // 리턴
            JSONObject json = new JSONObject(map);
            PrintWriter out = resp.getWriter();
            out.print(json);
        }
    }
}
