package com.gamejigi.attend.util;

public class Pagination {
    private int totalRows; // 전체 행의 수
    private int perPageRows; // 페이지당 행 수
    private int firstRow; // 시작 행
    private int endRow; // 마지막 행

    private int totalPages; // 전체 페이지 개수
    private int perPagination; // 화면에 나타나는 페이지 번호 총 개수
    private int curPageNo; // 현재 페이지 번호
    private int beginPageNo; // 시작 페이지 번호
    private int endPageNo; // 마지막 페이지 번호

    private int totalBlocks; // 전체 블럭 개수
    private int curBlock; // 현재 블럭


    // 생성자
    public Pagination(int curPageNo, int perPageRows, int perPagination, int totalRows){
        firstRow = (curPageNo - 1) * perPageRows + 1; // 첫 페이지는 행번호 1부터 시작, 두번째 페이지는 페이지당 행 수 + 1
        endRow = firstRow + perPageRows - 1;

        totalPages = totalRows / perPageRows;

        if((totalRows % perPageRows) > 0)
            totalPages++;

        beginPageNo = 0 ;
        endPageNo = 0;

        if(totalPages > 0) {
            beginPageNo = (curPageNo - 1) / perPagination * perPagination + 1;
            endPageNo = beginPageNo + perPagination - 1;
            if(endPageNo > totalPages)
                endPageNo = totalPages;
        }

        totalBlocks = (int)Math.ceil((float) totalPages / (float)perPagination);

        curBlock = (int)Math.ceil((float)curPageNo/(float)perPagination);

        setPerPageRows(perPageRows);
        setBeginPageNo(beginPageNo);
        setEndPageNo(endPageNo);
        setCurPageNo(curPageNo);
        setTotalPages(totalPages);
        setPerPagination(perPagination);
    }

    public int getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(int totalRows) {
        this.totalRows = totalRows;
    }

    public int getPerPageRows() {
        return perPageRows;
    }

    public void setPerPageRows(int perPageRows) {
        this.perPageRows = perPageRows;
    }

    public int getPerPagination() {
        return perPagination;
    }

    public void setPerPagination(int perPagination) {
        this.perPagination = perPagination;
    }

    public int getFirstRow() {
        return firstRow;
    }

    public void setFirstRow(int firstRow) {
        this.firstRow = firstRow;
    }

    public int getEndRow() {
        return endRow;
    }

    public void setEndRow(int endRow) {
        this.endRow = endRow;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getCurPageNo() {
        return curPageNo;
    }

    public void setCurPageNo(int curPageNo) {
        this.curPageNo = curPageNo;
    }

    public int getBeginPageNo() {
        return beginPageNo;
    }

    public void setBeginPageNo(int beginPageNo) {
        this.beginPageNo = beginPageNo;
    }

    public int getEndPageNo() {
        return endPageNo;
    }

    public void setEndPageNo(int endPageNo) {
        this.endPageNo = endPageNo;
    }

    public int getTotalBlocks() {
        return totalBlocks;
    }

    public void setTotalBlocks(int totalBlocks) {
        this.totalBlocks = totalBlocks;
    }

    public int getCurBlock() {
        return curBlock;
    }

    public void setCurBlock(int curBlock) {
        this.curBlock = curBlock;
    }

}
