<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 2022-07-02
  Time: 오후 10:55
  To change this template use File | Settings | File Templates.
--%>
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
                <h3><i class="fa fa-table"></i> 학생정보 : 입력</h3>
            </div>

            <div class="card-body" style="padding:10px">

                <form name="create_form" method="post" action="create-action.do" enctype="multipart/form-data">

                    <table class="table table-bordered mytable-centermiddle" style="width:100%;">
                        <tr>
                            <td class="mycolor2" style="vertical-align:middle;width:60px">상태</td>
                            <td>
                                <div class="form-inline">
                                    <select name="state" class="form-control form-control-sm" style="width:80px">
                                        <option value='0' selected>재학</option>
                                        <option value='1'>휴학</option>
                                        <option value='2'>자퇴</option>
                                    </select>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="mycolor2">학과</td>
                            <td>
                                <div class="form-inline">
                                    <select name="depart_id" class="form-control form-control-sm">
                                        <c:forEach var="depart" items="${departList}" varStatus="status">
                                            <option value='${depart.id}' <c:if test="${status.first}">selected</c:if>>${depart.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="mycolor2">학년/반</td>
                            <td>
                                <div class="form-inline">
                                    <select name="grade" class="form-control form-control-sm" style="width:80px">
                                        <option value='1'>1학년</option>
                                        <option value='2'>2학년</option>
                                        <option value='3'>3학년</option>
                                    </select>
                                    &nbsp;
                                    <select name="class" class="form-control form-control-sm" style="width:80px">
                                        <option value='A'>A 반</option>
                                        <option value='B'>B 반</option>
                                    </select>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="mycolor2">학번</td>
                            <td>
                                <div class="form-inline">
                                    <input type="text" name="schoolno" size="9" value="" class="form-control form-control-sm" required>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="mycolor2">이름</td>
                            <td>
                                <div class="form-inline">
                                    <input type="text" name="name" value="" class="form-control form-control-sm" required>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="mycolor2">핸드폰</td>
                            <td>
                                <div class="form-inline">
                                    <input type="text" name="phone1" maxlength="3" value="010" class="form-control form-control-sm" style="width:60px">-
                                    <input type="text" name="phone2" maxlength="4" value=""	class="form-control form-control-sm" style="width:60px">-
                                    <input type="text" name="phone3" maxlength="4" value="" class="form-control form-control-sm" style="width:60px">
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="mycolor2">성별</td>
                            <td>
                                <div class="form-inline">
                                    <input type="radio" name="gender" value="0" checked> &nbsp; 남자 &nbsp;&nbsp;
                                    <input type="radio" name="gender" value="1"> &nbsp; 여자
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="mycolor2">E-Mail</td>
                            <td>
                                <div class="form-inline">
                                    <input type="text" name="email" value="" class="form-control form-control-sm">
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="mycolor2">암호</td>
                            <td>
                                <div class="form-inline">
                                    <input type="password" name="pwd" value="" class="form-control form-control-sm">
                                </div>
                            </td>
                        </tr>

                        <tr>
                            <td class="mycolor2">사진</td>
                            <td style="text-align:left">
                                <div class="form-inline mymargin5">
                                    <input type="file" name="pic" class="form-control form-control-sm" onchange="setPreviewImage(event);">
                                </div>
                                <div id="image_preview"></div>
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

<script>
	function setPreviewImage(event){
	    let oldPreview = document.querySelector("#image_preview img");
	    if (oldPreview != null) {
	        oldPreview.remove();
	    }
		var reader = new FileReader();
		reader.onload = function(event){
			var img = document.createElement("img");
			img.setAttribute("src", event.target.result);
			img.setAttribute("class", "img-thumbnail");
			img.setAttribute("width", "120");
			img.setAttribute("border", "1");
			document.querySelector("#image_preview").appendChild(img);
		};

		reader.readAsDataURL(event.target.files[0]);
	}
</script>
<%@include file="../section/footer.jsp"%>