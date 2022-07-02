<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 2022-07-02
  Time: 오후 6:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../section/header.jsp"%>

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
                <li class="breadcrumb-item active">공지사항</li>
            </ol>
            <div class="clearfix"></div>
        </div>
    </div>
</div>

<div class="row">

    <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 col-xl-12">
        <div class="card mb-3">
            <div class="card-header mycolor3" style="padding:10px">
                <h3><i class="fa fa-table"></i> 공지사항 입력</h3>
            </div>

            <div class="card-body" style="padding:10px">
                <form name="notice_edit_form" method="post" action="edit-action.do">
                    <input type="hidden" name="id" value="${notice.id}" />
                    <table class="table table-bordered mytable-centermiddle" style="width:100%;">
                        <tr>
                            <td class="mycolor2">날짜</td>
                            <td>
                                <div class="form-inline">
                                    <input type="text" name="writeday" size="10" value="${notice.writeday}" class="form-control form-control-sm" readonly>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="mycolor2">제목</td>
                            <td>
                                <input type="text" name="title" value="${notice.title}" class="form-control form-control-sm" >
                            </td>
                        </tr>
                        <tr>
                            <td class="mycolor2">내용</td>
                            <td>
                                <textarea rows="10" name="content" class="form-control form-control-sm" >${notice.content}</textarea>
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

