package com.gamejigi.attend.model.service;

import com.gamejigi.attend.model.dao.LectureDayDAO;
import com.gamejigi.attend.model.dao.LectureDayDAOImpl;
import com.gamejigi.attend.model.dao.MyLectureDAOImpl;
import com.gamejigi.attend.model.dao.StudentDAOImpl;
import com.gamejigi.attend.model.dto.LectureDayDTO;
import com.gamejigi.attend.model.dto.StudentDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DailyAttendServiceImpl implements DailyAttendService{

    LectureDayDAOImpl lectureDayDAO = null;
    StudentDAOImpl studentDAO = null;
    MyLectureDAOImpl mylectureDAO = null;

    public DailyAttendServiceImpl() {
        lectureDayDAO = new LectureDayDAOImpl();
        studentDAO = new StudentDAOImpl();
        mylectureDAO = new MyLectureDAOImpl();
    }

    @Override
    public List<LectureDayDTO> getDailyAttendList(String date, int teacher_id) {
        //int norm_state = lectureDayDAO.findNormSate();
        //Boolean isRestClass = (norm_state == 3); // 보강 수업인지 확인
        return lectureDayDAO.readList(date, teacher_id);
    }

    @Override
    public LectureDayDTO getLectureDayById(Long id) {
        return lectureDayDAO.findById(id);
    }

    @Override
    public List<StudentDTO> getStudentAttend(Long lectureday_id) {
        int[] dataList = lectureDayDAO.findStarthAndNormOrResthour(lectureday_id);
        return studentDAO.findWithAttend(lectureday_id, dataList[0], dataList[1]);
    }

    @Override
    public HashMap<String, Integer> getAllAttend(Long lectureday_id) {
        List<StudentDTO> studentList = new ArrayList<>();
        int[] dataList = lectureDayDAO.findStarthAndNormOrResthour(lectureday_id);
        int starth = dataList[0];
        int hour = dataList[1];
        studentList = studentDAO.findWithAttend(lectureday_id, starth, hour);

        int appear = 0; // 출석
        int late = 0; // 지각
        int absent = 0; // 결석
        // 결석이나 지각이 하나라도 있으면 결석, 지각 처리
        for (StudentDTO student : studentList) {
            if (student.getAttendState1() == 1) {
                late++;
                continue;
            }
            else if (student.getAttendState1() == 2) {
                absent++;
                continue;
            }

            if (student.getAttendState2() != null && student.getAttendState2() == 1 ) {
                late++;
                continue;
            }
            else if (student.getAttendState2() != null && student.getAttendState2() == 2 ) {
                absent++;
                continue;
            }

            if (student.getAttendState3() != null && student.getAttendState3() == 1) {
                late++;
                continue;
            }
            else if (student.getAttendState3() != null && student.getAttendState3() == 2) {
                absent++;
                continue;
            }

            if (student.getAttendState4() != null && student.getAttendState4() == 1) {
                late++;
                continue;
            }
            else if (student.getAttendState4() != null && student.getAttendState4() == 2) {
                absent++;
                continue;
            }

            if (student.getAttendState1() != null || student.getAttendState2() != null || student.getAttendState3() != null || student.getAttendState4() != null) {
                appear++;
                continue;
            }
        }

        HashMap<String, Integer> map = new HashMap<>();
        map.put("appear", appear);
        map.put("late", late);
        map.put("absent", absent);
        return map;
    }

    @Override
    public int updateAttend(int lecture_id, int student_id, int h, int v) {
        return mylectureDAO.updateAttend(lecture_id,student_id, h, v);
    }

    @Override
    public int updateAllAttend(int lectureday_id) {
        int[] dataList = lectureDayDAO.findStarthAndNormOrResthour((long) lectureday_id);
        int lecture_id = lectureDayDAO.findLectureIdById(lectureday_id);
        return mylectureDAO.updateAllAttend(lecture_id, dataList[0], dataList[1]);
    }
}
