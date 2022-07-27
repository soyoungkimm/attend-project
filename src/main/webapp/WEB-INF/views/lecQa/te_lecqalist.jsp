<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="../section/header.jsp" %>
                <!------------------------------------------------------------------------------>
                <!-- 내용 시작 -->
                <!------------------------------------------------------------------------------>
                <div class="row">
                    <div class="col-xl-12">
                        <div class="breadcrumb-holder">
                            <h1 class="main-title float-left">컴퓨터소프트웨어학과</h1>
                            <ol class="breadcrumb float-right">
                                <li class="breadcrumb-item">Home</li>
                                <li class="breadcrumb-item">교수</li>
                                <li class="breadcrumb-item active">교과목 문의</li>
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
                                        <h3><i class="fa fa-table"></i> 교과목 문의 </h3>
                                    </div>
                                    <div class="col" align="right">
                                        <h3>교수님1</h3>
                                    </div>
                                </div>
                            </div>
                            <div class="card-body" style="padding:10px">

                                <form name="list_form" action="" method="post">

                                    <table class="table table-bordered mytable" style="width:100%;">
                                        <tr class="mycolor1">
                                            <td>날짜</td>
                                            <td>교과목</td>
                                            <td>제목</td>
                                            <td>학생</td>
                                            <td>상태</td>
                                            <td width="60"></td>
                                        </tr>
                                        <c:forEach var="mylecture" items="${mylectureList}">
                                            <tr>
                                                <td>${mylecture.qday}</td>
                                                <td>${mylecture.subject_name}</td>
                                                <td>${mylecture.qtitle}</td>
                                                <td>${mylecture.student_name}</td>
                                                <c:choose>
                                                    <c:when test="${mylecture.qkind == 1}">
                                                        <td><font color="red"><b>문의</b></font></td>
                                                    </c:when>
                                                    <c:when test="${mylecture.qkind == 2}">
                                                        <td>답변</td>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <td></td>
                                                    </c:otherwise>
                                                </c:choose>
                                                <td>
                                                    <a href="te_edit.do?id=${mylecture.id}" class="btn btn-xs btn-outline-primary">수정</a>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </table>
                                </form>

                                <c:if test="${fn:length(mylectureList) != 0}">
                                    <nav>
                                        <ul class="pagination pagination-sm justify-content-center">
                                                <%-- 블록 왼쪽 이동 --%>
                                            <c:choose>
                                                <c:when test="${pagination.curBlock > 1 }">
                                                    <li class="page-item"><a class="page-link" href="te_list.do?p=${pagination.beginPageNo- 1}">◀</a></li>
                                                </c:when>
                                                <c:otherwise>
                                                    <li class="page-item"><a class="page-link" href="">◀</a></li>
                                                </c:otherwise>
                                            </c:choose>

                                                <%-- 페이지 왼쪽 이동 --%>
                                            <c:choose>
                                                <c:when test="${pagination.curPageNo > 1 }">
                                                    <li class="page-item"><a class="page-link" href="te_list.do?p=${pagination.curPageNo - 1}">◁</a></li>
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
                                                        <li class="page-item"><a class="page-link" href="te_list.do?p=${pageNo}">${pageNo}</a></li>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>


                                                <%-- 페이지 오른쪽 이동 --%>
                                            <c:choose>
                                                <c:when test="${pagination.curPageNo < pagination.totalPages }">
                                                    <li class="page-item"><a class="page-link" href="te_list.do?p=${pagination.curPageNo + 1}">▷</a></li>
                                                </c:when>
                                                <c:otherwise>
                                                    <li class="page-item"><a class="page-link" href="">▷</a></li>
                                                </c:otherwise>
                                            </c:choose>

                                                <%-- 블록 오른쪽 이동 --%>
                                            <c:choose>
                                                <c:when test="${pagination.curBlock < pagination.totalBlocks }">
                                                    <li class="page-item"><a class="page-link" href="te_list.do?p=${pagination.endPageNo + 1}">▶</a></li>
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
<%@ include file="../section/footer.jsp" %>