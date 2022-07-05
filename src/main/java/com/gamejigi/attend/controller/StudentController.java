package com.gamejigi.attend.controller;

import com.gamejigi.attend.model.dto.StudentDTO;
import com.gamejigi.attend.model.service.StudentServiceImpl;
import com.gamejigi.attend.util.Pagination;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.ArrayList;


@WebServlet(name = "student", urlPatterns = { "/students/list.do", "/students/create.do", "/students/create-action.do",
        "/students/detail.do", "/students/delete.do", "/students/edit.do", "/students/edit-action.do" })
// 파일 관련 설정
@MultipartConfig(
        maxFileSize = 1024*1024*5, // 하나 파일 사이즈 : 5mb
        maxRequestSize = 1024*1024*5 // 전체 파일 사이즈 : 5mb
)
public class StudentController extends HttpServlet {

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

        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        req.setCharacterEncoding("UTF-8");

        String uri = req.getRequestURI();
        String contextPath = req.getContextPath();
        String command = uri.substring(contextPath.length() + 1);
        String action = command.substring(command.lastIndexOf("/") + 1);
        String absolutePath = req.getServletContext().getRealPath("/image/student"); // 절대경로

        // 리스트
        if (action.equals("list.do")) {

            // 검색
            String search = req.getParameter("s"); // 검색어
            search = (search != null)? search : "";

            // 페이지네이션
            String pageNo = req.getParameter("p"); // 현재 페이지 번호
            int curPageNo = (pageNo != null)? Integer.parseInt(pageNo) : 1;
            int perPageRows = 4; // 한 페이지에 나타날 행의 개수
            int perPagination = 5; // 한 화면에 나타날 페이지 번호 개수
            int totalRows = studentService.getStudentTotalNum(search); // 행의 총 개수
            Pagination pagination = new Pagination(curPageNo, perPageRows, perPagination, totalRows);

            // 리스트 구해오기
            ArrayList<StudentDTO> studentList = new ArrayList<StudentDTO>();
            studentList = (ArrayList<StudentDTO>)studentService.getStudentList(pagination, search);

            // 포워딩
            req.setAttribute("search", search);
            req.setAttribute("pagination", pagination);
            req.setAttribute("studentList", studentList);
            req.getRequestDispatcher("/WEB-INF/views/students/list.jsp").forward(req, resp);
        }
        // 새로 만들기 폼
        else if (action.equals("create.do")) {
            req.getRequestDispatcher("/WEB-INF/views/students/create.jsp").forward(req, resp);
        }
        // DB에 새로 생성
        else if (action.equals("create-action.do")) {

            // dto 설정
            StudentDTO studentDTO = new StudentDTO();
            studentDTO.setDepart_id(Integer.parseInt("1"));//임시
            studentDTO.setGrade(Integer.parseInt(req.getParameter("grade")));
            studentDTO.setBan(req.getParameter("class"));
            studentDTO.setSchoolno(req.getParameter("schoolno"));
            studentDTO.setPwd(req.getParameter("pwd"));
            String phone = req.getParameter("phone1") + req.getParameter("phone2") + req.getParameter("phone3");
            studentDTO.setPhone(phone);
            studentDTO.setGender(Integer.parseInt(req.getParameter("gender")));
            studentDTO.setName(req.getParameter("name"));
            studentDTO.setState(Integer.parseInt(req.getParameter("state")));
            studentDTO.setEmail(req.getParameter("email"));

            // 파일 업로드 설정
            Part filePart = req.getPart("pic"); // request된 파일을 받음

            // 서비스
            Boolean isSuccess = studentService.createStudent(studentDTO, filePart, absolutePath);

            if(isSuccess) {
                req.setAttribute("absolutePath", absolutePath);
                req.setAttribute("student", studentDTO);
                req.getRequestDispatcher("/WEB-INF/views/students/detail.jsp").forward(req, resp);
            }
        }
        // 자세히 보기
        else if (action.equals("detail.do")) {
            StudentDTO studentDTO = new StudentDTO();
            studentDTO = studentService.getStudent(Long.parseLong(req.getParameter("id")));

            if(studentDTO != null) {
                req.setAttribute("student", studentDTO);
                req.getRequestDispatcher("/WEB-INF/views/students/detail.jsp").forward(req, resp);
            }
        }
        // 삭제
        else if (action.equals("delete.do")) {
            int row_num = studentService.deleteStudent(Long.parseLong(req.getParameter("id")), absolutePath);

            if(row_num == 0) {
                req.getRequestDispatcher("/students/list.do").forward(req, resp);
            }
        }
        // 수정하기 폼
        else if (action.equals("edit.do")) {
            StudentDTO studentDTO = new StudentDTO();
            studentDTO = studentService.getStudent(Long.parseLong(req.getParameter("id")));

            if(studentDTO != null) {
                req.setAttribute("student", studentDTO);
                req.getRequestDispatcher("/WEB-INF/views/students/edit.jsp").forward(req, resp);
            }
        }
        // 학생 DB 수정
        else if (action.equals("edit-action.do")) {
            StudentDTO studentDTO = new StudentDTO();
            studentDTO.setId(Long.parseLong(req.getParameter("id")));
            studentDTO.setDepart_id(Integer.parseInt("1")); //임시
            studentDTO.setGrade(Integer.parseInt(req.getParameter("grade")));
            studentDTO.setBan(req.getParameter("class"));
            studentDTO.setSchoolno(req.getParameter("schoolno"));
            studentDTO.setPwd(req.getParameter("pwd"));
            String phone = req.getParameter("phone1") + req.getParameter("phone2") + req.getParameter("phone3");
            studentDTO.setPhone(phone);
            studentDTO.setGender(Integer.parseInt(req.getParameter("gender")));
            studentDTO.setName(req.getParameter("name"));
            studentDTO.setState(Integer.parseInt(req.getParameter("state")));
            studentDTO.setEmail(req.getParameter("email"));

            // 파일 업로드 설정
            Part filePart = req.getPart("pic"); // request된 파일을 받음

            // 서비스
            Boolean isSuccess = studentService.updateStudent(studentDTO, filePart, absolutePath);

            if(isSuccess) {
                req.setAttribute("student", studentDTO);
                req.getRequestDispatcher("/WEB-INF/views/students/detail.jsp").forward(req, resp);
            }
        }
    }
}
