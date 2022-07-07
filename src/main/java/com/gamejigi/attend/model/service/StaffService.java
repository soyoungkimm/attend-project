package com.gamejigi.attend.model.service;

import com.gamejigi.attend.model.dto.StaffDTO;
import com.gamejigi.attend.util.Pagination;

import javax.servlet.http.Part;
import java.util.List;

public interface StaffService {
    List<StaffDTO> getStaffList(Pagination pagination, String search);
    StaffDTO getStaff(int id);
    int getStaffTotalNum(String search);
    Boolean createStaff(StaffDTO staffDTO, Part filePart, String absolutePath);
    Boolean updateStaff(StaffDTO staffDTO, Part filePart, String absolutePath);
    int deleteStaff(int id, String absolutePath);

}
