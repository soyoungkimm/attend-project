package com.gamejigi.attend.model.service;

import com.gamejigi.attend.model.dto.StudentAttendDTO;

import java.util.List;

public interface StudentAttendService {
    List<StudentAttendDTO> getStudentAttendList(int student_id, int term);
    void setRestLecture(List<StudentAttendDTO> studentAttendList);
}
