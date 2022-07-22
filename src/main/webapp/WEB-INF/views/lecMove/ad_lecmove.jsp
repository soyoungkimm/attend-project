<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../section/header.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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
                <li class="breadcrumb-item active">휴보강</li>
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
                    <h3><i class="fa fa-table"></i> 휴보강 </h3>
                  </div>
                  <div class="col" align="right">
                    <h3>직원</h3>
                  </div>
                </div>
              </div>
              <div class="card-body" style="padding:10px">

                <form name="search_form" action="" method="get">

                  <div class="row" style="margin-bottom:3px">
                    <div class="col" align="left">
                      <div class="form-inline">

                        <div class="input-group input-group-sm">
                          <div class="input-group-prepend">
                            <span class="input-group-text">년도</span>
                          </div>
                          <div class="input-group-append">
                            <select name="sel1" class="form-control form-control-sm">
                              <c:forEach var="i" begin="2015" end="2022">
                                <c:choose>
                                  <c:when test="${sel1==i}">
                                    <option value='${i}' selected>${i}</option>
                                  </c:when>
                                  <c:otherwise>
                                    <option value='${i}'>${i}</option>
                                  </c:otherwise>
                                </c:choose>
                              </c:forEach>
                            </select>
                            &nbsp;
                            <select name="sel2" class="form-control form-control-sm">
                              <c:choose>
                                <c:when test="${sel2 == 2}">
                                  <option value='1'>1학기</option>
                                  <option value='2' selected>2학기</option>
                                </c:when>
                                <c:otherwise>
                                  <option value='1' selected>1학기</option>
                                  <option value='2' >2학기</option>
                                </c:otherwise>
                              </c:choose>
                            </select>
                          </div>
                          &nbsp;<button type="submit"class="btn btn-sm mycolor1" type="button">검색</button>
                        </div>

                      </div>
                    </div>
                  </div>

                  <table class="table table-bordered table-responsive-sm mytable" style="width:100%;">
                    <tr class="mycolor1">
                      <td>학과</td>
                      <td>교수님</td>
                      <td>교과목</td>
                      <td>학년/반</td>
                      <td>휴강날짜</td>
                      <td>휴강교시</td>
                      <td>보강날짜</td>
                      <td>보강교시</td>
                      <td>보강강의실</td>
                      <td>처리상태</td>
                      <td>직원</td>
                    </tr>
                    <c:forEach var="lectureDay" items="${lecMoveList}">
                      <tr>
                        <td>${lectureDay.departName}</td>
                        <td>${lectureDay.teacherName}</td>
                        <td>${lectureDay.subjectName}</td>
                        <td>${lectureDay.grade}학년/${lectureDay.ban}</td>
                        <td class="mycolor4">${lectureDay.normdate}</td>
                        <td class="mycolor4">
                          <c:forEach var="i" begin="${lectureDay.normstart}" end="${lectureDay.normstart + (lectureDay.normhour-1)}">
                            ${i}
                            <c:if test="${i != lectureDay.normstart + (lectureDay.normhour-1)}">,</c:if>
                          </c:forEach>
                          교시
                        </td>
                        <td class="mycolor3">${lectureDay.restdate}</td>
                        <td class="mycolor3">
                          <c:if test="${lectureDay.reststart > 0}">
                            <c:forEach var="i" begin="${lectureDay.reststart}" end="${lectureDay.reststart + (lectureDay.resthour-1)}">
                              ${i}
                              <c:if test="${i != lectureDay.reststart + (lectureDay.resthour-1)}">,</c:if>
                            </c:forEach>
                          </c:if>
                          교시
                        </td>
                        <td class="mycolor3">${lectureDay.buildingName} ${lectureDay.roomName}</td>
                        <td class="state"><b>
                          <c:choose>
                            <c:when test = "${lectureDay.state == 0}">신청</c:when>
                            <c:when test = "${lectureDay.state == 1}">취소</c:when>
                            <c:when test = "${lectureDay.state == 2}">학과장승인</c:when>
                            <c:when test = "${lectureDay.state == 3}">반려</c:when>
                            <c:when test = "${lectureDay.state == 4}">최종승인</c:when>
                          </c:choose>
                        </b></td>
                        <td>					<!-- 0 신청 or 1 취소 or 2 학과장승인 or 3 반려 or 4 최종승인 -->
                          <a href="edit.do?id=${lectureDay.id}&state=4&sel1=${sel1}&sel2=${sel2}" class="btn btn-xs btn-outline-primary">최종승인</a>
                          <a href="edit.do?id=${lectureDay.id}&state=3&sel1=${sel1}&sel2=${sel2}" class="btn btn-xs btn-outline-danger">반려</a>
                        </td>
                      </tr>
                    </c:forEach>
                  </table>
                </form>

                <c:if test="${fn:length(lecMoveList) != 0}">
                  <nav>
                    <ul class="pagination pagination-sm justify-content-center">
                        <%-- 블록 왼쪽 이동 --%>
                      <c:choose>
                        <c:when test="${pagination.curBlock > 1 }">
                          <li class="page-item"><a class="page-link" href="list.do?p=${pagination.beginPageNo- 1}&sel1=${sel1}&sel2=${sel2}">◀</a></li>
                        </c:when>
                        <c:otherwise>
                          <li class="page-item"><a class="page-link" href="">◀</a></li>
                        </c:otherwise>
                      </c:choose>

                        <%-- 페이지 왼쪽 이동 --%>
                      <c:choose>
                        <c:when test="${pagination.curPageNo > 1 }">
                          <li class="page-item"><a class="page-link" href="list.do?p=${pagination.curPageNo - 1}&sel1=${sel1}&sel2=${sel2}">◁</a></li>
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
                            <li class="page-item"><a class="page-link" href="list.do?p=${pageNo}&sel1=${sel1}&sel2=${sel2}">${pageNo}</a></li>
                          </c:otherwise>
                        </c:choose>
                      </c:forEach>


                        <%-- 페이지 오른쪽 이동 --%>
                      <c:choose>
                        <c:when test="${pagination.curPageNo < pagination.totalPages }">
                          <li class="page-item"><a class="page-link" href="list.do?p=${pagination.curPageNo + 1}&sel1=${sel1}&sel2=${sel2}">▷</a></li>
                        </c:when>
                        <c:otherwise>
                          <li class="page-item"><a class="page-link" href="">▷</a></li>
                        </c:otherwise>
                      </c:choose>

                        <%-- 블록 오른쪽 이동 --%>
                      <c:choose>
                        <c:when test="${pagination.curBlock < pagination.totalBlocks }">
                          <li class="page-item"><a class="page-link" href="list.do?p=${pagination.endPageNo + 1}&sel1=${sel1}&sel2=${sel2}">▶</a></li>
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