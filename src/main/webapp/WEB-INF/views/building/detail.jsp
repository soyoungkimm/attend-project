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
                <li class="breadcrumb-item active">건물</li>
            </ol>
            <div class="clearfix"></div>
        </div>
    </div>
</div>

<div class="row">

    <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 col-xl-12">
        <div class="card mb-3">
            <div class="card-header mycolor3" style="padding:10px">
                <h3><i class="fa fa-table"></i> 건물 : 입력</h3>
            </div>

            <div class="card-body" style="padding:10px">

                <script>
                    function find_text()
                    {
                        if (!form1.text1.value)
                            form1.action="/member/lists/page";
                        else
                            form1.action="/member/lists/text1/" + form1.text1.value+"/page";
                        form1.submit();
                    }
                </script>

                <form name="detail_form" method="post" action="">

                    <table class="table table-bordered mytable-centermiddle" style="width:100%;">
                        <tr>
                            <td class="mycolor2" width="70">건물명</td>
                            <td>
                                <input type="text" name="name" value="${building.name}" class="form-control form-control-sm" readonly>
                            </td>
                        </tr>
                        <tr>
                            <td class="mycolor2">층수</td>
                            <td>
                                <div class="form-inline">
                                    <select name="floor" class="form-control form-control-sm" disabled>
                                        <c:forEach var="i" begin="1" end="5">
                                            <c:choose>
                                                <c:when test="${building.floor == i}">
                                                    <option value="${building.floor}" selected>${building.floor}층</option>
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