package com.gamejigi.attend.model.service;

import com.gamejigi.attend.model.dao.*;
import com.gamejigi.attend.model.dto.*;

import java.util.*;

public class TimetableServiceImpl implements TimetableService {
    TimetableDAO timetableDAO = null;
    SubjectDAO subjectDAO = null;

    public TimetableServiceImpl() {
        timetableDAO = new TimetableDAOImpl();
        subjectDAO = new SubjectDAOImpl();
    }

    @Override
    public List<TimetableDTO> readList(){
        return timetableDAO.readList();
    }
    @Override
    public List<TimetableDTO> readListUseLecture(int lecture_id){
        return timetableDAO.readListUseLecture(lecture_id);
    }
    @Override
    public TimetableDTO readUseId(int id){
        return timetableDAO.readUseId(id);
    }
    @Override
    public int create(TimetableDTO timetable){
        return timetableDAO.create(timetable);
    }
    @Override
    public int update(TimetableDTO timetable){
        return timetableDAO.update(timetable);
    }
    @Override
    public int delete(int id){
        return timetableDAO.delete(id);
    }
    @Override
    public int deleteAll(int staffId, int year, int term) { return timetableDAO.deleteAll(staffId, year, term); }
    @Override
    public int count() {
        return timetableDAO.count();
    }
    @Override
    public int getMaxId() {
        return timetableDAO.getMaxId();
    }

    @Override
    public int saveData(List<String> list, int staffId, int year, int term) {
        int row = 0;
        deleteAll(staffId, year, term);
        for (String str : list) {
            String[] tokens = str.split("\\^");

            TimetableDTO time = new TimetableDTO();
            time.setLecture_id(Integer.parseInt(tokens[1]));
            time.setWeekday(Integer.parseInt(tokens[5]));
            time.setIstart(Integer.parseInt(tokens[6]));
            time.setIhour(Integer.parseInt(tokens[7]));
            time.setRoom_id(Integer.parseInt(tokens[11]));

            int id = Integer.parseInt(tokens[0]);
            if (id == -1 || id > getMaxId() || readUseId(id) == null) {
                time.setId(getMaxId() + 1);
                row += create(time);
            }
        }
        return row;
    }

    @Override
    public List<String> loadData(int departId, int year, int term) {
        List<TimetableDTO> result = timetableDAO.readListByDepartIdAndYearAndTerm(departId, year, term);

        //문자열로 바꾸기
        List<String> resultStr = new ArrayList<>();
        for (TimetableDTO time : result) {
            String[] arr = {
                    ""+time.getId(),
                    ""+time.getLecture_id(),
                    ""+time.getSubject_grade(),
                    ""+time.getLecture_class(),
                    ""+time.getSubject_hour(),
                    ""+time.getWeekday(),
                    ""+time.getIstart(),
                    ""+time.getIhour(),
                    ""+time.getSubject_name(),
                    ""+time.getTeacher_id(),
                    ""+time.getTeacher_name(),
                    ""+time.getRoom_id(),
                    ""+time.getRoom_name()
            };
            resultStr.add(String.join("^", arr));
        }

        return resultStr;
    }

    @Override
    public List<RoomDTO> getRoomsByStaffId(int staffId) {
        //조교 ID로 학과를 조회한다.
        StaffService staffService = new StaffServiceImpl();
        StaffDTO staff = staffService.getStaff(staffId);
        int departId = staff.getDepart_id();

        RoomDAO roomDAO = new RoomDAOImpl();
        return roomDAO.findByDepartId(departId);
    }

    @Override
    public List<BuildingDTO> getBuildingsByRooms(List<RoomDTO> rooms) {
        BuildingDAO buildingDAO = new BuildingDAOImpl();
        List<BuildingDTO> buildings = new ArrayList<>();
        Set<Integer> isAlreadyIn = new HashSet<>();

        for (RoomDTO room : rooms)
            if (!isAlreadyIn.contains(room.getBuilding_id())) {
                isAlreadyIn.add(room.getBuilding_id());

                BuildingDTO building = buildingDAO.findById(room.getBuilding_id());
                buildings.add(building);
            }

        return buildings;
    }

    @Override
    public int getGradeSystemByStaffId(int staffId) {
        //조교 ID로 학과를 조회한다.
        StaffService staffService = new StaffServiceImpl();
        StaffDTO staff = staffService.getStaff(staffId);
        int departId = staff.getDepart_id();

        //학과의 학제를 알아낸다.
        DepartDAO departDAO = new DepartDAOImpl();
        return departDAO.findById(departId).getGradeSystem();
    }

    @Override
    public List<String> getLecturesByStaffId(int staffId) {
        List<TimetableDTO> result = timetableDAO.getLecturesByStaffId(staffId);

        //문자열로 바꾸기
        List<String> resultStr = new ArrayList<>();
        for (TimetableDTO time : result) {
            String[] arr = {
                    "-1",
                    ""+time.getLecture_id(),
                    ""+time.getSubject_grade(),
                    ""+time.getLecture_class(),
                    ""+time.getSubject_hour(),
                    "",
                    "",
                    "",
                    ""+time.getSubject_name(),
                    ""+time.getTeacher_id(),
                    ""+time.getTeacher_name(),
                    "",
                    ""
            };
            resultStr.add(String.join("^", arr));
        }

        return resultStr;
    }
}
