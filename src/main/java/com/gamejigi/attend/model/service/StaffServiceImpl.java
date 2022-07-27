package com.gamejigi.attend.model.service;

import com.gamejigi.attend.model.dao.StaffDAOImpl;
import com.gamejigi.attend.model.dto.StaffDTO;
import com.gamejigi.attend.util.Pagination;


import java.util.List;
import javax.servlet.http.Part;

public class StaffServiceImpl implements StaffService {

    StaffDAOImpl staffDAO = null;
    FileServiceImpl fileService = null;

    public StaffServiceImpl() {
        staffDAO = new StaffDAOImpl();
        fileService = new FileServiceImpl();
    }

    @Override
    public List<StaffDTO> getStaffList(Pagination pagination, String search) {
        return staffDAO.readListUsePaginationAndSearch(pagination, search);
    }

    @Override
    public StaffDTO getStaff(int id) {
        return staffDAO.findById(id);
    }

    @Override
    public int getStaffTotalNum(String search) {
        return staffDAO.readTotalRowNumUseSearch(search);
    }

    @Override
    public Boolean createStaff(StaffDTO staffDTO, Part filePart, String absolutePath) {

        Boolean result = true;

        // 파일 업로드
        if(filePart.getSize() != 0) {
            String fileName = fileService.makeFileName(filePart);
            result = fileService.uploadImage(filePart, absolutePath, fileName);
            staffDTO.setPic(fileName);
        }

        // 학과 이름 set
        String departName = staffDAO.findDepartNameByDepartId(staffDTO.getDepart_id());
        staffDTO.setDepart_name(departName);

        // db에 값 저장
        if (result) {
            int row_num = staffDAO.create(staffDTO);
            if (row_num > 0) return true;
        }
        return false;
    }

    @Override
    public Boolean updateStaff(StaffDTO staffDTO, Part filePart, String absolutePath) {
        Boolean result = true;

        if(filePart.getSize() != 0) {
            // 이미지 이름 set
            String fileName = fileService.makeFileName(filePart);

            // 기존의 파일이 있으면 파일 삭제
            String pic = staffDAO.findPicByStaffId(staffDTO.getId());
            if (pic != null) {
                fileService.deleteFile(pic, absolutePath);
            }

            // 파일 업로드
            result = fileService.uploadImage(filePart, absolutePath, fileName);
            staffDTO.setPic(fileName);
        }

        // 학과 이름 set
        String departName = staffDAO.findDepartNameByDepartId(staffDTO.getDepart_id());
        staffDTO.setDepart_name(departName);

        // db에 값 저장
        if (result) {
            int row_num = staffDAO.update(staffDTO);
            if (row_num > 0) return true;
        }

        return false;
    }

    @Override
    public int deleteStaff(int id, String absolutePath) {

        String pic = staffDAO.findPicByStaffId(id);
        if (pic != null) {
            fileService.deleteFile(pic, absolutePath);
        }

        return staffDAO.delete(id);
    }

    @Override
    public int getDepartId(int staff_id) {
        return staffDAO.findDepartIdById(staff_id);
    }
}
