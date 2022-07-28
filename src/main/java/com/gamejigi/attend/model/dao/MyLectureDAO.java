package com.gamejigi.attend.model.dao;

import com.gamejigi.attend.model.dto.MyLectureDTO2;
import com.gamejigi.attend.model.dto.MylectureDTO;
import com.gamejigi.attend.model.dto.StLecDTO;
import com.gamejigi.attend.model.dto.StudentAttendDTO;
import com.gamejigi.attend.model.dto.SubjectAttendDTO;
import com.gamejigi.attend.util.Pagination;

import java.util.List;
import java.util.Map;

public interface MyLectureDAO {
    int updateAttend(int lecture_id, int student_id, int h, int v);
    int updateAllAttend(int lectureday_id, int starth, int normhour);
    int createMyLecture(StLecDTO stLecDTO);
    int deleteMyLecture(int student_id, int lecture_id);
    int readRowNumByStudentIdAndLectureId(int student_id, int lecture_id);
    int readTotalRowNumUseSearch(String grade, String term, int student_id);
    List<SubjectAttendDTO> readStudentAttendList(int lecture_id);
    MyLectureDTO2 findByLectureIdAndStudentId(int lecture_id, int student_id);
    int updateLateAndAbsentAndAttendScore(MyLectureDTO2 myLectureDTO);
    List<MyLectureDTO2> readMyLectureList(int lecture_id);
    MyLectureDTO2 findLateAbsentScore(int lecture_id, int student_id);
    List<StudentAttendDTO> readStudentAttendList2(int student_id, int term);
    List<MylectureDTO> readSugangList(Pagination pagination, String grade, String term, int student_id);
    int getPoint(String grade, String term, int student_id);
    double getIpoint(String grade, String term, int student_id);
    int getYear(String grade ,String term, int student_id);
}
