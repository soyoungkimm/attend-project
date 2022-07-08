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

                <form name="detail_form" method="post" action="">

                    <table class="table table-bordered mytable-centermiddle" style="width:100%;">
                        <tr>
                            <td class="mycolor2" width="80">전공구분</td>
                            <td>
                                <div class="form-inline">
                                    <c:choose>
                                        <c:when test="${subject.ismajor == 0}">
                                            <input type="radio" name="ismajor" value="0" checked disabled>&nbsp;교양 &nbsp;&nbsp;
                                            <input type="radio" name="ismajor" value="1" disabled>&nbsp;전공
                                        </c:when>
                                        <c:when test="${subject.ismajor == 1}">
                                            <input type="radio" name="ismajor" value="0" disabled>&nbsp;교양 &nbsp;&nbsp;
                                            <input type="radio" name="ismajor" value="1" checked disabled>전공
                                        </c:when>
                                    </c:choose>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="mycolor2">필수/선택</td>
                            <td>
                                <div class="form-inline">
                                    <c:choose>
                                        <c:when test="${subject.ischoice == 0}">
                                            <input type="radio" name="ischoice" value="0" checked disabled>&nbsp;필수 &nbsp;&nbsp;
                                            <input type="radio" name="ischoice" value="1" disabled>&nbsp;선택
                                        </c:when>
                                        <c:when test="${subject.ischoice == 1}">
                                            <input type="radio" name="ischoice" value="0" disabled>&nbsp;필수 &nbsp;
                                            <input type="radio" name="ischoice" value="1" checked disabled> 선택
                                        </c:when>
                                    </c:choose>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="mycolor2">과목구분</td>
                            <td>
                                <div class="form-inline">
                                    <c:choose>
                                        <c:when test="${subject.ispractice == 0}">
                                            <input type="radio" name="ispractice" value="0" checked disabled>&nbsp;이론 &nbsp;&nbsp;
                                            <input type="radio" name="ispractice" value="1" disabled>&nbsp;실습 &nbsp;&nbsp;
                                            <input type="radio" name="ispractice" value="2" disabled>&nbsp;현장실습
                                        </c:when>
                                        <c:when test="${subject.ispractice == 1}">
                                            <input type="radio" name="ispractice" value="0" disabled>&nbsp;이론 &nbsp;&nbsp;
                                            <input type="radio" name="ispractice" value="1" checked disabled>&nbsp;실습 &nbsp;&nbsp;
                                            <input type="radio" name="ispractice" value="2" disabled>&nbsp;현장실습
                                        </c:when>
                                        <c:when test="${subject.ispractice == 2}">
                                            <input type="radio" name="ispractice" value="0" disabled>&nbsp;이론 &nbsp;&nbsp;
                                            <input type="radio" name="ispractice" value="1" disabled>&nbsp;실습 &nbsp;&nbsp;
                                            <input type="radio" name="ispractice" value="2" checked disabled>&nbsp;현장실습
                                        </c:when>
                                    </c:choose>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="mycolor2">과목코드</td>
                            <td>
                                <div class="form-inline">
                                    <input type="text" name="code" value="${subject.code}" size="10" maxlength="10" class="form-control form-control-sm" disabled>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="mycolor2">년도</td>
                            <td>
                                <div class="form-inline">
                                    <input type="text" name="yyyy" value="${subject.yyyy}" size="4" maxlength="4" class="form-control form-control-sm" disabled>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="mycolor2">학년</td>
                            <td>
                                <div class="form-inline">
                                    <select name="grade" class="form-control form-control-sm" disabled>
                                        <c:forEach var="i" begin="1" end="4">
                                            <c:choose>
                                                <c:when test="${subject.grade == i}">
                                                    <option value='${i}' selected>${i}학년</option>
                                                </c:when>
                                                <c:otherwise>
                                                    <option value='${i}'>${i}학년</option>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:forEach>
                                    </select>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="mycolor2">학기</td>
                            <td>
                                <div class="form-inline">
                                    <c:choose>
                                        <c:when test="${subject.term == 1}">
                                            <input type="radio" name="term" value="1" checked disabled>&nbsp;1 학기&nbsp;&nbsp;
                                            <input type="radio" name="term" value="2" disabled>&nbsp;2 학기
                                        </c:when>
                                        <c:when test="${subject.term == 2}">
                                            <input type="radio" name="term" value="1" disabled>&nbsp;1 학기&nbsp;&nbsp;
                                            <input type="radio" name="term" value="2"checked disabled>&nbsp;2 학기
                                        </c:when>
                                    </c:choose>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="mycolor2">과목명</td>
                            <td>
                                <input type="text" name="name" value="${subject.name}" class="form-control form-control-sm" readonly>
                            </td>
                        </tr>
                        <tr>
                            <td class="mycolor2">학점</td>
                            <td>
                                <div class="form-inline">
                                    <select name="point" class="form-control form-control-sm" disabled>
                                        <c:forEach var="i" begin="1" end="5">
                                            <c:choose>
                                                <c:when test="${subject.point == i}">
                                                    <option value='${i}' selected>${i}</option>
                                                </c:when>
                                                <c:otherwise>
                                                    <option value='${i}'>${i}</option>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:forEach>
                                    </select>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="mycolor2">시간</td>
                            <td>
                                <div class="form-inline">
                                    <select name="hour" class="form-control form-control-sm" disabled>
                                        <c:forEach var="i" begin="1" end="5">
                                            <c:choose>
                                                <c:when test="${subject.hour == i}">
                                                    <option value='${i}' selected>${i}</option>
                                                </c:when>
                                                <c:otherwise>
                                                    <option value='${i}'>${i}</option>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:forEach>
                                    </select>
                                </div>
                            </td>
                        </tr>

                    </table>

                    <div align="center">
                        <input type="button" value="목록으로" class="btn btn-sm mycolor1" onclick="location.href='list.do'">
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