<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../section/header.jsp"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<style>
    #del_btn { color : red; }
    #del_btn:hover { color : white; }
</style>
                <!------------------------------------------------------------------------------>
                <!-- 내용 시작 -->
                <!------------------------------------------------------------------------------>
                <div class="row">
                    <div class="col-xl-12">
                        <div class="breadcrumb-holder">
                            <h1 class="main-title float-left">컴퓨터소프트웨어학과</h1>
                            <ol class="breadcrumb float-right">
                                <li class="breadcrumb-item">Home</li>
                                <li class="breadcrumb-item">조교</li>
                                <li class="breadcrumb-item active">학년별 교과목</li>
                            </ol>
                            <div class="clearfix"></div>
                        </div>
                    </div>
                </div>

                <div class="row">

                    <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 col-xl-12">
                        <div class="card mb-3">
                            <div class="card-header mycolor3" style="padding:10px">
                                <h3><i class="fa fa-table"></i> 학년별 교과목</h3>
                            </div>

                            <div class="card-body" style="padding:10px">

                                <form name="search_form" action="list.do" method="get">
                                    <div class="row" style="margin-bottom:3px">
                                        <div class="col" align="left">
                                            <div class="form-inline">

                                                <div class="input-group input-group-sm">
                                                    <div class="input-group-prepend">
                                                        <span class="input-group-text">년도/학년</span>
                                                    </div>
                                                    <div class="input-group-append">
                                                        <input type="text" name="s1" size="10" value="${searchY}" class="form-control"
                                                               onKeydown="if (event.keyCode == 13) document.search_form.submit()" >
                                                        &nbsp;
                                                        <select name="s2" class="form-control form-control-sm">
                                                            <option value="0" selected>전체</option>
                                                            <option value='1'>1학년</option>
                                                            <option value='2'>2학년</option>
                                                            <option value='3'>3학년</option>
                                                            <option value='4'>4학년</option>
                                                        </select>
                                                    </div>
                                                    &nbsp;<input type="submit"type="button" class="btn btn-sm btn-primary" value="검색"/>
                                                </div>

                                            </div>
                                        </div>
                                        <div class="col" align="right">
                                            <a href="create.do" class="btn btn-sm btn-primary">추가</a>
                                        </div>
                                    </div>
                                </form>

                                <table class="table table-bordered table-hover table-responsive-sm mytable" style="width:100%">
                                    <thead>
                                    <tr class="mycolor1">
                                        <th>전공여부</th>
                                        <th>필수</th>
                                        <th>실습</th>
                                        <th>과목코드</th>
                                        <th>학년</th>
                                        <th>학기</th>
                                        <th>과목명</th>
                                        <th>학점</th>
                                        <th>시간</th>
                                        <th width="100"></th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach var="subject" items="${subjectList}">
                                    <tr>
                                        <td>
                                            <c:choose>
                                                <c:when test="${subject.ismajor == 0}">교양</c:when>
                                                <c:when test="${subject.ismajor == 1}">전공</c:when>
                                                <c:otherwise></c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${subject.ischoice == 0}">필수</c:when>
                                                <c:when test="${subject.ischoice == 1}">선택</c:when>
                                                <c:otherwise></c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${subject.ispractice == 0}">이론</c:when>
                                                <c:when test="${subject.ispractice == 1}">실습</c:when>
                                                <c:when test="${subject.ispractice == 2}">현장실습</c:when>
                                                <c:otherwise></c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>${subject.code}</td>
                                        <td>${subject.grade}</td>
                                        <td>${subject.term}</td>
                                        <td>${subject.name}</td>
                                        <td>${subject.point}</td>
                                        <td>${subject.hour}</td>
                                        <td>
                                            <a href="edit.do?id=${subject.id}" class="btn btn-xs btn-outline-primary">수정</a>
                                            <a href="delete.do?id=${subject.id}" class="btn btn-xs btn-outline-danger" onClick="return confirm('삭제할까요 ?');">삭제</a>
                                        </td>
                                    </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>

                                <c:if test="${fn:length(subjectList) != 0}">
                                    <nav>
                                        <ul class="pagination pagination-sm justify-content-center">
                                                <%-- 블록 왼쪽 이동 --%>
                                            <c:choose>
                                                <c:when test="${pagination.curBlock > 1 }">
                                                    <li class="page-item"><a class="page-link" href="list.do?p=${pagination.beginPageNo- 1}&s1=${searchY}&s2=${searchG}">◀</a></li>
                                                </c:when>
                                                <c:otherwise>
                                                    <li class="page-item"><a class="page-link" href="">◀</a></li>
                                                </c:otherwise>
                                            </c:choose>

                                                <%-- 페이지 왼쪽 이동 --%>
                                            <c:choose>
                                                <c:when test="${pagination.curPageNo > 1 }">
                                                    <li class="page-item"><a class="page-link" href="list.do?p=${pagination.curPageNo - 1}&s1=${searchY}&s2=${searchG}">◁</a></li>
                                                </c:when>
                                                <c:otherwise>
                                                    <li class="page-item"><a class="page-link" href="">◁</a></li>
                                                </c:otherwise>
                                            </c:choose>

                                                <%-- 페이지 번호 --%>

                                            <c:forEach var="pageNo" begin="${pagination.beginPageNo}" end="${pagination.endPageNo}">
                                                <c:choose>
                                                    <c:when test="${pageNo == pagination.curPageNo }">
                                                        <li class="page-item active"><span class="page-link" style="background-color:steelblue">${pageNo}</span></li>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <li class="page-item"><a class="page-link" href="list.do?p=${pageNo}&s1=${searchY}&s2=${searchG}">${pageNo}</a></li>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>


                                                <%-- 페이지 오른쪽 이동 --%>
                                            <c:choose>
                                                <c:when test="${pagination.curPageNo < pagination.totalPages }">
                                                    <li class="page-item"><a class="page-link" href="list.do?p=${pagination.curPageNo + 1}&s1=${searchY}&s2=${searchG}">▷</a></li>
                                                </c:when>
                                                <c:otherwise>
                                                    <li class="page-item"><a class="page-link" href="">▷</a></li>
                                                </c:otherwise>
                                            </c:choose>

                                                <%-- 블록 오른쪽 이동 --%>
                                            <c:choose>
                                                <c:when test="${pagination.curBlock < pagination.totalBlocks }">
                                                    <li class="page-item"><a class="page-link" href="list.do?p=${pagination.endPageNo + 1}&s1=${searchY}&s2=${searchG}">▶</a></li>
                                                </c:when>
                                                <c:otherwise>
                                                    <li class="page-item"><a class="page-link" href="">▶</a></li>
                                                </c:otherwise>
                                            </c:choose>
                                        </ul>
                                    </nav>
                                </c:if>

                            </div>		<!-- card body end -->
                        </div>		<!-- card end -->
                    </div>

                </div>	<!-- row end -->
                <!------------------------------------------------------------------------------>
                <!-- 내용 끝 -->
                <!------------------------------------------------------------------------------>
<%@include file="../section/footer.jsp"%>