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
                    <h3><i class="fa fa-table"></i> 반별 교과목 상세</h3>
                </div>

                <div class="card-body" style="padding:10px">
                    <form name="lecture_create_form" method="post" action="create-action.do">
                        <table class="table table-bordered mytable-centermiddle" style="width:100%;">
                            <tr>
                                <td class="mycolor2">과목 </td>
                                <%-- 임시selector, JS--%>
                                <td>
                                    <div class="form-inline">
                                        <input type="text" name="subject" value="${lectureDTO.name}" class="form-control form-control-sm" readonly>
                                    </div>
                                </td>
                            </tr>
                            <tr>

                                <td class="mycolor2">담당교수</td>
                                <td>
                                    <div class="form-inline">
                                        <input type="text" name="teacher" value="${lectureDTO.teacher_name}" class="form-control form-control-sm" readonly>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td class="mycolor2">반</td>
                                <td>
                                    <div class="form-inline">
                                        <input type="text" name="class" value="${lectureDTO.classs}" class="form-control form-control-sm" readonly>
                                    </div>
                                </td>
                            </tr>
                        </table>
                        <div align="center">
                            <input type="button" value="목록으로" class="btn btn-sm mycolor1" onclick="location.href='list.do'">
                            <a href="../lectures/edit.do?id=${lectureDTO.id}" class="btn btn-sm btn-success">과목 수정</a>
                            <a href="../lectures/delete.do?id=${lectureDTO.id}" class="btn btn-sm btn-danger">과목 삭제</a>
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