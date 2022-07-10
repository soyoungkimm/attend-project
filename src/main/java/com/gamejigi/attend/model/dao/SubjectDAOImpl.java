package com.gamejigi.attend.model.dao;

import com.gamejigi.attend.model.dto.SubjectDTO;
import com.gamejigi.attend.util.Pagination;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SubjectDAOImpl extends DAOImplMySQL implements SubjectDAO{

    private Connection conn;
    private Statement stmt;
    private PreparedStatement pstmt;
    private ResultSet rs;

    public SubjectDAOImpl(){
        conn=getConnection();
    }

    @Override
    public int create(SubjectDTO subjectDTO) {
        int rows = 0;
        String query ="insert into subject(depart_id,code,yyyy,grade,term,ismajor,ischoice,ispractice,name,point,hour) values(?,?,?,?,?,?,?,?,?,?,?)";
        try{
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1,1);
            //pstmt.setInt(1,subjectDTO.getDepart_id());
            //로그인 구현시 기능 추가 필요
            pstmt.setString(2,subjectDTO.getCode());
            pstmt.setInt(3,subjectDTO.getYyyy());
            pstmt.setInt(4,subjectDTO.getGrade());
            pstmt.setInt(5,subjectDTO.getTerm());
            pstmt.setInt(6,subjectDTO.getIsmajor());
            pstmt.setInt(7,subjectDTO.getIschoice());
            pstmt.setInt(8,subjectDTO.getIspractice());
            pstmt.setString(9,subjectDTO.getName());
            pstmt.setInt(10,subjectDTO.getPoint());
            pstmt.setInt(11,subjectDTO.getHour());
            rows=pstmt.executeUpdate();
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }
        return rows;
    }

    @Override
    public int delete(SubjectDTO subjectDTO) {
        int rows = 0;
        String query = "delete from subject where id=?";
        try {
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, subjectDTO.getId());
            rows=pstmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return rows;
    }

    @Override
    public int update(SubjectDTO subjectDTO) {
        int rows= 0;
        String querry = "update subject set depart_id=?, code=?, yyyy=?, grade=?, term=?, ismajor=?, ischoice=?, ispractice=?, name=?, point=?, hour=? where id=?";
        try {
            pstmt = conn.prepareStatement(querry);
            pstmt.setInt(1,subjectDTO.getDepart_id());
            pstmt.setString(2,subjectDTO.getCode());
            pstmt.setInt(3,subjectDTO.getYyyy());
            pstmt.setInt(4,subjectDTO.getGrade());
            pstmt.setInt(5,subjectDTO.getTerm());
            pstmt.setInt(6,subjectDTO.getIsmajor());
            pstmt.setInt(7,subjectDTO.getIschoice());
            pstmt.setInt(8,subjectDTO.getIspractice());
            pstmt.setString(9,subjectDTO.getName());
            pstmt.setInt(10,subjectDTO.getPoint());
            pstmt.setInt(11,subjectDTO.getHour());
            pstmt.setInt(12,subjectDTO.getId());
            rows = pstmt.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return rows;
    }

    @Override
    public SubjectDTO findById(SubjectDTO subjectDTO) {
        String query = "select * from subject where id=?";
        try{
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, subjectDTO.getId());

            rs = pstmt.executeQuery();

            while(rs.next()) {
                subjectDTO = setSubject(rs);
            }
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return subjectDTO;
    }
    @Override
    public List<SubjectDTO> findByDepartIdAndYearAndTerm (int depart_id, int year, int term) {
        List<SubjectDTO> list = new ArrayList<>();
        String query = "select * from subject where depart_id=? and yyyy=? and term=?";
        try{
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, depart_id);
            pstmt.setInt(2, year);
            pstmt.setInt(3, term);

            rs = pstmt.executeQuery();

            while(rs.next()) {
                list.add(setSubject(rs));
            }
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return list;
    }
    @Override
    public List<SubjectDTO> readList() {
        ArrayList<SubjectDTO> subjectList= null;
        String query = "select * from subject";
        try {
            stmt = conn.createStatement();
            if((rs = stmt.executeQuery(query)) != null) {
                subjectList= new ArrayList<SubjectDTO>();
                while (rs.next()) {
                    SubjectDTO subjectDTO = new SubjectDTO();
                    subjectDTO = setSubject(rs);
                    subjectList.add(subjectDTO);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return subjectList;
    }

    @Override
    public List<SubjectDTO> readListUsePaginationAndSearch(Pagination pagination, String search1, String search2) {
        ArrayList<SubjectDTO> subjectList = null;
        if(Integer.parseInt(search2)==0){
            String query = "select * from subject where yyyy like ? limit ?, ?";
            try{
                pstmt = conn.prepareStatement(query);
                pstmt.setString(1, "%" + search1 + "%");
                pstmt.setInt(2, pagination.getFirstRow() - 1);
                pstmt.setInt(3, pagination.getPerPageRows());

                if((rs = pstmt.executeQuery()) != null){
                    subjectList = new ArrayList<SubjectDTO>();
                    while(rs.next()){
                        SubjectDTO subjectDTO = setSubject(rs);
                        subjectList.add(subjectDTO);
                    }
                }
            }catch (SQLException throwables){
                throwables.printStackTrace();
            }
        }
        else{
            String query = "select * from subject where yyyy like ? and grade = ? limit ?, ?";
            try{
                pstmt = conn.prepareStatement(query);
                pstmt.setString(1, "%" + search1 + "%");
                pstmt.setInt(2, Integer.parseInt(search2));
                pstmt.setInt(3, pagination.getFirstRow() - 1);
                pstmt.setInt(4, pagination.getPerPageRows());

                if((rs = pstmt.executeQuery()) != null){
                    subjectList = new ArrayList<SubjectDTO>();
                    while(rs.next()){
                        SubjectDTO subjectDTO = setSubject(rs);
                        subjectList.add(subjectDTO);
                    }
                }
            }catch (SQLException throwables){
                throwables.printStackTrace();
            }
        }
        return subjectList;
    }

    @Override
    public int readTotalRowNumUseSearch(String search1, String search2) {
        int totalNum = 0;
        if(Integer.parseInt(search2)==0){
            String query = "select count(*) as num from subject where yyyy like ? ";
            try {
                pstmt = conn.prepareStatement(query);
                pstmt.setString(1, "%" + search1 + "%");
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    totalNum = rs.getInt("num");
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        else{
            String query = "select count(*) as num from subject where yyyy like ? and grade = ? ";
            try {
                pstmt = conn.prepareStatement(query);
                pstmt.setString(1, "%" + search1 + "%");
                pstmt.setInt(2,Integer.parseInt(search2));
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    totalNum = rs.getInt("num");
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return totalNum;
    }

    private SubjectDTO setSubject(ResultSet rs) throws SQLException{

        SubjectDTO subjectDTO = new SubjectDTO();

        subjectDTO.setId(rs.getInt(1));
        subjectDTO.setDepart_id(rs.getInt(2));
        subjectDTO.setCode(rs.getString(3));
        subjectDTO.setYyyy(rs.getInt(4));
        subjectDTO.setGrade(rs.getInt(5));
        subjectDTO.setTerm(rs.getInt(6));
        subjectDTO.setIsmajor(rs.getInt(7));
        subjectDTO.setIschoice(rs.getInt(8));
        subjectDTO.setIspractice(rs.getInt(9));
        subjectDTO.setName(rs.getString(10));
        subjectDTO.setPoint(rs.getInt(11));
        subjectDTO.setHour(rs.getInt(12));

        return subjectDTO;
    }
}
