package com.gamejigi.attend.model.service;

import com.gamejigi.attend.model.dao.*;
import com.gamejigi.attend.model.dto.BuildingDTO;
import com.gamejigi.attend.model.dto.LectureDayDTO;
import com.gamejigi.attend.model.dto.RoomDTO;
import com.gamejigi.attend.model.dto.TimetableDTO;
import com.gamejigi.attend.util.Pagination;

import java.util.*;

public class TeLecMoveServiceImpl implements TeLecMoveService {
    private LectureDayDAO lectureDayDAO = null;
    private TimetableDAO timetableDAO = null;
    private BuildingDAO buildingDAO = null;
    private RoomDAO roomDAO = null;

    public TeLecMoveServiceImpl() {
        buildingDAO = new BuildingDAOImpl();
        lectureDayDAO = new LectureDayDAOImpl();
        timetableDAO = new TimetableDAOImpl();
        roomDAO = new RoomDAOImpl();
    }

    @Override
    public List<LectureDayDTO> readListUsePaginationByTeacher(Pagination p, int id){
        return lectureDayDAO.readListUsePaginationByTeacher(p, id);
    }

    @Override
    public int getTotalRowsByTeacher(int id) {
        return lectureDayDAO.getTotalRowsByTeacher(id);
    }

    @Override
    public List<Integer[]> getTimetable(int depart, int year, int term) {
        //불가능한 시간대는 1로 표시
        Integer[] week = {0, 0, 0, 0, 0, 0, 0};
        List<Integer[]> result = new ArrayList<>(14);
        for (int i = 0; i < 14; i++) {
            result.add(week.clone());
        }
        List<TimetableDTO> timetable = timetableDAO.readListByDepartIdAndYearAndTerm(depart, year, term);
        for (TimetableDTO time : timetable) {
            for (int i = 0; i < time.getIhour(); i++) {
                result.get(time.getIstart() + i)[time.getWeekday()] = 1;
            }
        }
        return result;
    }

    @Override
    public String getBuildingNameByRoomName(String roomName) {
        return buildingDAO.getBuildingNameByRoomName(roomName);
    }

    @Override
    public List<Map<String, String>> getBuildingListByDepart(int depart) {
        List<BuildingDTO> buildingList = buildingDAO.readListByDepart(depart);
        List<Map<String, String>> result = new ArrayList<>();
        for (BuildingDTO building :
                buildingList) {
            Map<String, String> temp = new HashMap<>();
            temp.put("name", building.getName());
            temp.put("id", ""+building.getId());
            result.add(temp);
        }
        return result;
    }

    @Override
    public List<Map<String, String>> getRoomListByDepart(int depart) {
        List<RoomDTO> roomList = roomDAO.findByDepartId(depart);
        List<Map<String, String>> result = new ArrayList<>();
        for (RoomDTO room :
                roomList) {
            Map<String, String> temp = new HashMap<>();
            temp.put("name", room.getName());
            temp.put("id", ""+room.getId());
            temp.put("building_id", ""+room.getBuilding_id());
            temp.put("depart_id", ""+room.getDepart_id());
            result.add(temp);
        }
        return result;
    }

    @Override
    public void applyLecMove(LectureDayDTO lectureDay) {
        lectureDayDAO.updateForLecMove(lectureDay);
    }

    @Override
    public void cancelLecMove(int lecDayId) {
        LectureDayDTO lectureDay = lectureDayDAO.findById(new Long(lecDayId));
        lectureDay.setNormstate(0);
        lectureDay.setRestdate("2000-01-01");
        lectureDay.setReststart(0);
        lectureDay.setResthour(0);
        lectureDay.setReststate(0);
        lectureDay.setState(4);
        RoomDTO room = roomDAO.findByLectureId(lectureDay.getLectureId());
        lectureDay.setRoomId(room.getId());
        lectureDayDAO.updateForLecMove(lectureDay);
    }
}
