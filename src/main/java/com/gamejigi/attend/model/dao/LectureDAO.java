package com.gamejigi.attend.model.dao;

import com.gamejigi.attend.model.dto.LectureDTO;
import com.gamejigi.attend.model.dto.SubjectDTO;
import com.gamejigi.attend.util.Pagination;

import java.util.List;

public interface LectureDAO {
    int create(LectureDTO lectureDTO);
    LectureDTO read(LectureDTO lectureDTO);
    LectureDTO findById(int id);
    List<LectureDTO> readListUsePaginationAndSearch(Pagination pagination, int depart_id, String yyyy, String term, String grade);
    int update(LectureDTO lectureDTO);
    int delete(int id);
    int readTotalRowNumUseSearch(int depart_id, String yyyy, String term, String grade);
    List<SubjectDTO> findSubjectByTeacherId(int teacher_id);
    int findIdByTeacherIdAndSearchText(String year, int term, int grade, String ban, int subject, int teacher_id);
    int findIdByStaffIdAndSearchText(String year, int term, int grade, String ban, int subject, int staff_id);
}
