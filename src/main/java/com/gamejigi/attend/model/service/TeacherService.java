package com.gamejigi.attend.model.service;

import com.gamejigi.attend.model.dto.LoginDTO;
import com.gamejigi.attend.model.dto.TeacherDTO;
import com.gamejigi.attend.util.Pagination;

import javax.servlet.http.Part;
import java.util.List;

public interface TeacherService {
    List<TeacherDTO> getTeacherList(Pagination pagination, String search);
    TeacherDTO getTeacher(int id);
    int getTeacherTotalNum(String search);
    Boolean createTeacher(TeacherDTO teacherDTO, Part filePart, String absolutePath);
    Boolean updateTeacher(TeacherDTO teacherDTO, Part filePart, String absolutePath);
    int deleteTeacher(int id, String absolutePath);
    LoginDTO loginCheck(String uid, String pwd);

}
