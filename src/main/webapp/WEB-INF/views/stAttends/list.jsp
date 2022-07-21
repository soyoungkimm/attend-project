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
              <h1 class="main-title float-left">컴퓨터소프트웨어학과</h1>
              <ol class="breadcrumb float-right">
                <li class="breadcrumb-item">Home</li>
                <li class="breadcrumb-item">학생</li>
                <li class="breadcrumb-item active">출석부</li>
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
                    <h3><i class="fa fa-table"></i> 출석부</h3>
                  </div>
                </div>
              </div>
              <div class="card-body" style="padding:10px">

                <div class="row">
                  <div class="col-auto" align="left">
                    <h6>&nbsp;<font color="gray">${year}년 ${term}학기</font></h6>
                  </div>
                  <div class="col" align="right">
                    <h6>&nbsp;<font color="gray">학생id=1</font>&nbsp;</h6>
                  </div>
                </div>

                <table class="table table-bordered table-hover table-responsive-sm mytable" style="width:100%;font-size:8pt;">
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

                    <c:set var="hour" value="1"/>
                    <c:forEach var="student" items="${studentAttendList}" varStatus="status">

                      <c:if test="${hour < student.normhour}">
                        <c:set var="hour" value="${student.normhour}"/>
                      </c:if>
                    </c:forEach>

                    <th colspan="6">1</th>
                    <th colspan="6">2</th>
                    <th colspan="6">3</th>
                    <th colspan="6">4</th>
                    <th colspan="6">5</th>
                    <th colspan="6">6</th>
                    <th colspan="6">7</th>
                    <th colspan="6">8</th>
                    <th colspan="6">9</th>
                    <th colspan="6">10</th>
                    <th colspan="6">11</th>
                    <th colspan="6">12</th>
                    <th colspan="6">13</th>
                    <th colspan="6">14</th>
                    <th colspan="6">15</th>
                  </tr>

                  <!--
                  <tr class="mycolor1" style="line-height:0.7rem">
                    <th></th>
                    <th></th>
                    <th></th>
                    <th></th>
                    <th></th>
                    <th></th>
                    <th></th>
                    <th style="vertical-align:middle">월일</th>
                    <th colspan="2">3<br>05</th>
                    <th colspan="2">3<br>12</th>
                    <th colspan="2">3<br>19</th>
                    <th colspan="2">3<br>26</th>
                    <th colspan="2">4<br>02</th>
                    <th colspan="2">4<br>09</th>
                    <th colspan="2">4<br>16</th>
                    <th colspan="2">4<br>23</th>
                    <th colspan="2">4<br>30</th>
                    <th colspan="2">5<br>02</th>
                    <th colspan="2">5<br>09</th>
                    <th colspan="2">5<br>16</th>
                    <th colspan="2">5<br>23</th>
                    <th colspan="2">5<br>30</th>
                    <th colspan="2">6<br>06</th>
                  </tr>
                  <tr class="mycolor1" style="line-height:0.7rem;">
                    <th>&nbsp;</th>
                    <th></th>
                    <th></th>
                    <th></th>
                    <th></th>
                    <th></th>
                    <th></th>
                    <th>보강</th>
                    <th></th>
                    <th></th>
                    <th></th>
                    <th></th>
                    <th></th>
                    <th></th>
                    <th></th>
                    <th></th>
                    <th></th>
                    <th></th>
                    <th>4<br>23</th>
                    <th>4<br>23</th>
                    <th></th>
                    <th></th>
                    <th></th>
                    <th></th>
                    <th></th>
                    <th></th>
                    <th>6<br>06</th>
                    <th>6<br>06</th>
                    <th></th>
                    <th></th>
                    <th></th>
                    <th></th>
                    <th></th>
                    <th></th>
                    <th></th>
                    <th></th>
                    <th></th>
                    <th></th>
                  </tr> -->


                  <tr class="mycolor1">
                    <th>과목명</th>
                    <th>학년</th>
                    <th>반</th>
                    <th>학점</th>
                    <th>시간</th>
                    <th>지각</th>
                    <th>결석</th>
                    <th>점수</th>
                    <th colspan="6"></th>
                    <th colspan="6"></th>
                    <th colspan="6"></th>
                    <th colspan="6"></th>
                    <th colspan="6"></th>
                    <th colspan="6"></th>
                    <th colspan="6"></th>
                    <th colspan="6"></th>
                    <th colspan="6"></th>
                    <th colspan="6"></th>
                    <th colspan="6"></th>
                    <th colspan="6"></th>
                    <th colspan="6"></th>
                    <th colspan="6"></th>
                    <th colspan="6"></th>
                  </tr>
                  </thead>
                  <tbody>


                  <c:forEach var="student" items="${studentAttendList}" varStatus="status">
                    <tr>
                      <td class="mycolor3">${student.subject_name}</td>
                      <td class="mycolor3">${student.grade}</td>
                      <td class="mycolor3">${student.class_name}</td>
                      <td class="mycolor3">${student.point}</td>
                      <td class="mycolor3">${student.normhour}</td>
                      <td class="mycolor4"><font color="blue"><b>${student.late}</b></font></td>
                      <td class="mycolor4"><font color="red"><b>${student.absent}</b></font></td>
                      <td class="mycolor4"><b>${student.score}</b></td>
                      <c:forEach var="h" items="${student.h}" varStatus="st" end="${student.normhour * 15 - 1}">
                        <td colspan="${6/student.normhour}"
                          >
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


                  <!--
                  <tr>
                    <td class="mycolor3">기독교개론</td>
                    <td class="mycolor3">2</td>
                    <td class="mycolor3">A</td>
                    <td class="mycolor3">2</td>
                    <td class="mycolor3">2</td>
                    <td class="mycolor4"><font color="blue"><b>1</b></font></td>
                    <td class="mycolor4"><font color="red"><b>2</b></font></td>
                    <td class="mycolor4"><b>18</b></td>
                    <td><i class="fa fa-circle-o fa-1x"></i></td>
                    <td><i class="fa fa-circle-o fa-1x"></i>  </td>
                    <td><i class="fa fa-circle-o fa-1x"></i></td>
                    <td><i class="text-danger fa fa-close fa-1x"></i></td>
                    <td><i class="fa fa-circle-o fa-1x"></i></td>
                    <td><i class="text-primary fa fa-times-circle-o fa-1x"></i></td>
                    <td><i class="fa fa-circle-o fa-1x"></i></td>
                    <td><i class="fa fa-circle-o fa-1x"></i></td>
                    <td><i class="fa fa-circle-o fa-1x"></i></td>
                    <td><i class="fa fa-circle-o fa-1x"></i></td>
                    <td><i class="fa fa-circle-o fa-1x"></i></td>
                    <td><i class="fa fa-circle-o fa-1x"></i></td>
                    <td><i class="fa fa-circle-o fa-1x"></i></td>
                    <td><i class="fa fa-circle-o fa-1x"></i></td>
                    <td><i class="fa fa-circle-o fa-1x"></i></td>
                    <td><i class="fa fa-circle-o fa-1x"></i></td>
                    <td><i class="fa fa-circle-o fa-1x"></i></td>
                    <td><i class="fa fa-circle-o fa-1x"></i></td>
                    <td><font color="green"><b>보</b></font></td>
                    <td><font color="green"><b>보</b></font></td>
                    <td><i class="text-warning fa fa-question fa-1x"></i></td>
                    <td><i class="text-warning fa fa-question fa-1x"></i></td>
                    <td><i class="text-warning fa fa-question fa-1x"></i></td>
                    <td><i class="text-warning fa fa-question fa-1x"></i></td>
                    <td><i class="text-warning fa fa-question fa-1x"></i></td>
                    <td><i class="text-warning fa fa-question fa-1x"></i></td>
                    <td><i class="text-warning fa fa-question fa-1x"></i></td>
                    <td><i class="text-warning fa fa-question fa-1x"></i></td>
                    <td><i class="text-warning fa fa-question fa-1x"></i></td>
                    <td><i class="text-warning fa fa-question fa-1x"></i></td>
                  </tr>
                  <tr>
                    <td class="mycolor3">PHP</td>
                    <td class="mycolor3">2</td>
                    <td class="mycolor3">A</td>
                    <td class="mycolor3">2</td>
                    <td class="mycolor3">2</td>
                    <td class="mycolor4"><font color="blue"><b>1</b></font></td>
                    <td class="mycolor4"><font color="red"><b>8</b></font></td>
                    <td class="mycolor4"><font color="red"><b>0</b></font></td>
                    <td><i class="fa fa-circle-o fa-1x"></i></td>
                    <td><i class="fa fa-circle-o fa-1x"></i>  </td>
                    <td><i class="fa fa-circle-o fa-1x"></i></td>
                    <td><i class="text-danger fa fa-close fa-1x"></i></td>
                    <td><i class="fa fa-circle-o fa-1x"></i></td>
                    <td><i class="text-primary fa fa-times-circle-o fa-1x"></i></td>
                    <td><i class="fa fa-circle-o fa-1x"></i></td>
                    <td><i class="fa fa-circle-o fa-1x"></i></td>
                    <td><i class="fa fa-circle-o fa-1x"></i></td>
                    <td><i class="text-danger fa fa-close fa-1x"></i></td>
                    <td><i class="text-danger fa fa-close fa-1x"></i></td>
                    <td><i class="fa fa-circle-o fa-1x"></i></td>
                    <td><i class="fa fa-circle-o fa-1x"></i></td>
                    <td><i class="fa fa-circle-o fa-1x"></i></td>
                    <td><i class="text-danger fa fa-close fa-1x"></i></td>
                    <td><i class="fa fa-circle-o fa-1x"></i></td>
                    <td><i class="fa fa-circle-o fa-1x"></i></td>
                    <td><i class="fa fa-circle-o fa-1x"></i></td>
                    <td><font color="green"><b>보</b></font></td>
                    <td><font color="green"><b>보</b></font></td>
                    <td><i class="text-warning fa fa-question fa-1x"></i></td>
                    <td><i class="text-warning fa fa-question fa-1x"></i></td>
                    <td><i class="text-warning fa fa-question fa-1x"></i></td>
                    <td><i class="text-warning fa fa-question fa-1x"></i></td>
                    <td><i class="text-warning fa fa-question fa-1x"></i></td>
                    <td><i class="text-warning fa fa-question fa-1x"></i></td>
                    <td><i class="text-warning fa fa-question fa-1x"></i></td>
                    <td><i class="text-warning fa fa-question fa-1x"></i></td>
                    <td><i class="text-warning fa fa-question fa-1x"></i></td>
                    <td><i class="text-warning fa fa-question fa-1x"></i></td>
                  </tr> -->


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