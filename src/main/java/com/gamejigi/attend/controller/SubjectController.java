package com.gamejigi.attend.controller;

import com.gamejigi.attend.model.dto.DepartDTO;
import com.gamejigi.attend.model.dto.SubjectDTO;
import com.gamejigi.attend.model.service.DepartServiceImpl;
import com.gamejigi.attend.model.service.SubjectServiceImpl;
import com.gamejigi.attend.util.Pagination;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name="subject", urlPatterns = {"/subject/list.do" , "/subject/create.do", "/subject/create-action.do","/subject/edit.do","/subject/edit-action.do","/subject/delete.do","/subject/detail.do"})
public class SubjectController extends HttpServlet {

    SubjectServiceImpl subjectService = new SubjectServiceImpl();
    DepartServiceImpl departService = new DepartServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    public void process(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException{

        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        req.setCharacterEncoding("UTF-8");

        String uri = req.getRequestURI();
        String contextPath = req.getContextPath();
        String command = uri.substring(contextPath.length() + 1);
        String action = command.substring(command.lastIndexOf("/") + 1);

        ArrayList<DepartDTO> departList = (ArrayList<DepartDTO>) departService.getDepartList();

        req.setAttribute("departList", departList);

        if(action.equals("list.do")){

            String searchY = req.getParameter("s1"); //년도 검색
            searchY = (searchY != null) ? searchY : "";
            String searchG = req.getParameter("s2"); //학년 검색
            searchG = (searchG != null) ? searchG : "0";

            String pageNo = req.getParameter("p"); // 현재 페이지 번호
            int curPageNo = (pageNo != null)? Integer.parseInt(pageNo) : 1;
            int perPageRows = 4; // 한 페이지에 나타날 행의 개수
            int perPagination = 5; // 한 화면에 나타날 페이지 번호 개수
            int totalRows = subjectService.getSubjectTotalNum(searchY,searchG); // 행의 총 개수
            Pagination pagination = new Pagination(curPageNo, perPageRows, perPagination, totalRows);

            //리스트 읽기
            ArrayList<SubjectDTO> subjectList = new ArrayList<>();
            subjectList=(ArrayList<SubjectDTO>) subjectService.getSubjectList(pagination, searchY, searchG);

            req.setAttribute("searchY", searchY);
            req.setAttribute("searchG", searchG);
            req.setAttribute("pagination", pagination);
            req.setAttribute("subjectList", subjectList);
            req.getRequestDispatcher("/WEB-INF/views/subject/list.jsp").forward(req, resp);
        }
        else if(action.equals("create.do")){
            req.getRequestDispatcher("/WEB-INF/views/subject/create.jsp").forward(req, resp);
        }
        else if(action.equals("create-action.do")){
            SubjectDTO subjectDTO = new SubjectDTO();
            //subjectDTO.setDepart_id(Integer.parseInt(req.getParameter("depart_id")));
            //로그인 구현 시 기능 추가 필요
            subjectDTO.setCode(req.getParameter("code"));
            subjectDTO.setYyyy(Integer.parseInt(req.getParameter("yyyy")));
            subjectDTO.setGrade(Integer.parseInt(req.getParameter("grade")));
            subjectDTO.setTerm(Integer.parseInt(req.getParameter("term")));
            subjectDTO.setIsmajor(Integer.parseInt(req.getParameter("ismajor")));
            subjectDTO.setIschoice(Integer.parseInt(req.getParameter("ischoice")));
            subjectDTO.setIspractice(Integer.parseInt(req.getParameter("ispractice")));
            subjectDTO.setName(req.getParameter("name"));
            subjectDTO.setPoint(Integer.parseInt(req.getParameter("point")));
            subjectDTO.setHour(Integer.parseInt(req.getParameter("hour")));

            if(subjectService.createSubject(subjectDTO)>0){
                req.setAttribute("subject", subjectDTO);
                req.getRequestDispatcher("/WEB-INF/views/subject/detail.jsp").forward(req, resp);
            }
        }
        else if(action.equals("edit.do")){
            SubjectDTO subject = new SubjectDTO();
            subject.setId(Integer.parseInt(req.getParameter("id")));

            SubjectDTO subjectDTO = subjectService.getSubject(subject);

            if(subjectDTO != null){
                req.setAttribute("subject", subjectDTO);
                req.getRequestDispatcher("/WEB-INF/views/subject/edit.jsp").forward(req, resp);
            }
        }
        else if(action.equals("edit-action.do")){
            SubjectDTO subjectDTO = new SubjectDTO();
            subjectDTO.setId(Integer.parseInt(req.getParameter("id")));
            subjectDTO.setDepart_id(Integer.parseInt(req.getParameter("depart_id")));
            subjectDTO.setCode(req.getParameter("code"));
            subjectDTO.setYyyy(Integer.parseInt(req.getParameter("yyyy")));
            subjectDTO.setGrade(Integer.parseInt(req.getParameter("grade")));
            subjectDTO.setTerm(Integer.parseInt(req.getParameter("term")));
            subjectDTO.setIsmajor(Integer.parseInt(req.getParameter("ismajor")));
            subjectDTO.setIschoice(Integer.parseInt(req.getParameter("ischoice")));
            subjectDTO.setIspractice(Integer.parseInt(req.getParameter("ispractice")));
            subjectDTO.setName(req.getParameter("name"));
            subjectDTO.setPoint(Integer.parseInt(req.getParameter("point")));
            subjectDTO.setHour(Integer.parseInt(req.getParameter("hour")));

            if(subjectService.updateSubject(subjectDTO)>0){
                req.setAttribute("subject", subjectDTO);
                req.getRequestDispatcher("/WEB-INF/views/subject/detail.jsp").forward(req, resp);
            }
        }
        else if(action.equals("delete.do")){
            SubjectDTO subject = new SubjectDTO();
            subject.setId(Integer.parseInt(req.getParameter("id")));
            if(subjectService.deleteSubject(subject)>0){
                req.getRequestDispatcher("/subject/list.do").forward(req, resp);
            }
        }
        else if(action.equals("detail.do")){
            SubjectDTO subject = new SubjectDTO();
            subject.setId(Integer.parseInt(req.getParameter("id")));

            SubjectDTO subjectDTO = subjectService.getSubject(subject);

            if(subjectDTO != null){
                req.setAttribute("subject",subjectDTO);
                req.getRequestDispatcher("/WEB-INF/views/subject/detail.jsp").forward(req, resp);
            }
        }
    }
}
