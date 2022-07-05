package com.gamejigi.attend.model.service;

import com.gamejigi.attend.model.dto.StudentDTO;
import com.gamejigi.attend.util.Pagination;

import javax.servlet.http.Part;
import java.util.List;

public interface StudentService {
    List<StudentDTO> getStudentList(Pagination pagination, String search);
    Boolean createStudent(StudentDTO studentDTO, Part filePart, String absolutePath);
    StudentDTO getStudent(Long id);
    int deleteStudent(Long id, String absolutePath);
    int getStudentTotalNum(String search);
    Boolean updateStudent(StudentDTO studentDTO, Part filePart, String absolutePath);
}
