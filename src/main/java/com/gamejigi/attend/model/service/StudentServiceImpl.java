package com.gamejigi.attend.model.service;

import com.gamejigi.attend.model.dao.StudentDAOImpl;
import com.gamejigi.attend.model.dto.LoginDTO;
import com.gamejigi.attend.model.dto.StudentDTO;
import com.gamejigi.attend.util.Pagination;

import javax.servlet.http.Part;
import java.util.List;

public class StudentServiceImpl implements StudentService {

    StudentDAOImpl studentDAO = null;
    FileServiceImpl fileService = null;

    public StudentServiceImpl() {
        studentDAO = new StudentDAOImpl();
        fileService = new FileServiceImpl();
    }

    @Override
    public List<StudentDTO> getStudentList(Pagination pagination, String search) {
        return studentDAO.readListUsePaginationAndSearch(pagination, search);
    }

    @Override
    public Boolean createStudent(StudentDTO studentDTO, Part filePart, String absolutePath) {
        Boolean result = true;

        // 파일 업로드
        if(filePart.getSize() != 0) {
            String fileName = fileService.makeFileName(filePart);
            result = fileService.uploadImage(filePart, absolutePath, fileName);
            studentDTO.setPic(fileName);
        }

        // 학과 이름 set
        String departName = studentDAO.findDepartNameByDepartId(studentDTO.getDepart_id());
        studentDTO.setDepart_name(departName);

        // db에 값 저장
        if (result) {
            int row_num = studentDAO.create(studentDTO);
            if (row_num > 0) return true;
        }

        return false;
    }

    @Override
    public StudentDTO getStudent(Long id) {
        return studentDAO.findById(id);
    }

    @Override
    public int deleteStudent(Long id, String absolutePath) {

        // 기존의 파일이 있으면 파일 삭제
        String pic = studentDAO.findPicByStudentId(id);
        if (pic != null) {
            fileService.deleteFile(pic, absolutePath);
        }

        return studentDAO.delete(id);
    }

    @Override
    public int getStudentTotalNum(String search) {
        return studentDAO.readTotalRowNumUseSearch(search);
    }

    @Override
    public Boolean updateStudent(StudentDTO studentDTO, Part filePart, String absolutePath) {
        Boolean result = true;

        if(filePart.getSize() != 0) {
            // 이미지 이름 set
            String fileName = fileService.makeFileName(filePart);

            // 기존의 파일이 있으면 파일 삭제
            String pic = studentDAO.findPicByStudentId(studentDTO.getId());
            if (pic != null) {
                fileService.deleteFile(pic, absolutePath);
            }

            // 파일 업로드
            result = fileService.uploadImage(filePart, absolutePath, fileName);
            studentDTO.setPic(fileName);
        }

        // 학과 이름 set
        String departName = studentDAO.findDepartNameByDepartId(studentDTO.getDepart_id());
        studentDTO.setDepart_name(departName);

        // db에 값 저장
        if (result) {
            int row_num = studentDAO.update(studentDTO);
            if (row_num > 0) return true;
        }

        return false;
    }

    @Override
    public LoginDTO loginCheck(String uid, String pwd) {
        return studentDAO.findByUidAndPwd(uid, pwd);
    }

}
