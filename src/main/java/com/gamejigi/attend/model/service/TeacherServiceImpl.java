package com.gamejigi.attend.model.service;

import com.gamejigi.attend.model.dao.TeacherDAOImpl;
import com.gamejigi.attend.model.dto.LoginDTO;
import com.gamejigi.attend.model.dto.TeacherDTO;
import com.gamejigi.attend.util.Pagination;


import java.util.List;
import javax.servlet.http.Part;

public class TeacherServiceImpl implements TeacherService {

    TeacherDAOImpl teacherDAO = null;
    FileServiceImpl fileService = null;

    public TeacherServiceImpl() {
        teacherDAO = new TeacherDAOImpl();
        fileService = new FileServiceImpl();
    }

    @Override
    public List<TeacherDTO> getTeacherList(Pagination pagination, String search) {
        return teacherDAO.readListUsePaginationAndSearch(pagination, search);
    }

    @Override
    public TeacherDTO getTeacher(int id) {
        return teacherDAO.findById(id);
    }

    @Override
    public int getTeacherTotalNum(String search) {
        return teacherDAO.readTotalRowNumUseSearch(search);
    }

    @Override
    public Boolean createTeacher(TeacherDTO teacherDTO, Part filePart, String absolutePath) {

        Boolean result = true;

        // 파일 업로드
        if(filePart.getSize() != 0) {
            String fileName = fileService.makeFileName(filePart);
            result = fileService.uploadImage(filePart, absolutePath, fileName);
            teacherDTO.setPic(fileName);
        }

        // 학과 이름 set
        String departName = teacherDAO.findDepartNameByDepartId(teacherDTO.getDepart_id());
        teacherDTO.setDepart_name(departName);

        // db에 값 저장
        if (result) {
            int row_num = teacherDAO.create(teacherDTO);
            return row_num > 0;
        }
        return false;
    }

    @Override
    public Boolean updateTeacher(TeacherDTO teacherDTO, Part filePart, String absolutePath) {
        Boolean result = true;

        if(filePart.getSize() != 0) {
            // 이미지 이름 set
            String fileName = fileService.makeFileName(filePart);

            // 기존의 파일이 있으면 파일 삭제
            String pic = teacherDAO.findPicByTeacherId(teacherDTO.getId());
            if (pic != null) {
                fileService.deleteFile(pic, absolutePath);
            }

            // 파일 업로드
            result = fileService.uploadImage(filePart, absolutePath, fileName);
            teacherDTO.setPic(fileName);
        }

        // 학과 이름 set
        String departName = teacherDAO.findDepartNameByDepartId(teacherDTO.getDepart_id());
        teacherDTO.setDepart_name(departName);

        // db에 값 저장
        if (result) {
            int row_num = teacherDAO.update(teacherDTO);
            if (row_num > 0) return true;
        }

        return false;
    }

    @Override
    public int deleteTeacher(int id, String absolutePath) {

        String pic = teacherDAO.findPicByTeacherId(id);
        if (pic != null) {
            fileService.deleteFile(pic, absolutePath);
        }

        return teacherDAO.delete(id);
    }

    @Override
    public LoginDTO loginCheck(String uid, String pwd) {
        return teacherDAO.findByUidAndPwd(uid, pwd);
    }
}
