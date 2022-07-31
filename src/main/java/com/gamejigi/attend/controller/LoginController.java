package com.gamejigi.attend.controller;

import com.gamejigi.attend.model.dto.*;
import com.gamejigi.attend.model.service.StaffServiceImpl;
import com.gamejigi.attend.model.service.StudentServiceImpl;
import com.gamejigi.attend.model.service.TeacherService;
import com.gamejigi.attend.model.service.TeacherServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@WebServlet(name = "login", urlPatterns = {"/login/login.do", "/login/login-action.do", "/login/logout.do"})

public class LoginController extends HttpServlet {

    StaffServiceImpl staffService = new StaffServiceImpl();
    TeacherServiceImpl teacherService = new TeacherServiceImpl();
    StudentServiceImpl studentService = new StudentServiceImpl();


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
        String picPath = "/image/";

        if (action.equals("login.do")) {        // 로그인 화면


            req.getRequestDispatcher("/WEB-INF/views/login/login.jsp").forward(req, resp);

        } else if (action.equals("login-action.do")) {        // 로그인

            LoginDTO loginDTO = null;

            String uid = req.getParameter("login_uid");
            String pwd = req.getParameter("login_password");

            if (Objects.equals(uid, "admin") && Objects.equals(pwd, "1234"))    // 직원 로그인
            {
                loginDTO = new LoginDTO();

                loginDTO.setId(0);
                loginDTO.setUid("admin");
                loginDTO.setPwd("1234");
                loginDTO.setKind(0);

                session.setAttribute("logined", loginDTO);
                resp.sendRedirect(req.getContextPath() + "/main/ad.do");
            }


            else if ((loginDTO = staffService.loginCheck(uid, pwd)) != null && loginDTO.getUid() != null)         // 조교 로그인
            {
                loginDTO.setKind(1);

                StaffDTO staffDTO = new StaffDTO();
                staffDTO = staffService.getStaff(loginDTO.getId());
                loginDTO.setName(staffDTO.getName());

                loginDTO.setPic(staffDTO.getPic());
                loginDTO.setPicPath(picPath + "staff/");

                session.setAttribute("logined", loginDTO);
                resp.sendRedirect(contextPath + "/main/ad.do");
            }


            else if ((loginDTO = teacherService.loginCheck(uid, pwd)) != null && loginDTO.getUid() != null)       // 교수 로그인
            {
                loginDTO.setKind(2);

                TeacherDTO teacherDTO = new TeacherDTO();
                teacherDTO = teacherService.getTeacher(loginDTO.getId());
                loginDTO.setName(teacherDTO.getName());

                loginDTO.setPic(teacherDTO.getPic());
                loginDTO.setPicPath(picPath + "teacher/");

                session.setAttribute("logined", loginDTO);
                resp.sendRedirect(contextPath + "/main/teacher.do");
            }


            else if ((loginDTO = studentService.loginCheck(uid, pwd)) != null && loginDTO.getUid() != null)      // 학생 로그인
            {
                loginDTO.setKind(3);

                StudentDTO studentDTO = new StudentDTO();
                studentDTO = studentService.getStudent((long) loginDTO.getId());
                loginDTO.setName(studentDTO.getName());

                loginDTO.setPic(studentDTO.getPic());
                loginDTO.setPicPath(picPath + "student/");

                session.setAttribute("logined", loginDTO);
                resp.sendRedirect(contextPath + "/main/st.do");
            }

            else            // 로그인 실패
            {
                resp.sendRedirect("login.do");
            }


        } else if (action.equals("logout.do")) {       // 로그아웃
            session.invalidate();
            resp.sendRedirect("login.do");
        }
    }
}
