package com.gamejigi.attend.model.service;

import com.gamejigi.attend.model.dto.StLecDTO;

import java.util.List;

public interface StLecService {
    List<StLecDTO> getStLecList(int term);
    int getTerm(int id);
    int searchDupl(int student_id, int lecture_id);
    int createMyLecture(StLecDTO stLecDTO);
}
