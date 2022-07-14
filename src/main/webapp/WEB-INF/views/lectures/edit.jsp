<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../section/header.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<script>

</script>
    <!------------------------------------------------------------------------------>
    <!-- 내용 시작 -->
    <!------------------------------------------------------------------------------>
    <div class="row">
        <div class="col-xl-12">
            <div class="breadcrumb-holder">
                <h1 class="main-title float-left">교무처</h1>
                <ol class="breadcrumb float-right">
                    <li class="breadcrumb-item">Home</li>
                    <li class="breadcrumb-item">조교</li>
                    <li class="breadcrumb-item active">반별 교과목</li>
                </ol>
                <div class="clearfix"></div>
            </div>
        </div>
    </div>

    <div class="row">

        <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 col-xl-12">
            <div class="card mb-3">
                <div class="card-header mycolor3" style="padding:10px">
                    <h3><i class="fa fa-table"></i> 반별 교과목 수정</h3>
                </div>

                <div class="card-body" style="padding:10px">
                    <form name="lecture_create_form" method="post" action="create-action.do">
                        <table class="table table-bordered mytable-centermiddle" style="width:100%;">
                            <inpput type="hidden" name="id" value="${lectureDTO.id}">
                            <tr>
                                <td class="mycolor2">과목 </td>
                                <%-- 임시selector, JS--%>
                                <td>
                                    <div class="form-inline">
                                        <select name="subject" id= "aa" class="form-control form-control-sm">
                                            <c:forEach var="subjectList" items="${subjectList}">
                                                <c:choose>
                                                    <c:when test="${lectureDTO.teacher_id == subjectList.id}">
                                                        <option value='${subjectList.id}' selected>${subjectList.name}</option>
                                                    </c:when>

                                                    <c:otherwise>
                                                        <option value='${subjectList.id}'>${subjectList.name}</option>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </td>
                            </tr>
                            <tr>

                                <td class="mycolor2">담당교수</td>
                                <td>
                                    <div class="form-inline">
                                        <select name="teacher" id="teacher_sel" class="form-control form-control-sm">
                                            <c:forEach var="teacherList" items="${teacherList}">
                                                <c:choose>
                                                    <c:when test="${lectureDTO.teacher_id == teacherList.id}">
                                                        <option value='${teacherList.id}' selected>${teacherList.name}</option>
                                                    </c:when>

                                                    <c:otherwise>
                                                        <option value='${teacherList.id}'>${teacherList.name}</option>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td class="mycolor2">반</td>
                                <td>
                                    <div class="form-inline">
                                        <input type="text" name="class" value="${lectureDTO.classs}" class="form-control form-control-sm" required>
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
        <!------------------------------------------------------------------------------>
        <!-- 내용 끝 -->
        <!------------------------------------------------------------------------------>

</div>	<!-- row end -->
<%@include file="../section/footer.jsp"%>