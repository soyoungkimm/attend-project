package com.gamejigi.attend.model.service;

import com.gamejigi.attend.model.dao.*;
import com.gamejigi.attend.model.dto.LectureDayDTO;
import com.gamejigi.attend.model.dto.MyLectureDTO;
import com.gamejigi.attend.model.dto.StudentDTO;
import com.gamejigi.attend.model.dto.SubjectAttendDTO;

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
        int result = mylectureDAO.updateAttend(lecture_id,student_id, h, v);

        // 출석점수, 지각횟수, 결석 시간 update
        MyLectureDTO myLectureDTO = mylectureDAO.findByLectureIdAndStudentId(lecture_id,student_id);
        this.setLateAndAbsentAndAttendScore(myLectureDTO, lecture_id);
        mylectureDAO.updateLateAndAbsentAndAttendScore(myLectureDTO);

        return result;
    }

    @Override
    public int updateAllAttend(int lectureday_id) {
        int[] dataList = lectureDayDAO.findStarthAndNormOrResthour((long) lectureday_id);
        int lecture_id = lectureDayDAO.findLectureIdById(lectureday_id);
        int result = mylectureDAO.updateAllAttend(lecture_id, dataList[0], dataList[1]);

        // 출석점수, 지각횟수, 결석 시간 update
        List<MyLectureDTO> myLectureDTOList = mylectureDAO.readMyLectureList(lecture_id);
        for (MyLectureDTO myLectureDTO : myLectureDTOList) {
            this.setLateAndAbsentAndAttendScore(myLectureDTO, lecture_id);
            mylectureDAO.updateLateAndAbsentAndAttendScore(myLectureDTO);
        }

        return result;
    }

    @Override
    public void setLateAndAbsentAndAttendScore(MyLectureDTO myLectureDTO, int lecture_id) {
        int late = 0;
        int absent = 0;

        if (myLectureDTO.getH1() != null) {if (myLectureDTO.getH1() == 1) late++; else if (myLectureDTO.getH1() == 2) absent++;}
        if (myLectureDTO.getH2() != null) {if (myLectureDTO.getH2() == 1) late++; else if (myLectureDTO.getH2() == 2) absent++;}
        if (myLectureDTO.getH3() != null) {if (myLectureDTO.getH3() == 1) late++; else if (myLectureDTO.getH3() == 2) absent++;}
        if (myLectureDTO.getH4() != null) {if (myLectureDTO.getH4() == 1) late++; else if (myLectureDTO.getH4() == 2) absent++;}
        if (myLectureDTO.getH5() != null) {if (myLectureDTO.getH5() == 1) late++; else if (myLectureDTO.getH5() == 2) absent++;}
        if (myLectureDTO.getH6() != null) {if (myLectureDTO.getH6() == 1) late++; else if (myLectureDTO.getH6() == 2) absent++;}
        if (myLectureDTO.getH7() != null) {if (myLectureDTO.getH7() == 1) late++; else if (myLectureDTO.getH7() == 2) absent++;}
        if (myLectureDTO.getH8() != null) {if (myLectureDTO.getH8() == 1) late++; else if (myLectureDTO.getH8() == 2) absent++;}
        if (myLectureDTO.getH9() != null) {if (myLectureDTO.getH9() == 1) late++; else if (myLectureDTO.getH9() == 2) absent++;}
        if (myLectureDTO.getH10() != null) {if (myLectureDTO.getH10() == 1) late++; else if (myLectureDTO.getH10() == 2) absent++;}
        if (myLectureDTO.getH11() != null) {if (myLectureDTO.getH11() == 1) late++; else if (myLectureDTO.getH11() == 2) absent++;}
        if (myLectureDTO.getH12() != null) {if (myLectureDTO.getH12() == 1) late++; else if (myLectureDTO.getH12() == 2) absent++;}
        if (myLectureDTO.getH13() != null) {if (myLectureDTO.getH13() == 1) late++; else if (myLectureDTO.getH13() == 2) absent++;}
        if (myLectureDTO.getH14() != null) {if (myLectureDTO.getH14() == 1) late++; else if (myLectureDTO.getH14() == 2) absent++;}
        if (myLectureDTO.getH15() != null) {if (myLectureDTO.getH15() == 1) late++; else if (myLectureDTO.getH15() == 2) absent++;}
        if (myLectureDTO.getH16() != null) {if (myLectureDTO.getH16() == 1) late++; else if (myLectureDTO.getH16() == 2) absent++;}
        if (myLectureDTO.getH17() != null) {if (myLectureDTO.getH17() == 1) late++; else if (myLectureDTO.getH17() == 2) absent++;}
        if (myLectureDTO.getH18() != null) {if (myLectureDTO.getH18() == 1) late++; else if (myLectureDTO.getH18() == 2) absent++;}
        if (myLectureDTO.getH19() != null) {if (myLectureDTO.getH19() == 1) late++; else if (myLectureDTO.getH19() == 2) absent++;}
        if (myLectureDTO.getH20() != null) {if (myLectureDTO.getH20() == 1) late++; else if (myLectureDTO.getH20() == 2) absent++;}
        if (myLectureDTO.getH21() != null) {if (myLectureDTO.getH21() == 1) late++; else if (myLectureDTO.getH21() == 2) absent++;}
        if (myLectureDTO.getH22() != null) {if (myLectureDTO.getH22() == 1) late++; else if (myLectureDTO.getH22() == 2) absent++;}
        if (myLectureDTO.getH23() != null) {if (myLectureDTO.getH23() == 1) late++; else if (myLectureDTO.getH23() == 2) absent++;}
        if (myLectureDTO.getH24() != null) {if (myLectureDTO.getH24() == 1) late++; else if (myLectureDTO.getH24() == 2) absent++;}
        if (myLectureDTO.getH25() != null) {if (myLectureDTO.getH25() == 1) late++; else if (myLectureDTO.getH25() == 2) absent++;}
        if (myLectureDTO.getH26() != null) {if (myLectureDTO.getH26() == 1) late++; else if (myLectureDTO.getH26() == 2) absent++;}
        if (myLectureDTO.getH27() != null) {if (myLectureDTO.getH27() == 1) late++; else if (myLectureDTO.getH27() == 2) absent++;}
        if (myLectureDTO.getH28() != null) {if (myLectureDTO.getH28() == 1) late++; else if (myLectureDTO.getH28() == 2) absent++;}
        if (myLectureDTO.getH29() != null) {if (myLectureDTO.getH29() == 1) late++; else if (myLectureDTO.getH29() == 2) absent++;}
        if (myLectureDTO.getH30() != null) {if (myLectureDTO.getH30() == 1) late++; else if (myLectureDTO.getH30() == 2) absent++;}
        if (myLectureDTO.getH31() != null) {if (myLectureDTO.getH31() == 1) late++; else if (myLectureDTO.getH31() == 2) absent++;}
        if (myLectureDTO.getH32() != null) {if (myLectureDTO.getH32() == 1) late++; else if (myLectureDTO.getH32() == 2) absent++;}
        if (myLectureDTO.getH33() != null) {if (myLectureDTO.getH33() == 1) late++; else if (myLectureDTO.getH33() == 2) absent++;}
        if (myLectureDTO.getH34() != null) {if (myLectureDTO.getH34() == 1) late++; else if (myLectureDTO.getH34() == 2) absent++;}
        if (myLectureDTO.getH35() != null) {if (myLectureDTO.getH35() == 1) late++; else if (myLectureDTO.getH35() == 2) absent++;}
        if (myLectureDTO.getH36() != null) {if (myLectureDTO.getH36() == 1) late++; else if (myLectureDTO.getH36() == 2) absent++;}
        if (myLectureDTO.getH37() != null) {if (myLectureDTO.getH37() == 1) late++; else if (myLectureDTO.getH37() == 2) absent++;}
        if (myLectureDTO.getH38() != null) {if (myLectureDTO.getH38() == 1) late++; else if (myLectureDTO.getH38() == 2) absent++;}
        if (myLectureDTO.getH39() != null) {if (myLectureDTO.getH39() == 1) late++; else if (myLectureDTO.getH39() == 2) absent++;}
        if (myLectureDTO.getH40() != null) {if (myLectureDTO.getH40() == 1) late++; else if (myLectureDTO.getH40() == 2) absent++;}
        if (myLectureDTO.getH41() != null) {if (myLectureDTO.getH41() == 1) late++; else if (myLectureDTO.getH41() == 2) absent++;}
        if (myLectureDTO.getH42() != null) {if (myLectureDTO.getH42() == 1) late++; else if (myLectureDTO.getH42() == 2) absent++;}
        if (myLectureDTO.getH43() != null) {if (myLectureDTO.getH43() == 1) late++; else if (myLectureDTO.getH43() == 2) absent++;}
        if (myLectureDTO.getH44() != null) {if (myLectureDTO.getH44() == 1) late++; else if (myLectureDTO.getH44() == 2) absent++;}
        if (myLectureDTO.getH45() != null) {if (myLectureDTO.getH45() == 1) late++; else if (myLectureDTO.getH45() == 2) absent++;}

        // 결석, 지각 set
        myLectureDTO.setIlate(late);
        myLectureDTO.setIxhour(absent);

        // 출석 점수 set
        int hour = lectureDayDAO.findHourByLectureId(lecture_id);
        int score = 15 * hour;
        int deduction = late / 3;// 지각 3번은 결석 1번
        score = score - absent - deduction;
        myLectureDTO.setIattend(score);
    }
}
