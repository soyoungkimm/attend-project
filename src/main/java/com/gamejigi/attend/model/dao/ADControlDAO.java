package com.gamejigi.attend.model.dao;

import com.gamejigi.attend.model.dto.ADControlDTO;

public interface ADControlDAO {
    ADControlDTO read();
    int readTotalRowNum();
    int create();
    int update(ADControlDTO adControlDTO);
}
