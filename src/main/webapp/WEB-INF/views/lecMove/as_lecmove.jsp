<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="d" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="../section/header.jsp" %>
<!------------------------------------------------------------------------------>
<!-- 내용 시작 -->
<!------------------------------------------------------------------------------>
<script>

	function approveLectureMove(id, state){
		if (state != 0){
			alert("수정할 수 없습니다")
			return;
		}
		location.href='../asLecMove/approve.do?id='+id;
	}
	function denialLectureMove(id, state){
		if (state != 0){
			alert("수정할 수 없습니다")
			return;
		}
		location.href='../asLecMove/denial.do?id='+id;
	}
</script>
				<div class="row">
					<div class="col-xl-12">
						<div class="breadcrumb-holder">
							<h1 class="main-title float-left">컴퓨터소프트웨어학과</h1>
							<ol class="breadcrumb float-right">
								<li class="breadcrumb-item">Home</li>
								<li class="breadcrumb-item">조교</li>
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
										<h3>조교</h3>
									</div>
								</div>
							</div>
							<div class="card-body" style="padding:10px">

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
										<td width="140">학과장</td>
									</tr>
									<c:forEach var="lectureDay" items="${lectureDayList}">
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
											<div onClick="approveLectureMove(${lectureDay.id}, ${lectureDay.state});" class="btn btn-xs btn-outline-primary approve">학과장 승인</div>
											<div onclick="denialLectureMove(${lectureDay.id}, ${lectureDay.state});" class="btn btn-xs btn-outline-danger">반려</div>
										</td>
									</tr>
									</c:forEach>

								</table>
								</form>

								<c:if test="${fn:length(lectureDayList) != 0}">
									<nav>
										<ul class="pagination pagination-sm justify-content-center">
												<%-- 블록 왼쪽 이동 --%>
											<c:choose>
												<c:when test="${pagination.curBlock > 1 }">
													<li class="page-item"><a class="page-link" href="main.do?p=${pagination.beginPageNo- 1}">◀</a></li>
												</c:when>
												<c:otherwise>
													<li class="page-item"><a class="page-link" href="">◀</a></li>
												</c:otherwise>
											</c:choose>

												<%-- 페이지 왼쪽 이동 --%>
											<c:choose>
												<c:when test="${pagination.curPageNo > 1 }">
													<li class="page-item"><a class="page-link" href="main.do?p=${pagination.curPageNo - 1}">◁</a></li>
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
														<li class="page-item"><a class="page-link" href="main.do?p=${pageNo}">${pageNo}</a></li>
													</c:otherwise>
												</c:choose>
											</c:forEach>


												<%-- 페이지 오른쪽 이동 --%>
											<c:choose>
												<c:when test="${pagination.curPageNo < pagination.totalPages }">
													<li class="page-item"><a class="page-link" href="main.do?p=${pagination.curPageNo + 1}">▷</a></li>
												</c:when>
												<c:otherwise>
													<li class="page-item"><a class="page-link" href="">▷</a></li>
												</c:otherwise>
											</c:choose>

												<%-- 블록 오른쪽 이동 --%>
											<c:choose>
												<c:when test="${pagination.curBlock < pagination.totalBlocks }">
													<li class="page-item"><a class="page-link" href="main.do?p=${pagination.endPageNo + 1}">▶</a></li>
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