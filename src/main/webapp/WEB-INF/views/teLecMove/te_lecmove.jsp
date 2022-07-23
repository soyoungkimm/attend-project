<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../section/header.jsp" %>
<!------------------------------------------------------------------------------>
<!-- 내용 시작 -->
<!------------------------------------------------------------------------------>
				<div class="row">
					<div class="col-xl-12">
						<div class="breadcrumb-holder">
							<h1 class="main-title float-left">${departName}</h1>
							<ol class="breadcrumb float-right">
								<li class="breadcrumb-item">Home</li>
								<li class="breadcrumb-item">교수</li>
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
										<h3>${teacherName}</h3>
									</div>
								</div>
							</div>
							<div class="card-body" style="padding:10px">

								<form name="form1" action="" method="post">
								<div class="row" style="margin-bottom:3px">
									<div class="col" align="left">
									</div>
									<div class="col" align="right">
										<a href="/TeLecMove/norm.do" class="btn btn-sm mycolor1">신청</a>
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
										<td>학과장</td>									</tr>
									<c:forEach items="${lectureDays}" var="lecture">
									<tr>
										<td>${lecture.getDepartName()}</td>
										<td>${lecture.getTeacherName()}</td>
										<td>${lecture.getSubjectName()}</td>
										<td>${lecture.getGrade()}학년/${lecture.getBan()}반</td>
										<td class="mycolor4">${lecture.getNormdate()}</td>
										<td class="mycolor4">${lecture.getNormstart()}~${lecture.getNormstart()+lecture.getNormhour()} 교시</td>
										<td class="mycolor3">${lecture.getRestdate()}</td>
										<td class="mycolor3">${lecture.getReststart()}~${lecture.getReststart()+lecture.getResthour()} 교시</td>
										<td class="mycolor3">${lecture.getBuildingName()} ${lecture.getRoomName()}</td>
										<td><b>신청</b></td>
										<td>					<!-- 0 신청 or 1 취소 or 2 학과장승인 or 3 반려 or 4 최종승인 -->
											<a href="/TeLecMove/cancel.do?id=${lecture.getId()}" class="btn btn-xs btn-outline-danger">취소</a>
										</td>
									</tr>
									</c:forEach>

								</table>
								</form>

								<nav>
									<ul class='pagination pagination-sm justify-content-center'>
										<li class='page-item'><a class="page-link" href="/TeLecMove/list.do">◀</a></li>
										<li class='page-item'><a class="page-link" href="/TeLecMove/list.do?p=${pagination.getBeginPageNo()-1}">◁</a></li>
										<c:forEach begin="${pagination.getBeginPageNo()}" end="${pagination.getEndPageNo()}" var="var">
											<c:if test="${pagination.getCurPageNo() != var}">
												<li class='page-item'><a class="page-link" href="/TeLecMove/list.do?p=${var}">${var}</a></li>
											</c:if>
											<c:if test="${pagination.getCurPageNo() == var}">
												<li class='page-item active'>
													<span class='page-link' style='background-color:steelblue'>
														<a href="/TeLecMove/list.do?p=${var}">${var}</a>
													</span>
												</li>
											</c:if>
										</c:forEach>
										<li class='page-item'><a class="page-link" href="/TeLecMove/list.do?p=${pagination.getEndPageNo()+1}">▷</a></li>
										<li class='page-item'><a class="page-link" href="/TeLecMove/list.do?p=${pagination.getTotalPages()}">▶</a></li>
									</ul>
								</nav>

							</div>		<!-- card body end -->
						</div>		<!-- card end -->
					</div>
						
				</div>	<!-- row end -->
<!------------------------------------------------------------------------------>
<!-- 내용 끝 -->
<!------------------------------------------------------------------------------>
<%@ include file="../section/footer.jsp" %>