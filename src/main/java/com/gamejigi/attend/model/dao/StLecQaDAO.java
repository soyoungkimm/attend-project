package com.gamejigi.attend.model.dao;

import com.gamejigi.attend.model.dto.MylectureDTO;
import com.gamejigi.attend.util.Pagination;

import java.util.List;

public interface StLecQaDAO {
    List<MylectureDTO> readListUsePaginationByStId(Pagination pagination, int student_id);
    int readTotalNumByStId(int student_id);
    MylectureDTO readLecQaByStIdAndMylecId(int mylecture_id , int student_id);
    int create(MylectureDTO mylectureDTO);
    int update(MylectureDTO mylectureDTO);
}
