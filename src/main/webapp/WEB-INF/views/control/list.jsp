<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@include file="../section/header.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

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
                                <li class="breadcrumb-item active">행정 제어</li>
                            </ol>
                            <div class="clearfix"></div>
                        </div>
                    </div>
                </div>

                <div class="row">

                    <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 col-xl-12">
                        <div class="card mb-3">
                            <div class="card-header mycolor3" style="padding:10px">
                                <h3><i class="fa fa-table"></i> 행정 제어</h3>
                            </div>

                            <div class="card-body" style="padding:10px">

                                <form name="form1" method="post" action="edit-action.do">
                                    <input type="hidden" name="id" value="${adControl.id}">

                                    <table class="table table-bordered mytable-leftmiddle" style="width:100%;">
                                        <tr>
                                            <td width="180" class="mycolor2">1. 교과목 등록기간</td>
                                            <td>
                                                <div class="form-inline">&nbsp;
                                                    <c:if test="${adControl.subjecttime eq 0}">
                                                        <input type="radio" name="subjecttime" value="1">&nbsp;열기&nbsp;&nbsp;&nbsp;
                                                        <input type="radio" name="subjecttime" value="0" checked>&nbsp;마감
                                                    </c:if>
                                                    <c:if test="${adControl.subjecttime eq 1}">
                                                        <input type="radio" name="subjecttime" value="1" checked>&nbsp;열기&nbsp;&nbsp;&nbsp;
                                                        <input type="radio" name="subjecttime" value="0">&nbsp;마감
                                                    </c:if>
                                                </div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td class="mycolor2">2. 강의/시간표 등록기간</td>
                                            <td>
                                                <div class="form-inline">&nbsp;
                                                    <c:if test="${adControl.lecturetime eq 0}">
                                                        <input type="radio" name="lecturetime" value="1">&nbsp;열기&nbsp;&nbsp;&nbsp;
                                                        <input type="radio" name="lecturetime" value="0" checked>&nbsp;마감
                                                    </c:if>
                                                    <c:if test="${adControl.lecturetime eq 1}">
                                                        <input type="radio" name="lecturetime" value="1" checked>&nbsp;열기&nbsp;&nbsp;&nbsp;
                                                        <input type="radio" name="lecturetime" value="0">&nbsp;마감
                                                    </c:if>
                                                </div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td class="mycolor2">3. 수강신청 기간</td>
                                            <td>
                                                <div class="form-inline">&nbsp;
                                                    <c:if test="${adControl.mylecturetime eq 0}">
                                                        <input type="radio" name="mylecturetime" value="1">&nbsp;열기&nbsp;&nbsp;&nbsp;
                                                        <input type="radio" name="mylecturetime" value="0" checked>&nbsp;마감
                                                    </c:if>
                                                    <c:if test="${adControl.mylecturetime eq 1}">
                                                        <input type="radio" name="mylecturetime" value="1" checked>&nbsp;열기&nbsp;&nbsp;&nbsp;
                                                        <input type="radio" name="mylecturetime" value="0">&nbsp;마감
                                                    </c:if>

                                                </div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td class="mycolor2">4. 전 과목 출석부 만들기</td>
                                            <td>&nbsp;
                                                <input type="button" value="생성하기" class="btn btn-xs mycolor1" onClick="">
                                            </td>
                                        </tr>
                                        <tr>
                                            <td class="mycolor2">5. 출석부 사용기간</td>
                                            <td>
                                                <div class="form-inline">&nbsp;

                                                    <c:if test="${adControl.attendtime eq 0}">
                                                        <input type="radio" name="attendtime" value="1">&nbsp;열기&nbsp;&nbsp;&nbsp;
                                                        <input type="radio" name="attendtime" value="0" checked>&nbsp;마감
                                                    </c:if>
                                                    <c:if test="${adControl.attendtime eq 1}">
                                                        <input type="radio" name="attendtime" value="1" checked>&nbsp;열기&nbsp;&nbsp;&nbsp;
                                                        <input type="radio" name="attendtime" value="0">&nbsp;마감
                                                    </c:if>

                                                </div>
                                            </td>
                                        </tr>
                                    </table>

                                    <div align="center">
                                        <input type="submit" value=" 저장 " class="btn btn-sm mycolor1">&nbsp;
                                    </div>

                                </form>

                            </div>		<!-- card body end -->
                        </div>		<!-- card end -->
                    </div>

                </div>	<!-- row end -->
                <!------------------------------------------------------------------------------>
                <!-- 내용 끝 -->
                <!------------------------------------------------------------------------------>

<%@include file="../section/footer.jsp"%>