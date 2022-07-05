<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 2022-07-02
  Time: 오후 10:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@include file="../section/header.jsp"%>
<style>
    #del_btn { color : red; }
    #del_btn:hover { color : white; }
</style>
<div class="row">
    <div class="col-xl-12">
        <div class="breadcrumb-holder">
            <h1 class="main-title float-left">교무처</h1>
            <ol class="breadcrumb float-right">
                <li class="breadcrumb-item">Home</li>
                <li class="breadcrumb-item">직원</li>
                <li class="breadcrumb-item active">학생정보</li>
            </ol>
            <div class="clearfix"></div>
        </div>
    </div>
</div>
<!------------------------------------------------------------------------------>
<!-- 내용 시작 -->
<!------------------------------------------------------------------------------>
<div class="row">
    <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 col-xl-12">
        <div class="card mb-3">
            <div class="card-header mycolor3" style="padding:10px">
                <h3><i class="fa fa-table"></i> 학생정보</h3>
            </div>

            <div class="card-body" style="padding:10px">
                <form name="search_form" method="get" action="list.do">
                    <div class="row" style="margin-bottom:5px">
                        <div class="col-auto" align="left">
                            <div class="form-inline">
                                <div class="input-group input-group-sm">
                                    <div class="input-group-prepend">
                                        <span class="input-group-text">이름</span>
                                    </div>
                                    <input type="text" name="s" size="10" value="${search}" class="form-control"
                                            onKeydown="if (event.keyCode == 13) document.search_form.submit()" >
                                    <div class="input-group-append">
                                        <input type="submit" class="btn btn-sm mycolor1" type="button" value="검색" />
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col" align="right">
                            <a href="create.do" class="btn btn-sm mycolor1">추가</a>
                        </div>
                    </div>
                </form>

                <table class="table table-bordered table-hover table-responsive-sm mytable" style="width:100%">
                    <thead>
                        <tr class="mycolor1">
                            <th>학과</th>
                            <th>학년</th>
                            <th>반</th>
                            <th>학번</th>
                            <th>이름</th>
                            <th>핸드폰</th>
                            <th>성별</th>
                            <th>상태</th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="student" items="${studentList}">
                            <tr>
                                <td>${student.depart_name}</td>
                                <td>${student.grade}</td>
                                <td>${student.ban}</td>
                                <td>${student.schoolno}</td>
                                <td><a href="detail.do?id=${student.id}">${student.name}</a></td>
                                <td>${fn:substring(student.phone,0,3)}-${fn:substring(student.phone,4,8)}-${fn:substring(student.phone,9,13)}</td>
                                <td>${student.gender eq 0? "남자" : "여자"}</td>
                                <td><c:if test="${student.state eq 0}">재학</c:if><c:if test="${student.state eq 1}">휴학</c:if><c:if test="${student.state eq 2}">자퇴</c:if></td>
                                <td>
                                    <a href="edit.do?id=${student.id}" class="btn btn-xs btn-outline-primary">수정</a>
                                    <a id="del_btn" class="btn btn-xs btn-outline-danger" onClick="if(confirm('삭제할까요?')) location.href='delete.do?id=${student.id}';">삭제</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>

                <c:if test="${fn:length(studentList) != 0}">
                    <nav>
                        <ul class="pagination pagination-sm justify-content-center">
                            <%-- 블록 왼쪽 이동 --%>
                            <c:choose>
                                <c:when test="${pagination.curBlock > 1 }">
                                    <li class="page-item"><a class="page-link" href="list.do?p=${pagination.beginPageNo- 1}&s=${search}">◀</a></li>
                                </c:when>
                                <c:otherwise>
                                    <li class="page-item"><a class="page-link" href="">◀</a></li>
                                </c:otherwise>
                            </c:choose>

                            <%-- 페이지 왼쪽 이동 --%>
                            <c:choose>
                                <c:when test="${pagination.curPageNo > 1 }">
                                    <li class="page-item"><a class="page-link" href="list.do?p=${pagination.curPageNo - 1}&s=${search}">◁</a></li>
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
                                        <li class="page-item"><a class="page-link" href="list.do?p=${pageNo}&s=${search}">${pageNo}</a></li>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>


                            <%-- 페이지 오른쪽 이동 --%>
                            <c:choose>
                                <c:when test="${pagination.curPageNo < pagination.totalPages }">
                                    <li class="page-item"><a class="page-link" href="list.do?p=${pagination.curPageNo + 1}&s=${search}">▷</a></li>
                                </c:when>
                                <c:otherwise>
                                    <li class="page-item"><a class="page-link" href="">▷</a></li>
                                </c:otherwise>
                            </c:choose>

                            <%-- 블록 오른쪽 이동 --%>
                            <c:choose>
                                <c:when test="${pagination.curBlock < pagination.totalBlocks }">
                                    <li class="page-item"><a class="page-link" href="list.do?p=${pagination.endPageNo + 1}&s=${search}">▶</a></li>
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