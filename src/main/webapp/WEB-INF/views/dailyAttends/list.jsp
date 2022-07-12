<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 2022-07-06
  Time: 오후 3:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@include file="../section/header.jsp"%>
<jsp:useBean id="now" class="java.util.Date" />
<link href="${pageContext.request.contextPath}/template/attend/my/css/bootstrap-datetimepicker.min.css" rel="stylesheet">	<!-- datetimepicker -->
<link href="${pageContext.request.contextPath}/template/attend/my/css/dataTables.bootstrap4.min.css" rel="stylesheet">		<!-- datatable.net -->
<link href="${pageContext.request.contextPath}/template/attend/my/css/daterangepicker.css" rel="stylesheet" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/template/attend/my/flatpickr_datetime/flatpickr.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/template/attend/my/flatpickr_datetime/material_blue.css">
<!------------------------------------------------------------------------------>
<!-- 내용 시작 -->
<!------------------------------------------------------------------------------>
<div class="row">
    <div class="col-xl-12">
        <div class="breadcrumb-holder">
            <h1 class="main-title float-left">${teacher.depart_name}</h1>
            <ol class="breadcrumb float-right">
                <li class="breadcrumb-item">Home</li>
                <li class="breadcrumb-item"><c:if test="${teacher.kind == 0}">교수</c:if><c:if test="${teacher.kind == 1}">겸임교수</c:if><c:if test="${teacher.kind == 2}">시간강사</c:if></li>
                <li class="breadcrumb-item active">일별 출석부</li>
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
                        <h3><i class="fa fa-table"></i> 일별 출석부</h3>
                    </div>
                    <div class="col" align="right">
                        <h3>${teacher.name}</h3>
                    </div>
                </div>
            </div>

            <div class="card-body" style="padding:10px">
                <form name="form1" action="" method="post">
                    <div class="row mymargin5" style="width:100%;">
                        <div class="col" align="left">
                            <div class="form-inline">
                                <div class="input-group input-group-sm">
                                    <div class="input-group-prepend">
                                        <span class="input-group-text">날짜</span>
                                    </div>
                                    <input type="text" id="text1" name="date" value="${date}" size="12" class="flatpickr form-control" style="text-align:center;background-color:white">
                                </div>
                            </div>
                        </div>
                    </div>
                </form>
                <div class="row mymargin5">
                    <fmt:formatDate var="today" value="${now}" pattern="yyyyMMddHHmmss" />
                    <c:forEach var="da" items="${dailyAttendList}">
                        <c:choose>
                            <c:when test="${da.normstate eq 3}">
                                <fmt:parseDate value="${da.restdate} ${8 + da.reststart}:00:00" pattern="yyyy-MM-dd HH:mm:ss" var="aa" />
                                <fmt:formatDate var="lecture_start_time" value="${aa}" pattern="yyyyMMddHHmmss"/>
                                <fmt:parseDate value="${da.restdate} ${7 + da.reststart + da.resthour}:50:00" pattern="yyyy-MM-dd HH:mm:ss" var="bb" />
                                <fmt:formatDate var="lecture_end_time" value="${bb}" pattern="yyyyMMddHHmmss"/>
                            </c:when>
                            <c:otherwise>
                                <fmt:parseDate value="${da.normdate} ${8 + da.normstart}:00:00" pattern="yyyy-MM-dd HH:mm:ss" var="aa" />
                                <fmt:formatDate var="lecture_start_time" value="${aa}" pattern="yyyyMMddHHmmss"/>
                                <fmt:parseDate value="${da.normdate} ${7 + da.normstart + da.normhour}:50:00" pattern="yyyy-MM-dd HH:mm:ss" var="bb" />
                                <fmt:formatDate var="lecture_end_time" value="${bb}" pattern="yyyyMMddHHmmss"/>
                            </c:otherwise>
                        </c:choose>

                        <div class="col-12 col-lg-6" align="left">
                            <div class="card border-dark mb-3" style="max-width: 20rem;">
                                <div class="card-header bg-info text-white"  style="padding:10px">
                                    <h5 class="card-title" style="margin:0px">${da.subjectName} (${da.ban}반)</h5>
                                </div>
                                <div class="card-body" style="padding:10px">
                                    ${da.teacherName}<br>
                                    ${da.departName} : ${da.roomName}<br>
                                    <c:choose>
                                        <c:when test="${da.normstate eq 3}">
                                            ${da.reststart}교시~${da.reststart + da.resthour}교시(${8 + da.reststart}:00 ~ ${7 + da.reststart + da.resthour}:50)<br>
                                        </c:when>
                                        <c:otherwise>
                                            ${da.normstart}교시~${da.normstart + da.normhour}교시(${8 + da.normstart}:00 ~ ${7 + da.normstart + da.normhour}:50)<br>
                                        </c:otherwise>
                                    </c:choose>
                                    수강생 ${da.studentNum}명<br>
                                    <center>
                                        <a href="detail.do?id=${da.id}" class="btn btn-sm btn-primary mymargin5">
                                            <c:if test="${today < lecture_start_time}"> 강의전 </c:if>
                                            <c:if test="${lecture_start_time <= today && today <= lecture_end_time}"> 강의중 </c:if>
                                            <c:if test="${today > lecture_end_time}"> 강의완료 </c:if>
                                        </a>
                                    </center>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>		<!-- card body end -->
        </div>		<!-- card end -->
    </div>
</div>	<!-- row end -->
<!------------------------------------------------------------------------------>
<!-- 내용 끝 -->
<!------------------------------------------------------------------------------>
<%@include file="../section/footer.jsp"%>
<!-- datetimepicker -->
<script src="${pageContext.request.contextPath}/template/attend/my/flatpickr_datetime/flatpickr.js"></script>
<script src="${pageContext.request.contextPath}/template/attend/my/flatpickr_datetime/flatpickr1.js"></script>
<script src="${pageContext.request.contextPath}/template/attend/my/flatpickr_datetime/ko.js"></script>
<script src="${pageContext.request.contextPath}/template/attend/my/js/moment.min.js"></script>
<script src="${pageContext.request.contextPath}/template/attend/my/js/daterangepicker.js"></script>
<script>
flatpickr("#text1", {
    dateFormat: "Y-m-d",
    locale: "ko",
    defaultDate: "${date}",
    onChange: function(selectedDates, dateStr, instance)
    {
        let date = $('#text1').val();
        location.href="list.do?date="+date;
    }
});
</script>