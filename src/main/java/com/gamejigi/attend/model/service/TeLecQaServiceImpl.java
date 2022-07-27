package com.gamejigi.attend.model.service;

import com.gamejigi.attend.model.dao.TeLecQaDAOImpl;
import com.gamejigi.attend.model.dto.MylectureDTO;
import com.gamejigi.attend.util.Pagination;

import java.util.List;

public class TeLecQaServiceImpl implements TeLecQaService{
    TeLecQaDAOImpl teLecQaDAO = new TeLecQaDAOImpl();

    @Override
    public List<MylectureDTO> getLecQaList(Pagination pagination) {
        return teLecQaDAO.readListUsePagination(pagination);
    }

    @Override
    public int editLecQaList(MylectureDTO mylectureDTO) {
        return teLecQaDAO.update(mylectureDTO);
    }

    @Override
    public int getLecQaListTotalNum() {
        return teLecQaDAO.readTotalNum();
    }

    @Override
    public MylectureDTO getLecQa(MylectureDTO mylectureDTO) {
        return teLecQaDAO.readLecQaUseId(mylectureDTO);
    }
}
