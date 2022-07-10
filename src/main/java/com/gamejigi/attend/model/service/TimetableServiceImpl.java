package com.gamejigi.attend.model.service;

import com.gamejigi.attend.model.dao.*;
import com.gamejigi.attend.model.dto.*;

import java.nio.charset.CodingErrorAction;
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
    public int count() {
        return timetableDAO.count();
    }
    @Override
    public int getMaxId() {
        return timetableDAO.getMaxId();
    }

    @Override
    public int saveData(List<String> list) {
        int row = 0;
        for (String str : list) {
            String[] tokens = str.split("^");
            int id = Integer.parseInt(tokens[0]);
            TimetableDTO time = new TimetableDTO();
            time.setLecture_id(Integer.parseInt(tokens[1]));
            time.setWeekday(Integer.parseInt(tokens[5]));
            time.setIstart(Integer.parseInt(tokens[6]));
            time.setIhour(Integer.parseInt(tokens[7]));
            time.setRoom_id(Integer.parseInt(tokens[11]));
            if (id == -1 || id > getMaxId()) {
                time.setId(getMaxId() + 1);
                row += create(time);
            }
            else {
                time.setId(Integer.parseInt(tokens[0]));
                row += update(time);
            }
        }
        return row;
    }

    @Override
    public List<String> loadData(int staffId, int year, int term) {
        //조교 ID로 학과를 조회한다.
        StaffService staffService = new StaffServiceImpl();
        StaffDTO staff = staffService.getStaff(staffId);
        int departId = staff.getDepart_id();

        //해당 학과의 과목들을 가져온다.
        List<SubjectDTO> subjects = subjectDAO.findByDepartIdAndYearAndTerm(departId, year, term);

        /*
        List<LectureDAO> lectures = new ArrayList<>();
        for (SubjectDTO subject : subjects)
            lectures.addAll(lectureDao.findLectureBySubjectId(subject.getId()));

        List<TimetableDTO> result = new ArrayList<>();
        for (LectureDAO lecture : lectures) {
            List<TimetableDTO> times = readListUseLecture(lecture.getId());
            result.addAll(times);
        }

        //id를 기준으로 정렬하기
        class TimetableIdComparator implements Comparator<TimetableDTO> {
            @Override public int compare(TimetableDTO t1, TimetableDTO t2) {
                if (t1.getId() == t2.getId()) return 0;
                if (t1.getId() < t2.getId()) return -1;
                return 1;
            }
        }
        Collections.sort(result, new TimetableIdComparator());

        //문자열로 바꾸기
        List<String> resultStr = new ArrayList<>();
        for (TimetableDTO time : result) {
            String[] arr = {
                    ""+time.getId(),
                    ""+time.getLecture_id(),
                    "subject:grade",
                    "lecture:class",
                    "subject:hour",
                    ""+time.getWeekday(),
                    ""+time.getIstart(),
                    ""+time.getIhour(),
                    "subject:name("+time.getLecture_id()+")",
                    "lecture:teacher_id",
                    "teacher:name",
                    ""+time.getRoom_id(),
                    "room:name("+time.getRoom_id()+")"
            };
            resultStr.add(String.join("^", arr));
        }
        */

        List<String> resultStr = new ArrayList<>(Arrays.asList(
                "1^1^2^A^4^1^1^4^PHP^1^교수님1^2^컴실2",
                "2^2^2^B^4^2^1^4^PHP^1^교수님1^2^컴실2",
                "3^3^1^A^3^3^2^3^C^2^교수님2^1^컴실1",
                "4^4^1^B^3^4^6^3^C^2^교수님2^1^컴실1"
        ));

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
}
