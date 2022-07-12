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
				<h1 class="main-title float-left">컴퓨터소프트웨어학과</h1>
				<ol class="breadcrumb float-right">
					<li class="breadcrumb-item">Home</li>
					<li class="breadcrumb-item active">반별 교과목</li>
				</ol>
				<div class="clearfix"></div>
			</div>
		</div>
	</div>

	<div class="row">

		<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 col-xl-12">
			<div class="card mb-3">
				<div class="card-header mycolor3" style="padding:10px">
					<h3><i class="fa fa-table"></i> 반별 교과목</h3>
				</div>

				<div class="card-body" style="padding:10px">
				<!------------------------------------------------------------>

					<script>
						function find_text()
						{
							form1.action="../lectures/list.do?y=" + form1.sel1.value + "&t=" + form1.sel2.value + "&g=" + form1.sel3.value;
							form1.submit();
						}
						function update_teacher(pos)
						{
							form2.teacherno.value=eval("form2.teacher"+pos).value;
							form2.submit();
						}
					</script>

					<form name="form1" method="post" action="list.do">
					<div class="row" style="margin-bottom:3px">
						<div class="col" align="left">
							<div class="form-inline">
								<div class="input-group input-group-sm">
									<div class="input-group-prepend">
										<span class="input-group-text">년도</span>
									</div>
									<div class="input-group-append">
										<jsp:useBean id="now" class="java.util.Date" />

										<fmt:formatDate value="${now}" pattern="yyyy" var="yearStart"/>
										<c:set var = "index" value="${fn:split('0,1,2,3,4', ',')}"/>

										<select name="sel1" class="form-control form-control-sm" >
											<c:forEach var="i" items="${index}">
												<c:choose>
													<c:when test="${yyyy == yearStart-i}">
														<option value="${yearStart - i}" selected>${yearStart - i}</option>
													</c:when>

													<c:otherwise>
														<option value='${yearStart - i}'>${yearStart - i}</option>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</select>
										&nbsp;
										<select name="sel2" class="form-control form-control-sm" >
											<option value='1' <c:if test="${term == 1}">selected</c:if>>1 학기</option>
											<option value='2' <c:if test="${term == 2}">selected</c:if>>2 학기</option>
										</select>
										&nbsp;
										<select name="sel3" class="form-control form-control-sm" >
											<option value='1' <c:if test="${grade == 1}">selected</c:if>>1 학년</option>
											<option value='2' <c:if test="${grade == 2}">selected</c:if>>2 학년</option>
											<option value='3' <c:if test="${grade == 3}">selected</c:if>>3 학년</option>
											<option value='4' <c:if test="${grade == 4}">selected</c:if>>4 학년</option>
										</select>
									</div>
									&nbsp;<input type="button" class="btn btn-sm btn-primary" value="검색" onclick="find_text();">
								</div>

							</div>
						</div>
						<div class="col" align="right">
							<a href="../lectures/create.do" class="btn btn-sm btn-primary">반별과목 생성</a>
						</div>
					</div>
					</form>

					<form name="form2" method="post" action="lecture_update.html">

						<input type="hidden" name="teacherno" value="">

						<table class="table table-bordered table-hover table-responsive-sm mytable" style="width:100%">
							<thead>
								<tr class="mycolor1">
									<th>과목코드</th>
									<th>과목명</th>
									<th>학점</th>
									<th>시간</th>
									<th>반</th>
									<th width="120">담당교수</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="lectureList" items="${lectureList}">
								<tr>
									<td><a href="detail.do?id=${lectureList.id}">${lectureList.code}</a></td>
									<td>${lectureList.name}</td>
									<td>${lectureList.point}</td>
									<td>${lectureList.hour}</td>
									<td>${lectureList.classs}</td>
									<td>
										<div class="form-inline justify-content-center">
											<select name="teacher1" class="form-control form-control-sm" onchange="update_teacher(this.value);">
												<c:forEach var="teacherList" items="${teacherList}">
													<c:choose>
														<c:when test="${lectureList.teacher_id == teacherList.id}">
															<option value='${teacherList.id}' selected>${teacherList.name}</option>
														</c:when>

														<c:otherwise>
														<option value='${teacherList.id}'>${teacherList.name}</option>
														</c:otherwise>
													</c:choose>
												</c:forEach>
											</select>
										</div>
									</td>
								</tr>
								</c:forEach>
							</tbody>
						</table>
						<c:if test="${fn:length(teacherList) != 0}">
							<nav>
								<ul class="pagination pagination-sm justify-content-center">
										<%-- 블록 왼쪽 이동 --%>
									<c:choose>
										<c:when test="${pagination.curBlock > 1 }">
											<li class="page-item"><a class="page-link" href="list.do?p=${pagination.beginPageNo- 1}&y=${yyyy}&t=${term}&g=${grade}">◀</a></li>
										</c:when>
										<c:otherwise>
											<li class="page-item"><a class="page-link" href="">◀</a></li>
										</c:otherwise>
									</c:choose>

										<%-- 페이지 왼쪽 이동 --%>
									<c:choose>
										<c:when test="${pagination.curPageNo > 1 }">
											<li class="page-item"><a class="page-link" href="list.do?p=${pagination.curPageNo - 1}&y=${yyyy}&t=${term}&g=${grade}">◁</a></li>
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
												<li class="page-item"><a class="page-link" href="list.do?p=${pageNo}&y=${yyyy}&t=${term}&g=${grade}">${pageNo}</a></li>
											</c:otherwise>
										</c:choose>
									</c:forEach>


										<%-- 페이지 오른쪽 이동 --%>
									<c:choose>
										<c:when test="${pagination.curPageNo < pagination.totalPages }">
											<li class="page-item"><a class="page-link" href="list.do?p=${pagination.curPageNo + 1}&y=${yyyy}&t=${term}&g=${grade}">▷</a></li>
										</c:when>
										<c:otherwise>
											<li class="page-item"><a class="page-link" href="">▷</a></li>
										</c:otherwise>
									</c:choose>

										<%-- 블록 오른쪽 이동 --%>
									<c:choose>
										<c:when test="${pagination.curBlock < pagination.totalBlocks }">
											<li class="page-item"><a class="page-link" href="list.do?p=${pagination.endPageNo + 1}&y=${yyyy}&t=${term}&g=${grade}">▶</a></li>
										</c:when>
										<c:otherwise>
											<li class="page-item"><a class="page-link" href="">▶</a></li>
										</c:otherwise>
									</c:choose>
								</ul>
							</nav>
						</c:if>
					</form>
				<!------------------------------------------------------------>
				</div>		<!-- card body end -->
			</div>		<!-- card end -->
		</div>

	</div>	<!-- row end -->
	<!------------------------------------------------------------------------------>
	<!-- 내용 끝 -->
	<!------------------------------------------------------------------------------>
<%@include file="../section/footer.jsp"%>