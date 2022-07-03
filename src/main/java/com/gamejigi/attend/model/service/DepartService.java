package com.gamejigi.attend.model.service;

import com.gamejigi.attend.model.dao.DepartDAO;
import com.gamejigi.attend.model.dto.DepartDTO;
import java.util.List;

public interface DepartService {
    int create(DepartDTO depart);
    DepartDTO findById(int id);
    List<DepartDTO> searchByName(String name, int startRow, int size);
    int countByName(String name);
    int update(DepartDTO depart);
    int delete(int id);
}
