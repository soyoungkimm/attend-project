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
                                <li class="breadcrumb-item active">수강신청</li>
                            </ol>
                            <div class="clearfix"></div>
                        </div>
                    </div>
                </div>

                <!--- My JS  --------------------------------------------------->
                <script>
                    // 강의번호, 학과명, 학년, 반, 전공일반/교양, 선택/필수, 과목코드, 과목명, 학점, 시간, 교수님, 수강구분
                    function add_mylecture(lec0,lec1,lec2,lec3,lec4,lec5,lec6,lec7,lec8,lec9,lec10,lec11)
                    {
                        if ($("#rowno"+lec0).length!=0) { alert("동일과목이 있습니다."); return; }
                        $("#mylecture_list").append("<tr id='rowno"+lec0+"'><td>"+lec1+"</td><td>"+lec2+"</td><td>"+lec3+"</td><td>"+lec4+"</td><td>"+lec5+"</td><td>"+lec6+"</td><td>"+lec7+"</td><td>"+lec8+"</td><td>"+lec9+"</td><td>"+lec10+"</td><td>"+lec11+"</td><td><a href='javascript:del_mylecture(\""+lec0+"\");' class='btn btn-xs btn-outline-danger'>삭제</a></td></tr>");
                    }

                    function del_mylecture(lec0)
                    {
                        var cells = document.getElementById("rowno"+lec0).getElementsByTagName("td");
                        var temp;
                        var pm = "?";
                        var url = "delete.do";
                        var rownum = 0;
                        var cell = "";

                        for (n=0; n<cells.length-1; n++)
                        {

                            temp = cells[n].childNodes[0].nodeValue;
                            if (n==0)
                            {
                                cell = cell + temp;
                            }
                            else
                            {
                                cell = cell + "," + temp;
                            }

                            if ((n+1) == cells.length-1)
                            {
                                cell = cell + "," + lec0;
                            }
                        }

                        rownum = rownum + 1;

                        if (pm == "?")
                        {
                            pm = pm + "row" + rownum + "=" + cell;
                        }
                        else
                        {
                            pm = pm + "&row" + rownum + "=" + cell;
                        }

                        $("#rowno"+lec0).remove();
                        location.href = "delete.do?lec_id="+lec0;
                    }

                    function save_mylecture()
                    {
                        var nodes=document.getElementById("mylecture_list").childNodes;

                        var cells;
                        var temp;
                        var pm = "?";
                        var url = "create-action.do";
                        var rownum = 0;



                        for(i=0;i<nodes.length;i++)
                        {
                            var node=nodes[i].childNodes;
                            for(j=0;j<node.length;j++)
                            {
                                var idname=node[j].id;

                                if (idname != undefined && idname.indexOf("rowno")==0)
                                {
                                    //alert(idname+" "+idname.substr(5) + " " + rownum + " " + node.length);

                                    cells = document.getElementById(idname).getElementsByTagName("td");
                                    var cell = "";

                                    for (n=0; n<cells.length-1; n++)
                                    {

                                        temp = cells[n].childNodes[0].nodeValue;
                                        if (n==0)
                                        {
                                            cell = cell + temp;
                                        }
                                        else
                                        {
                                            cell = cell + "," + temp;
                                        }

                                        if ((n+1) == cells.length-1)
                                        {
                                            cell = cell + "," + idname.substr(5);
                                        }
                                    }

                                    rownum = rownum + 1;

                                    if (pm == "?")
                                    {
                                        pm = pm + "row" + rownum + "=" + cell;
                                    }
                                    else
                                    {
                                        pm = pm + "&row" + rownum + "=" + cell;
                                    }

                                }
                            }
                        }
                        if (pm == "?")
                        {
                            pm = pm + "num=" + rownum;
                        }
                        else
                        {
                            pm = pm + "&num=" + rownum;
                        }
                        location.href = "create-action.do"+pm;
                    }

                    function filter_lecture(kind)
                    {
                        var nodes=document.getElementById("lecture_list").childNodes;
                        for(i=0;i<nodes.length;i++)
                        {
                            var node=nodes[i].childNodes;
                            for(j=0;j<node.length;j++)
                            {
                                var idname=node[j].id;
                                if (idname != undefined && idname.indexOf("row")==0)
                                {
                                    str=idname.split("^");
                                    if (str[1]==kind)
                                    {
                                        document.getElementById(idname).style.display="table-row";
                                        document.getElementById(idname).style.visibility="visible";
                                    }
                                    else
                                    {
                                        document.getElementById(idname).style.display="none";
                                        document.getElementById(idname).style.visibility="hidden";
                                    }
                                }
                            }
                        }
                    }

                    document.addEventListener('DOMContentLoaded', function() {

                        date_ = new Date();
                        year = date_.getFullYear();
                        month = date_.getMonth();
                        if (month < 8 && month > 1)
                        {
                            term_ = 1;
                        }
                        else
                        {
                            term_ = 2;
                        }
                        document.getElementById("date").innerHTML = year + "년 " + term_ + "학기"

                    }, false);


                </script>

                <div class="row">

                    <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 col-xl-12">
                        <div class="card mb-3">
                            <div class="card-header mycolor3" style="padding:10px">
                                <div class="row">
                                    <div class="col" align="left">
                                        <h3><i class="fa fa-table"></i> 수강신청</h3>
                                    </div>
                                </div>
                            </div>

                            <div class="card-body" style="padding:10px">

                                <div class="row" style="margin-bottom:10px">
                                    <div class="col-auto" align="left">
                                        <h6 id="date">&nbsp;<font color="gray"></font></h6>
                                    </div>
                                    <div class="col" align="right">
                                        <h6>&nbsp;<font color="gray">학생id=1</font>&nbsp;</h6>
                                    </div>
                                </div>

                                <form name="form1" action="" method="post">
                                    <div class="row">
                                        <div class="col" align="left">
                                            <div class="form-inline">
                                                <div class="input-group input-group-sm">
                                                    <div class="input-group-prepend">
                                                        &nbsp;<span class="input-group-text">교과목</span>
                                                    </div>
                                                    &nbsp;
                                                    <input type="button" class="btn btn-sm btn-primary" value="1학년" onclick="filter_lecture(1);">&nbsp;
                                                    <input type="button" class="btn btn-sm btn-primary" value="2학년" onclick="filter_lecture(2);">&nbsp;
                                                    <input type="button" class="btn btn-sm btn-primary" value="3학년" onclick="filter_lecture(3);">&nbsp;
                                                    <input type="button" class="btn btn-sm btn-primary" value="교양" onclick="filter_lecture(0);">&nbsp;
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </form>

                                <div style="width:100%; height:300px; overflow:auto">	<!-- 세로스크롤바 -->
                                    <table id="lecture_list" class="table table-bordered table-hover mytable" style="width:100%">
                                        <thead>
                                        <tr class="mycolor1">
                                            <th>학과</th>
                                            <th>학년</th>
                                            <th>반</th>
                                            <th>전공</th>
                                            <th>필수</th>
                                            <th>과목코드</th>
                                            <th>과목명</th>
                                            <th>학점</th>
                                            <th>시간</th>
                                            <th>담당교수</th>
                                            <th width="110">신청</th>
                                        </tr>
                                        </thead>
                                        <tbody>

                                        <c:forEach items="${requestScope.stLecList}" var="stLec" varStatus="status">
                                        <tr id="row${status.count}^<c:choose> 
                                                                        <c:when test="${stLec.ismajor eq 0}">
                                                                            ${stLec.ismajor}
                                                                        </c:when>
                                                                        <c:otherwise>
                                                                            ${stLec.grade}
                                                                        </c:otherwise>
                                                                    </c:choose>">
                                            <td>${stLec.depart_name}</td>
                                            <td>${stLec.grade}</td>
                                            <td>${stLec.class_name}</td>
                                            <td>${ismajor = stLec.ismajor eq 0? "교양" : "전공"}</td>
                                            <td>${ischoice = stLec.ischoice eq 0? "필수" : "선택"}</td>
                                            <td>${stLec.code}</td>
                                            <td>${stLec.subject_name}</td>
                                            <td>${stLec.point}</td>
                                            <td>${stLec.hour}</td>
                                            <td>${stLec.teacher_name}</td>
                                            <td>
                                                <a href="javascript:add_mylecture('${stLec.lecture_id}','${stLec.depart_name}','${stLec.grade}','${stLec.class_name}','${ismajor}','${ischoice}','${stLec.code}','${stLec.subject_name}','${stLec.point}','${stLec.hour}','${stLec.teacher_name}','정상')" class="btn btn-xs btn-outline-primary">정상</a>
                                                <a href="javascript:add_mylecture('${stLec.lecture_id}','${stLec.depart_name}','${stLec.grade}','${stLec.class_name}','${ismajor}','${ischoice}','${stLec.code}','${stLec.subject_name}','${stLec.point}','${stLec.hour}','${stLec.teacher_name}','재수강')" class="btn btn-xs btn-outline-warning">재수강</a>
                                            </td>
                                        </tr>
                                        </c:forEach>
                                        <!--
                                        <tr id="row1^2">
                                            <td>컴소과</td>
                                            <td>2</td>
                                            <td>A</td>
                                            <td>전공</td>
                                            <td>선택</td>
                                            <td>CS1</td>
                                            <td>PHP</td>
                                            <td>3</td>
                                            <td>4</td>
                                            <td>교수님1</td>
                                            <td>
                                                <a href="javascript:add_mylecture('1','컴소과','2','A','전공','선택','CS1','PHP','3','4','교수님1','정상')" class="btn btn-xs btn-outline-primary">정상</a>
                                                <a href="javascript:add_mylecture('1','컴소과','2','A','전공','선택','CS1','PHP','3','4','교수님1','재수강')" class="btn btn-xs btn-outline-warning">재수강</a>
                                            </td>
                                        </tr>   -->




                                        </tbody>
                                    </table>
                                </div>
                                <br>

                                <form name="form2" method="post">
                                    <div class="row">
                                        <div class="col" align="left">
                                            <h5 style="color:gray"><i class="fa fa-table"></i> 신청과목 </h5>
                                        </div>
                                        <div class="col" align="right">
                                            <button class="btn btn-sm btn-primary" type="button" onClick="save_mylecture();">신청과목 저장</button>
                                        </div>
                                    </div>
                                    <table id="mylecture_list" class="table table-bordered table-responsive-sm mytable mb-0" style="width:100%;padding:0px">
                                        <tr class="mycolor1">
                                            <td>학과</td>
                                            <td>학년</td>
                                            <td>반</td>
                                            <td>전공</td>
                                            <td>필수</td>
                                            <td>과목코드</td>
                                            <td>과목명</td>
                                            <td>학점</td>
                                            <td>시간</td>
                                            <td>교수님</td>
                                            <td>구분</td>
                                            <td width="60"></td>
                                        </tr>

                                        <c:forEach items="${requestScope.mylectureList}" var="mylec" varStatus="status">
                                            <tr id="rowno${mylec.lecture_id}">
                                                <td>${mylec.depart_name}</td>
                                                <td>${mylec.grade}</td>
                                                <td>${mylec.class_name}</td>
                                                <td>${ismajor = mylec.ismajor eq 0? "교양" : "전공"}</td>
                                                <td>${ischoice = mylec.ischoice eq 0? "필수" : "선택"}</td>
                                                <td>${mylec.subject_code}</td>
                                                <td>${mylec.subject_name}</td>
                                                <td>${mylec.point}</td>
                                                <td>${mylec.hour}</td>
                                                <td>${mylec.teacher_name}</td>
                                                <td>${retake = mylec.retake eq 0? "정상" : "재수강"}</td>
                                                <td>
                                                    <a href="javascript:del_mylecture(${mylec.lecture_id})" class='btn btn-xs btn-outline-danger'>삭제</a>
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
 <%@include file="../section/footer.jsp"%>