package com.gamejigi.attend.model.service;

import com.gamejigi.attend.model.dao.LectureDAOImpl;
import com.gamejigi.attend.model.dao.SubjectDAOImpl;
import com.gamejigi.attend.model.dao.TeacherDAOImpl;
import com.gamejigi.attend.model.dto.LectureDTO;
import com.gamejigi.attend.model.dto.SubjectDTO;
import com.gamejigi.attend.model.dto.TeacherDTO;
import com.gamejigi.attend.util.Pagination;

import java.time.LocalDate;
import java.util.List;

public class LectureServiceImpl implements LectureService{

    LectureDAOImpl lectureDAO = new LectureDAOImpl();
    TeacherDAOImpl teacherDAO = new TeacherDAOImpl();
    SubjectDAOImpl subjectDAO = new SubjectDAOImpl();

    public LectureServiceImpl(){
        lectureDAO = new LectureDAOImpl();
        teacherDAO = new TeacherDAOImpl();
        subjectDAO = new SubjectDAOImpl();
    }


    @Override
    public List<LectureDTO> getLectureList(Pagination pagination, String yyyy, String term, String grade) {
        return lectureDAO.readListUsePaginationAndSearch(pagination, yyyy, term, grade);
    }

    @Override
    public List<TeacherDTO> getTeacherListByDepartID(int depart_id) {
        return teacherDAO.readListByDepartId(depart_id);
    }

    @Override
    public List<SubjectDTO> getSubjectList(int depart_id, int year, int term){
        return subjectDAO.findByDepartIdAndYearAndTerm(depart_id, year, term);
    }

    @Override
    public int createLecture(LectureDTO lectureDTO) {
        return lectureDAO.create(lectureDTO);
    }

    @Override
    public LectureDTO getLecture(int id) {
        return lectureDAO.findById(id);
    }

    @Override
    public int deleteLecture(int id) {
        return lectureDAO.delete(id);
    }

    @Override
    public int getLectureTotalNum(String yyyy, String term, String grade) {
        int var = lectureDAO.readTotalRowNumUseSearch(yyyy, term, grade);
        return var;
    }


    @Override
    public int updateLecture(LectureDTO lectureDTO) {
        return lectureDAO.update(lectureDTO);
    }

    public String getYear(){
        LocalDate now = LocalDate.now();

        return Integer.toString(now.getYear());
    }

    public String getTerm(){
        LocalDate now = LocalDate.now();

        if(now.getMonthValue() < 8){
            return "1";
        }else{
            return "2";
        }

    }

    public String getGrade(){
        return "1";
    }

}
