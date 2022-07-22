package com.gamejigi.attend.model.service;

import com.gamejigi.attend.model.dto.LectureDayDTO;
import com.gamejigi.attend.util.Pagination;

import java.util.List;

public interface LecMoveService {
    List<LectureDayDTO> readLecMoveList(Pagination pagination, String s1, String s2);
    int getLecMoveTotalNum(String s1, String s2);
}
