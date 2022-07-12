<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../section/header.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<script>
    //  function setTeacher(){
    //     //임시, 새창에서 depart_id 값을 받아왔을 때 실행
    //     //$(document).on('click','#aa',function(){
    //         let depart_id= 1;
    //         //document.getElementById('inputStoreName').value;
    //         $.ajax({
    //             url: '../lectures/getTeacherOptions.do',
    //             type: "POST",
    //             dataType: 'text',
    //             data: {
    //                 depart_id: depart_id
    //             },
    //             success: function (data) {
    //                 let options;
    //                 //alert("data: "+data);
    //
    //                 $('#teacher_sel').empty();
    //                 $('#teacher_sel').html(data);
    //             },
    //             error: function (request, status, error) {
    //                 alert("교수 목록을 불러올 수 없습니다");
    //             }
    //         });
    //    // });
    // }

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
                    <h3><i class="fa fa-table"></i> 반별 교과목 입력</h3>
                </div>

                <div class="card-body" style="padding:10px">
                    <form name="lecture_create_form" method="post" action="create-action.do">
                        <table class="table table-bordered mytable-centermiddle" style="width:100%;">
                            <tr>
                                <td class="mycolor2">과목 </td>
                                <%-- 임시selector, JS--%>
                                <td>
                                    <div class="form-inline">
                                        <select name="subject" id= "aa" class="form-control form-control-sm" onchange="setTeacher();">
                                            <c:forEach var="subjectList" items="${subjectList}">
                                                <option value='${subjectList.id}'>${subjectList.name}</option>
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
                                                 <option value='${teacherList.id}'>${teacherList.name}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td class="mycolor2">반</td>
                                <td>
                                    <div class="form-inline">
                                        <input type="text" name="class" value="" class="form-control form-control-sm" required>
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