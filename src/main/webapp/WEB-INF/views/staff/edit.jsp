
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
                <li class="breadcrumb-item active">조교정보</li>
            </ol>
            <div class="clearfix"></div>
        </div>
    </div>
</div>

<div class="row">

    <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 col-xl-12">
        <div class="card mb-3">
            <div class="card-header mycolor3" style="padding:10px">
                <h3><i class="fa fa-table"></i> 조교정보 : 입력</h3>
            </div>

            <div class="card-body" style="padding:10px">

                <form name="form1" method="post" action="edit-action.do" enctype="multipart/form-data">
                    <input type="hidden" name="id" value="${staff.id}" />
                    <table class="table table-bordered mytable-centermiddle" style="width:100%;">
                        <tr>
                            <td class="mycolor2" style="vertical-align:middle;width:60px">학과</td>
                            <td>
                                <div class="form-inline">
                                    <select name="depart_id" class="form-control form-control-sm">
                                        <c:forEach var="depart" items="${departList}">
                                            <option value='${depart.id}' <c:if test="${staff.depart_id eq depart.id}">selected</c:if>>${depart.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="mycolor2">아이디</td>
                            <td>
                                <div class="form-inline">
                                    <input type="text" name="uid" value="${staff.uid}" class="form-control form-control-sm" >
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="mycolor2">암호</td>
                            <td>
                                <div class="form-inline">
                                    <input type="text" name="pwd" value="${staff.pwd}" class="form-control form-control-sm" >
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="mycolor2">이름</td>
                            <td>
                                <div class="form-inline">
                                    <input type="text" name="name" value="${staff.name}" class="form-control form-control-sm" >
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="mycolor2">전화</td>
                            <td>
                                <div class="form-inline">
                                    <input type="text" name="tel1" size="2" maxlength="2" value="${fn:substring(staff.tel,0,2)}" class="form-control form-control-sm" style="width:50px">-
                                    <input type="text" name="tel2" size="4" maxlength="4" value="${fn:substring(staff.tel,2,6)}"	class="form-control form-control-sm" style="width:50px">-
                                    <input type="text" name="tel3" size="4" maxlength="4" value="${fn:substring(staff.tel,6,10)}" class="form-control form-control-sm" style="width:50px">
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="mycolor2">핸드폰</td>
                            <td>
                                <div class="form-inline">
                                    <input type="text" name="phone1" size="3" maxlength="3" value="${fn:substring(staff.phone,0,3)}" class="form-control form-control-sm" style="width:50px">-
                                    <input type="text" name="phone2" size="4" maxlength="4" value="${fn:substring(staff.phone,3,7)}"	class="form-control form-control-sm" style="width:50px">-
                                    <input type="text" name="phone3" size="4" maxlength="4" value="${fn:substring(staff.phone,7,11)}" class="form-control form-control-sm" style="width:50px">
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="mycolor2">E-Mail</td>
                            <td>
                                <input type="text" name="email" value="${staff.email}" class="form-control form-control-sm" >
                            </td>
                        </tr>
                        <tr>
                            <td class="mycolor2">사진</td>
                            <td style="text-align:left">
                                <div class="form-inline mymargin5" >
                                    <input type="file" name="pic" value="" onchange="readURL(this);" class="form-control form-control-sm">
                                </div>
                                <img id="preview" src="${pageContext.request.contextPath}/image/staff/${staff.pic}" class="img-thumbnail" width="120" border="1"/>
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