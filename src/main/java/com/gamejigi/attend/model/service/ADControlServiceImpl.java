package com.gamejigi.attend.model.service;

import com.gamejigi.attend.model.dao.ADControlDAOImpl;
import com.gamejigi.attend.model.dto.ADControlDTO;

public class ADControlServiceImpl implements ADControlService {

    ADControlDAOImpl adControlDAO = null;

    public ADControlServiceImpl() {
        adControlDAO = new ADControlDAOImpl();
    }

    @Override
    public ADControlDTO findControl() {
        return adControlDAO.read();
    }

    @Override
    public int readCount() {
        return adControlDAO.readTotalRowNum();
    }

    @Override
    public Boolean createControl() {

        int row_num = adControlDAO.create();
        if (row_num > 0) return true;
        else return false;
    }

    @Override
    public Boolean updateControl(ADControlDTO adControlDTO) {
        int row_num = adControlDAO.update(adControlDTO);
        if (row_num > 0) return true;
        else return false;
    }
}
