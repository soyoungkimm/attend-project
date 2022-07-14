package com.gamejigi.attend.model.service;

import com.gamejigi.attend.model.dao.LectureDAOImpl;
import com.gamejigi.attend.model.dao.LectureDayDAOImpl;
import com.gamejigi.attend.model.dao.MyLectureDAOImpl;
import com.gamejigi.attend.model.dto.LectureDayDTO;
import com.gamejigi.attend.model.dto.MyLectureDTO2;
import com.gamejigi.attend.model.dto.SubjectAttendDTO;
import com.gamejigi.attend.model.dto.SubjectDTO;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SubjectAttendServiceImpl implements SubjectAttendService{

    LectureDayDAOImpl lectureDayDAO = null;
    MyLectureDAOImpl mylectureDAO = null;
    LectureDAOImpl lectureDAO = null;

    public SubjectAttendServiceImpl() {
        lectureDayDAO = new LectureDayDAOImpl();
        mylectureDAO = new MyLectureDAOImpl();
        lectureDAO = new LectureDAOImpl();
    }

    @Override
    public List<SubjectAttendDTO> getStudentAttendList(int lecture_id) {
        return mylectureDAO.readStudentAttendList(lecture_id);
    }

    @Override
    public LectureDayDTO getStartTeachingAndHourAndStartDate(int lecture_id) {
        if (lecture_id == 0) return null;
        else return lectureDayDAO.findNormstartAndNormhourAndNormdate(lecture_id);
    }

    @Override
    public List<SubjectDTO> getSubject(int teacher_id) {
        return lectureDAO.findSubjectByTeacherId(teacher_id);
    }

    @Override
    public int getLectureId(String year, int term, int grade, String ban, int subject, int teacher_id) {
        return lectureDAO.findIdByTeacherIdAndSearchText(year, term, grade, ban, subject, teacher_id);
    }

    @Override
    public List<String> getDateList(String startDate) {
        List<String> dateList = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = sdf.parse(startDate);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            dateList.add(startDate);
            for (int i = 1; i <= 14; i++) {
                cal.add(Calendar.DATE, 7);
                dateList.add(sdf.format(cal.getTime()));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dateList;
    }

    @Override
    public List<LectureDayDTO> getRestList(int lecture_id) {
        return lectureDayDAO.readRestList(lecture_id);
    }

    @Override
    public MyLectureDTO2 getLateAbsentScore(int lecture_id, int student_id) {
        return mylectureDAO.findLateAbsentScore(lecture_id,student_id);
    }

    @Override
    public void setRestLecture(List<SubjectAttendDTO> subjectAttendList, int lecture_id) {
        List<LectureDayDTO> restList = lectureDayDAO.findRestStarthAndHour(lecture_id);
        for(LectureDayDTO lecturedayDTO : restList) {

            for (SubjectAttendDTO subjectAttendDTO :subjectAttendList) {

                Integer[] h = subjectAttendDTO.getH();

                for(int i = 0; i < 45; i++) {
                    if (i + 1 >= lecturedayDTO.getStarth() && i + 1 <= lecturedayDTO.getStarth() + lecturedayDTO.getNormhour() - 1) {
                        h[i] = 10;
                    }
                }
                subjectAttendDTO.setH(h);
            }
        }
    }
}
