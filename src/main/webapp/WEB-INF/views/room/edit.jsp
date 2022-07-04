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
            <h1 class="main-title float-left">교무처</h1>
            <ol class="breadcrumb float-right">
                <li class="breadcrumb-item">Home</li>
                <li class="breadcrumb-item">직원</li>
                <li class="breadcrumb-item active">강의실</li>
            </ol>
            <div class="clearfix"></div>
        </div>
    </div>
</div>

<div class="row">

    <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 col-xl-12">
        <div class="card mb-3">
            <div class="card-header mycolor3" style="padding:10px">
                <h3><i class="fa fa-table"></i> 강의실 입력</h3>
            </div>

            <div class="card-body" style="padding:10px">

                <form name="form1" method="post" action="edit-action.do">

                    <input type="hidden" name="id" value="${room.id}"/>

                    <table class="table table-bordered mytable-centermiddle" style="width:100%;">
                        <tr>
                            <td class="mycolor2" width="80">건물명</td>
                            <td>
                                <div class="form-inline">
                                    <select name="building_id" class="form-control form-control-sm">
                                        <c:forEach var="building" items="${buildingList}">
                                            <c:choose>
                                                <c:when test="${building.id == room.building_id}">
                                                    <option value="${building.id}" selected>${building.name}</option>
                                                </c:when>
                                                <c:otherwise>
                                                    <option value='${building.id}'>${building.name}</option>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:forEach>
                                    </select>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="mycolor2">층</td>
                            <td>
                                <div class="form-inline">
                                    <select name="floor" class="form-control form-control-sm" style="width:70px">
                                        <c:forEach var="i" begin="1" end="5">
                                            <c:choose>
                                                <c:when test="${room.floor == i}">
                                                    <option value="${room.floor}" selected>${room.floor}층</option>
                                                </c:when>
                                                <c:otherwise>
                                                    <option value='${i}'>${i}층</option>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:forEach>
                                    </select>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="mycolor2">호</td>
                            <td>
                                <div class="form-inline">
                                    <input type="text" name="ho" value="${room.ho}" class="form-control form-control-sm" required>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="mycolor2">소속</td>
                            <td>
                                <div class="form-inline">
                                    <select name="depart_id" class="form-control form-control-sm">
                                        <c:forEach var="depart" items="${departList}">
                                            <c:choose>
                                                <c:when test="${depart.id == room.depart_id}">
                                                    <option value="${depart.id}" selected>${depart.name}</option>
                                                </c:when>
                                                <c:otherwise>
                                                    <option value='${depart.id}'>${depart.name}</option>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:forEach>
                                    </select>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="mycolor2">명칭</td>
                            <td>
                                <div class="form-inline">
                                    <input type="text" name="name" value="${room.name}" class="form-control form-control-sm" required>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="mycolor2">구분</td>
                            <td>
                                <div class="form-inline">
                                    <select name="kind" class="form-control form-control-sm">
                                        <c:choose>
                                            <c:when test="${room.kind == 1}">
                                                <option value='1' selected>일반</option>
                                                <option value='2'>실습실</option>
                                                <option value='3'>컴퓨터실</option>
                                            </c:when>
                                            <c:when test="${room.kind == 2}">
                                                <option value='1'>일반</option>
                                                <option value='2'selected>실습실</option>
                                                <option value='3'>컴퓨터실</option>
                                            </c:when>
                                            <c:when test="${room.kind == 3}">
                                                <option value='1'>일반</option>
                                                <option value='2'>실습실</option>
                                                <option value='3'selected>컴퓨터실</option>
                                            </c:when>
                                            <c:otherwise>
                                                <option value='0'></option>
                                                <option value='1'>일반</option>
                                                <option value='2'>실습실</option>
                                                <option value='3'>컴퓨터실</option>
                                            </c:otherwise>
                                        </c:choose>
                                    </select>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="mycolor2">면적(m<sup>2</sup>)</td>
                            <td>
                                <div class="form-inline">
                                    <input type="text" name="area" value="${room.area}" class="form-control form-control-sm" style="width:80px">
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