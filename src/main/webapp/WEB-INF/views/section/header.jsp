<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 2022-07-01
  Time: 오후 4:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!-------------------------------------------------------------------------------->
<!-- 프로그램 : 인덕대학교 컴퓨터소프트웨어학과 전자출석 Demo                              -->
<!--                                                                                                                  -->
<!-- 소속 : 인덕대학교  컴퓨터소프트웨어학과  창업동아리 겜지기                              -->
<!-- 교수 : 윤형태 ( 2019.05 - 2022.06 )                                                                  -->
<!-------------------------------------------------------------------------------->

<!DOCTYPE html>
<html lang="kr">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>인덕대학교 전자출석 Demo (겜지기)</title>

    <link rel="shortcut icon" href="${pageContext.request.contextPath}/template/attend/my/images/favicon.ico">

    <!-- css 선언부 ---------------------------------------------------------------->
    <link href="${pageContext.request.contextPath}/template/attend/my/css/bootstrap.min.css" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/template/attend/my/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/template/attend/my/css/style.css" rel="stylesheet" type="text/css" />

    <link href="${pageContext.request.contextPath}/template/attend/my/css/dataTables.bootstrap4.min.css" rel="stylesheet">	<!-- datatable.net -->

    <link href="${pageContext.request.contextPath}/template/attend/my/css/my.css" rel="stylesheet" type="text/css">
</head>

<body class="adminbody" >
<%--onLoad="load_lec();"--%>

<div id="main">

    <!--상단 메뉴 시작 -->
    <div class="headerbar">

        <div class="headerbar-left">
            <a href="index.html" class="logo"><img src="${pageContext.request.contextPath}/template/attend/my/images/induk_logo.png"> <span>전자출석 Demo</span></a>
        </div>

        <nav class="navbar-custom">
            <ul class="list-inline float-right mb-0">
                <li class="list-inline-item dropdown notif">
                    <a class="nav-link dropdown-toggle nav-user" data-toggle="dropdown" href="#" role="button" aria-haspopup="false" aria-expanded="false">

                        <c:choose>
                            <c:when test="${logined.kind eq 0}">
                                <img src="${pageContext.request.contextPath}/template/attend/my/images/avatars/admin.png" alt="Profile image" class="avatar-rounded">

                            </c:when>
                            <c:otherwise>
                                <img src="${pageContext.request.contextPath}${logined.picPath}${logined.pic}" alt="Profile image" class="avatar-rounded">

                            </c:otherwise>
                        </c:choose>
                    </a>
                    <div class="dropdown-menu dropdown-menu-right profile-dropdown">
                        <div class="dropdown-item noti-title">
                            <h5 class="text-overflow"><small>Hello,
                                <c:choose>
                                    <c:when test="${logined.kind eq 0}">
                                        admin
                                    </c:when>
                                    <c:otherwise>
                                        ${logined.name}
                                    </c:otherwise>
                                </c:choose>
                                </small> </h5>
                        </div>
                        <a href="../login/logout.do" class="dropdown-item notify-item">
                            <i class="fa fa-power-off"></i> <span>Logout</span>
                        </a>
                    </div>
                </li>
            </ul>

            <ul class="list-inline menu-left mb-0">
                <li class="float-left">
                    <button class="button-menu-mobile open-left">
                        <i class="fa fa-fw fa-bars"></i>
                    </button>
                </li>
            </ul>
        </nav>

    </div>
    <!--상단 메뉴 끝 -->


    <!-- 좌측 Sidebar 메뉴 시작-->
    <div class="left main-sidebar">
        <div class="sidebar-inner leftscroll">
            <div id="sidebar-menu">
                <ul>

                    <li class="submenu">
                        <a href="#" class="active"><i class="fa fa-fw fa-table"></i> <span> 직원(학사행정) </span> <span class="menu-arrow"></span></a>
                        <ul class="list-unstyled">
                            <li><a href="ad_main.html" style="padding:5px 0 5px 40px;">직원 메인</a></li>
                            <li><a href="ad_control.html" style="padding:5px 0 5px 40px;">제어판</a></li>
                            <li class="active"><a href="${pageContext.request.contextPath}/notice/list.do" style="padding:5px 0 5px 40px;">공지사항</a></li>
                            <li><hr style="background-color:gray;margin:0 25px 0 25px;"></li>
                            <li><a href="${pageContext.request.contextPath}/student/list.do" style="padding:5px 0 5px 40px;">학생정보</a></li>
                            <li><a href="ad_teacher.html" style="padding:5px 0 5px 40px;">교수정보</a></li>
                            <li><a href="ad_assist.html" style="padding:5px 0 5px 40px;">조교정보</a></li>
                            <li><hr style="background-color:gray;margin:0 25px 0 25px;"></li>
                            <li><a href="ad_depart.html" style="padding:5px 0 5px 40px;">학과/부서</a></li>
                            <li><a href="/room/list.do" style="padding:5px 0 5px 40px;">강의실</a></li>
                            <li><a href="/building/list.do " style="padding:5px 0 5px 40px;">건물</a></li>
                            <li><a href="/holidays/list.do" style="padding:5px 0 5px 40px;">휴일</a></li>
                        </ul>
                    </li>
                    <li class="submenu">
                        <a href="#"><i class="fa fa-fw fa-table"></i> <span> 직원(전자출석) </span> <span class="menu-arrow"></span></a>
                        <ul class="list-unstyled">
                            <li><a href="../adTimes/timeAll.do" style="padding:5px 0 5px 40px;">학과별 시간표</a></li>
                            <li><a href="ad_timeteacher.html" style="padding:5px 0 5px 40px;">교수별 강의현황</a></li>
                            <li><a href="/lecMove/list.do" style="padding:5px 0 5px 40px;">휴보강</a></li>
                        </ul>
                    </li>
                    <li class="submenu">
                        <a href="#"><i class="fa fa-fw fa-male"></i> <span>조교</span> <span class="menu-arrow"></span></a>
                        <ul class="list-unstyled">
                            <li><a href="as_main.html" style="padding:5px 0 5px 40px;">조교 메인</a></li>
                            <li><a href="/subject/list.do" style="padding:5px 0 5px 40px;">학년별 교과목</a></li>
                            <li><a href="as_lec.html" style="padding:5px 0 5px 40px;">반별 교과목</a></li>
                            <li><a href="as_time.html" style="padding:5px 0 5px 40px;">시간표 작성</a></li>
                            <li><a href="as_timeall.html" style="padding:5px 0 5px 40px;">학과별 시간표</a></li>
                            <li><a href="as_lecall.html" style="padding:5px 0 5px 40px;">과목별 출석부</a></li>
                            <li><a href="as_lecmove.html" style="padding:5px 0 5px 40px;">휴보강</a></li>
                        </ul>
                    </li>
                    <li class="submenu">
                        <a href="#"><i class="fa fa-fw fa-user"></i> <span> 교수 </span> <span class="menu-arrow"></span></a>
                        <ul class="list-unstyled">
                            <li><a href="te_main.html" style="padding:5px 0 5px 40px;">교수 메인</a></li>
                            <li><a href="/time/te_time.do" style="padding:5px 0 5px 40px;">시간표</a></li>
                            <li><a href="${pageContext.request.contextPath}/daily-attend/list.do" style="padding:5px 0 5px 40px;">일별 출석부</a></li>
                            <li><a href="${pageContext.request.contextPath}/subject-attend/list.do" style="padding:5px 0 5px 40px;">과목별 출석부</a></li>
                            <li><a href="te_lecmove.html" style="padding:5px 0 5px 40px;">휴보강</a></li>
                            <li><a href="/lecQa/te_list.do" style="padding:5px 0 5px 40px;">교과목 문의</a></li>
                        </ul>
                    </li>
                    <li class="submenu">
                        <a href="#"><i class="fa fa-fw fa-table"></i> <span> 학생 </span> <span class="menu-arrow"></span></a>
                        <ul class="list-unstyled">
                            <li><a href="/main/st.do" style="padding:5px 0 5px 40px;">학생 메인</a></li>
                            <li><a href="st_time.html" style="padding:5px 0 5px 40px;">시간표</a></li>
                            <li><a href="st_lecall.html" style="padding:5px 0 5px 40px;">출석부</a></li>
                            <li><hr style="background-color:gray;margin:0 25px 0 25px;"></li>
                            <li><a href="st_lec.html" style="padding:5px 0 5px 40px;">수강신청</a></li>
                            <li><a href="st_lecsj.html" style="padding:5px 0 5px 40px;">수강과목</a></li>
                            <li><a href="st_lecqa.html" style="padding:5px 0 5px 40px;">교과목 문의</a></li>
                        </ul>
                    </li>

                </ul>
                <div class="clearfix"></div>
            </div>
            <div class="clearfix"></div>
        </div>
    </div>
    <!-- 좌측 Sidebar 메뉴 끝-->
    <div class="content-page">
        <div class="content">
            <div class="container-fluid">