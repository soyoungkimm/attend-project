package com.gamejigi.attend.model.dao;

import com.gamejigi.attend.model.dto.StudentDTO;
import com.gamejigi.attend.util.Pagination;

import java.util.List;

public interface StudentDAO {
    List<StudentDTO> readListUsePaginationAndSearch(Pagination pagination, String search);
    int create(StudentDTO studentDTO);
    StudentDTO findById(Long id);
    int delete(Long id);
    int readTotalRowNumUseSearch(String search);
    int update(StudentDTO studentDTO);
    String findDepartNameByDepartId(int depart_id);
    String findPicByStudentId(Long id);
    List<StudentDTO> findWithAttend(Long lectureday_id, int starth, int normhour);
}
