package com.gamejigi.attend.model.service;

import com.gamejigi.attend.model.dto.MylectureDTO;
import com.gamejigi.attend.util.Pagination;

import java.util.List;

public interface TeLecQaService {
    List<MylectureDTO> getLecQaList(Pagination pagination);
    int editLecQaList(MylectureDTO mylectureDTO);
    int getLecQaListTotalNum();
    MylectureDTO getLecQa(MylectureDTO mylectureDTO);
}
