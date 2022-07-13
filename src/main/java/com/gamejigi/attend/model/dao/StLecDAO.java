package com.gamejigi.attend.model.dao;

import com.gamejigi.attend.model.dto.StLecDTO;

import java.util.List;

public interface StLecDAO {
    List<StLecDTO> readList();
    int findDepartIdByDepartName(String depart_name);
    int findTermByLectureId(int lecture_id);
}
