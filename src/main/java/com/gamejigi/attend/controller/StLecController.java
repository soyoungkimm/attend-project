package com.gamejigi.attend.controller;

import com.gamejigi.attend.model.dao.SubjectDAOImpl;
import com.gamejigi.attend.model.dto.LectureDTO;
import com.gamejigi.attend.model.dto.MylectureDTO;
import com.gamejigi.attend.model.dto.StLecDTO;
import com.gamejigi.attend.model.dto.SubjectDTO;
import com.gamejigi.attend.model.service.DepartServiceImpl;
import com.gamejigi.attend.model.service.LectureServiceImpl;
import com.gamejigi.attend.model.service.StLecServiceImpl;
import com.gamejigi.attend.model.service.SubjectServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import javax.servlet.annotation.MultipartConfig;

@WebServlet(name = "stLec", urlPatterns = {"/stLec/list.do", "/stLec/create.do", "/stLec/create-action.do",
        "/stLec/detail.do", "/stLec/delete.do", "/stLec/edit.do", "/stLec/edit-action.do"})

@MultipartConfig(
        fileSizeThreshold = 1024*1024,
        maxFileSize = 1024*1024*50, //50메가
        maxRequestSize = 1024*1024*50*5 // 50메가 5개까지
)

public class StLecController extends HttpServlet {

    StLecServiceImpl stLecService = new StLecServiceImpl();
    LectureServiceImpl lectureService = new LectureServiceImpl();
    DepartServiceImpl departService = new DepartServiceImpl();
    SubjectServiceImpl subjectService = new SubjectServiceImpl();
    SubjectDAOImpl subjectDAO = new SubjectDAOImpl();


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

        // 리스트
        if (action.equals("list.do")) {

            int student_id = 1;


            ArrayList<StLecDTO> stLecList;
            stLecList = (ArrayList<StLecDTO>) stLecService.getStLecList();

            ArrayList<MylectureDTO> mylectureList;
            mylectureList = (ArrayList<MylectureDTO>) stLecService.getMyLecList(student_id);

            // 포워딩
            req.setAttribute("stLecList", stLecList);
            req.setAttribute("mylectureList", mylectureList);
            req.getRequestDispatcher("/WEB-INF/views/stLec/list.jsp").forward(req, resp);

        }
/*
        //생성 폼
        else if (action.equals("create.do")) {

            ArrayList<DepartDTO> departList = new ArrayList<DepartDTO>();
            departList = (ArrayList<DepartDTO>)departService.getDepartList();

            req.setAttribute("departList", departList);

            req.getRequestDispatcher("/WEB-INF/views/stLec/create.jsp").forward(req, resp);
        }   */

        //DB 생성
        else if (action.equals("create-action.do")) {

            StLecDTO stLecDTO = new StLecDTO();
            LectureDTO lectureDTO;
            SubjectDTO subjectDTO;

            int num = Integer.parseInt(req.getParameter("num"));
            String[] row;
            int dupCount = 0;


            if (num!=0)
            {
                int result = 0;

                for (int n=0; n<num; n++)
                {

                    row = new String[num];
                    row[n] = req.getParameter("row"+(n+1));
                    String[] value = row[n].split(",");

                    int dup = stLecService.searchDupl(1, Integer.parseInt(value[11]));    // 중복 검사

                    if (dup == 0)
                    {
                        stLecDTO.setStudent_id(1);                                          // student_id
                        stLecDTO.setLecture_id(Integer.parseInt(value[11]));                // lecture_id
                        stLecDTO.setDepart_name(value[0]);                                  // depart_name
                        stLecDTO.setGrade(Integer.parseInt(value[1]));                      // grade

                        int a = stLecService.getTerm(Integer.parseInt(value[11]));
                        stLecDTO.setTerm(a);                                                // term

                        a = (Objects.equals(value[10], "정상")) ? 0 : 1;
                        stLecDTO.setRetake(a);                                              // retake

                        stLecService.createMyLecture(stLecDTO);
                    }


                }

                resp.sendRedirect("list.do");

            }
            else
            {
                resp.sendRedirect("list.do");
            }
        }

        // 삭제
        else if (action.equals("delete.do")) {

            int row_num = stLecService.deleteMyLecture(1, Integer.parseInt(req.getParameter("lec_id")));

            if(row_num == 0) {
                req.getRequestDispatcher("/stLec/list.do").forward(req, resp);
            }
        }

    }


}
