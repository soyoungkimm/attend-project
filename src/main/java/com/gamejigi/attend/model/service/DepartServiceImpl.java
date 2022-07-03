package com.gamejigi.attend.model.service;

import com.gamejigi.attend.model.dao.DepartDAO;
import com.gamejigi.attend.model.dao.DepartDAOImpl;
import com.gamejigi.attend.model.dto.DepartDTO;

import java.util.List;

public class DepartServiceImpl implements DepartService {

    DepartDAO dao = null;

    public DepartServiceImpl() {
        dao = new DepartDAOImpl();
    }

    @Override
    public int create(DepartDTO depart) {
        return dao.create(depart);
    }

    @Override
    public DepartDTO findById(int id) {
        return dao.findById(id);
    }

    @Override
    public List<DepartDTO> searchByName(String name, int startRow, int size) {
        return dao.searchByName(name, startRow, size);
    }

    @Override
    public int countByName(String name) {
        return dao.countByName(name);
    }

    @Override
    public int update(DepartDTO depart) {
        return dao.update(depart);
    }

    @Override
    public int delete(int id) {
        return dao.delete(id);
    }
}
