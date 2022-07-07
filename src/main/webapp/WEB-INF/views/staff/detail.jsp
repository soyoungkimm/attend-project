
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

                <form name="form1" method="post" action="" enctype="multipart/form-data">

                    <table class="table table-bordered mytable-centermiddle" style="width:100%;">
                        <tr>
                            <td class="mycolor2" style="vertical-align:middle;width:60px">학과</td>
                            <td>
                                <div class="form-inline">
                                    <input type="text" name="depart_name" value="${staff.depart_name}" class="form-control form-control-sm" readonly>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="mycolor2">아이디</td>
                            <td>
                                <div class="form-inline">
                                    <input type="text" name="uid" value="${staff.uid}" class="form-control form-control-sm" readonly>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="mycolor2">암호</td>
                            <td>
                                <div class="form-inline">
                                    <input type="text" name="pwd" value="${staff.pwd}" class="form-control form-control-sm" readonly>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="mycolor2">이름</td>
                            <td>
                                <div class="form-inline">
                                    <input type="text" name="name" value="${staff.name}" class="form-control form-control-sm" readonly>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="mycolor2">전화</td>
                            <td>
                                <div class="form-inline">
                                    <input type="text" name="tel" value="${fn:substring(staff.tel,0,2)}-${fn:substring(staff.tel,2,6)}-${fn:substring(staff.tel,6,10)}" class="form-control form-control-sm" readonly>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="mycolor2">핸드폰</td>
                            <td>
                                <div class="form-inline">
                                    <input type="text" name="phone" value="${fn:substring(staff.phone,0,3)}-${fn:substring(staff.phone,3,7)}-${fn:substring(staff.phone,7,11)}" class="form-control form-control-sm" readonly>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="mycolor2">E-Mail</td>
                            <td>
                                <input type="text" name="email" value="${staff.email}" class="form-control form-control-sm" readonly>
                            </td>
                        </tr>
                        <tr>
                            <td class="mycolor2">사진</td>
                            <td style="text-align:left">
                                <img src="${pageContext.request.contextPath}/image/staff/${staff.pic}" class="img-thumbnail" width="120" border="1"/>
                            </td>
                        </tr>


                    </table>

                    <div align="center">
                        <input type="button" value="목록" class="btn btn-sm mycolor1" onclick="location.href='list.do'">
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