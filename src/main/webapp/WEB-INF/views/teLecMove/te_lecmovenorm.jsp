<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../section/header.jsp" %>
<!------------------------------------------------------------------------------>
<!-- 내용 시작 -->
<!------------------------------------------------------------------------------>
				<div class="row">
					<div class="col-xl-12">
						<div class="breadcrumb-holder">
							<h1 class="main-title float-left">${departName}</h1>
							<ol class="breadcrumb float-right">
								<li class="breadcrumb-item">Home</li>
								<li class="breadcrumb-item">교수</li>
								<li class="breadcrumb-item active">휴보강</li>
							</ol>
							<div class="clearfix"></div>
						</div>
					</div>
				</div>

				<!--- 시간표 관련 JS  ---------------------------------------------->
				<script>

					function init() 
					{
						var sday=new Date();
						if (sday.getMonth() < 2)
							sday.setMonth(2, 1);
						if (sday.getMonth() > 5)
							sday.setMonth(5, 30);
						form1.startday.value=get_day( sday, -sday.getDay() );
						moveweek(0);
						load_lec();
					}

					function load_lec()			// 해당학기 시간표읽어 모두 표시
					{	
						// 학년^반^요일^시작교시^시간^과목명^교수님^강의실
						var timetable = [
							<c:forEach var="time" items="${timetable}" varStatus="status">
								"${time}"
								<c:if test="${status.index != timetable.size()-1}">,</c:if>
							</c:forEach>
						];

						for (i=0;i<timetable.length;i++)
						{
							show_lecture(2, timetable[i]);
						}
					}

					function moveweek(dir)
					{
						show_week(dir);
						clear_lecture();
					}

					let selectedLecture = "";
					function sel_lecture(pos)		// 시간표내의 강의 선택 
					{
						clear_lecture();

						selectedLecture = pos;
						var w=['일','월','화','수','목','금','토'];
						str = pos.split("^");
						form1.normdate.value=get_day( form1.startday.value, Number(str[2]) );
						form1.normweek.value=w[ str[2] ];
						form1.normstart.value=str[3];
						form1.normhour.value=str[4];
					
						document.getElementById( pos ).style.borderColor="red";
					}

					function click_rest() {
						let url = "/TeLecMove/rest.do"+
							"?sellecture="+selectedLecture+
							"&normdate="+form1.normdate.value+
							"&normweek="+form1.normweek.value+
							"&normstart="+form1.normstart.value+
							"&normhour="+form1.normhour.value;
						url = encodeURI(url);
						location.href = url;
					}

					window.onload = function () {
						init();
					}
				</script>

				<div class="row">
				
					<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 col-xl-12">
						<div class="card mb-3">
							<div class="card-header mycolor3" style="padding:10px">
								<div class="row">
									<div class="col" align="left">
										<h3><i class="fa fa-table"></i> 출석부</h3>
									</div>
									<div class="col" align="right">
										<h3>${teacherName}</h3>
									</div>
								</div>
							</div>
							<div class="card-body" style="padding:10px">

								<ul class="nav nav-tabs nav-pills nav-fill">
								  <li class="nav-item">
									<a class="nav-link active" href="#"><h6>휴강날짜 선택</h6></a>
								  </li>
								  <li class="nav-item">
									<a class="nav-link" href="#" onclick="click_rest()"><h6>보강날짜 선택</h6></a>
								  </li>
								</ul>
						
								<form name="form1" method="post" action="">

								<input type="hidden" name="startday" value="${startday}">

								<div class="row">
									<div class="col-auto" align="center">
										<br>
										<div class="form-inline" style="margin-bottom:3px">
											<div class="input-group input-group-sm">
												<div class="input-group-prepend">
													<span class="input-group-text alert-primary"><a href="javascript:moveweek(-1);"><i class="fa fa-chevron-left"></i></a></span>&nbsp;
													<span class="input-group-text alert-primary">휴강일</span>&nbsp;
												</div>
												<input type="text"name="normdate" value="" style="width:110px;text-align:center" class="form-control form-control-sm">&nbsp;
												<input type="text" name="normweek" size="1" value="" style="width:35px;text-align:center" class="form-control form-control-sm">&nbsp;
												<input type="text" name="normstart"  size="1" value="" style="width:35px;text-align:center" class="form-control form-control-sm">&nbsp;
												<input type="text" name="normhour" size="1" value="" style="width:35px;text-align:center" class="form-control form-control-sm">&nbsp;
												<div class="input-group-append">
													<span class="input-group-text alert-primary"><a href="javascript:moveweek(1);"><i class="fa fa-chevron-right"></i></a></span>
												</div>
											</div>
										</div>
										<div class="form-inline" style="margin-bottom:3px">
											<div class="input-group input-group-sm">
												<div class="input-group-prepend">
													<span class="input-group-text"><i class="fa fa-chevron-left"></i></span>&nbsp;
													<span class="input-group-text">보강일</span>&nbsp;
												</div>
												<input type="text" name="restdate" value="" readonly style="width:110px;text-align:center" class="form-control form-control-sm" >&nbsp;
												<input type="text" name="restweek" size="1" value="" readonly style="width:35px;text-align:center" class="form-control form-control-sm" >&nbsp;
												<input type="text" name="reststart"  size="1" value="" readonly style="width:35px;text-align:center" class="form-control form-control-sm" >&nbsp;
												<input type="text" name="resthour" size="1" value="" readonly style="width:35px;text-align:center" class="form-control form-control-sm" >&nbsp;
												<div class="input-group-append">
													<span class="input-group-text"><i class="fa fa-chevron-right"></i></span>
												</div>
											</div>
										</div>
										<div class="form-inline" style="margin-bottom:3px">
											<div class="input-group input-group-sm">
												<div class="input-group-prepend">
													<span class="input-group-text"><i class="fa fa-chevron-left" disabled></i></span>&nbsp;
													<span class="input-group-text">강의실</span>&nbsp;
												</div>
												<input type="text" name="bulding_name" value="인관" readonly style="width:76px;text-align:center" class="form-control form-control-sm" >&nbsp;
												<input type="text" name="room_name" value="컴퓨터실1" readonly style="width:193px;text-align:center" class="form-control form-control-sm" >&nbsp;
												<input type="hidden" name="building_no"  value="1">
												<input type="hidden" name="room_no" value="1">
											</div>
										</div>
										
										<table class="table table-bordered table-responsive-sm mytable" style="width:100%;">
											<tr class="mycolor1">
												<td width="80">&nbsp;</td>
												<td id="w0" width="80">일<br></td>
												<td id="w1" width="80">월<br></td>
												<td id="w2" width="80">화<br></td>
												<td id="w3" width="80">수<br></td>
												<td id="w4" width="80">목<br></td>
												<td id="w5" width="80">금<br></td>
												<td id="w6" width="80">토<br></td>
											</tr>
											<tr>
												<td bgcolor="#eeeeee"><b>1</b>&nbsp;<font size="1">( 9:00)</font></td>
												<td class="mycolor3"><div id="010" class="lecbox"></div></td>
												<td><div id="011" class="lecbox"></div></td>
												<td><div id="012" class="lecbox"></div></td>
												<td><div id="013" class="lecbox"></div></td>
												<td><div id="014" class="lecbox"></div></td>
												<td><div id="015" class="lecbox"></div></td>
												<td><div id="016" class="lecbox"></div></td>
											</tr>
											<tr>
												<td bgcolor="#eeeeee"><b>2</b>&nbsp;<font size="1">(10:00)</font></td>
												<td class="mycolor3"><div id="020" class="lecbox"></div></td>
												<td><div id="021" class="lecbox"></div></td>
												<td><div id="022" class="lecbox"></div></td>
												<td><div id="023" class="lecbox"></div></td>
												<td><div id="024" class="lecbox"></div></td>
												<td><div id="025" class="lecbox"></div></td>
												<td><div id="026" class="lecbox"></div></td>
											</tr>
											<tr>
												<td bgcolor="#eeeeee"><b>3</b>&nbsp;<font size="1">(11:00)</font></td>
												<td class="mycolor3"><div id="030" class="lecbox"></div></td>
												<td><div id="031" class="lecbox"></div></td>
												<td><div id="032" class="lecbox"></div></td>
												<td><div id="033" class="lecbox"></div></td>
												<td><div id="034" class="lecbox"></div></td>
												<td><div id="035" class="lecbox"></div></td>
												<td><div id="036" class="lecbox"></div></td>
											</tr>
											<tr>
												<td bgcolor="#eeeeee"><b>4</b>&nbsp;<font size="1">(12:00)</font></td>
												<td class="mycolor3"><div id="040" class="lecbox"></div></td>
												<td><div id="041" class="lecbox"></div></td>
												<td><div id="042" class="lecbox"></div></td>
												<td><div id="043" class="lecbox"></div></td>
												<td><div id="044" class="lecbox"></div></td>
												<td><div id="045" class="lecbox"></div></td>
												<td><div id="046" class="lecbox"></div></td>
											</tr>
											<tr>
												<td bgcolor="#eeeeee"><b>5</b>&nbsp;<font size="1">( 1:00)</font></td>
												<td class="mycolor3"><div id="050" class="lecbox"></div></td>
												<td><div id="051" class="lecbox"></div></td>
												<td><div id="052" class="lecbox"></div></td>
												<td><div id="053" class="lecbox"></div></td>
												<td><div id="054" class="lecbox"></div></td>
												<td><div id="055" class="lecbox"></div></td>
												<td><div id="056" class="lecbox"></div></td>
											</tr>
											<tr>
												<td bgcolor="#eeeeee"><b>6</b>&nbsp;<font size="1">( 2:00)</font></td>
												<td class="mycolor3"><div id="060" class="lecbox"></div></td>
												<td><div id="061" class="lecbox"></div></td>
												<td><div id="062" class="lecbox"></div></td>
												<td><div id="063" class="lecbox"></div></td>
												<td><div id="064" class="lecbox"></div></td>
												<td><div id="065" class="lecbox"></div></td>
												<td><div id="066" class="lecbox"></div></td>
											</tr>
											<tr>
												<td bgcolor="#eeeeee"><b>7</b>&nbsp;<font size="1">( 3:00)</font></td>
												<td class="mycolor3"><div id="070" class="lecbox"></div></td>
												<td><div id="071" class="lecbox"></div></td>
												<td><div id="072" class="lecbox"></div></td>
												<td><div id="073" class="lecbox"></div></td>
												<td><div id="074" class="lecbox"></div></td>
												<td><div id="075" class="lecbox"></div></td>
												<td><div id="076" class="lecbox"></div></td>
											</tr>
											<tr>
												<td bgcolor="#eeeeee"><b>8</b>&nbsp;<font size="1">( 4:00)</font></td>
												<td class="mycolor3"><div id="080" class="lecbox"></div></td>
												<td><div id="081" class="lecbox"></div></td>
												<td><div id="082" class="lecbox"></div></td>
												<td><div id="083" class="lecbox"></div></td>
												<td><div id="084" class="lecbox"></div></td>
												<td><div id="085" class="lecbox"></div></td>
												<td><div id="086" class="lecbox"></div></td>
											</tr>
											<tr>
												<td bgcolor="#eeeeee"><b>9</b>&nbsp;<font size="1">( 5:00)</font></td>
												<td class="mycolor3"><div id="090" class="lecbox"></div></td>
												<td><div id="091" class="lecbox"></div></td>
												<td><div id="092" class="lecbox"></div></td>
												<td><div id="093" class="lecbox"></div></td>
												<td><div id="094" class="lecbox"></div></td>
												<td><div id="095" class="lecbox"></div></td>
												<td><div id="096" class="lecbox"></div></td>
											</tr>
											<tr>
												<td bgcolor="#dddddd" colspan="30" style="height:3px;padding:0px"></td>
											</tr>
											<tr>
												<td bgcolor="#eeeeee"><b>10</b>&nbsp;<font size="1">(6:00)</font></td>
												<td class="mycolor3"><div id="100" class="lecbox"></div></td>
												<td><div id="101" class="lecbox"></div></td>
												<td><div id="102" class="lecbox"></div></td>
												<td><div id="103" class="lecbox"></div></td>
												<td><div id="104" class="lecbox"></div></td>
												<td><div id="105" class="lecbox"></div></td>
												<td><div id="106" class="lecbox"></div></td>
											</tr>
											<tr>
												<td bgcolor="#eeeeee"><b>11</b>&nbsp;<font size="1">(7:00)</font></td>
												<td class="mycolor3"><div id="110" class="lecbox"></div></td>
												<td><div id="111" class="lecbox"></div></td>
												<td><div id="112" class="lecbox"></div></td>
												<td><div id="113" class="lecbox"></div></td>
												<td><div id="114" class="lecbox"></div></td>
												<td><div id="115" class="lecbox"></div></td>
												<td><div id="116" class="lecbox"></div></td>
											</tr>
											<tr>
												<td bgcolor="#eeeeee"><b>12</b>&nbsp;<font size="1">(8:00)</font></td>
												<td class="mycolor3"><div id="120" class="lecbox"></div></td>
												<td><div id="121" class="lecbox"></div></td>
												<td><div id="122" class="lecbox"></div></td>
												<td><div id="123" class="lecbox"></div></td>
												<td><div id="124" class="lecbox"></div></td>
												<td><div id="125" class="lecbox"></div></td>
												<td><div id="126" class="lecbox"></div></td>
											</tr>
											<tr>
												<td bgcolor="#eeeeee"><b>13</b>&nbsp;<font size="1">(9:00)</font></td>
												<td class="mycolor3"><div id="130" class="lecbox"></div></td>
												<td><div id="131" class="lecbox"></div></td>
												<td><div id="132" class="lecbox"></div></td>
												<td><div id="133" class="lecbox"></div></td>
												<td><div id="134" class="lecbox"></div></td>
												<td><div id="135" class="lecbox"></div></td>
												<td><div id="136" class="lecbox"></div></td>
											</tr>
											<tr>
												<td bgcolor="#eeeeee"><b>14</b>&nbsp;<font size="1">(10:00)</font></td>
												<td class="mycolor3"><div id="140" class="lecbox"></div></td>
												<td><div id="141" class="lecbox"></div></td>
												<td><div id="142" class="lecbox"></div></td>
												<td><div id="143" class="lecbox"></div></td>
												<td><div id="144" class="lecbox"></div></td>
												<td><div id="145" class="lecbox"></div></td>
												<td><div id="146" class="lecbox"></div></td>
											</tr>
										</table>
									</div>
								</div>

								</form>

							</div>		<!-- card body end -->
						</div>		<!-- card end -->
					</div>
						
				</div>	<!-- row end -->
<!------------------------------------------------------------------------------>
<!-- 내용 끝 -->
<!------------------------------------------------------------------------------>
<%@ include file="../section/footer.jsp" %>