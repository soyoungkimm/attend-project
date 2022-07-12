package com.gamejigi.attend.controller;

import com.gamejigi.attend.model.dto.LectureDTO;
import com.gamejigi.attend.model.dto.SubjectDTO;
import com.gamejigi.attend.model.dto.TeacherDTO;
import com.gamejigi.attend.model.service.LectureServiceImpl;
import com.gamejigi.attend.model.service.TeacherServiceImpl;
import com.gamejigi.attend.util.Pagination;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet(name="lectures", urlPatterns = {"/lectures/","/lectures/list.do", "/lectures/create.do","/lectures/create-action.do",
            "/lectures/detail.do", "/lectures/delete.do", "/lectures/edit.do", "/lectures/edit-action.do", "/lectures/getTeacherOptions.do"})
public class LectureController extends HttpServlet {

    LectureServiceImpl lectureService = new LectureServiceImpl();


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

        //lecture 목록
        if(action.equals("list.do") || action.isEmpty()){

            // 검색키워드
            String yyyy = req.getParameter("y"); // 년도
            yyyy = (yyyy != null)? yyyy : lectureService.getYear();
            String term = req.getParameter("t"); // 학기
            term = (term != null)? term : lectureService.getTerm();
            String grade = req.getParameter("g"); // 학년
            grade = (grade != null)? grade : lectureService.getGrade();

            // 페이지네이션
            String pageNo = req.getParameter("p"); // 현재 페이지 번호
            int curPageNo = (pageNo != null)? Integer.parseInt(pageNo) : 1;
            int perPageRows = 4; // 한 페이지에 나타날 데이터 행의 개수
            int perPagination = 5; // 한 화면에 나타날 페이지 번호 개수
            int totalRows = lectureService.getLectureTotalNum(yyyy, term, grade);
            Pagination pagination = new Pagination(curPageNo, perPageRows, perPagination, totalRows);

            //lecture리스트 저장
            ArrayList<LectureDTO> lectureList = new ArrayList<LectureDTO>();
            lectureList = (ArrayList<LectureDTO>) lectureService.getLectureList(pagination, yyyy, term, grade);

            ArrayList<TeacherDTO> teacherList = new ArrayList<TeacherDTO>();
            //@임시 - depart_id: 로그인된 id로 변경예정
            teacherList = (ArrayList<TeacherDTO>) lectureService.getTeacherListByDepartID(1);

            // 포워딩
            req.setAttribute("yyyy", yyyy);
            req.setAttribute("term", term);
            req.setAttribute("grade", grade);
            req.setAttribute("pagination", pagination);
            req.setAttribute("lectureList", lectureList);
            req.setAttribute("teacherList", teacherList);
            req.getRequestDispatcher("/WEB-INF/views/lectures/list.jsp").forward(req, resp);
        //@@@ Set Lecture 함수 readList에 맞게 바꾸기@@@
        }
        //lecture 생성 폼
        else if(action.equals("create.do")){

            ArrayList<SubjectDTO> subjectList = new ArrayList<SubjectDTO>();
            ArrayList<TeacherDTO> teacherList = new ArrayList<TeacherDTO>();

            int yyyy = Integer.parseInt(lectureService.getYear());
            int term = Integer.parseInt(lectureService.getTerm());

            //@임시 - depart_id: 로그인된 id로 변경예정
            subjectList = (ArrayList<SubjectDTO>) lectureService.getSubjectList(1, yyyy, term);
            teacherList = (ArrayList<TeacherDTO>) lectureService.getTeacherListByDepartID(1);

            req.setAttribute("subjectList", subjectList);
            req.setAttribute("teacherList", teacherList);
            req.getRequestDispatcher("/WEB-INF/views/lectures/create.jsp").forward(req, resp);
        }
        //lecture DB생성
        else if(action.equals("create-action.do")){

            LectureDTO lectureDTO = new LectureDTO();
            lectureDTO.setSubject_id( Integer.parseInt(req.getParameter("subject")) );
            lectureDTO.setTeacher_id( Integer.parseInt(req.getParameter("teacher")) );
            lectureDTO.setClasss(req.getParameter("class"));
            int id = (int)lectureService.createLecture(lectureDTO);

            if(id > 0) {
                lectureDTO = lectureService.getLecture(id);
                req.setAttribute("lectureDTO", lectureDTO);
                req.getRequestDispatcher("/WEB-INF/views/lectures/detail.jsp").forward(req, resp);
            }
        
        }
        //lecture 상세
        else if(action.equals("detail.do")){
            LectureDTO lectureDTO = new LectureDTO();
            lectureDTO = lectureService.getLecture(Integer.parseInt(req.getParameter("id")));
            if(lectureDTO != null) {
                req.setAttribute("lectureDTO", lectureDTO);
                req.getRequestDispatcher("/WEB-INF/views/lectures/detail.jsp").forward(req, resp);
            }

        }
        //lecture 삭제
        else if(action.equals("delete.do")){
            int row_num = lectureService.deleteLecture(Integer.parseInt(req.getParameter("id")));

            if(row_num > 0) {
                req.getRequestDispatcher("/lectures/list.do").forward(req, resp);
            }

        }
        //lecture 수정 폼
        else if(action.equals("edit.do")){
            LectureDTO lectureDTO = new LectureDTO();
            lectureDTO = lectureService.getLecture(Integer.parseInt(req.getParameter("id")));

            ArrayList<SubjectDTO> subjectList = new ArrayList<SubjectDTO>();
            ArrayList<TeacherDTO> teacherList = new ArrayList<TeacherDTO>();
            int yyyy = Integer.parseInt(lectureService.getYear());
            int term = Integer.parseInt(lectureService.getTerm());

            //@임시 - depart_id: 로그인된 id로 변경예정
            subjectList = (ArrayList<SubjectDTO>) lectureService.getSubjectList(1, yyyy, term);
            teacherList = (ArrayList<TeacherDTO>) lectureService.getTeacherListByDepartID(1);

            if(lectureDTO != null) {
                req.setAttribute("lectureDTO", lectureDTO);
                req.setAttribute("subjectList", subjectList);
                req.setAttribute("teacherList", teacherList);
                req.getRequestDispatcher("/WEB-INF/views/lectures/edit.jsp").forward(req, resp);
            }

        }
        //lecture DB수정
        else if(action.equals("edit-action.do")){
            LectureDTO lectureDTO = new LectureDTO();
            lectureDTO.setId(Integer.parseInt(req.getParameter("id")));
            lectureDTO.setSubject_id( Integer.parseInt(req.getParameter("subject")) );
            lectureDTO.setTeacher_id( Integer.parseInt(req.getParameter("teacher")) );
            lectureDTO.setClasss(req.getParameter("class"));
            int id = (int)lectureService.updateLecture(lectureDTO);

            if(id > 0) {
                lectureDTO = lectureService.getLecture(id);
                req.setAttribute("lectureDTO", lectureDTO);
                req.getRequestDispatcher("/WEB-INF/views/lectures/detail.jsp").forward(req, resp);
            }

        }
        else if(action.equals("getTeacherOptions.do")){

            resp.setContentType("text/html;charset=UTF-8");
            PrintWriter out = resp.getWriter();
            out.print("<option value='1'>윤형태</option><option value='2'>한성현</option><option value='5'>고수정</option><option value='9'>유응구</option><option value='10'>교수1</option><option value='11'>교수2</option>");
            out.close();

//            resp.setContentType("application/json");
//            PrintWriter out = resp.getWriter();
//            out.print("{\"sucMessage\": \"LBV Teil wurde in der Datenbank gespeichert.\"}");
//            out.flush();

        }

    }

}
