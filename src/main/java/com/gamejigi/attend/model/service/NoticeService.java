package com.gamejigi.attend.model.service;

import com.gamejigi.attend.model.dto.NoticeDTO;
import com.gamejigi.attend.util.Pagination;

import java.util.List;

public interface NoticeService {
    List<NoticeDTO> getNoticeList(Pagination pagination, String search);
    int createNotice(NoticeDTO noticeDTO);
    NoticeDTO getNotice(Long id);
    int deleteNotice(Long id);
    int getNoticeTotalNum(String search);
    int updateNotice(NoticeDTO noticeDTO);
}
