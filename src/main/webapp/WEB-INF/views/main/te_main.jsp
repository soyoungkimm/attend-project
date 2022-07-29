<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@include file="../section/header.jsp" %>
<!------------------------------------------------------------------------------>
<!-- 내용 시작 -->
<!------------------------------------------------------------------------------>
<style>
    .page-item {
        color : #007bff;
        cursor: pointer;
    }
</style>
<div class="row">
    <div class="col-xl-12">
        <div class="breadcrumb-holder">
            <h1 class="main-title float-left">소속 학과표시</h1>
            <ol class="breadcrumb float-right">
                <li class="breadcrumb-item">Home</li>
                <li class="breadcrumb-item">교수</li>
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
                        <h3>${sessionScope.logined.name}</h3>
                    </div>
                </div>
            </div>
            <div class="card-body" style="padding:10px">
                <div id="notice_div">
                    <table class="table table-bordered mytable" style="width:100%;">
                        <tr class="mycolor1">
                            <td width="30%">날짜</td>
                            <td>제목</td>
                            <td width="60"></td>
                        </tr>
                        <c:forEach var="notice" items="${noticeList}">
                            <tr>
                                <td>${notice.writeday}</td>
                                <td style="text-align:left">${notice.title}</td>
                                <td>
                                    <a href="/notice/detail.do?id=${notice.id}" class="btn btn-xs btn-outline-primary">보기</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                </div>

                <c:if test="${fn:length(noticeList) != 0}">
                    <nav>
                        <div id="pagination_div">
                            <ul class='pagination pagination-sm justify-content-center'>
                                    <%-- 블록 왼쪽 이동 --%>
                                <li class="page-item"><a class="page-link">◀</a></li>

                                    <%-- 페이지 왼쪽 이동 --%>
                                <li class="page-item"><a class="page-link">◁</a></li>

                                    <%-- 페이지 번호 --%>
                                <c:forEach var="pageNo" begin="${note_pagination.beginPageNo}"
                                           end="${note_pagination.endPageNo}">
                                    <c:choose>
                                        <c:when test="${pageNo == note_pagination.curPageNo }">
                                            <li class="page-item active"><span class="page-link"
                                                                               style="background-color:steelblue">${pageNo}</span>
                                            </li>
                                        </c:when>
                                        <c:otherwise>
                                            <li class="page-item"><a class="page-link"
                                                                     onclick="notice_pagination(${pageNo})">${pageNo}</a>
                                            </li>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>

                                    <%-- 페이지 오른쪽 이동 --%>
                                <c:choose>
                                    <c:when test="${note_pagination.curPageNo < note_pagination.totalPages }">
                                        <li class="page-item"><a class="page-link"
                                                                 onclick="notice_pagination(${note_pagination.curPageNo + 1})">▷</a>
                                        </li>
                                    </c:when>
                                    <c:otherwise>
                                        <li class="page-item"><a class="page-link">▷</a></li>
                                    </c:otherwise>
                                </c:choose>

                                    <%-- 블록 오른쪽 이동 --%>
                                <c:choose>
                                    <c:when test="${note_pagination.curBlock < note_pagination.totalBlocks }">
                                        <li class="page-item"><a class="page-link"
                                                                 onclick="notice_pagination(${note_pagination.endPageNo + 1})">▶</a>
                                        </li>
                                    </c:when>
                                    <c:otherwise>
                                        <li class="page-item"><a class="page-link">▶</a></li>
                                    </c:otherwise>
                                </c:choose>
                            </ul>
                        </div>
                    </nav>
                </c:if>
            </div>        <!-- card body end -->
        </div>        <!-- card end -->
    </div>

</div>
<!-- row end -->

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
                <div id="lec_div">
                    <table class="table table-bordered mytable" style="width:100%;">
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
                        <c:forEach var="lec" items="${lecturedayList}">
                            <tr>
                                <td>${lec.departName}</td>
                                <td>${lec.teacherName}</td>
                                <td>${lec.subjectName}</td>
                                <td>${lec.grade}학년/${lec.ban}반</td>
                                <td class="mycolor4">${lec.normdate}</td>
                                <td class="mycolor4">
                                    <c:forEach begin="${lec.normstart}" end="${lec.normstart + lec.normhour - 1}"
                                               var="period" varStatus="status">
                                        ${period}
                                        <c:if test="${status.index != status.end}">,</c:if>
                                    </c:forEach>
                                    교시
                                </td>
                                <td class="mycolor3">${lec.restdate}</td>
                                <td class="mycolor3">
                                    <c:if test="${reststart ne null}">
                                        <c:forEach begin="${lec.reststart}" end="${lec.reststart + lec.resthour - 1}"
                                                   var="period" varStatus="status">
                                            ${period}
                                            <c:if test="${status.index != status.end}">,</c:if>
                                        </c:forEach>
                                        교시
                                    </c:if>
                                </td>
                                <td class="mycolor3">${lec.buildingName} ${lec.roomName}</td>
                                <td><b>
                                    <c:choose>
                                        <c:when test="${lec.state == 0}">신청</c:when>
                                        <c:when test="${lec.state == 1}">취소</c:when>
                                        <c:when test="${lec.state == 2}">반려</c:when>
                                        <c:when test="${lec.state == 3}">학과장승인</c:when>
                                        <c:otherwise>최종승인</c:otherwise>
                                    </c:choose>
                                </b></td>
                                <td>
                                    <fmt:parseDate value="${lec.normdate}" pattern="yyyy-mm-dd" var="normdate"/>
                                    <fmt:formatDate var="normyear" value="${normdate}" pattern="yyyy"/>
                                    <a href="/TeLecMove/list.do"
                                       class="btn btn-xs btn-outline-primary">보기</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                </div>

                <c:if test="${fn:length(lecturedayList) != 0}">
                    <nav>
                        <div id="lec_pagination_div">
                            <ul class='pagination pagination-sm justify-content-center'>
                                    <%-- 블록 왼쪽 이동 --%>
                                <li class="page-item"><a class="page-link">◀</a></li>

                                    <%-- 페이지 왼쪽 이동 --%>
                                <li class="page-item"><a class="page-link">◁</a></li>

                                    <%-- 페이지 번호 --%>
                                <c:forEach var="pageNo" begin="${lec_pagination.beginPageNo}"
                                           end="${lec_pagination.endPageNo}">
                                    <c:choose>
                                        <c:when test="${pageNo == lec_pagination.curPageNo }">
                                            <li class="page-item active"><span class="page-link"
                                                                               style="background-color:steelblue">${pageNo}</span>
                                            </li>
                                        </c:when>
                                        <c:otherwise>
                                            <li class="page-item"><a class="page-link"
                                                                     onclick="lec_pagination(${pageNo})">${pageNo}</a>
                                            </li>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>

                                    <%-- 페이지 오른쪽 이동 --%>
                                <c:choose>
                                    <c:when test="${lec_pagination.curPageNo < lec_pagination.totalPages }">
                                        <li class="page-item"><a class="page-link"
                                                                 onclick="lec_pagination(${lec_pagination.curPageNo + 1})">▷</a>
                                        </li>
                                    </c:when>
                                    <c:otherwise>
                                        <li class="page-item"><a class="page-link">▷</a></li>
                                    </c:otherwise>
                                </c:choose>

                                    <%-- 블록 오른쪽 이동 --%>
                                <c:choose>
                                    <c:when test="${lec_pagination.curBlock < lec_pagination.totalBlocks }">
                                        <li class="page-item"><a class="page-link"
                                                                 onclick="lec_pagination(${lec_pagination.endPageNo + 1})">▶</a>
                                        </li>
                                    </c:when>
                                    <c:otherwise>
                                        <li class="page-item"><a class="page-link">▶</a></li>
                                    </c:otherwise>
                                </c:choose>
                            </ul>
                        </div>
                    </nav>
                </c:if>


            </div>        <!-- card body end -->
        </div>        <!-- card end -->
    </div>

</div>
<!-- row end -->

<div class="row">

    <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 col-xl-12">
        <div class="card mb-3">
            <div class="card-header mycolor3" style="padding:10px">
                <div class="row">
                    <div class="col" align="left">
                        <h3><i class="fa fa-table"></i> 교과목 문의 </h3>
                    </div>
                    <div class="col" align="right">
                        <h3></h3>
                    </div>
                </div>
            </div>
            <div class="card-body" style="padding:10px">
                <div id="qa_div">
                    <table class="table table-bordered mytable" style="width:100%;">
                        <tr class="mycolor1">
                            <td>날짜</td>
                            <td>교과목</td>
                            <td>제목</td>
                            <td>학생</td>
                            <td>상태</td>
                            <td width="60"></td>
                        </tr>
                        <c:forEach var="qa" items="${mylectureQaList}">
                            <tr>
                                <td>${qa.qday}</td>
                                <td>${qa.subject_name}</td>
                                <td style="text-align:left">${qa.qtitle}</td>
                                <td>${qa.student_name}</td>
                                <td><b>
                                    <c:choose>
                                        <c:when test="${qa.qkind == 0}">신청</c:when>
                                        <c:when test="${qa.qkind == 1}"><font color="red">문의</font></c:when>
                                        <c:when test="${qa.qkind == 2}"><font color="blue">답변</font></c:when>
                                    </c:choose>
                                </b></td>
                                <td>
                                    <a href="/lecQa/te_edit.do?id=${qa.id}" class="btn btn-xs btn-outline-primary">보기</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                </div>

                <c:if test="${fn:length(mylectureQaList) != 0}">
                    <nav>
                        <div id="qa_pagination_div">
                            <ul class='pagination pagination-sm justify-content-center'>
                                    <%-- 블록 왼쪽 이동 --%>
                                <li class="page-item"><a class="page-link">◀</a></li>

                                    <%-- 페이지 왼쪽 이동 --%>
                                <li class="page-item"><a class="page-link">◁</a></li>

                                    <%-- 페이지 번호 --%>
                                <c:forEach var="pageNo" begin="${qa_pagination.beginPageNo}"
                                           end="${qa_pagination.endPageNo}">
                                    <c:choose>
                                        <c:when test="${pageNo == qa_pagination.curPageNo }">
                                            <li class="page-item active"><span class="page-link"
                                                                               style="background-color:steelblue">${pageNo}</span>
                                            </li>
                                        </c:when>
                                        <c:otherwise>
                                            <li class="page-item"><a class="page-link"
                                                                     onclick="qa_pagination(${pageNo})">${pageNo}</a>
                                            </li>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>

                                    <%-- 페이지 오른쪽 이동 --%>
                                <c:choose>
                                    <c:when test="${qa_pagination.curPageNo < qa_pagination.totalPages }">
                                        <li class="page-item"><a class="page-link"
                                                                 onclick="qa_pagination(${qa_pagination.curPageNo + 1})">▷</a>
                                        </li>
                                    </c:when>
                                    <c:otherwise>
                                        <li class="page-item"><a class="page-link">▷</a></li>
                                    </c:otherwise>
                                </c:choose>

                                    <%-- 블록 오른쪽 이동 --%>
                                <c:choose>
                                    <c:when test="${qa_pagination.curBlock < qa_pagination.totalBlocks }">
                                        <li class="page-item"><a class="page-link"
                                                                 onclick="qa_pagination(${qa_pagination.endPageNo + 1})">▶</a>
                                        </li>
                                    </c:when>
                                    <c:otherwise>
                                        <li class="page-item"><a class="page-link">▶</a></li>
                                    </c:otherwise>
                                </c:choose>
                            </ul>
                        </div>
                    </nav>
                </c:if>

            </div>        <!-- card body end -->
        </div>        <!-- card end -->
    </div>

</div>
<!-- row end -->
<!------------------------------------------------------------------------------>
<!-- 내용 끝 -->
<!------------------------------------------------------------------------------>
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

                $('#notice_div').empty();
                $('#notice_div').html(str);


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

                $('#pagination_div').empty();
                $('#pagination_div').html(str);

            },
            error: function (request, status, error) {
                alert("code = " + request.status + " message = " + request.responseText + " error = " + error);
                console.log("code = " + request.status + " message = " + request.responseText + " error = " + error);
            }
        });
    }


    function lec_pagination(page) {
        $.ajax({
            url: "lec-pagination-teacher.do",
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

                $('#lec_div').empty();
                $('#lec_div').html(str);


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


                $('#lec_pagination_div').empty();
                $('#lec_pagination_div').html(str);

            },
            error: function (request, status, error) {
                alert("code = " + request.status + " message = " + request.responseText + " error = " + error);
                console.log("code = " + request.status + " message = " + request.responseText + " error = " + error);
            }
        });
    }

    function qa_pagination(page) {
        $.ajax({
            url: "qa-pagination.do",
            type: "POST",
            dataType: 'json',
            data: {
                page: page
            },
            success: function (data) {

                // ----------- qa ---------------
                let qaList = data.qaList;

                let str = '<table class="table table-bordered mytable" style="width:100%;"> ' +
                    '<tr class="mycolor1">' +
                    '<td>날짜</td>' +
                    '<td>교과목</td>' +
                    '<td>제목</td>' +
                    '<td>학생</td>' +
                    '<td>상태</td>' +
                    '<td width="60"></td> ' +
                    '</tr>';
                for (qa of qaList) {
                    str += '<tr> ' +
                        '<td>'+qa.qday+'</td> ' +
                        '<td>'+qa.subject_name+'</td> ' +
                        '<td style="text-align:left">'+qa.qtitle+'</td> ' +
                        '<td>'+qa.student_name+'</td> ' +
                        '<td><b>';
                    if(qa.qkind == 1){
                        str += '<font color="red">문의</font></b></td>'
                    }else if (qa.qkind == 2){
                        str += '<font color="blue">답변</font></b></td>'
                    }
                    str += '<td> ' +
                            '<a href="/lecQa/te_edit.do?id='+qa.id+'"class="btn btn-xs btn-outline-primary">보기</a> ' +
                        '</td> ' +
                        '</tr>';
                }
                str += '</table>';

                $('#qa_div').empty();
                $('#qa_div').html(str);


                // ----------- qa pagination --------------
                str = '';
                if (qaList.length != 0) {
                    str = '<ul class="pagination pagination-sm justify-content-center">';
                    let pagination = data.qa_pagination;

                    // 블록 왼쪽 이동
                    if (pagination.curBlock > 1) {
                        str += '<li class="page-item"><a class="page-link" onclick="qa_pagination(' + (pagination.beginPageNo - 1) + ')">◀</a></li>';
                    } else {
                        str += '<li class="page-item"><a class="page-link" href="">◀</a></li>';
                    }

                    // 페이지 왼쪽 이동
                    if (pagination.curPageNo > 1) {
                        str += '<li class="page-item"><a class="page-link" onclick="qa_pagination(' + (pagination.curPageNo - 1) + ')">◁</a></li>';
                    } else {
                        str += '<li class="page-item"><a class="page-link" href="">◁</a></li>';
                    }

                    // 페이지 번호
                    for (let pageNo = pagination.beginPageNo; pageNo <= pagination.endPageNo; pageNo++) {
                        if (pageNo == pagination.curPageNo) {
                            str += '<li class="page-item active"><span class="page-link" style="background-color:steelblue">' + pageNo + '</span></li>';
                        } else {
                            str += '<li class="page-item"><a class="page-link" onclick="qa_pagination(' + pageNo + ')">' + pageNo + '</a></li>';
                        }
                    }

                    // 페이지 오른쪽 이동
                    if (pagination.curPageNo < pagination.totalPages) {
                        str += '<li class="page-item"><a class="page-link" onclick="qa_pagination(' + (pagination.curPageNo + 1) + ')">▷</a></li>';
                    } else {
                        str += '<li class="page-item"><a class="page-link" href="">▷</a></li>';
                    }


                    // 블록 오른쪽 이동
                    if (pagination.curBlock < pagination.totalBlocks) {
                        str += '<li class="page-item"><a class="page-link" onclick="qa_pagination(' + (pagination.endPageNo + 1) + ')">▶</a></li>';
                    } else {
                        str += '<li class="page-item"><a class="page-link" href="">▶</a></li>';
                    }

                    str += '</ul>';
                }

                $('#qa_pagination_div').empty();
                $('#qa_pagination_div').html(str);

            },
            error: function (request, status, error) {
                alert("code = " + request.status + " message = " + request.responseText + " error = " + error);
                console.log("code = " + request.status + " message = " + request.responseText + " error = " + error);
            }
        });
    }

</script>
<%@include file="../section/footer.jsp" %>