package com.gamejigi.attend.model.service;

import com.gamejigi.attend.model.dto.SubjectDTO;

import java.util.List;

public interface StaffSubAttService {
    List<SubjectDTO> getSubject(int staff_id);
    int getLectureId(String year, int term, int grade, String ban, int subject, int staff_id);
}
