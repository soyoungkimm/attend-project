<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../section/header.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
                                <li class="breadcrumb-item active">학년별 교과목</li>
                            </ol>
                            <div class="clearfix"></div>
                        </div>
                    </div>
                </div>

                <div class="row">

                    <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 col-xl-12">
                        <div class="card mb-3">
                            <div class="card-header mycolor3" style="padding:10px">
                                <h3><i class="fa fa-table"></i> 학년별 교과목 : 입력</h3>
                            </div>

                            <div class="card-body" style="padding:10px">

                                <form name="create_form" method="post" action="create-action.do">

                                    <table class="table table-bordered mytable-centermiddle" style="width:100%;">
                                        <tr>
                                            <td class="mycolor2" width="80">전공구분</td>
                                            <td>
                                                <div class="form-inline">
                                                    <input type="radio" name="ismajor" value="0" checked>&nbsp;교양 &nbsp;&nbsp;
                                                    <input type="radio" name="ismajor" value="1">&nbsp;전공
                                                </div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td class="mycolor2">필수/선택</td>
                                            <td>
                                                <div class="form-inline">
                                                    <input type="radio" name="ischoice" value="0" checked>&nbsp;필수 &nbsp;&nbsp;
                                                    <input type="radio" name="ischoice" value="1">&nbsp;선택
                                                </div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td class="mycolor2">과목구분</td>
                                            <td>
                                                <div class="form-inline">
                                                    <input type="radio" name="ispractice" value="0" checked>&nbsp;이론 &nbsp;&nbsp;
                                                    <input type="radio" name="ispractice" value="1">&nbsp;실습 &nbsp;&nbsp;
                                                    <input type="radio" name="ispractice" value="2">&nbsp;현장실습
                                                </div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td class="mycolor2">과목코드</td>
                                            <td>
                                                <div class="form-inline">
                                                    <input type="text" name="code" value="" maxlength="10" class="form-control form-control-sm">
                                                </div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td class="mycolor2">년도</td>
                                            <td>
                                                <div class="form-inline">
                                                    <jsp:useBean id="now" class="java.util.Date"/>
                                                    <fmt:formatDate var="year" value="${now}" pattern="yyyy"/>
                                                    <input type="text" name="yyyy" value="${year}" size="4" maxlength="4" class="form-control form-control-sm">
                                                </div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td class="mycolor2">학년</td>
                                            <td>
                                                <div class="form-inline">
                                                    <select name="grade" class="form-control form-control-sm">
                                                        <option value='1' selected>1학년</option>
                                                        <option value='2'>2학년</option>
                                                        <option value='3'>3학년</option>
                                                        <option value='4'>4학년</option>
                                                    </select>
                                                </div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td class="mycolor2">학기</td>
                                            <td>
                                                <div class="form-inline">
                                                    <input type="radio" name="term" value="1" checked>&nbsp;1 학기&nbsp;&nbsp;
                                                    <input type="radio" name="term" value="2">&nbsp;2 학기
                                                </div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td class="mycolor2">과목명</td>
                                            <td>
                                                <input type="text" name="name" value="" class="form-control form-control-sm">
                                            </td>
                                        </tr>
                                        <tr>
                                            <td class="mycolor2">학점</td>
                                            <td>
                                                <div class="form-inline">
                                                    <select name="point" class="form-control form-control-sm">
                                                        <option value='1' selected>1</option>
                                                        <option value='2'>2</option>
                                                        <option value='3'>3</option>
                                                        <option value='4'>4</option>
                                                        <option value='5'>5</option>
                                                    </select>
                                                </div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td class="mycolor2">시간</td>
                                            <td>
                                                <div class="form-inline">
                                                    <select name="hour" class="form-control form-control-sm">
                                                        <option value='1' selected>1</option>
                                                        <option value='2'>2</option>
                                                        <option value='3'>3</option>
                                                        <option value='4'>4</option>
                                                        <option value='5'>5</option>
                                                    </select>
                                                </div>
                                            </td>
                                        </tr>

                                    </table>

                                    <div align="center">
                                        <input type="submit" value="저장" class="btn btn-sm mycolor1">&nbsp;
                                        <input type="button" value="이전화면" class="btn btn-sm mycolor1" onclick="history.back();">
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