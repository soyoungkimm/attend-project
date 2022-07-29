<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../section/header.jsp" %>
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
								<li class="breadcrumb-item active">교수별 담당과목 현황</li>
							</ol>
							<div class="clearfix"></div>
						</div>
					</div>
				</div>

				<!--- 시간표 관련 CSS  ---------------------------------------------->

				<script>
					function movePage(pageNum) {
						location.href = "/TimeTeacher/list.do" +
								"?year=" + form1.sel1.value +
								"&term=" + form1.sel2.value +
								"&depart=" + form1.sel3.value +
								"&page=" + pageNum;
					}
				</script>

				<div class="row">
				
					<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 col-xl-12">
						<div class="card mb-3">
							<div class="card-header mycolor3" style="padding:10px">
								<h3><i class="fa fa-table"></i> 교수별 당담과목 현황</h3>
							</div>
								
							<div class="card-body" style="padding:10px">

								<form name="form1" action="/TimeTeacher/list.do" method="post">
								<div class="row" style="margin-bottom:3px">
									<div class="col" align="left">
										<div class="form-inline">

											<div class="input-group input-group-sm">
												<div class="input-group-prepend">
													<span class="input-group-text">년도</span>
												</div>
												<div class="input-group-append">
													<select name="sel1" class="form-control form-control-sm">
														<c:forEach var="var" items="${yearList}" varStatus="status">
															<option <c:if test="${status.index == year}">selected</c:if>>${var}</option>
														</c:forEach>
													</select>
													&nbsp;
													<select name="sel2" class="form-control form-control-sm">
														<option value='1' <c:if test="${term==1}">selected</c:if>>1학기</option>
														<option value='2' <c:if test="${term==2}">selected</c:if>>2학기</option>
													</select>
												</div>
											</div>
											&nbsp;
											<div class="input-group input-group-sm">
												<div class="input-group-prepend">
													<span class="input-group-text">학과</span>
												</div>
												<div class="input-group-append">
													<select name="sel3" class="form-control form-control-sm">
														<option value='0' selected>전체</option>
														<c:forEach var="var" items="${departsList}">
															<option value="${var.getId()}" <c:if test="${var.getId()==depart}">selected</c:if>>${var.getName()}</option>
														</c:forEach>
													</select>
												</div>
												&nbsp;<input type="button" class="btn btn-sm btn-primary" value="검색" onclick="movePage(1);">
											</div>

										</div>
									</div>
								</div>

								</form>

								<table id="timeTable" class="table table-bordered table-responsive-sm mytable" style="width:100%">
									<tr class="mycolor1">
										<td>학과</td>
										<td>교수님</td>
										<td>구분</td>
										<td>담당과목 개수</td>
										<td>주당 강의시간</td>
										<td>주당 강의일수</td>
									</tr>
									<c:forEach var="time" items="${timeTableList}">
										<tr>
											<td>${time.getDepartName()}</td>
											<td>${time.getTeacherName()}</td>
											<td>${teacherKindList[time.getTeacherKind()]}</td>
											<td>${time.getSubjectCount()}</td>
											<td>${time.getWeekTeachHour()}</td>
											<td>${time.getWeekTeachDay()}</td>
										</tr>
									</c:forEach>
								</table>

								<nav>
									<ul class="pagination pagination-sm justify-content-center">
										<li class='page-item'><a class="page-link" href="javascript:movePage(1)">◀</a></li>
										<li class='page-item'><a class="page-link" href="javascript:movePage(${pagination.getBeginPageNo()-1})">◁</a></li>
										<c:forEach begin="${pagination.getBeginPageNo()}" end="${pagination.getEndPageNo()}" var="var">
											<c:if test="${pagination.getCurPageNo() != var}">
												<li class='page-item'><a class="page-link" href="javascript:movePage(${var})">${var}</a></li>
											</c:if>
											<c:if test="${pagination.getCurPageNo() == var}">
												<li class='page-item active'>
													<span class='page-link' style='background-color:steelblue'>
														<a href="javascript:movePage(${var})">${var}</a>
													</span>
												</li>
											</c:if>
										</c:forEach>
										<li class='page-item'><a class="page-link" href="javascript:movePage(${pagination.getEndPageNo()+1})">▷</a></li>
										<li class='page-item'><a class="page-link" href="javascript:movePage(${pagination.getTotalPages()})">▶</a></li>
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