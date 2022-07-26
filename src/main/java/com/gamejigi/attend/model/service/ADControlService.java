package com.gamejigi.attend.model.service;

import com.gamejigi.attend.model.dto.ADControlDTO;

public interface ADControlService {
    ADControlDTO findControl();
    int readCount();
    Boolean createControl();
    Boolean updateControl(ADControlDTO adControlDTO);
}
