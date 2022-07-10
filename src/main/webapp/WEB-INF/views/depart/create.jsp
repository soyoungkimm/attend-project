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
								<li class="breadcrumb-item active">학과 및 부서</li>
							</ol>
							<div class="clearfix"></div>
						</div>
					</div>
				</div>

				<div class="row">
				
					<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 col-xl-12">
						<div class="card mb-3">
							<div class="card-header mycolor3" style="padding:10px">
								<h3><i class="fa fa-table"></i> 학과 및 부서 입력</h3>
							</div>
								
							<div class="card-body" style="padding:10px">

								<form name="form1" method="post" action="/depart/new.do">

								<table class="table table-bordered mytable-centermiddle" style="width:100%;">
									<tr>
										<td class="mycolor2">번호</td>
										<td>
											<div class="form-inline">
												<input type="text" name="id" value="" class="form-control form-control-sm" style="width:50px">
											</div>
										</td>
									</tr>
									<tr>
										<td class="mycolor2">학과/부서명</td>
										<td>
											<div class="form-inline">
												<input type="text" name="name" value="" class="form-control form-control-sm" required>
											</div>
										</td>
									</tr>
									<tr>
										<td class="mycolor2">반 개수</td>
										<td>
											<div class="form-inline">
											<select name="classnum" class="form-control form-control-sm" style="width:120px">
												<option value="0">없음</option>
												<option value='1'>1</option>
												<option value='2' selected>2</option>
												<option value='3'>3C</option>
												<option value='4'>4</option>
												<option value='5'>5</option>
												<option value='6'>6</option>
											</select>
											</div>
										</td>
									</tr>
									<tr>
										<td class="mycolor2">학제</td>
										<td>
											<div class="form-inline">
											<select name="gradesystem" class="form-control form-control-sm" style="width:80px">
												<option value="0" selected></option>
												<option value='2'>2년제</option>
												<option value='3'>3년제</option>
											</select>
											</div>
										</td>
									</tr>
								</table>

								<div align="center">
									<input type="submit" value="저장" class="btn btn-sm mycolor1">&nbsp;
									<input type="button" value="이전화면" class="btn btn-sm mycolor1" onclick="history.back();">
								</div>
								<br>※ [번호]는 반드시 중복된 값이 없어야 합니다.

								</form>


							</div>		<!-- card body end -->
						</div>		<!-- card end -->
					</div>

				</div>	<!-- row end -->
<!------------------------------------------------------------------------------>
<!-- 내용 끝 -->
<!------------------------------------------------------------------------------>
<%@ include file="../section/footer.jsp" %>