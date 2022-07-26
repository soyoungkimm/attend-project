package com.gamejigi.attend.controller;

import com.gamejigi.attend.model.dto.ADControlDTO;
import com.gamejigi.attend.model.dto.StudentAttendDTO;
import com.gamejigi.attend.model.dto.TeacherDTO;
import com.gamejigi.attend.model.service.ADControlServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "control", urlPatterns = { "/control/list.do", "/control/create-action.do", "/control/edit-action.do" })

public class ADControlController extends HttpServlet {

    ADControlServiceImpl adControlService = new ADControlServiceImpl();

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


            int count = adControlService.readCount();
            if (count < 1)
            {
                resp.sendRedirect("create-action.do");
            }
            else
            {
                ADControlDTO adControlDTO;
                adControlDTO = adControlService.findControl();

                req.setAttribute("adControl", adControlDTO);
                req.getRequestDispatcher("/WEB-INF/views/control/list.jsp").forward(req, resp);
            }

        }

        else if (action.equals("create-action.do")) {

            adControlService.createControl();
            resp.sendRedirect("list.do");

        }
        else if (action.equals("edit-action.do")) {
            ADControlDTO adControlDTO = new ADControlDTO();
            adControlDTO.setId(Integer.parseInt(req.getParameter("id")));
            adControlDTO.setSubjecttime(Integer.parseInt(req.getParameter("subjecttime")));
            adControlDTO.setLecturetime(Integer.parseInt(req.getParameter("lecturetime")));
            adControlDTO.setMylecturetime(Integer.parseInt(req.getParameter("mylecturetime")));
            adControlDTO.setAttendtime(Integer.parseInt(req.getParameter("attendtime")));




            Boolean isSuccess = adControlService.updateControl(adControlDTO);

            if(isSuccess) {
                resp.sendRedirect("list.do");
            }
        }

    }
}
