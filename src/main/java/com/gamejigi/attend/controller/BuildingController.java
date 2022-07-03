package com.gamejigi.attend.controller;

import com.gamejigi.attend.model.dto.BuildingDTO;
import com.gamejigi.attend.model.service.BuildingServiceImpl;
import com.gamejigi.attend.util.Pagination;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name="building", urlPatterns = { "/building/list.do" ,"/building/detail.do", "/building/create.do" , "/building/create-action.do" ,"/building/delete.do" , "/building/edit.do" , "/building/edit-action.do" })
public class BuildingController extends HttpServlet {

    BuildingServiceImpl buildingService = new BuildingServiceImpl();

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

        //건물 리스트
        if(action.equals("list.do") || action.isEmpty()){

            //검색
            String search = req.getParameter("s"); // 검색어
            if (search==null){
                search="";
            }
            //페이지네이션
            String pageNo = req.getParameter("p"); // 현재 페이지 번호
            int curPageNo = (pageNo != null)? Integer.parseInt(pageNo) : 1;
            int perPageRows = 4; // 한 페이지에 나타날 데이터 행의 개수
            int perPagination = 5; // 한 화면에 나타날 페이지 번호 개수
            int totalRows = buildingService.getBuildingTotalNum(search);
            Pagination pagination = new Pagination(curPageNo, perPageRows, perPagination, totalRows);

            //건물리스트 저장
            ArrayList<BuildingDTO> buildingList;
            buildingList = (ArrayList<BuildingDTO>) buildingService.getBuildingList(pagination, search);

            // 포워딩
            req.setAttribute("search", search);
            req.setAttribute("pagination", pagination);
            req.setAttribute("buildingList", buildingList);
            req.getRequestDispatcher("/WEB-INF/views/building/list.jsp").forward(req, resp);

        }
        //생성 폼
        else if(action.equals("create.do")){
            req.getRequestDispatcher("/WEB-INF/views/building/create.jsp").forward(req, resp);
        }
        //DB 생성
        else if(action.equals("create-action.do")){

            BuildingDTO buildingDTO = new BuildingDTO();
            buildingDTO.setName(req.getParameter("name"));
            buildingDTO.setFloor(Integer.parseInt(req.getParameter("floor")));
            int rowNum = (int)buildingService.createBuilding(buildingDTO);

            if(rowNum > 0) {
                req.setAttribute("building", buildingDTO);
                req.getRequestDispatcher("/WEB-INF/views/building/detail.jsp").forward(req, resp);
            }

        }
        //상세
        else if(action.equals("detail.do")){
            BuildingDTO buildingDTO = new BuildingDTO();
            buildingDTO = buildingService.getBuilding(Integer.parseInt(req.getParameter("id")));
            if(buildingDTO != null) {
                req.setAttribute("building", buildingDTO);
                req.getRequestDispatcher("/WEB-INF/views/building/detail.jsp").forward(req, resp);
            }

        }
        //삭제
        else if(action.equals("delete.do")){
            int row_num = buildingService.deleteBuilding(Integer.parseInt(req.getParameter("id")));

            if(row_num > 0) {
                req.getRequestDispatcher("/WEB-INF/views/building/list.jsp").forward(req, resp);
            }

        }
        //수정 폼
        else if(action.equals("edit.do")){
            BuildingDTO buildingDTO = new BuildingDTO();
            buildingDTO = buildingService.getBuilding(Integer.parseInt(req.getParameter("id")));

            if(buildingDTO != null) {
                req.setAttribute("building", buildingDTO);
                req.getRequestDispatcher("/WEB-INF/views/building/edit.jsp").forward(req, resp);
            }

        }
        //DB 수정
        else if(action.equals("edit-action.do")){
            BuildingDTO buildingDTO = new BuildingDTO();
            buildingDTO.setId(Integer.parseInt(req.getParameter("id")));
            buildingDTO.setName(req.getParameter("name"));
            buildingDTO.setFloor(Integer.parseInt(req.getParameter("floor")));
            int rowNum = (int)buildingService.updateBuilding(buildingDTO);

            if(rowNum > 0) {
                req.setAttribute("building", buildingDTO);
                req.getRequestDispatcher("/WEB-INF/views/building/detail.jsp").forward(req, resp);
            }

        }

    }
}
