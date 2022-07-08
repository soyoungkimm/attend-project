package com.gamejigi.attend.model.service;

import com.gamejigi.attend.model.dao.SubjectDAOImpl;
import com.gamejigi.attend.model.dto.SubjectDTO;
import com.gamejigi.attend.util.Pagination;

import java.util.List;

public class SubjectServiceImpl implements SubjectService{

    SubjectDAOImpl subjectDAO = new SubjectDAOImpl();

    @Override
    public int createSubject(SubjectDTO subjectDTO) {
        return subjectDAO.create(subjectDTO);
    }

    @Override
    public int deleteSubject(SubjectDTO subjectDTO) {
        return subjectDAO.delete(subjectDTO);
    }

    @Override
    public int updateSubject(SubjectDTO subjectDTO) {
        return subjectDAO.update(subjectDTO);
    }

    @Override
    public SubjectDTO getSubject(SubjectDTO subjectDTO) {
        return subjectDAO.findById(subjectDTO);
    }

    @Override
    public List<SubjectDTO> getSubjectList(Pagination pagination, String search1, String search2) {
        return subjectDAO.readListUsePaginationAndSearch(pagination,search1,search2);
    }

    @Override
    public int getSubjectTotalNum(String search1,String search2) {
        return subjectDAO.readTotalRowNumUseSearch(search1,search2);
    }
}
