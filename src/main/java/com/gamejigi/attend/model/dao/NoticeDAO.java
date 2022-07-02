package com.gamejigi.attend.model.dao;

import com.gamejigi.attend.model.dto.NoticeDTO;
import com.gamejigi.attend.util.Pagination;

import java.util.List;

public interface NoticeDAO {
    List<NoticeDTO> readList();
    List<NoticeDTO> readListUsePagination(Pagination pagination);
    List<NoticeDTO> readListUsePaginationAndSearch(Pagination pagination, String search);
    int create(NoticeDTO noticeDTO);
    NoticeDTO findById(Long id);
    int delete(Long id);
    int readTotalRowNum();
    int readTotalRowNumUseSearch(String search);
    int update(NoticeDTO noticeDTO);
}
