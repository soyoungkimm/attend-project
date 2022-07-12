package com.gamejigi.attend.model.service;

import com.gamejigi.attend.model.dto.HolidayDTO;
import com.gamejigi.attend.model.dto.LectureDTO;
import com.gamejigi.attend.model.dto.SubjectDTO;
import com.gamejigi.attend.model.dto.TeacherDTO;
import com.gamejigi.attend.util.Pagination;

import java.util.List;

public interface LectureService {
    List<LectureDTO> getLectureList(Pagination pagination, String yyyy, String term, String grade);
    List<TeacherDTO> getTeacherListByDepartID(int depart_id);
    List<SubjectDTO> getSubjectList(int depart_id, int year, int term);
    int createLecture(LectureDTO lectureDTO);
    LectureDTO getLecture(int id);
    int deleteLecture(int id);
    int getLectureTotalNum(String yyyy, String term, String grade);
    int updateLecture(LectureDTO lectureDTO);
}
