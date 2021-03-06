package com.gamejigi.attend.model.dao;

import com.gamejigi.attend.model.dto.DepartDTO;

import java.util.List;

public interface DepartDAO {
    int create(DepartDTO depart);
    DepartDTO findById(int id);
    List<DepartDTO> searchByName(String name, int startRow, int size);
    List<DepartDTO> readList();
    int countByName(String name);
    int update(DepartDTO depart);
    int delete(int id);
}
