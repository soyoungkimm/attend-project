package com.gamejigi.attend.model.service;

import com.gamejigi.attend.model.dao.NoticeDAO;
import com.gamejigi.attend.model.dao.NoticeDAOImpl;
import com.gamejigi.attend.model.dto.NoticeDTO;
import com.gamejigi.attend.util.Pagination;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NoticeServiceImpl implements NoticeService {

    NoticeDAOImpl noticeDAO = null;

    public NoticeServiceImpl() {
        noticeDAO = new NoticeDAOImpl();
    }

    @Override
    public List<NoticeDTO> getNoticeList(Pagination pagination, String search) {
        return noticeDAO.readListUsePaginationAndSearch(pagination, search);
    }

    @Override
    public int createNotice(NoticeDTO noticeDTO) {
        return noticeDAO.create(noticeDTO);
    }

    @Override
    public NoticeDTO getNotice(Long id) {
        return noticeDAO.findById(id);
    }

    @Override
    public int deleteNotice(Long id) {
        return noticeDAO.delete(id);
    }

    @Override
    public int getNoticeTotalNum(String search) {
        return noticeDAO.readTotalRowNumUseSearch(search);
    }

    @Override
    public int updateNotice(NoticeDTO noticeDTO) {
        return noticeDAO.update(noticeDTO);
    }
}
