package com.gamejigi.attend.model.service;

import com.gamejigi.attend.model.dao.StLecQaDAOImpl;
import com.gamejigi.attend.model.dto.MylectureDTO;
import com.gamejigi.attend.util.Pagination;

import java.util.List;

public class StLecQaServiceImpl implements StLecQaService {
    StLecQaDAOImpl stLecQaDAO = new StLecQaDAOImpl();

    @Override
    public List<MylectureDTO> getLecQaList(Pagination pagination, int st_id) {
        return stLecQaDAO.readListUsePaginationByStId(pagination, st_id);
    }

    @Override
    public int getLecQaListTotalNum(int st_id) {
        return stLecQaDAO.readTotalNumByStId(st_id);
    }

    @Override
    public MylectureDTO getLecQa(int mylec_id, int st_id) {
        return stLecQaDAO.readLecQaByStIdAndMylecId(mylec_id, st_id);
    }

    @Override
    public int createLecQa(MylectureDTO mylectureDTO) {
        return stLecQaDAO.create(mylectureDTO);
    }

    @Override
    public int updateLecQa(MylectureDTO mylectureDTO) {
        return stLecQaDAO.update(mylectureDTO);
    }
}
