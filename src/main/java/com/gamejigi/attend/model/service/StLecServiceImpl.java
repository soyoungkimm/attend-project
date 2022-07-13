package com.gamejigi.attend.model.service;

import com.gamejigi.attend.model.dao.MyLectureDAOImpl;
import com.gamejigi.attend.model.dao.StLecDAOImpl;
import com.gamejigi.attend.model.dto.MylectureDTO;
import com.gamejigi.attend.model.dto.StLecDTO;
import com.gamejigi.attend.model.dto.TeacherDTO;

import javax.servlet.http.Part;
import java.util.List;

public class StLecServiceImpl implements StLecService{
    StLecDAOImpl stLecDAO = null;
    MyLectureDAOImpl mylectureDAO = null;

    public StLecServiceImpl() {
        stLecDAO = new StLecDAOImpl();
        mylectureDAO = new MyLectureDAOImpl();
    }

    @Override
    public List<StLecDTO> getStLecList() {
        return stLecDAO.readList();
    }

    public List<MylectureDTO> getMyLecList(int student_id) {
        return mylectureDAO.readStLecListByStudentId(student_id);
    }

    @Override
    public int getTerm(int id){
        return stLecDAO.findTermByLectureId(id);
    }

    @Override
    public int searchDupl(int student_id, int lecture_id) {
        return mylectureDAO.readRowNumByStudentIdAndLectureId(student_id, lecture_id);
    }

    @Override
    public int createMyLecture(StLecDTO stLecDTO) {
        return mylectureDAO.createMyLecture(stLecDTO);
    }

    public int deleteMyLecture(int student_id, int lecture_id){
        return mylectureDAO.deleteMyLecture(student_id, lecture_id);
    }
}
