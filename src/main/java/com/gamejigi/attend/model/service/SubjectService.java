package com.gamejigi.attend.model.service;

import com.gamejigi.attend.model.dto.SubjectDTO;
import com.gamejigi.attend.util.Pagination;

import java.util.List;

public interface SubjectService {
    int createSubject(SubjectDTO subjectDTO);
    int deleteSubject(SubjectDTO subjectDTO);
    int updateSubject(SubjectDTO subjectDTO);
    SubjectDTO getSubject(SubjectDTO subjectDTO);
    List<SubjectDTO> getSubjectList(Pagination pagination, String search1, String search2);
    int getSubjectTotalNum(String search1,String search2);
}
