<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 2022-07-07
  Time: 오전 1:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@include file="../section/header.jsp"%>

<!------------------------------------------------------------------------------>
<!-- 내용 시작 -->
<!------------------------------------------------------------------------------>
<div class="row">
    <div class="col-xl-12">
        <div class="breadcrumb-holder">
            <h1 class="main-title float-left">${lecture.departName}</h1>
            <ol class="breadcrumb float-right">
                <li class="breadcrumb-item">Home</li>
                <li class="breadcrumb-item">교수</li>
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
                        <h3>${lecture.subjectName} (${lecture.ban}반)</h3>
                    </div>
                    <div class="col" align="right">
                        <h3>${lecture.teacherName}</h3>
                    </div>
                </div>
            </div>
            <div class="card-body" style="padding:10px 5px 0px 5px">
                <form name="update_form" method="post" action="">
                    <div class="row">
                        <div class="col-auto" align="left" style="margin:0px 0px 3px 8px">
                            <fmt:parseDate value="${lecture.normstate == 3 ? lecture.restdate : lecture.normdate}" pattern="yyyy-mm-dd" var="temp"/>
                            <fmt:formatDate value="${temp}" pattern="yyyy-mm-dd (E)" var="lecDay"/>
                            <i class='fa fa-calendar-o fa-1x'></i> ${lecDay} <br>
                            <c:choose>
                                <c:when test="${lecture.normstate == 3}">
                                    <i class='fa fa-clock-o fa-1x'></i> ${lecture.reststart}교시~${lecture.reststart + lecture.resthour}교시 (${8 + lecture.reststart}:00 ~ ${7 + lecture.reststart + lecture.resthour}:50)<br>
                                </c:when>
                                <c:otherwise>
                                    <i class='fa fa-clock-o fa-1x'></i> ${lecture.normstart}교시~${lecture.normstart + lecture.normhour}교시 (${8 + lecture.normstart}:00 ~ ${7 + lecture.normstart + lecture.normhour}:50)<br>
                                </c:otherwise>
                            </c:choose>

                            <i class='fa fa-building-o fa-1x'></i> ${lecture.buildingName} ${lecture.roomName}<br>
                        </div>
                        <div class="col" align="left" style="margin:0px 0px 3px 8px">
                            <i class="fa fa-circle-o fa-1x"></i> 출석 : <span id="appear_span">${appear}</span><br>
                            <i class="text-primary fa fa-times-circle-o fa-1x"></i> 지각 : <span id="late_span">${late}</span><br>
                            <i class="text-danger fa fa-close fa-1x"></i> 결석 : <span id="absent_span">${absent}</span>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col" align="right" style="margin:10px 0px 3px 0px">
<%--                            <a href="" class="btn btn-xs btn-primary">출석시작->완료</a>--%>
                            <a href="update-all-attend.do?lectureday_id=${lecture.id}" class="btn btn-xs btn-primary">전체출석</a>
                            <a href="list.do" class="btn btn-xs btn-primary">목록</a><br>
                        </div>
                    </div>
                    <table class="table table-bordered mytable-centermiddle" style="width:100%;">
                        <tr class="mycolor1">
                            <td style="vertical-align:middle">학생정보</td>
                            <td style="line-height:1.0rem">1<br><font size="1">&nbsp;&nbsp;9:0~</font>&nbsp;</td>
                            <td style="line-height:1.0rem">2<br><font size="1">&nbsp;10:0~</font>&nbsp;</td>
                            <td style="line-height:1.0rem">3<br><font size="1">&nbsp;11:0~</font>&nbsp;</td>
                            <td style="line-height:1.0rem">4<br><font size="1">&nbsp;12:0~</font>&nbsp;</td>
                        </tr>
                        <c:forEach var="student" items="${studentList}" varStatus="status">
                            <tr>
                                <td style="padding:0px;">
                                    <table>
                                        <tr>
                                            <td align="left" style="border:0;padding-bottom:0px" rowspan="2">
                                                <c:choose>
                                                    <c:when test="${student.pic eq null}">
                                                        <img class="img-fluid mx-auto" src="${pageContext.request.contextPath}/image/student/default.JPG" width="45" height="60" border="1">
                                                    </c:when>
                                                    <c:otherwise>
                                                        <img class="img-fluid mx-auto" src="${pageContext.request.contextPath}/image/student/${student.pic}" width="45" height="60" border="1">
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td align="left" style="border:0;padding:5px 0px 0px 0px;">
                                                <font size="3"><b>${student.name}</b></font>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td align="left" style="border:0;padding:0px;line-height:1.0rem">
                                                <font size="1">${student.schoolno}<br><c:if test="${student.state == 0}">재학</c:if><c:if test="${student.state == 1}">휴학</c:if><c:if test="${student.state == 2}">자퇴</c:if></font>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td align="left" style="border:0;vertical-align:top;padding:0px 5px 0px 5px" colspan="2">
                                                <font size="1">${student.depart_name}</font>
                                            </td>
                                        </tr>
                                    </table>
                                </td>

                                <%-- 1교시--%>
                                <td id="${status.count}^1" style="line-height:1.3rem;">
                                    <c:choose>
                                        <c:when test="${student.attendState1 ne null}">
                                            <c:choose>
                                                <c:when test="${student.attendState1 eq 0}">
                                                    <i class="fa fa-circle-o fa-2x" onclick="choose1(${status.count},1,${lecture.lectureId},${student.id});" style="cursor:pointer"></i>
                                                </c:when>
                                                <c:when test="${student.attendState1 eq 1}">
                                                    <i class="text-primary fa fa-times-circle-o fa-2x" onclick="choose1(${status.count},1,${lecture.lectureId},${student.id});" style="cursor:pointer"></i>
                                                </c:when>
                                                <c:otherwise>
                                                    <i class="text-danger fa fa-close fa-2x" onclick="choose1(${status.count},1,${lecture.lectureId},${student.id});" style="cursor:pointer"></i>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:when>
                                        <c:otherwise>
                                            <i class="fa fa-question fa-2x" onclick="choose1(${status.count},1,${lecture.lectureId},${student.id});" style="cursor:pointer"></i>
                                        </c:otherwise>
                                    </c:choose>
                                </td>

                                <%-- 2교시--%>
                                <td id="${status.count}^2" style="line-height:1.3rem; <c:if test="${lecture.normstate eq 3 ? lecture.resthour < 2 : lecture.normhour < 2}">background: #e7f3ff</c:if>">
                                    <c:if test="${!(lecture.normstate eq 3 ? lecture.resthour < 2 : lecture.normhour < 2)}">
                                        <c:choose>
                                            <c:when test="${student.attendState2 ne null}">
                                                <c:choose>
                                                    <c:when test="${student.attendState2 eq 0}">
                                                        <i class="fa fa-circle-o fa-2x" onclick="choose1(${status.count},2,${lecture.lectureId},${student.id});" style="cursor:pointer"></i>
                                                    </c:when>
                                                    <c:when test="${student.attendState2 eq 1}">
                                                        <i class="text-primary fa fa-times-circle-o fa-2x" onclick="choose1(${status.count},2,${lecture.lectureId},${student.id});" style="cursor:pointer"></i>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <i class="text-danger fa fa-close fa-2x" onclick="choose1(${status.count},2,${lecture.lectureId},${student.id});" style="cursor:pointer"></i>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:when>
                                            <c:otherwise>
                                                <i class="fa fa-question fa-2x" onclick="choose1(${status.count},2,${lecture.lectureId},${student.id});" style="cursor:pointer"></i>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:if>
                                </td>

                                <%-- 3교시--%>
                                <td id="${status.count}^3" style="line-height:1.3rem; <c:if test="${lecture.normstate eq 3 ? lecture.resthour < 3 : lecture.normhour < 3}">background: #e7f3ff</c:if>">
                                    <c:if test="${!(lecture.normstate eq 3 ? lecture.resthour < 3 : lecture.normhour < 3)}">
                                        <c:choose>
                                            <c:when test="${student.attendState3 ne null}">
                                                <c:choose>
                                                    <c:when test="${student.attendState3 eq 0}">
                                                        <i class="fa fa-circle-o fa-2x" onclick="choose1(${status.count},3,${lecture.lectureId},${student.id});" style="cursor:pointer"></i>
                                                    </c:when>
                                                    <c:when test="${student.attendState3 eq 1}">
                                                        <i class="text-primary fa fa-times-circle-o fa-2x" onclick="choose1(${status.count},3,${lecture.lectureId},${student.id});" style="cursor:pointer"></i>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <i class="text-danger fa fa-close fa-2x" onclick="choose1(${status.count},3,${lecture.lectureId},${student.id});" style="cursor:pointer"></i>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:when>
                                            <c:otherwise>
                                                <i class="fa fa-question fa-2x" onclick="choose1(${status.count},3,${lecture.lectureId},${student.id});" style="cursor:pointer"></i>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:if>
                                </td>

                                <%-- 4교시--%>
                                <td id="${status.count}^4" style="line-height:1.3rem; <c:if test="${lecture.normstate eq 3 ? lecture.resthour < 4 : lecture.normhour < 4}">background: #e7f3ff</c:if>">
                                    <c:if test="${!(lecture.normstate eq 3 ? lecture.resthour < 4 : lecture.normhour < 4)}">
                                        <c:choose>
                                            <c:when test="${student.attendState4 ne null}">
                                                <c:choose>
                                                    <c:when test="${student.attendState4 eq 0}">
                                                        <i class="fa fa-circle-o fa-2x" onclick="choose1(${status.count},4,${lecture.lectureId},${student.id});" style="cursor:pointer"></i>
                                                    </c:when>
                                                    <c:when test="${student.attendState4 eq 1}">
                                                        <i class="text-primary fa fa-times-circle-o fa-2x" onclick="choose1(${status.count},4,${lecture.lectureId},${student.id});" style="cursor:pointer"></i>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <i class="text-danger fa fa-close fa-2x" onclick="choose1(${status.count},4,${lecture.lectureId},${student.id});" style="cursor:pointer"></i>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:when>
                                            <c:otherwise>
                                                <i class="fa fa-question fa-2x" onclick="choose1(${status.count},4,${lecture.lectureId},${student.id});" style="cursor:pointer"></i>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:if>
                                </td>
                           </tr>
                        </c:forEach>
                    </table>
                </form>
            </div>		<!-- card body end -->
        </div>		<!-- card end -->
    </div>
</div>	<!-- row end -->
<!------------------------------------------------------------------------------>
<!-- 내용 끝 -->
<!------------------------------------------------------------------------------>
<script>
    function choose1(rowno, colno, lecture_id, student_id)
    {
        pos1=rowno+"^"+colno;
        pos2=rowno+","+colno;
        document.getElementById( pos1 ).innerHTML="<div style='font-size:10px;'><i class='fa fa-circle-o fa-2x' onclick='choose3("+pos2+",0," + lecture_id + "," + student_id + ");' style='cursor:pointer'></i><br><i class='text-primary fa fa-times-circle-o fa-2x' onclick='choose3("+pos2+",1," + lecture_id + "," + student_id + ");' style='cursor:pointer'></i><br><i class='text-danger fa fa-close fa-2x' onclick='choose3("+pos2+",2," + lecture_id + "," + student_id + ");' style='cursor:pointer'></font>";
    }
    function choose3(rowno, colno, v, lecture_id, student_id)
    {
        let h = ${lecture.starth - 1} + colno;
        $.ajax({
            url: 'ajax-update-attend.do',
            type: "POST",
            dataType: 'json',
            data: {
                lecture_id : lecture_id,
                lectureday_id : ${lecture.id},
                student_id : student_id,
                h : h,
                v : v
            },
            success : function(data) {
                pos1=rowno+"^"+colno;
                pos2=rowno+","+colno+","+lecture_id+","+student_id;

                if (v==0) s="<i class='fa fa-circle-o fa-2x' onclick='choose1("+pos2+");' style='cursor:pointer'></i>";
                else if (v==1) s="<i class='text-primary fa fa-times-circle-o fa-2x' onclick='choose1("+pos2+");' style='cursor:pointer'></i>";
                else s="<i class='text-danger fa fa-close fa-2x' onclick='choose1("+pos2+");' style='cursor:pointer'></i>";
                document.getElementById( pos1 ).innerHTML=s;

                document.getElementById("appear_span").innerHTML = data.appear;
                document.getElementById("late_span").innerHTML = data.late;
                document.getElementById("absent_span").innerHTML = data.absent;
            },
            error: function(request,status,error){
                alert("code = "+ request.status + " message = " + request.responseText + " error = " + error);
                console.log("code = "+ request.status + " message = " + request.responseText + " error = " + error);
            }
        });
    }
</script>
<%@include file="../section/footer.jsp"%>
