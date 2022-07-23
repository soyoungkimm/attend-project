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
					function init_board()			// 해당학기 시간표읽어 모두 표시
					{	
						var a = [
							<c:forEach var="hour" begin="0" end="13">
								[
								<c:forEach var="week" begin="0" end="6">
									${timetable[hour][week]}
									<c:if test="${week != 6}">
										,
									</c:if>
								</c:forEach>
								]
								<c:if test="${hour != 13}">
									,
								</c:if>
							</c:forEach>
						];

						for (h=1;h<=14 ;h++){		// 시간(1-14)
						for (w=0;w<=6 ;w++) {		// 요일(0-6)
							pos=String(h)+String(w);
							if (h<10) pos="0"+pos;
							if (a[h-1][w]== 1)
							{
								document.getElementById( pos ).parentNode.style.backgroundColor="#dfefff";
								document.getElementById( pos ).innerHTML="&nbsp;";
							}
						}}
					}

					function init() 
					{
						var sday=new Date();
						if (sday.getMonth() < 2)
							sday.setMonth(2, 1);
						if (sday.getMonth() > 5)
							sday.setMonth(5, 30);
						form1.startday.value=get_day( sday, -sday.getDay() );
						moveweek(0);

						init_board();

						document.getElementById(form1.selpos.value).innerHTML="";
						show_lecture(3, form1.sellecture.value);
					}

					function moveweek(dir) { show_week(dir);	}

					function sel_room(room_no,building_no,roomname)
					{
						form1.room_no.value=room_no;
						let opt = document.getElementById("building"+building_no);
						form1.buildingname.value=opt.value;
						form1.buildingno.value=building_no;
						form1.roomname.value=roomname;
					}

					function change_building() {
						const buildingNo = form1.building_no.value;
						for (let i = 0; i < ${buildingList.size()}; i++) {
							const curNo = form1.building_no.options[i+1].id.substring(8);
							let state = (curNo != buildingNo && curNo != 0) ? "none" : "";
							let rooms = document.getElementsByName("room"+curNo);
							for (let room in rooms)
								room.style.display = state;
						}
					}

					function dragEnter(ev) { ev.preventDefault(); }
					function drag(ev) {	ev.dataTransfer.setData("text", ev.target.id); }
					function drop(ev,target) 
					{
						if (typeof(document.getElementById( ev.target.id ).childNodes[0])=='undefined')
						{
							ev.preventDefault();
							var data = ev.dataTransfer.getData("text");
							ev.target.appendChild(document.getElementById(data));
							pos=ev.target.id;
							form1.selpos.value=pos;

							var w=['일','월','화','수','목','금','토'];
							ww=Number(pos.substr(2,1));
							form1.restdate.value=get_day(form1.startday.value, ww);
							form1.restweek.value=w[ww];
							form1.reststart.value=pos.substr(1,1);

							str=form1.sellecture.value;
							str=str.split("^");
							form1.resthour.value=str[4];
						}
						else
							alert("여기에는 놓을 수 없습니다.");
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
									<a class="nav-link" href="/TeLecMove/norm.do"><h6>휴강날짜 선택</h6></a>
								  </li>
								  <li class="nav-item">
									<a class="nav-link active" href="#"><h6>보강날짜 선택</h6></a>
								  </li>
								</ul>
						
								<br>

		
								<div class="row">
									<div class="col" align="left">

										<form name="form1" method="post" action="/TeLecMove/new.do">

										<input type="hidden" name="startday" value="">
										<input type="hidden" name="selpos" value="firstpos">
										<input type="hidden" name="sellecture" value="${sellecture}">

										<input type="hidden" name="buildingno" value="">
										<input type="hidden" name="room_no" value="">

										<div class="form-inline" style="margin-bottom:3px">
											<div class="input-group input-group-sm">
												<div class="input-group-prepend">
													<span class="input-group-text"><i class="fa fa-chevron-left"></i></span>&nbsp;
													<span class="input-group-text">휴강일</span>&nbsp;
												</div>
												<input type="text"name="normdate" value="${normdate}"  readonly style="width:110px;text-align:center" class="form-control form-control-sm">&nbsp;
												<input type="text" name="normweek" size="1"  readonly value="${normweek}" style="width:35px;text-align:center" class="form-control form-control-sm">&nbsp;
												<input type="text" name="normstart"  size="1"  readonly value="${normstart}" style="width:35px;text-align:center" class="form-control form-control-sm">&nbsp;
												<input type="text" name="normhour" size="1"  readonly value="${normhour}" style="width:35px;text-align:center" class="form-control form-control-sm">&nbsp;
												<div class="input-group-append">
													<span class="input-group-text"><i class="fa fa-chevron-right"></i></span>
												</div>
											</div>
										</div>
										<div class="form-inline" style="margin-bottom:3px">
											<div class="input-group input-group-sm">
												<div class="input-group-prepend">
													<span class="input-group-text alert-primary"><a href="javascript:moveweek(-1);"><i class="fa fa-chevron-left"></i></a></span>&nbsp;
													<span class="input-group-text alert-primary">보강일</span>&nbsp;
												</div>
												<input type="text" name="restdate" value=""style="width:110px;text-align:center" class="form-control form-control-sm" >&nbsp;
												<input type="text" name="restweek" size="1" value="" style="width:35px;text-align:center" class="form-control form-control-sm" >&nbsp;
												<input type="text" name="reststart"  size="1" value="" style="width:35px;text-align:center" class="form-control form-control-sm" >&nbsp;
												<input type="text" name="resthour" size="1" value="" style="width:35px;text-align:center" class="form-control form-control-sm" >&nbsp;
												<div class="input-group-append">
													<span class="input-group-text alert-primary"><a href="javascript:moveweek(1);"><i class="fa fa-chevron-right"></i></a></span>
												</div>
											</div>
										</div>
										<div class="form-inline" style="margin-bottom:3px">
											<div class="input-group input-group-sm">
												<div class="input-group-prepend">
													<span class="input-group-text"><i class="fa fa-chevron-left" disabled></i></span>&nbsp;
													<span class="input-group-text">강의실</span>&nbsp;
												</div>
												<input type="text" name="buildingname" value="${buildingName}" readonly style="width:76px;text-align:center" class="form-control form-control-sm" >&nbsp;
												<input type="text" name="roomname" value="${roomName}" readonly style="width:193px;text-align:center" class="form-control form-control-sm" >&nbsp;
											</div>
										</div>

										<table class="table table-bordered table-responsive-sm mytable" style="width:100%;">
											<tr class="mycolor1">
												<td width="80"><div id="firstpos" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
												<td id="w0" width="80">일<br>2019-06-02</td>
												<td id="w1" width="80">월<br>2019-06-03</td>
												<td id="w2" width="80">화<br>2019-06-04</td>
												<td id="w3" width="80">수<br>2019-06-05</td>
												<td id="w4" width="80">목<br>2019-06-06</td>
												<td id="w5" width="80">금<br>2019-06-07</td>
												<td id="w6" width="80">토<br>2019-06-08</td>
											</tr>
											<tr>
												<td bgcolor="#eeeeee"><b>1</b>&nbsp;<font size="1">( 9:00)</font></td>
												<td><div id="010" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
												<td><div id="011" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
												<td><div id="012" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
												<td><div id="013" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
												<td><div id="014" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
												<td><div id="015" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
												<td><div id="016" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
											</tr>
											<tr>
												<td bgcolor="#eeeeee"><b>2</b>&nbsp;<font size="1">(10:00)</font></td>
												<td><div id="020" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
												<td><div id="021" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
												<td><div id="022" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
												<td><div id="023" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
												<td><div id="024" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
												<td><div id="025" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
												<td><div id="026" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
											</tr>
											<tr>
												<td bgcolor="#eeeeee"><b>3</b>&nbsp;<font size="1">(11:00)</font></td>
												<td><div id="030" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
												<td><div id="031" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
												<td><div id="032" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
												<td><div id="033" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
												<td><div id="034" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
												<td><div id="035" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
												<td><div id="036" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
											</tr>
											<tr>
												<td bgcolor="#eeeeee"><b>4</b>&nbsp;<font size="1">(12:00)</font></td>
												<td><div id="040" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
												<td><div id="041" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
												<td><div id="042" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
												<td><div id="043" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
												<td><div id="044" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
												<td><div id="045" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
												<td><div id="046" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
											</tr>
											<tr>
												<td bgcolor="#eeeeee"><b>5</b>&nbsp;<font size="1">( 1:00)</font></td>
												<td><div id="050" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
												<td><div id="051" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
												<td><div id="052" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
												<td><div id="053" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
												<td><div id="054" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
												<td><div id="055" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
												<td><div id="056" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
											</tr>
											<tr>
												<td bgcolor="#eeeeee"><b>6</b>&nbsp;<font size="1">( 2:00)</font></td>
												<td><div id="060" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
												<td><div id="061" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
												<td><div id="062" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
												<td><div id="063" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
												<td><div id="064" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
												<td><div id="065" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
												<td><div id="066" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
											</tr>
											<tr>
												<td bgcolor="#eeeeee"><b>7</b>&nbsp;<font size="1">( 3:00)</font></td>
												<td><div id="070" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
												<td><div id="071" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
												<td><div id="072" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
												<td><div id="073" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
												<td><div id="074" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
												<td><div id="075" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
												<td><div id="076" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
											</tr>
											<tr>
												<td bgcolor="#eeeeee"><b>8</b>&nbsp;<font size="1">( 4:00)</font></td>
												<td><div id="080" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
												<td><div id="081" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
												<td><div id="082" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
												<td><div id="083" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
												<td><div id="084" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
												<td><div id="085" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
												<td><div id="086" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
											</tr>
											<tr>
												<td bgcolor="#eeeeee"><b>9</b>&nbsp;<font size="1">( 5:00)</font></td>
												<td><div id="090" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
												<td><div id="091" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
												<td><div id="092" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
												<td><div id="093" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
												<td><div id="094" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
												<td><div id="095" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
												<td><div id="096" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
											</tr>
											<tr>
												<td bgcolor="#dddddd" colspan="30" style="height:3px;padding:0px"></td>
											</tr>
											<tr>
												<td bgcolor="#eeeeee"><b>10</b>&nbsp;<font size="1">(6:00)</font></td>
												<td><div id="100" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
												<td><div id="101" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
												<td><div id="102" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
												<td><div id="103" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
												<td><div id="104" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
												<td><div id="105" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
												<td><div id="106" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
											</tr>
											<tr>
												<td bgcolor="#eeeeee"><b>11</b>&nbsp;<font size="1">(7:00)</font></td>
												<td><div id="110" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
												<td><div id="111" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
												<td><div id="112" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
												<td><div id="113" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
												<td><div id="114" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
												<td><div id="115" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
												<td><div id="116" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
											</tr>
											<tr>
												<td bgcolor="#eeeeee"><b>12</b>&nbsp;<font size="1">(8:00)</font></td>
												<td><div id="120" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
												<td><div id="121" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
												<td><div id="122" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
												<td><div id="123" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
												<td><div id="124" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
												<td><div id="125" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
												<td><div id="126" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
											</tr>
											<tr>
												<td bgcolor="#eeeeee"><b>13</b>&nbsp;<font size="1">(9:00)</font></td>
												<td><div id="130" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
												<td><div id="131" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
												<td><div id="132" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
												<td><div id="133" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
												<td><div id="134" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
												<td><div id="135" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
												<td><div id="136" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
											</tr>
											<tr>
												<td bgcolor="#eeeeee"><b>14</b>&nbsp;<font size="1">(10:00)</font></td>
												<td><div id="140" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
												<td><div id="141" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
												<td><div id="142" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
												<td><div id="143" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
												<td><div id="144" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
												<td><div id="145" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
												<td><div id="146" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
											</tr>
										</table>
										</form>

									</div>
									<div class="col" align="left">

										<form name="form2" method="post" action="">
										<div class="row" style="margin-top:67px;margin-bottom:3px">
											<div class="col" align="left">
												<div class="form-inline">
													<div class="input-group input-group-sm">
														<div class="input-group-prepend">
															<span class="input-group-text">건물</span>
														</div>
														<div class="input-group-append">
															<div class="form-inline">
																<select name="buiding_no" class="form-control form-control-sm" onChange="change_building()" style="width:150px">
																	<option id="building0"selected></option>
																	<c:forEach var="building" items="${buildingList}">
																		<option id='building${building.id}'>${building.name}</option>
																	</c:forEach>
																</select>
															</div>
														</div>
													</div>
												</div>
											</div>
											<div class="col" align="right">
													<a onclick="form1.submit();" class="btn btn-sm btn-primary"> 휴보강 신청하기 </a>
											</div>
										</div>

										<div style="width:100%; height:400px; overflow:auto">

											<table class="table table-bordered table-hover mytable" style="width:100%;">
												<thead>
													<tr class="mycolor1">
														<th>소속</th>
														<th>명칭</th>
														<th width="55"></th>
													</tr>
												</thead>
												<tbody>
													<c:forEach var="room" items="${roomList}">
														<tr name='room${room.building_id}'>
															<c:if test="${room.depart_id == depart}">
																<td>${departName}</td>
															</c:if>
															<c:if test="${room.depart_id != depart}">
																<td>공용</td>
															</c:if>
															<td>${room.name}</td>
															<td>
																<a href="javascript:sel_room('${room.id}','${room.building_id}','${room.name}');" class="btn btn-xs btn-outline-primary">선택</a>
															</td>
														</tr>
													</c:forEach>
												</tbody>
											</table>
										</div>
										</form>

									</div>
								</div>

							</div>		<!-- card body end -->
						</div>		<!-- card end -->
					</div>
						
				</div>	<!-- row end -->
<!------------------------------------------------------------------------------>
<!-- 내용 끝 -->
<!------------------------------------------------------------------------------>
<%@ include file="../section/footer.jsp" %>