package com.gamejigi.attend.model.service;

import com.gamejigi.attend.model.dto.MylectureDTO;
import com.gamejigi.attend.model.dto.StudentDTO;
import com.gamejigi.attend.model.dto.TeacherDTO;
import com.gamejigi.attend.util.Pagination;

import java.util.List;
import java.util.Map;

public interface SugangService {

    int getSugangTotalNum(String grade, String term, int student_id);
    List<MylectureDTO> getSugangList(Pagination pagination, String grade, String term, int studnet_id);
    StudentDTO getStudent(Long id);

    int getPoint(String grade, String term, int student_id);
    double getIpoint(String grade, String term, int student_id);
    int getYear(String grade, String term, int student_id);
}
