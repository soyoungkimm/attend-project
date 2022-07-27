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
                                <li class="breadcrumb-item">교수</li>
                                <li class="breadcrumb-item active">교과목 문의</li>
                            </ol>
                            <div class="clearfix"></div>
                        </div>
                    </div>
                </div>

                <div class="row">

                    <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 col-xl-12">
                        <div class="card mb-3">
                            <div class="card-header mycolor3" style="padding:10px">
                                <h3><i class="fa fa-table"></i> 교과목 문의</h3>
                            </div>

                            <div class="card-body" style="padding:10px">

                                <form name="edit_form" method="get" action="te_edit-action.do">

                                    <input type="hidden" name="id" value="${mylecture.id}"/>

                                    <table class="table table-bordered mytable-centermiddle" style="width:100%;">
                                        <tr>
                                            <td class="mycolor2" style="vertical-align:middle">교과목</td>
                                            <td align="left">${mylecture.subject_name}</td>
                                        </tr>
                                        <tr>
                                            <td class="mycolor2" style="vertical-align:middle">교수님</td>
                                            <td align="left">${mylecture.teacher_name}</td>
                                        </tr>
                                        <tr>
                                            <td class="mycolor2" style="vertical-align:middle">학생</td>
                                            <td align="left">${mylecture.depart_name}<br> ${mylecture.grade}학년 ${mylecture.class_name}반 ${mylecture.student_schoolno} ${mylecture.student_name}<br><i class="fa fa-phone fa-x2"></i> ${mylecture.student_phone}</td>
                                        </tr>
                                    </table>

                                    <table class="table table-bordered mytable-centermiddle" style="width:100%;">
                                        <tr>
                                            <td class="mycolor2" style="vertical-align:middle">날짜</td>
                                            <td align="left">
                                                <div class="form-inline">
                                                    <input type="text" name="qawriteday" size="10" value="${mylecture.qday}" class="form-control form-control-sm" readonly>
                                                </div>
                                            </td>
                                            <td class="mycolor2" style="vertical-align:middle">상태</td>
                                            <c:choose>
                                                <c:when test="${mylecture.qkind == 1}">
                                                    <td align="left" style="text-align:center;vertical-align:middle"><font color="red"><b>문의</b></font></td>
                                                </c:when>
                                                <c:when test="${mylecture.qkind == 2}">
                                                    <td align="left" style="text-align:center;vertical-align:middle">답변</td>
                                                </c:when>
                                                <c:otherwise>
                                                    <td align="left" style="text-align:center;vertical-align:middle"></td>
                                                </c:otherwise>
                                            </c:choose>
                                        </tr>
                                        <tr>
                                            <td class="mycolor2" style="vertical-align:middle">제목</td>
                                            <td align="left" colspan="3">
                                                <input type="text" name="qatitle" value="${mylecture.qtitle}" class="form-control form-control-sm" readonly>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td class="mycolor2" style="vertical-align:middle">질문</td>
                                            <td align="left" colspan="3">
                                                <textarea name="qatxt1" rows="5" class="form-control form-control-sm" readonly>${mylecture.qask}</textarea>
                                            </td>
                                        </tr>

                                        <tr>
                                            <td class="mycolor2" style="vertical-align:middle">답변</td>
                                            <td align="left" colspan="3">
                                                <textarea name="qatxt2" rows="5" class="form-control form-control-sm">${mylecture.qanswer}</textarea>
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
<%@ include file="../section/footer.jsp" %>