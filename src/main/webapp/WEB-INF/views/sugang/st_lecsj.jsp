<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../section/header.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
								<li class="breadcrumb-item">학생</li>
								<li class="breadcrumb-item active">수강과목</li>
							</ol>
							<div class="clearfix"></div>
						</div>
					</div>
				</div>
<script>
	function find_text(){
		form1.submit();
	}
</script>

				<div class="row">

					<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 col-xl-12">
						<div class="card mb-3">
							<div class="card-header mycolor3" style="padding:10px">
								<div class="row">
									<div class="col" align="left">
										<h3><i class="fa fa-table"></i> 수강과목</h3>
									</div>
								</div>

							</div>

							<div class="card-body" style="padding:10px">

								<div class="row">
									<div class="col-auto" align="left">
										<h6>&nbsp;<font color="gray">${yyyy}년 ${term}학기</font></h6>
									</div>
									<div class="col" align="right">
										<h6>&nbsp;<font color="gray">${student.schoolno} ${student.name}</font>&nbsp;</h6>
									</div>
								</div>
								<div class="row" style="margin-bottom:5px">
									<div class="col" align="right">
										<h6>학점: <font color="red">${point}</font> &nbsp; 평점: <font color="red">${ipoint}</font>&nbsp;</h6>
									</div>
								</div>

								<form name="form1" action="../student/sugang.do" method="get">
								<div class="row">
									<div class="col" align="left">
											<div class="input-group input-group-sm">
												<div class="input-group-prepend">
													<span class="input-group-text">선택</span>
												</div>
												<div class="input-group-append">
													<select name="g" class="form-control form-control-sm">
														<option value='1' <c:if test="${grade == 1}" >selected</c:if> >1학년 </option>
														<option value='2' <c:if test="${grade == 2}" >selected</c:if> >2학년</option>
														<option value='3' <c:if test="${grade == 3}" >selected</c:if> >3학년</option>
													</select>
													&nbsp;
													<select name="t" class="form-control form-control-sm">
														<option value='1' <c:if test="${term == 1}" >selected</c:if>>1학기</option>
														<option value='2' <c:if test="${term == 2}" >selected</c:if>>2학기</option>
													</select>
												</div>
												&nbsp;<button class="btn btn-sm mycolor1" onClick="javascript:find_text();">검색</button>
											</div>
									</div>
								</div>
								</form>

								<table class="table table-bordered table-hover  table-responsive-sm mytable" style="width:100%;">
									<tr class="mycolor1">
										<td>전공</td>
										<td>필수</td>
										<td>과목코드</td>
										<td>과목명</td>
										<td>학점</td>
										<td>출석</td>
										<td>평소</td>
										<td>중간</td>
										<td>기말</td>
										<td>실습</td>
										<td>총점</td>
										<td>학점</td>
										<td>등급</td>
										<td>재수강</td>
									</tr>
									<c:forEach var="sugang" items="${sugangList}">
									<tr>
										<td>
											<c:if test="${sugang.ismajor == 0}">교양</c:if>
											<c:if test="${sugang.ismajor == 1}">전공</c:if>
										</td>
										<td>
											<c:if test="${sugang.ismajor == 0}">필수</c:if>
											<c:if test="${sugang.ismajor == 1}">선택</c:if>
										</td>
										<td>${sugang.subject_code}</td>
										<td>${sugang.subject_name}</td>
										<td>${sugang.point}</td>
										<td>${sugang.iattend}</td>
										<td>${sugang.inormal}</td>
										<td>${sugang.imiddle}</td>
										<td>${sugang.ilast}</td>
										<td>${sugang.ipractice}</td>
										<td class="mycolor4"><b>${sugang.itotal}</b></td>
										<td class="mycolor4"><b>${sugang.ipoint}</b></td>
										<td class="mycolor4"><b>${sugang.igrade}</b></td>
										<td><c:if test="${sugang.retake ==1}">재수강</c:if></td>
									</tr>
									</c:forEach>

								</table>
								<c:if test="${fn:length(sugangList) != 0}">
									<nav>
										<ul class="pagination pagination-sm justify-content-center">
												<%-- 블록 왼쪽 이동 --%>
											<c:choose>
												<c:when test="${pagination.curBlock > 1 }">
													<li class="page-item"><a class="page-link" href="sugang.do?p=${pagination.beginPageNo- 1}&g=${grade}&t=${term}">◀</a></li>
												</c:when>
												<c:otherwise>
													<li class="page-item"><a class="page-link" href="">◀</a></li>
												</c:otherwise>
											</c:choose>

												<%-- 페이지 왼쪽 이동 --%>
											<c:choose>
												<c:when test="${pagination.curPageNo > 1 }">
													<li class="page-item"><a class="page-link" href="sugang.do?p=${pagination.curPageNo - 1}&g=${grade}&t=${term}">◁</a></li>
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
														<li class="page-item"><a class="page-link" href="sugang.do?p=${pageNo}&g=${grade}&t=${term}">${pageNo}</a></li>
													</c:otherwise>
												</c:choose>
											</c:forEach>


												<%-- 페이지 오른쪽 이동 --%>
											<c:choose>
												<c:when test="${pagination.curPageNo < pagination.totalPages }">
													<li class="page-item"><a class="page-link" href="sugang.do?p=${pagination.curPageNo + 1}&g=${grade}&t=${term}">▷</a></li>
												</c:when>
												<c:otherwise>
													<li class="page-item"><a class="page-link" href="">▷</a></li>
												</c:otherwise>
											</c:choose>

												<%-- 블록 오른쪽 이동 --%>
											<c:choose>
												<c:when test="${pagination.curBlock < pagination.totalBlocks }">
													<li class="page-item"><a class="page-link" href="sugang.do?p=${pagination.endPageNo + 1}&g=${grade}&t=${term}">▶</a></li>
												</c:when>
												<c:otherwise>
													<li class="page-item"><a class="page-link" href="">▶</a></li>
												</c:otherwise>
											</c:choose>
										</ul>
									</nav>
								</c:if>
								</div>

							</div>		<!-- card body end -->
						</div>		<!-- card end -->
					</div>

				</div>	<!-- row end -->
<!------------------------------------------------------------------------------>
<!-- 내용 끝 -->
<!------------------------------------------------------------------------------>
<%@include file="../section/footer.jsp"%>