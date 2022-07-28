package com.gamejigi.attend.model.dao;

import com.gamejigi.attend.model.dto.LoginDTO;
import com.gamejigi.attend.model.dto.TeacherDTO;
import com.gamejigi.attend.util.Pagination;

import java.util.List;

public interface TeacherDAO {
    List<TeacherDTO> readList();
    TeacherDTO findById(int id);
    String findDepartNameByDepartId(int depart_id);
    String findPicByTeacherId(int id);
    List<TeacherDTO> readListUsePagination(Pagination pagination);
    List<TeacherDTO> readListUsePaginationAndSearch(Pagination pagination, String search);
    List<TeacherDTO> readListByDepartId(int depart_id);
    int readTotalRowNum();
    int readTotalRowNumUseSearch(String search);
    int create(TeacherDTO teacherDTO);
    int delete(int id);
    int update(TeacherDTO teacherDTO);
    LoginDTO findByUidAndPwd(String uid, String pwd);
}
