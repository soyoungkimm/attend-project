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
				<h3><i class="fa fa-table"></i> 학과 및 부서</h3>
			</div>

			<div class="card-body" style="padding:10px">

				<script>
					function find_text()
					{
						if (!form1.text1.value)
							form1.action="/depart/list.do";
						else
							form1.action="/depart/list.do?text1=" + form1.text1.value+"&page=${page}";
						form1.submit();
					}
				</script>

				<form name="form1" method="post" action="">
					<div class="row" style="margin-bottom:5px">
						<div class="col-auto" style="text-align:left">
							<div class="form-inline">
								<div class="input-group input-group-sm">
									<div class="input-group-prepend">
										<span class="input-group-text">이름</span>
									</div>
									<input type="text" name="text1" size="10" value="" class="form-control"
										   onKeydown="if (event.keyCode == 13) { find_text(); }" >
									<div class="input-group-append">
										<button class="btn btn-sm mycolor1" type="button" onClick="find_text();">검색</button>
									</div>
								</div>
							</div>
						</div>
						<div class="col" align="right">
							<a href="/depart/new.do" class="btn btn-sm mycolor1">추가</a>
						</div>
					</div>
				</form>

				<table class="table table-bordered table-hover table-responsive-sm mytable" style="width:100%">
					<thead>
					<tr class="mycolor1">
						<th>번호</th>
						<th>학과/부서명</th>
						<th>학제</th>
						<th>반수</th>
						<th width="95"></th>
					</tr>
					</thead>
					<tbody>
						<c:forEach var="depart" items="${departs}">
							<tr>
								<td>${depart.getId()}</td>
								<td style="text-align:left">${depart.getName()}</td>
								<td>${depart.getGradeSystem()}</td>
								<td>${depart.getClassNum()}</td>
								<td>
									<a href="/depart/edit.do?id=${depart.getId()}" class="btn btn-xs btn-outline-primary">수정</a>
									<a href="#none" class="btn btn-xs btn-outline-danger" onClick="if (confirm('삭제할까요 ?')) location.href='/depart/delete.do?id=${depart.getId()}'">삭제</a>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>

				<nav>
					<ul class='pagination pagination-sm justify-content-center'>
						<li class='page-item'><a class="page-link" href="/depart/list.do?text=${text1}&page=${pageBegin-1}">◀</a></li>
						<li class='page-item'><a class="page-link" href="/depart/list.do?text=${text1}&page=${page-1}">◁</a></li>
						<c:forEach var="pageIndex" items="${pageList}">
							<c:if test="${pageIndex != page}">
								<li class='page-item'>
									<a class="page-link" href="/depart/list.do?text=${text1}&page=${pageIndex}">${pageIndex}</a>
								</li>
							</c:if>
							<c:if test="${pageIndex == page}">
								<li class='page-item active'>
									<a href="/depart/list.do?text=${text1}&page=${pageIndex}">
										<span class='page-link' style='background-color:steelblue'>${pageIndex}</span>
									</a>
								</li>
							</c:if>
						</c:forEach>
						<li class='page-item'><a class="page-link" href="/depart/list.do?text1=${text1}&page=${page+1}">▷</a></li>
						<li class='page-item'><a class="page-link" href="/depart/list.do?text1=${text1}&page=${pageEnd+1}">▶</a></li>
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