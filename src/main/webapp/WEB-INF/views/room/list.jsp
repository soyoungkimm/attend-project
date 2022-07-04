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
                            <h1 class="main-title float-left">교무처</h1>
                            <ol class="breadcrumb float-right">
                                <li class="breadcrumb-item">Home</li>
                                <li class="breadcrumb-item">직원</li>
                                <li class="breadcrumb-item active">강의실</li>
                            </ol>
                            <div class="clearfix"></div>
                        </div>
                    </div>
                </div>

                <div class="row">

                    <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 col-xl-12">
                        <div class="card mb-3">
                            <div class="card-header mycolor3" style="padding:10px">
                                <h3><i class="fa fa-table"></i> 강의실</h3>
                            </div>

                            <div class="card-body" style="padding:10px">

                                <script>
                                    function find_text()
                                    {
                                        if (!form1.text1.value)
                                            form1.action="/member/lists/page";
                                        else
                                            form1.action="/member/lists/text1/" + form1.text1.value+"/page";
                                        form1.submit();
                                    }
                                </script>

                                <form name="form1" method="post" action="">
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
                                                        <button class="btn btn-sm mycolor1" type="button" onClick="find_text();">검색</button>
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
                                    <tr class="mycolor1">
                                        <th>건물명</th>
                                        <th>층</th>
                                        <th>호</th>
                                        <th>소속</th>
                                        <th>명칭</th>
                                        <th>구분</th>
                                        <th>면적</th>
                                        <th></th>
                                    </tr>
                                    <c:forEach var="room" items="${roomList}">
                                    <tr>
                                        <c:forEach var="building" items="${buildingList}">
                                            <c:if test="${building.id == room.building_id}">
                                                <td>${building.name}</td>
                                            </c:if>
                                        </c:forEach>
                                        <td>${room.floor}</td>
                                        <td>${room.ho}</td>
                                        <c:forEach var="depart" items="${departList}">
                                            <c:if test="${room.depart_id == depart.id}">
                                                <td>${depart.name}</td>
                                            </c:if>
                                        </c:forEach>
                                        <td><a href="detail.do?id=${room.id}">${room.name}</a></td>
                                        <c:choose>
                                            <c:when test="${room.kind == 1}">
                                                <td>일반</td>
                                            </c:when>
                                            <c:when test="${room.kind == 2}">
                                                <td>실습실</td>
                                            </c:when>
                                            <c:when test="${room.kind == 3}">
                                                <td>컴퓨터실</td>
                                            </c:when>
                                            <c:otherwise>
                                                <td></td>
                                            </c:otherwise>
                                        </c:choose>
                                        <td>${room.area}</td>
                                        <td>
                                            <a href="edit.do?id=${room.id}" class="btn btn-xs btn-outline-primary">수정</a>
                                            <a href="delete.do?id=${room.id}" class="btn btn-xs btn-outline-danger" onClick="return confirm('삭제할까요 ?');">삭제</a>
                                        </td>
                                    </tr>
                                    </c:forEach>
                                </table>

                                <c:if test="${fn:length(roomList) != 0}">
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