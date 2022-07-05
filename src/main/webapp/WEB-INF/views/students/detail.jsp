<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 2022-07-02
  Time: 오후 10:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../section/header.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

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
                <li class="breadcrumb-item active">학생정보</li>
            </ol>
            <div class="clearfix"></div>
        </div>
    </div>
</div>

<div class="row">

    <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 col-xl-12">
        <div class="card mb-3">
            <div class="card-header mycolor3" style="padding:10px">
                <h3><i class="fa fa-table"></i> 학생정보 상세</h3>
            </div>
            <div class="card-body" style="padding:10px">
                <table class="table table-bordered mytable-centermiddle" style="width:100%;">
                    <tr>
                        <td class="mycolor2" style="vertical-align:middle;width:60px">상태</td>
                        <td>
                            <div class="form-inline">
                                <input type="text" class="form-control form-control-sm" value="<c:if test="${student.state eq 0}">재학</c:if><c:if test="${student.state eq 1}">휴학</c:if><c:if test="${student.state eq 2}">자퇴</c:if>" readonly>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="mycolor2">학과</td>
                        <td>
                            <div class="form-inline">
                                <input type="text" class="form-control form-control-sm" value="${student.depart_name}" readonly>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="mycolor2">학년/반</td>
                        <td>
                            <div class="form-inline">
                                <input type="text" class="form-control form-control-sm" value="${student.grade}학년 ${student.ban}반" readonly>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="mycolor2">학번</td>
                        <td>
                            <div class="form-inline">
                                <input type="text" name="schoolno" size="9" value="${student.schoolno}" class="form-control form-control-sm" readonly>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="mycolor2">이름</td>
                        <td>
                            <div class="form-inline">
                                <input type="text" name="name" value="${student.name}" class="form-control form-control-sm" readonly>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="mycolor2">핸드폰</td>
                        <td>
                            <div class="form-inline">
                                <input type="text" name="phone" value="${fn:substring(student.phone,0,3)}-${fn:substring(student.phone,3,7)}-${fn:substring(student.phone,7,11)}" class="form-control form-control-sm" readonly/>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="mycolor2">성별</td>
                        <td>
                            <div class="form-inline">
                                <input type="text" name="name" value="${student.gender == 0 ? "남자" : "여자"}" class="form-control form-control-sm" readonly>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="mycolor2">E-Mail</td>
                        <td>
                            <div class="form-inline">
                                <input type="text" name="email" value="${student.email}" class="form-control form-control-sm" readonly>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="mycolor2">암호</td>
                        <td>
                            <div class="form-inline">
                                <input type="text" name="pwd" value="${student.pwd}" class="form-control form-control-sm" readonly>
                            </div>
                        </td>
                    </tr>

                    <tr>
                        <td class="mycolor2">사진</td>
                        <td style="text-align:left">
                            <div class="form-inline mymargin5">
                                <c:choose>
                                    <c:when test="${student.pic eq null}">
                                        없음
                                    </c:when>
                                    <c:otherwise>
                                        <img src="${pageContext.request.contextPath}/image/student/${student.pic}" alt="학생 이미지" class="img-thumbnail" width="120" border="1"/>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </td>
                    </tr>
                </table>
                <div align="center">
                    <input type="button" value="목록으로" class="btn btn-sm mycolor1" onclick="location.href='list.do'">
                </div>
            </div>		<!-- card body end -->
        </div>		<!-- card end -->
    </div>

</div>	<!-- row end -->
<!------------------------------------------------------------------------------>
<!-- 내용 끝 -->
<!------------------------------------------------------------------------------>

<%@include file="../section/footer.jsp"%>
