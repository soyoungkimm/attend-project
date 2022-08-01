<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@include file="../section/header.jsp" %>
<!------------------------------------------------------------------------------>
<!-- 내용 시작 -->
<!------------------------------------------------------------------------------>
				<div class="row">
					<div class="col-xl-12">
						<div class="breadcrumb-holder">
							<h1 class="main-title float-left">컴퓨터소프트웨어학과</h1>
							<ol class="breadcrumb float-right">
								<li class="breadcrumb-item">Home</li>
								<li class="breadcrumb-item">조교</li>
								<li class="breadcrumb-item active">메인</li>
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
										<h3><i class="fa fa-table"></i> 공지사항 </h3>
									</div>
									<div class="col" align="right">
										<h3></h3>
									</div>
								</div>
							</div>
							<div class="card-body" style="padding:10px">
								<div id="notice-div">
									<table class="table table-bordered mytable" style="width:100%;">
										<tr class="mycolor1">
											<td>날짜</td>
											<td>제목</td>
											<td width="60"></td>
										</tr>
										<tr>
											<td>2019-06-06</td>
											<td style="text-align:left">수강신청기간 공지</td>
											<td>
												<a href="" class="btn btn-xs btn-outline-primary">보기</a>
											</td>
										</tr>
										<tr>
											<td>2019-06-06</td>
											<td style="text-align:left">수강신청기간 공지</td>
											<td>
												<a href="" class="btn btn-xs btn-outline-primary">보기</a>
											</td>
										</tr>
									</table>
								</div>

								<nav>
									<div id="notice-pagination-div">
										<ul class='pagination pagination-sm justify-content-center'>
											<li class='page-item'><a class="page-link" href="#">◀</a></li>
											<li class='page-item'><a class="page-link" href="#">◁</a></li>
											<li class='page-item'><a class="page-link" href="#">2</a></li>
											<li class='page-item'><a class="page-link" href="#">3</a></li>
											<li class='page-item active'><span class='page-link' style='background-color:steelblue'>4</span></li>
											<li class='page-item'><a class="page-link" href="#">5</a></li>
											<li class='page-item'><a class="page-link" href="#">6</a></li>
											<li class='page-item'><a class="page-link" href="#">▷</a></li>
											<li class='page-item'><a class="page-link" href="#">▶</a></li>
										</ul>
									</div>
								</nav>

							</div>		<!-- card body end -->
						</div>		<!-- card end -->
					</div>
						
				</div>	<!-- row end -->

				<div class="row">
				
					<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 col-xl-12">
						<div class="card mb-3">
							<div class="card-header mycolor3" style="padding:10px">
								<div class="row">
									<div class="col" align="left">
										<h3><i class="fa fa-table"></i> 휴보강 </h3>
									</div>
									<div class="col" align="right">
										<h3></h3>
									</div>
								</div>
							</div>
							<div class="card-body" style="padding:10px">
								<div id="lec-div">
									<table class="table table-bordered table-hover table-responsive-sm mytable" style="width:100%">
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
											<td>학과장</td>
										</tr>
										<tr>
											<td>컴소과</td>
											<td>교수님1</td>
											<td>PHP</td>
											<td>2학년/A반</td>
											<td class="mycolor4">2019-03-12</td>
											<td class="mycolor4">3, 4 교시</td>
											<td class="mycolor3">2019-06-15</td>
											<td class="mycolor3">1, 2 교시</td>
											<td class="mycolor3">인관 컴퓨터실1</td>
											<td><b>신청</b></td>
											<td>
												<a href="" class="btn btn-xs btn-outline-primary">보기</a>
											</td>
										</tr>
									</table>
								</div>

								<nav>
									<div id="lec-pagination-div">
										<ul class='pagination pagination-sm justify-content-center'>
											<li class='page-item'><a class="page-link" href="#">◀</a></li>
											<li class='page-item'><a class="page-link" href="#">◁</a></li>
											<li class='page-item'><a class="page-link" href="#">2</a></li>
											<li class='page-item'><a class="page-link" href="#">3</a></li>
											<li class='page-item active'><span class='page-link' style='background-color:steelblue'>4</span></li>
											<li class='page-item'><a class="page-link" href="#">5</a></li>
											<li class='page-item'><a class="page-link" href="#">6</a></li>
											<li class='page-item'><a class="page-link" href="#">▷</a></li>
											<li class='page-item'><a class="page-link" href="#">▶</a></li>
										</ul>
									</div>
								</nav>


							</div>		<!-- card body end -->
						</div>		<!-- card end -->
					</div>
						
				</div>	<!-- row end -->
<script>
	function notice_pagination(page) {
		$.ajax({
			url: "notice-pagination.do",
			type: "POST",
			dataType: 'json',
			data: {
				page: page
			},
			success: function (data) {

				// ----------- notice ---------------
				let noticeList = data.noticeList;
				let str = '<table class="table table-bordered mytable" style="width:100%;">\n' +
						'<tr class="mycolor1">\n' +
						'<td width="30%">날짜</td>\n' +
						'<td>제목</td>\n' +
						'<td width="60"></td>\n' +
						'</tr>\n';
				for (notice of noticeList) {
					str += '<tr>\n' +
							'<td>' + notice.writeday + '</td>\n' +
							'<td style="text-align:left">' + notice.title + '</td>\n' +
							'<td>\n' +
							'<a href="/notice/detail.do?id=' + notice.id + '" class="btn btn-xs btn-outline-primary">보기</a>\n' +
							'</td>\n' +
							'</tr>\n';
				}
				str += '</table>';

				$('#notice-div').empty();
				$('#notice-div').html(str);


				// ----------- notice pagination --------------
				str = '';
				if (noticeList.length != 0) {
					str = '<ul class="pagination pagination-sm justify-content-center">';
					let pagination = data.pagination;

					// 블록 왼쪽 이동
					if (pagination.curBlock > 1) {
						str += '<li class="page-item"><a class="page-link" onclick="notice_pagination(' + (pagination.beginPageNo - 1) + ')">◀</a></li>';
					} else {
						str += '<li class="page-item"><a class="page-link" href="">◀</a></li>';
					}

					// 페이지 왼쪽 이동
					if (pagination.curPageNo > 1) {
						str += '<li class="page-item"><a class="page-link" onclick="notice_pagination(' + (pagination.curPageNo - 1) + ')">◁</a></li>';
					} else {
						str += '<li class="page-item"><a class="page-link" href="">◁</a></li>';
					}

					// 페이지 번호
					for (let pageNo = pagination.beginPageNo; pageNo <= pagination.endPageNo; pageNo++) {
						if (pageNo == pagination.curPageNo) {
							str += '<li class="page-item active"><span class="page-link" style="background-color:steelblue">' + pageNo + '</span></li>';
						} else {
							str += '<li class="page-item"><a class="page-link" onclick="notice_pagination(' + pageNo + ')">' + pageNo + '</a></li>';
						}
					}

					// 페이지 오른쪽 이동
					if (pagination.curPageNo < pagination.totalPages) {
						str += '<li class="page-item"><a class="page-link" onclick="notice_pagination(' + (pagination.curPageNo + 1) + ')">▷</a></li>';
					} else {
						str += '<li class="page-item"><a class="page-link" href="">▷</a></li>';
					}


					// 블록 오른쪽 이동
					if (pagination.curBlock < pagination.totalBlocks) {
						str += '<li class="page-item"><a class="page-link" onclick="notice_pagination(' + (pagination.endPageNo + 1) + ')">▶</a></li>';
					} else {
						str += '<li class="page-item"><a class="page-link" href="">▶</a></li>';
					}

					str += '</ul>';
				}

				$('#notice-pagination-div').empty();
				$('#notice-pagination-div').html(str);

			},
			error: function (request, status, error) {
				alert("code = " + request.status + " message = " + request.responseText + " error = " + error);
				console.log("code = " + request.status + " message = " + request.responseText + " error = " + error);
			}
		});
	}


	function lec_pagination(page) {
		$.ajax({
			url: "as-lec-pagination.do",
			type: "POST",
			dataType: 'json',
			data: {
				page: page
			},
			success: function (data) {

				// ----------- 휴보강 ---------------
				let lecList = data.lecList;
				let str = '<table class="table table-bordered table-responsive-sm mytable" style="width:100%;">' +
						'<thead>' +
						'<tr class="mycolor1">' +
						'<th>학과</th>' +
						'<th>교수님</th>' +
						'<th>교과목</th>' +
						'<th>학년/반</th>' +
						'<th>휴강날짜</th>' +
						'<th>휴강교시</th>' +
						'<th>보강날짜</th>' +
						'<th>보강교시</th>' +
						'<th>보강강의실</th>' +
						'<th>처리상태</th>' +
						'<th>학과장</th>' +
						'</tr>' +
						'<thead>' +
						'<tbody>';
				for (lec of lecList) {
					str += '<tr>' +
							'<td>' + lec.departName + '</td>' +
							'<td>' + lec.teacherName + '</td>' +
							'<td>' + lec.subjectName + '</td>' +
							'<td>' + lec.grade + '학년/' + lec.ban + '반</td>' +
							'<td class="mycolor4">' + lec.normdate + '</td>' +
							'<td class="mycolor4">';

					if (lec.normstart != 0) {
						let count = 0;
						for (let i = lec.normstart; i <= lec.normstart + lec.normhour - 1; i++) {
							count++;
							str += i;
							if (count != (lec.normhour)) str += ',';
						}
						str += '교시';
					}

					str += '</td>' +
							'<td class="mycolor3">';
					if (lec.restdate != undefined) {
						str += lec.restdate;
					}
					str += '</td>' +
							'<td class="mycolor3">';

					count = 0;
					if (lec.reststart != 0 && lec.resthour != 0) {
						for (let i = lec.reststart; i <= lec.reststart + lec.resthour - 1; i++) {
							count++;
							str += i;
							if (count != (lec.resthour)) str += ',';
						}
						str += '교시';
					}

					str += '</td>' +
							'<td class="mycolor3">' + lec.buildingName + ' ' + lec.roomName + '</td>' +
							'<td><b>';

					if (lec.state == 0) {
						str += '신청';
					} else if (lec.state == 1) {
						str += '취소';
					} else if (lec.state == 2) {
						str += '반려';
					} else if (lec.state == 3) {
						str += '학과장승인';
					} else {
						str += '최종승인';
					}

					str += '</b></td>' +
							'<td>' +
							'<a href="/TeLecMove/list.do" class="btn btn-xs btn-outline-primary">보기</a>' +
							'</td>' +
							'</tr>';
				}
				str += '</tbody>' +
						'</table>';

				$('#lec-div').empty();
				$('#lec-div').html(str);


				// ----------- 휴보강 pagination --------------
				str = '';
				if (lecList.length != 0) {
					str = '<ul class="pagination pagination-sm justify-content-center">';
					let pagination = data.pagination;

					// 블록 왼쪽 이동
					if (pagination.curBlock > 1) {
						str += '<li class="page-item"><a class="page-link" onclick="lec_pagination(' + (pagination.beginPageNo - 1) + ')">◀</a></li>';
					} else {
						str += '<li class="page-item"><a class="page-link">◀</a></li>';
					}

					// 페이지 왼쪽 이동
					if (pagination.curPageNo > 1) {
						str += '<li class="page-item"><a class="page-link" onclick="lec_pagination(' + (pagination.curPageNo - 1) + ')">◁</a></li>';
					} else {
						str += '<li class="page-item"><a class="page-link" >◁</a></li>';
					}

					// 페이지 번호
					for (let pageNo = pagination.beginPageNo; pageNo <= pagination.endPageNo; pageNo++) {
						if (pageNo == pagination.curPageNo) {
							str += '<li class="page-item active"><span class="page-link" style="background-color:steelblue">' + pageNo + '</span></li>';
						} else {
							str += '<li class="page-item"><a class="page-link" onclick="lec_pagination(' + pageNo + ')">' + pageNo + '</a></li>';
						}
					}

					// 페이지 오른쪽 이동
					if (pagination.curPageNo < pagination.totalPages) {
						str += '<li class="page-item"><a class="page-link" onclick="lec_pagination(' + (pagination.curPageNo + 1) + ')">▷</a></li>';
					} else {
						str += '<li class="page-item"><a class="page-link">▷</a></li>';
					}


					// 블록 오른쪽 이동
					if (pagination.curBlock < pagination.totalBlocks) {
						str += '<li class="page-item"><a class="page-link" onclick="lec_pagination(' + (pagination.endPageNo + 1) + ')">▶</a></li>';
					} else {
						str += '<li class="page-item"><a class="page-link">▶</a></li>';
					}

					str += '</ul>';
				}


				$('#lec-pagination-div').empty();
				$('#lec-pagination-div').html(str);

			},
			error: function (request, status, error) {
				alert("code = " + request.status + " message = " + request.responseText + " error = " + error);
				console.log("code = " + request.status + " message = " + request.responseText + " error = " + error);
			}
		});
	}

	window.onload = function() {
		notice_pagination(1);
		lec_pagination(1);
	}
</script>
<%@include file="../section/footer.jsp" %>