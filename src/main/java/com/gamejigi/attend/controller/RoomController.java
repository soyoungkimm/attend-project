package com.gamejigi.attend.controller;

import com.gamejigi.attend.model.dto.BuildingDTO;
import com.gamejigi.attend.model.dto.DepartDTO;
import com.gamejigi.attend.model.dto.RoomDTO;
import com.gamejigi.attend.model.service.RoomServiceImpl;
import com.gamejigi.attend.util.Pagination;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "room", urlPatterns = { "/room/list.do", "/room/create.do", "/room/create-action.do",
        "/room/detail.do", "/room/delete.do", "/room/edit.do", "/room/edit-action.do" })
public class RoomController extends HttpServlet {

    RoomServiceImpl roomService = new RoomServiceImpl();

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

        ArrayList<BuildingDTO> buildingList = new ArrayList<BuildingDTO>();
        buildingList =(ArrayList<BuildingDTO>) roomService.getBuildingList();
        ArrayList<DepartDTO> departList = new ArrayList<DepartDTO>();
        departList =(ArrayList<DepartDTO>) roomService.getDepartList();

        req.setAttribute("buildingList", buildingList);
        req.setAttribute("departList", departList);

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
            int totalRows = roomService.getRoomTotalNum(search); // 행의 총 개수
            Pagination pagination = new Pagination(curPageNo, perPageRows, perPagination, totalRows);

            // 리스트 구해오기
            ArrayList<RoomDTO> roomList = new ArrayList<RoomDTO>();
            roomList = (ArrayList<RoomDTO>)roomService.getRoomList(pagination, search);

            // 포워딩
            req.setAttribute("search", search);
            req.setAttribute("pagination", pagination);
            req.setAttribute("roomList", roomList);
            req.getRequestDispatcher("/WEB-INF/views/room/list.jsp").forward(req, resp);
        }
        // 새로 만들기 폼
        else if (action.equals("create.do")) {
            req.getRequestDispatcher("/WEB-INF/views/room/create.jsp").forward(req, resp);
        }
        // DB에 새로 생성
        else if (action.equals("create-action.do")) {

            RoomDTO roomDTO = new RoomDTO();
            roomDTO.setBuilding_id(Integer.parseInt(req.getParameter("building_id")));
            roomDTO.setDepart_id(Integer.parseInt(req.getParameter("depart_id")));
            roomDTO.setFloor(Integer.parseInt(req.getParameter("floor")));
            roomDTO.setHo(Integer.parseInt(req.getParameter("ho")));
            roomDTO.setName(req.getParameter("name"));
            roomDTO.setKind(Integer.parseInt(req.getParameter("kind")));
            roomDTO.setArea(Integer.parseInt(req.getParameter("area")));
            int rowNum = (int)roomService.createRoom(roomDTO);

            if(rowNum > 0) {
                req.setAttribute("room", roomDTO);
                req.getRequestDispatcher("/WEB-INF/views/room/detail.jsp").forward(req, resp);
            }
        }
        // 자세히 보기
        else if (action.equals("detail.do")) {
            RoomDTO roomDTO = new RoomDTO();
            roomDTO = roomService.getRoom(Integer.parseInt(req.getParameter("id")));

            if(roomDTO != null) {
                req.setAttribute("room", roomDTO);
                req.getRequestDispatcher("/WEB-INF/views/room/detail.jsp").forward(req, resp);
            }
        }
        // 삭제
        else if (action.equals("delete.do")) {
            int row_num = roomService.deleteRoom(Integer.parseInt(req.getParameter("id")));

            // 검색
            String search = req.getParameter("s"); // 검색어
            search = (search != null)? search : "";

            // 페이지네이션
            String pageNo = req.getParameter("p"); // 현재 페이지 번호
            int curPageNo = (pageNo != null)? Integer.parseInt(pageNo) : 1;
            int perPageRows = 4; // 한 페이지에 나타날 행의 개수
            int perPagination = 5; // 한 화면에 나타날 페이지 번호 개수
            int totalRows = roomService.getRoomTotalNum(search); // 행의 총 개수
            Pagination pagination = new Pagination(curPageNo, perPageRows, perPagination, totalRows);

            // 리스트 구해오기
            ArrayList<RoomDTO> roomList = new ArrayList<RoomDTO>();
            roomList = (ArrayList<RoomDTO>)roomService.getRoomList(pagination, search);

            // 포워딩
            req.setAttribute("search", search);
            req.setAttribute("pagination", pagination);
            req.setAttribute("roomList", roomList);

            if(row_num > 0) {
                req.getRequestDispatcher("/WEB-INF/views/room/list.jsp").forward(req, resp);
            }
        }
        // 수정하기 폼
        else if (action.equals("edit.do")) {
            RoomDTO roomDTO = new RoomDTO();
            roomDTO = roomService.getRoom(Integer.parseInt(req.getParameter("id")));

            if(roomDTO != null) {
                req.setAttribute("room", roomDTO);
                req.getRequestDispatcher("/WEB-INF/views/room/edit.jsp").forward(req, resp);
            }
        }
        // 공지사항 DB 수정
        else if (action.equals("edit-action.do")) {
            RoomDTO roomDTO = new RoomDTO();
            roomDTO.setId(Integer.parseInt(req.getParameter("id")));
            roomDTO.setBuilding_id(Integer.parseInt(req.getParameter("building_id")));
            roomDTO.setDepart_id(Integer.parseInt(req.getParameter("depart_id")));
            roomDTO.setFloor(Integer.parseInt(req.getParameter("floor")));
            roomDTO.setName(req.getParameter("name"));
            roomDTO.setKind(Integer.parseInt(req.getParameter("kind")));
            roomDTO.setArea(Integer.parseInt(req.getParameter("area")));
            int rowNum = (int)roomService.updateRoom(roomDTO);

            if(rowNum > 0) {
                req.setAttribute("room", roomDTO);
                req.getRequestDispatcher("/WEB-INF/views/room/detail.jsp").forward(req, resp);
            }
        }
    }
}
