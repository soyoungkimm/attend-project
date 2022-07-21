package com.gamejigi.attend.model.service;

import com.gamejigi.attend.model.dto.DepartDTO;
import com.gamejigi.attend.model.dto.LectureDayDTO;
import com.gamejigi.attend.model.dto.RoomDTO;
import com.gamejigi.attend.model.dto.TeacherDTO;
import com.gamejigi.attend.util.Pagination;

import java.util.List;

public interface ADLecMoveService {

    TeacherDTO getTeacher(int id);
    RoomDTO getRoom(int id);
    DepartDTO getDepart(int id);
    List<LectureDayDTO> readRestListByStateAndDepartId(Pagination pagination, int depart_id);

    int getRestTotalNum(int depart_id);
    LectureDayDTO getLectureDayDTO(long id);
    int updateLectureDayState(long id, int state);
}
