package com.gamejigi.attend.model.service;

import com.gamejigi.attend.model.dto.LectureDayDTO;
import com.gamejigi.attend.util.Pagination;

import java.util.List;
import java.util.Map;

public interface TeLecMoveService {
    List<LectureDayDTO> readListUsePaginationByTeacher(Pagination p, int id);

    int getTotalRowsByTeacher(int id);

    List<Integer[]> getTimetable(int depart, int year, int term);

    String getBuildingNameByRoomName(String roomName);

    List<Map<String, String>> getBuildingListByDepart(int depart);

    List<Map<String, String>> getRoomListByDepart(int depart);

    void applyLecMove(LectureDayDTO lectureDay);

    void cancelLecMove(int lecDayId);
}
