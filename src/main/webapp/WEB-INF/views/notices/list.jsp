<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 2022-07-01
  Time: 오후 4:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../section/header.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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
                            <h1 class="main-title float-left">교무처</h1>
                            <ol class="breadcrumb float-right">
                                <li class="breadcrumb-item">Home</li>
                                <li class="breadcrumb-item">직원</li>
                                <li class="breadcrumb-item active">공지사항</li>
                            </ol>
                            <div class="clearfix"></div>
                        </div>
                    </div>
                </div>

                <div class="row">

                    <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 col-xl-12">
                        <div class="card mb-3">
                            <div class="card-header mycolor3" style="padding:10px">
                                <div class="row">
                                    <div class="col" align="left">
                                        <h3><i class="fa fa-table"></i> 공지사항 </h3>
                                    </div>
                                    <div class="col" align="right">
                                        <h3></h3>
                                    </div>
                                </div>
                            </div>
                            <div class="card-body" style="padding:10px">
                                <form name="search_form" method="get" action="list.do">
                                    <div class="row" style="margin-bottom:5px">
                                        <div class="col-auto" align="left">
                                            <div class="form-inline">
                                                <div class="input-group input-group-sm">
                                                    <div class="input-group-prepend">
                                                        <span class="input-group-text">제목</span>
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

                                <table class="table table-bordered mytable" style="width:100%;">
                                    <tr class="mycolor1">
                                        <td>날짜</td>
                                        <td>제목</td>
                                        <td width="95"></td>
                                    </tr>
                                    <c:forEach var="notice" items="${noticeList}">
                                        <tr>
                                            <td width="30%">${notice.writeday}</td>
                                            <td style="text-align:left"><a href="detail.do?id=${notice.id}">${notice.title}</a></td>
                                            <td>
                                                <a href="edit.do?id=${notice.id}" class="btn btn-xs btn-outline-primary">수정</a>
                                                <a id="del_btn" class="btn btn-xs btn-outline-danger" onClick="if(confirm('삭제할까요?')) location.href='delete.do?id=${notice.id}';">삭제</a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </table>
                                <c:if test="${fn:length(noticeList) != 0}">
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
