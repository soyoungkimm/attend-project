package com.gamejigi.attend.model.dao;

import com.gamejigi.attend.model.dto.SubjectDTO;
import com.gamejigi.attend.util.Pagination;

import java.util.List;

public interface SubjectDAO {
    int create(SubjectDTO subjectDTO);
    int delete(SubjectDTO subjectDTO);
    int update(SubjectDTO subjectDTO);
    SubjectDTO findById(SubjectDTO subjectDTO);
    List<SubjectDTO> findByDepartIdAndYearAndTerm(int depart_id, int year, int term);
    List<SubjectDTO> readList();
    List<SubjectDTO> readListUsePaginationAndSearch(Pagination pagination, String search1, String search2);
    int readTotalRowNumUseSearch(String search1,String search2);
    List<SubjectDTO> readSubjectListByStaffId(int staff_id);
}
