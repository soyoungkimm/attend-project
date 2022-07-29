package com.gamejigi.attend.model.dao;

import com.gamejigi.attend.model.dto.MylectureDTO;
import com.gamejigi.attend.util.Pagination;

import java.util.List;

public interface TeLecQaDAO {
    List<MylectureDTO> readListUsePagination(Pagination pagination);
    int update(MylectureDTO mylectureDTO);
    int readTotalNum();
    MylectureDTO readLecQaUseId(MylectureDTO mylectureDTO);
    List<MylectureDTO> readListUsePaginationByTeacherId(Pagination pagination, int teacher_id);
    int readTotalNumByTeacherId(int teacher_id);


}
