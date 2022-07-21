package com.gamejigi.attend.model.service;

import com.gamejigi.attend.model.dao.*;
import com.gamejigi.attend.model.dto.DepartDTO;
import com.gamejigi.attend.model.dto.LectureDayDTO;
import com.gamejigi.attend.model.dto.RoomDTO;
import com.gamejigi.attend.model.dto.TeacherDTO;
import com.gamejigi.attend.util.Pagination;

import java.util.List;

public class ADLecMoveServiceImpl implements ADLecMoveService{

    LectureDayDAOImpl lectureDayDAO = new LectureDayDAOImpl();
    RoomDAOImpl roomDAO = new RoomDAOImpl();
    DepartDAOImpl departDAO = new DepartDAOImpl();
    TeacherDAOImpl teacherDAO = new TeacherDAOImpl();

    public ADLecMoveServiceImpl(){
        lectureDayDAO = new LectureDayDAOImpl();
        roomDAO = new RoomDAOImpl();
        departDAO = new DepartDAOImpl();
        teacherDAO = new TeacherDAOImpl();
    }

    @Override
    public TeacherDTO getTeacher(int id) {
        return teacherDAO.findById(id);
    }

    @Override
    public RoomDTO getRoom(int id) {
        return roomDAO.findById(id);
    }

    @Override
    public DepartDTO getDepart(int id) {
        return departDAO.findById(id);
    }

    @Override
    public List<LectureDayDTO> readRestListByStateAndDepartId(Pagination pagination, int depart_id) {
        List<LectureDayDTO>lectureDayDTOList =  lectureDayDAO.readRestListUsePaginationAndDepartId(pagination, depart_id);
//        lectureDayDTOList.forEach(dto -> {
//            if (dto.getReststart() == null)
//        });
        return lectureDayDTOList;
    }

    @Override
    public int getRestTotalNum(int depart_id){
        return lectureDayDAO.readTotalRowNumUseDepartID(depart_id);
    }

    @Override
    public LectureDayDTO getLectureDayDTO(long id){
        return lectureDayDAO.findById(id);
    }

    public int updateLectureDayState(long id, int state){
        return lectureDayDAO.updateStateById(id, state);
    }
}
