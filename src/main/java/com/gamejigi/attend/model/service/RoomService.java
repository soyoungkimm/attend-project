package com.gamejigi.attend.model.service;

import com.gamejigi.attend.model.dto.RoomDTO;
import com.gamejigi.attend.util.Pagination;

import java.util.List;

public interface RoomService {
    List<RoomDTO> getRoomList(Pagination pagination, String search);
    int createRoom(RoomDTO roomDTO);
    RoomDTO getRoom(int id);
    int deleteRoom(int id);
    int getRoomTotalNum(String search);
    int updateRoom(RoomDTO roomDTO);
}
