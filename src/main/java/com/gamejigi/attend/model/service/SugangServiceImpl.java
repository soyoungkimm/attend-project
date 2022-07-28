package com.gamejigi.attend.model.service;

import com.gamejigi.attend.model.dao.MyLectureDAOImpl;
import com.gamejigi.attend.model.dao.StudentDAOImpl;
import com.gamejigi.attend.model.dto.MylectureDTO;
import com.gamejigi.attend.model.dto.StudentDTO;
import com.gamejigi.attend.util.Pagination;

import java.util.List;
import java.util.Map;

public class SugangServiceImpl implements SugangService{

    MyLectureDAOImpl myLectureDAO = null;
    StudentDAOImpl studentDAO = null;

    public SugangServiceImpl(){
        myLectureDAO = new MyLectureDAOImpl();
        studentDAO = new StudentDAOImpl();
    }

    @Override
    public int getSugangTotalNum(String grade, String term, int student_id) {
        return myLectureDAO.readTotalRowNumUseSearch(grade, term, student_id);
    }

    @Override
    public List<MylectureDTO> getSugangList(Pagination pagination, String grade, String term, int studnet_id) {

        return myLectureDAO.readSugangList(pagination, grade, term, studnet_id);
    }

    @Override
    public StudentDTO getStudent(Long id){
        return studentDAO.findById(id);
    }

    @Override
    public int getPoint(String grade, String term, int student_id){
        return myLectureDAO.getPoint(grade, term, student_id);
    }

    @Override
    public double getIpoint(String grade, String term, int student_id){
        return myLectureDAO.getIpoint(grade, term, student_id);
    }

    @Override
    public int getYear(String grade, String term, int student_id){
        return myLectureDAO.getYear(grade, term, student_id);
    }

}
