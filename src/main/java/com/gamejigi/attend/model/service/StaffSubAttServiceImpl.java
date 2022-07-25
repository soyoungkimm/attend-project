package com.gamejigi.attend.model.service;

import com.gamejigi.attend.model.dao.*;
import com.gamejigi.attend.model.dto.SubjectDTO;

import java.util.List;

public class StaffSubAttServiceImpl implements StaffSubAttService {

    SubjectDAOImpl subjectDAO;
    LectureDAOImpl lectureDAO;

    public StaffSubAttServiceImpl() {
        subjectDAO = new SubjectDAOImpl();
        lectureDAO = new LectureDAOImpl();
    }

    @Override
    public List<SubjectDTO> getSubject(int staff_id) {
        return subjectDAO.readSubjectListByStaffId(staff_id);
    }

    @Override
    public int getLectureId(String year, int term, int grade, String ban, int subject, int staff_id) {
        return lectureDAO.findIdByStaffIdAndSearchText(year, term, grade, ban, subject, staff_id);
    }
}
