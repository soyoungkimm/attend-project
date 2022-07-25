<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 2022-07-25
  Time: 오후 9:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@include file="../section/header.jsp"%>
<jsp:useBean id="now" class="java.util.Date" />
<!------------------------------------------------------------------------------>
<!-- 내용 시작 -->
<!------------------------------------------------------------------------------>
<div class="row">
    <div class="col-xl-12">
        <div class="breadcrumb-holder">
            <h1 class="main-title float-left">${staff.depart_name}</h1>
            <ol class="breadcrumb float-right">
                <li class="breadcrumb-item">Home</li>
                <li class="breadcrumb-item">조교</li>
                <li class="breadcrumb-item active">과목별 출석부</li>
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
                        <h3><i class="fa fa-table"></i> 과목별 출석부</h3>
                    </div>
                </div>
            </div>
            <div class="card-body" style="padding:10px">
                <form name="form1" action="list.do" method="post">
                    <div class="row" style="margin-bottom:3px">
                        <div class="col" align="left">
                            <div class="form-inline">
                                <div class="input-group input-group-sm">
                                    <div class="input-group-prepend">
                                        <span class="input-group-text">년도</span>
                                    </div>
                                    <div class="input-group-append">
                                        <fmt:formatDate var="today" value="${now}" pattern="yyyy" />
                                        <c:set var="prev_year1" value="${today - 1}" />
                                        <c:set var="prev_year2" value="${today - 2}" />
                                        <c:set var="prev_year3" value="${today - 3}" />
                                        <c:set var="prev_year4" value="${today - 4}" />

                                        <select name="year" class="form-control form-control-sm" >

                                            <option value="${today}" <c:if test="${year eq today}">selected</c:if>>${today}</option>
                                            <option value='${prev_year1}' <c:if test="${year eq prev_year1}">selected</c:if>>${prev_year1}</option>
                                            <option value='${prev_year2}' <c:if test="${year eq prev_year2}">selected</c:if>>${prev_year2}</option>
                                            <option value='${prev_year3}' <c:if test="${year eq prev_year3}">selected</c:if>>${prev_year3}</option>
                                            <option value='${prev_year4}' <c:if test="${year eq prev_year4}">selected</c:if>>${prev_year4}</option>
                                        </select>
                                        &nbsp;
                                        <select name="term" class="form-control form-control-sm" >
                                            <option value='1' <c:if test="${term eq 1}">selected</c:if>>1학기</option>
                                            <option value='2' <c:if test="${term eq 2}">selected</c:if>>2학기</option>
                                        </select>
                                    </div>
                                </div>
                                &nbsp;
                                <div class="input-group input-group-sm">
                                    <div class="input-group-prepend">
                                        <span class="input-group-text">학년</span>
                                    </div>
                                    <div class="input-group-append">
                                        <select name="grade" class="form-control form-control-sm" >
                                            <option value='1' <c:if test="${grade eq 1}">selected</c:if>>1학년</option>
                                            <option value='2' <c:if test="${grade eq 2}">selected</c:if>>2학년</option>
                                            <option value='3' <c:if test="${grade eq 3}">selected</c:if>>3학년</option>
                                            <option value='4' <c:if test="${grade eq 4}">selected</c:if>>4학년</option>
                                        </select>
                                        &nbsp;
                                        <select name="ban" class="form-control form-control-sm" >
                                            <option value='A' <c:if test="${ban eq 'A'}">selected</c:if>>A반</option>
                                            <option value='B' <c:if test="${ban eq 'B'}">selected</c:if>>B반</option>
                                        </select>
                                    </div>
                                </div>
                                &nbsp;
                                <div class="input-group input-group-sm">
                                    <div class="input-group-prepend">
                                        <span class="input-group-text">과목</span>
                                    </div>
                                    <div class="input-group-append">
                                        <select name="subject" class="form-control form-control-sm" >
                                            <c:forEach var="subject" items="${subjectList}">
                                                <option value='${subject.id}' <c:if test="${subject.id == sub}">selected</c:if>>${subject.name}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    &nbsp;
                                    <input type="submit" value="검색" class="btn btn-sm btn-primary">
                                </div>
                            </div>
                        </div>
                    </div>
                </form>



                <table class="table table-bordered table-sm table-hover table-responsive-sm mytable" style="width:100%;font-size:8pt;">
                    <thead>
                    <tr class="mycolor1">
                        <th></th>
                        <th></th>
                        <th></th>
                        <th></th>
                        <th></th>
                        <th></th>
                        <th></th>
                        <th>주</th>
                        <th colspan="${hour}">1</th>
                        <th colspan="${hour}">2</th>
                        <th colspan="${hour}">3</th>
                        <th colspan="${hour}">4</th>
                        <th colspan="${hour}">5</th>
                        <th colspan="${hour}">6</th>
                        <th colspan="${hour}">7</th>
                        <th colspan="${hour}">8</th>
                        <th colspan="${hour}">9</th>
                        <th colspan="${hour}">10</th>
                        <th colspan="${hour}">11</th>
                        <th colspan="${hour}">12</th>
                        <th colspan="${hour}">13</th>
                        <th colspan="${hour}">14</th>
                        <th colspan="${hour}">15</th>
                    </tr>
                    <tr class="mycolor1" style="line-height:0.7rem">
                        <th></th>
                        <th></th>
                        <th></th>
                        <th></th>
                        <th></th>
                        <th></th>
                        <th></th>
                        <th style="vertical-align:middle">월일</th>
                        <c:choose>
                            <c:when test="${dateList ne null}">
                                <c:forEach var="date" items="${dateList}">
                                    <fmt:parseDate value="${date}" pattern="yyyy-mm-dd" var="startdate" />
                                    <fmt:formatDate var="month" value="${startdate}" pattern="m"></fmt:formatDate>
                                    <fmt:formatDate var="th" value="${startdate}" pattern="dd"></fmt:formatDate>
                                    <th colspan="${hour}">${month}<br>${th}</th>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <c:forEach var="i" begin="0" end="14">
                                    <th colspan="${hour}"><br></th>
                                </c:forEach>
                            </c:otherwise>
                        </c:choose>

                    </tr>
                    <tr class="mycolor1" style="line-height:0.7rem;">
                        <th>&nbsp;</th>
                        <th></th>
                        <th></th>
                        <th></th>
                        <th></th>
                        <th></th>
                        <th></th>
                        <th style="vertical-align:middle">보강</th>
                        <c:set var="myh" value="1"></c:set>
                        <c:forEach var="i" begin="0" end="14">
                            <c:choose>
                                <c:when test="${restList ne null}">
                                    <c:forEach var="rest" items="${restList}">
                                        <c:choose>
                                            <c:when test="${rest.starth == myh}">
                                                <fmt:parseDate value="${rest.restdate}" pattern="yyyy-mm-dd" var="restdate" />
                                                <fmt:formatDate var="month" value="${restdate}" pattern="m"></fmt:formatDate>
                                                <fmt:formatDate var="th" value="${restdate}" pattern="dd"></fmt:formatDate>
                                                <c:choose>
                                                    <c:when test="${hour == 1}">
                                                        <th>${month}<br>${th}</th>
                                                    </c:when>
                                                    <c:when test="${hour == 2}">
                                                        <th>${month}<br>${th}</th>
                                                        <th>${month}<br>${th}</th>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <th>${month}<br>${th}</th>
                                                        <th>${month}<br>${th}</th>
                                                        <th>${month}<br>${th}</th>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:when>
                                            <c:otherwise>
                                                <c:choose>
                                                    <c:when test="${hour == 1}">
                                                        <th></th>
                                                    </c:when>
                                                    <c:when test="${hour == 2}">
                                                        <th></th>
                                                        <th></th>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <th></th>
                                                        <th></th>
                                                        <th></th>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                    <c:set var="myh" value="${myh + hour}"></c:set>
                                </c:when>
                                <c:otherwise>
                                    <c:choose>
                                        <c:when test="${hour == 1}">
                                            <th></th>
                                        </c:when>
                                        <c:when test="${hour == 2}">
                                            <th></th>
                                            <th></th>
                                        </c:when>
                                        <c:otherwise>
                                            <th></th>
                                            <th></th>
                                            <th></th>
                                        </c:otherwise>
                                    </c:choose>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </tr>
                    <tr class="mycolor1">
                        <th>학과</th>
                        <th>학년</th>
                        <th>학번</th>
                        <th>학적</th>
                        <th>이름</th>
                        <th>지각</th>
                        <th>결석</th>
                        <th>점수</th>
                        <c:forEach var="i" begin="0" end="14">
                            <c:choose>
                                <c:when test="${hour == 1}">
                                    <th>${start}</th>
                                </c:when>
                                <c:when test="${hour == 2}">
                                    <th>${start}</th>
                                    <th>${start + 1}</th>
                                </c:when>
                                <c:otherwise>
                                    <th>${start}</th>
                                    <th>${start + 1}</th>
                                    <th>${start + 2}</th>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </tr>
                    </thead>
                    <tbody>

                    <c:forEach var="student" items="${studentList}" varStatus="status">
                        <tr>
                            <td class="mycolor3">${student.departName}</td>
                            <td class="mycolor3">${student.grade}</td>
                            <td class="mycolor3">${student.schoolno}</td>
                            <td class="mycolor3"><c:if test="${student.studentState == 0}">재학</c:if><c:if test="${student.studentState == 1}">휴학</c:if><c:if test="${student.studentState == 2}">자퇴</c:if></td>
                            <td class="mycolor3">${student.name}</td>
                            <td class="mycolor4"><font color="blue"><b><span id="${status.count}_late">${student.late}</span></b></font></td>
                            <td class="mycolor4"><font color="red"><b><span id="${status.count}_absent">${student.absent}</span></b></font></td>
                            <td class="mycolor4"><b><span id="${status.count}_score">${student.score}</span></b></td>
                            <c:forEach var="h" items="${student.h}" varStatus="st" end="${hour * 15}">
                                <td id="${status.count}^${st.count}">
                                    <c:if test="${h ne 10}">
                                        <i class="<c:if test="${h eq 0}">fa fa-circle-o</c:if>
                                            <c:if test="${h eq 1}">text-primary fa fa-times-circle-o</c:if>
                                            <c:if test="${h eq 2}">text-danger fa fa-close</c:if>
                                            <c:if test="${h eq null}">text-warning fa fa-question</c:if> fa-1x">
                                        </i>
                                    </c:if>
                                    <c:if test="${h eq 10}"><font color="green"><b>보</b></font></c:if>
                                </td>
                            </c:forEach>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>		<!-- card body end -->
        </div>		<!-- card end -->
    </div>

</div>	<!-- row end -->
<!------------------------------------------------------------------------------>
<!-- 내용 끝 -->
<!------------------------------------------------------------------------------>
<%@include file="../section/footer.jsp"%>
