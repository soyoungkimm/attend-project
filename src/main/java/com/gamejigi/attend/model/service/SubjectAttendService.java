package com.gamejigi.attend.model.service;

import com.gamejigi.attend.model.dto.LectureDayDTO;
import com.gamejigi.attend.model.dto.MyLectureDTO;
import com.gamejigi.attend.model.dto.SubjectAttendDTO;
import com.gamejigi.attend.model.dto.SubjectDTO;

import java.util.List;

public interface SubjectAttendService {
    List<SubjectAttendDTO> getStudentAttendList(int lecture_id);
    LectureDayDTO getStartTeachingAndHourAndStartDate(int lecture_id);
    List<SubjectDTO> getSubject(int teacher_id);
    int getLectureId(String year, int term, int grade, String ban, int subject, int teacher_id);
    List<String> getDateList(String startDate);
    List<LectureDayDTO> getRestList(int lecture_id);
    MyLectureDTO getLateAbsentScore(int lecture_id, int student_id);
    void setRestLecture(List<SubjectAttendDTO> subjectAttendList, int lecture_id);
}
