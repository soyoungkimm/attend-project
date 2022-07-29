package com.gamejigi.attend.model.service;

import com.gamejigi.attend.model.dto.MylectureDTO;
import com.gamejigi.attend.util.Pagination;

import java.util.List;

public interface StLecQaService {
    List<MylectureDTO> getLecQaList(Pagination pagination, int st_id);
    int getLecQaListTotalNum(int st_id);
    MylectureDTO getLecQa(int mylec_id, int st_id);
    int createLecQa(MylectureDTO mylectureDTO);
    int updateLecQa(MylectureDTO mylectureDTO);
}
