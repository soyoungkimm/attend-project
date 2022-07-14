package com.gamejigi.attend.model.dao;

import com.gamejigi.attend.model.dto.MyLectureDTO;
import com.gamejigi.attend.model.dto.SubjectAttendDTO;

import java.util.List;

public interface MyLectureDAO {
    int updateAttend(int lecture_id, int student_id, int h, int v);
    int updateAllAttend(int lectureday_id, int starth, int normhour);
    List<SubjectAttendDTO> readStudentAttendList(int lecture_id);
    MyLectureDTO findByLectureIdAndStudentId(int lecture_id, int student_id);
    int updateLateAndAbsentAndAttendScore(MyLectureDTO myLectureDTO);
    List<MyLectureDTO> readMyLectureList(int lecture_id);
    MyLectureDTO findLateAbsentScore(int lecture_id, int student_id);
}
