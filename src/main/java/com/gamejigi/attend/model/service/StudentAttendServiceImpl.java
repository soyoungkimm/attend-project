package com.gamejigi.attend.model.service;

import com.gamejigi.attend.model.dao.LectureDayDAOImpl;
import com.gamejigi.attend.model.dao.MyLectureDAOImpl;
import com.gamejigi.attend.model.dto.LectureDayDTO;
import com.gamejigi.attend.model.dto.StudentAttendDTO;

import java.util.List;

public class StudentAttendServiceImpl implements StudentAttendService {

    MyLectureDAOImpl myLectureDAO = null;
    LectureDayDAOImpl lectureDayDAO = null;

    public StudentAttendServiceImpl() {
        myLectureDAO = new MyLectureDAOImpl();
        lectureDayDAO = new LectureDayDAOImpl();
    }

    @Override
    public List<StudentAttendDTO> getStudentAttendList(int student_id, int term) {
        return myLectureDAO.readStudentAttendList2(student_id, term);
    }

    @Override
    public void setRestLecture(List<StudentAttendDTO> studentAttendList) {
        for (StudentAttendDTO studentAttendDTO : studentAttendList) {

            List<LectureDayDTO> readList = lectureDayDAO.findRestStarthAndHour(studentAttendDTO.getLecture_id());
            for (LectureDayDTO lectureDayDTO : readList) {

                Integer[] h = studentAttendDTO.getH();

                for(int i = 0; i < 45; i++) {


                    if (i + 1 >= lectureDayDTO.getStarth() && i + 1 <= lectureDayDTO.getStarth() + lectureDayDTO.getNormhour() - 1) {
                        h[i] = 10;
                    }
                }
                studentAttendDTO.setH(h);
            }
        }
    }
}
