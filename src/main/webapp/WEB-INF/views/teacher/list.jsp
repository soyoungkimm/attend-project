
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
        <li class="breadcrumb-item active">교수정보</li>
      </ol>
      <div class="clearfix"></div>
    </div>
  </div>
</div>

<div class="row">

  <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 col-xl-12">
    <div class="card mb-3">
      <div class="card-header mycolor3" style="padding:10px">
        <h3><i class="fa fa-table"></i> 교수정보</h3>
      </div>

      <div class="card-body" style="padding:10px">


        <form name="search_form" method="get" action="list.do">
          <div class="row" style="margin-bottom:5px">
            <div class="col-auto" align="left">
              <div class="form-inline">
                <div class="input-group input-group-sm">
                  <div class="input-group-prepend">
                    <span class="input-group-text">이름</span>
                  </div>
                  <input type="text" name="s" size="10" value="${search}" class="form-control"
                         onKeydown="if (event.keyCode == 13) document.search_form.submit()" >
                  <div class="input-group-append">
                    <input type="submit" class="btn btn-sm mycolor1" type="button" value="검색" />
                  </div>
                </div>
              </div>
            </div>
            <div class="col" align="right">
              <a href="create.do" class="btn btn-sm mycolor1">추가</a>
            </div>
          </div>
        </form>

        <table class="table table-bordered table-hover table-responsive-sm mytable" style="width:100%;">
          <tr class="mycolor1">
            <th>학과</th>
            <th>구분</th>
            <th>이름</th>
            <th>전화</th>
            <th>핸드폰</th>
            <th>이메일</th>
            <th width="95"></th>
          </tr>

            <c:forEach items="${requestScope.teacherList}" var="teacher">
            <tr>
              <td>${teacher.depart_name}</td>
              <td><c:if test="${teacher.kind eq 0}">전임교수</c:if>
                  <c:if test="${teacher.kind eq 1}">겸임교수</c:if>
                  <c:if test="${teacher.kind eq 2}">시간강사</c:if></td>
              <td><a href="detail.do?id=${teacher.id}">${teacher.name}</a></td>
              <td>${fn:substring(teacher.tel,0,2)}-${fn:substring(teacher.tel,2,6)}-${fn:substring(teacher.tel,6,10)}</td>
              <td>${fn:substring(teacher.phone,0,3)}-${fn:substring(teacher.phone,3,7)}-${fn:substring(teacher.phone,7,11)}</td>
              <td>${teacher.email}</td>
              <td>
                <a href="edit.do?id=${teacher.id}" class="btn btn-xs btn-outline-primary">수정</a>
                <a href="delete.do?id=${teacher.id}" class="btn btn-xs btn-outline-danger" onClick="return confirm('삭제할까요 ?');">삭제</a>
              </td>
            </tr>
            </c:forEach>


        </table>

        <c:if test="${fn:length(teacherList) != 0}">
          <nav>
            <ul class="pagination pagination-sm justify-content-center">
                <%-- 블록 왼쪽 이동 --%>
              <c:choose>
                <c:when test="${pagination.curBlock > 1 }">
                  <li class="page-item"><a class="page-link" href="list.do?p=${pagination.beginPageNo- 1}&s=${search}">◀</a></li>
                </c:when>
                <c:otherwise>
                  <li class="page-item"><a class="page-link" href="">◀</a></li>
                </c:otherwise>
              </c:choose>

                <%-- 페이지 왼쪽 이동 --%>
              <c:choose>
                <c:when test="${pagination.curPageNo > 1 }">
                  <li class="page-item"><a class="page-link" href="list.do?p=${pagination.curPageNo - 1}&s=${search}">◁</a></li>
                </c:when>
                <c:otherwise>
                  <li class="page-item"><a class="page-link" href="">◁</a></li>
                </c:otherwise>
              </c:choose>

                <%-- 페이지 번호 --%>

              <c:forEach var="pageNo" begin="${pagination.beginPageNo}" end="${pagination.endPageNo}">
                <c:choose>
                  <c:when test="${pageNo == pagination.curPageNo }">
                    <li class="page-item active"><span class="page-link" style="background-color:steelblue">${pageNo}</span></li>
                  </c:when>
                  <c:otherwise>
                    <li class="page-item"><a class="page-link" href="list.do?p=${pageNo}&s=${search}">${pageNo}</a></li>
                  </c:otherwise>
                </c:choose>
              </c:forEach>


                <%-- 페이지 오른쪽 이동 --%>
              <c:choose>
                <c:when test="${pagination.curPageNo < pagination.totalPages }">
                  <li class="page-item"><a class="page-link" href="list.do?p=${pagination.curPageNo + 1}&s=${search}">▷</a></li>
                </c:when>
                <c:otherwise>
                  <li class="page-item"><a class="page-link" href="">▷</a></li>
                </c:otherwise>
              </c:choose>

                <%-- 블록 오른쪽 이동 --%>
              <c:choose>
                <c:when test="${pagination.curBlock < pagination.totalBlocks }">
                  <li class="page-item"><a class="page-link" href="list.do?p=${pagination.endPageNo + 1}&s=${search}">▶</a></li>
                </c:when>
                <c:otherwise>
                  <li class="page-item"><a class="page-link" href="">▶</a></li>
                </c:otherwise>
              </c:choose>
            </ul>
          </nav>
        </c:if>

      </div>		<!-- card body end -->
    </div>		<!-- card end -->
  </div>

</div>	<!-- row end -->
<!------------------------------------------------------------------------------>
<!-- 내용 끝 -->
<!------------------------------------------------------------------------------>

<%@include file="../section/footer.jsp"%>