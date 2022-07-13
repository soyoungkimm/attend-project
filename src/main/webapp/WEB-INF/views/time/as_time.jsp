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
								<li class="breadcrumb-item">조교</li>
								<li class="breadcrumb-item active">시간표 작성</li>
							</ol>
							<div class="clearfix"></div>
						</div>
					</div>
				</div>

				<!--- 시간표 관련 JS  ---------------------------------------------->
				<script>

					function dragEnter(ev) { ev.preventDefault(); }
					function drag(ev) {	ev.dataTransfer.setData("text", ev.target.id); }
					function drop(ev,target)
					{
						if (typeof(document.getElementById( ev.target.id ).childNodes[0])=='undefined')
						{
							ev.preventDefault();
							var data = ev.dataTransfer.getData("text");
							ev.target.appendChild(document.getElementById(data));

							unsel_lecture();
							form1.selpos.value=data;
							document.getElementById( data ).style.borderColor="red";
						}
						else
							alert("다른 과목이 있습니다.");
					}

					function sel_lecture(pos)		// 시간표내의 강의 선택
					{
						unsel_lecture();
						form1.selpos.value=pos;
						document.getElementById( pos ).style.borderColor="red";
					}

					function unsel_lecture()		// 시간표내의 강의 선택 취소
					{
						for (h=1;h<=10 ;h++){		// 시간(1-10)
							for (w=1;w<=5 ;w++) {		// 요일(1-5)
								for (g=1;g<=3 ;g++)	{		// 학년(1,2,3)
									for (b=1;b<=2 ;b++)	{		// 반(A,B)
										ban=(b==1) ? "A" : "B";
										pos1=String(h)+String(w)+String(g)+ban;
										if (h<10) pos1="0"+pos1;
										if (typeof(document.getElementById( pos1 ).childNodes[0]) != 'undefined')
										{
											document.getElementById( pos1 ).style.borderColor="#cccccc";
										}
									}}}}
					}

					function make_lecture(h)	// 새강의 그리기
					{
						str = form1.sel4.value.split("^");
						let newLecture = [
							str[0],
							str[1],
							str[2],
							str[3],
							str[4],
							"",
							"",
							h,
							str[8],
							str[9],
							str[10],
							"",
							""
						];
						newLecture=newLecture.join("^");

						draw_lecture(1,newLecture,"");
						sel_lecture(newLecture);
					}

					function postMsg(url, msg, fn) {
						let request = new XMLHttpRequest();
						request.open("POST", url, true);
						request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
						request.send(msg);
						request.onreadystatechange = function(){fn(request)};
					}

					let state = 0;
					let term = "2022^1";
					// id^강의id^학년^반^시간^요일^시작교시^시간^과목명^교수님번호^교수님^강의실번호^강의실
					let timetable = [];
					function load_lec()			// 해당학기 시간표읽어 모두 표시
					{
						if (state == 1) { //이미 검색 되었음
							if (form1.sel1.value + "^" + form1.sel2.value == term)
								return;
							state = 0; //다시 검색하기
						}
						if (state == 0) { //데이터 가져오기
							clear_lecture();
							function fn(request) {
								if (request.readyState == 4 && request.status == 200) {
									timetable = request.responseText.split("/");
									//그리기
									for (const str of timetable) {
										draw_lecture(0, str, "");
									}
									state = 1;
								}
							}
							term = form1.sel1.value + "^" + form1.sel2.value;
							postMsg("/astime/search.do", "term="+term, fn);
							state = 1;
						}
					}

					function clear_lecture()	// 시간표내의 모든 강의 선택 취소
					{
						for (h=1;h<=10 ;h++){		// 시간(1-10)
						for (w=1;w<=5 ;w++) {		// 요일(1-5)
						for (g=1;g<=3 ;g++)	{		// 학년(1,2,3)
						for (b=1;b<=2 ;b++)	{		// 반(A,B)
							ban=(b==1) ? "A" : "B";
							pos1=String(h)+String(w)+String(g)+ban;
							if (h<10) pos1="0"+pos1;
							if (typeof(document.getElementById( pos1 ).childNodes[0]) != 'undefined')
							{
								document.getElementById( pos1 ).childNodes[0].remove();
							}
						}}}}
						unsel_lecture();
					}

					function save_lecture()	// 작성한 시간표 강의내용 저장
					{
						save_data = "";
						for (h=1;h<=10 ;h++){		// 시간(1-10)
						for (w=1;w<=5 ;w++) {		// 요일(1-5)
						for (g=1;g<=3 ;g++)	{		// 학년(1,2,3)
						for (b=1;b<=2 ;b++)	{		// 반(A,B)
							ban = (b==1) ? "A":"B";
							pos=String(h)+String(w)+String(g)+ban;
							if (h<10) pos="0"+pos;
							if (typeof(document.getElementById( pos ).childNodes[0]) != 'undefined')
							{
								let data = document.getElementById( pos ).childNodes[0].id;
								data = data.split("^");
								data[6] = h; //강의 시작시간
								data[5] = w; //요일
								if (data[12] == "") {
									alert(data[8]+" 과목이 강의실 지정이 되지 않았습니다.");
									continue;
								}
								if (data[3] != ban) {
									alert(data[8]+" 강의의 반이 시간표와 다릅니다.");
									continue;
								}
								data = data.join("^");
								save_data += data + "/";
							}
						}}}}
						function fn(request) {
							if (request.readyState == 4 && request.status == 200) {
								state = 0; //강제로 다시 로드하기
								load_lec();
							}
						}
						let term = form1.sel1.value + "^" + form1.sel2.value;
						postMsg("/astime/save.do", "save="+save_data+"&term="+term, fn);
					}

					function del_lecture() 	// 선택한 강의 삭제
					{
						if (!form1.selpos.value) {	alert("먼저 강의를 선택하세요."); return; }

						pos=form1.selpos.value;
						document.getElementById( pos ).remove();
						form1.selpos.value="";
					}

					function change_room()	// 선택한 강의에 강의실 지정
					{
						if (!form1.selpos.value) {	alert("먼저 강의를 선택하세요."); return; }

						str=form1.sel6.value;
						str = str.split("^");
						lec_roomno=str[0];
						lec_room=str[1];

						str=form1.selpos.value;
						oldstr=str;
						str = str.split("^");
						str[11]=lec_roomno;
						str[12]=lec_room;
						str=str.join("^");

						draw_lecture(2,str,oldstr);
						sel_lecture(str);
					}

					function draw_lecture(kind,str,oldstr)	// 시간표에 강의 그리기
					{
						//	0	1		2	3	4	5	6		7	8		9		10		11		12
						//	id^	강의id^	학년^반^	시간^요일^시작교시^	시간^과목명^	교수님번호^교수님^	강의실번호^강의실
						let lec_id = str;
						str = str.split("^");
						lec_count=str[0];
						lec_no=str[1];
						lec_grade=str[2];
						lec_ban=str[3];
						lec_hour=str[4];
						lec_week=str[5];
						lec_start=str[6];
						lec_hours=str[7];
						lec_name=str[8];
						lec_teacherno=str[9];
						lec_teacher=str[10];
						lec_roomno=str[11];
						lec_room=str[12];
						lec_caption="<div style='cursor:pointer'><font color='blue'>"+lec_name+"</font><br>"+lec_grade+"-"+lec_ban+"</font><br>"+lec_teacher+"<br><font color='red'>"+lec_room+"</font></div>";

						if (kind==0)
						{
							pos=lec_start+lec_week+lec_grade+lec_ban;
							pos = (lec_start<10) ? "0"+pos : pos;
						}
						else if (kind==1)
							pos = "firstpos";
						else if (kind==2)
							pos=document.getElementById(oldstr).parentElement.id;

						hh=(25+34*( lec_hours-1) )+"px";

						document.getElementById( pos ).innerHTML="<div  class='lecbox_text' style='height:"+hh+"' id='"+lec_id+"' draggable='true' ondragstart='drag(event)' onclick='sel_lecture(\""+lec_id+"\")'>"+lec_caption+"</div>";
					}

					function building_change() {
						const rooms = {
							<c:forEach var="b" items="${indexList}" varStatus="status">
								${status.index} : [
								<c:forEach var="c" items="${b}">
									${c}
									<c:if test="${status.count != b.size()}">
										,
									</c:if>
								</c:forEach>
								]
								<c:if test="${status.count != indexList.size()}">
									,
								</c:if>
							</c:forEach>
						}
						buildingIndex = form1.sel5.selectedIndex;
						for (let r1 = 0; r1 < ${rooms.size()}; r1++) {
							form1.sel6.options[r1].setAttribute("hidden", "");
							for (let r2 of rooms[buildingIndex])
								if (r1 == r2)
									form1.sel6.options[r1].removeAttribute("hidden");
						}
					}

					function grade_change() {
						let grade = form1.sel3.value;
						let selectedIndex = 0;
						for (let lecture of form1.sel4.options) {
							if (grade == lecture.innerHTML[0]) {
								lecture.removeAttribute("hidden");
								if (selectedIndex == 0) {
									selectedIndex = 1;
									lecture.selected = true;
								}
							}
							else
								lecture.setAttribute("hidden", "");
						}
					}

					window.onload = function () {
						load_lec();
						grade_change();
						building_change();
					}
				</script>

				<div class="row">

					<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 col-xl-12">
						<div class="card mb-3">
							<div class="card-header mycolor3" style="padding:10px">
								<h3><i class="fa fa-table"></i> 시간표 작성</h3>
							</div>

							<div class="card-body" style="padding:10px">

								<form name="form1" action="" method="post">
								<div class="row" style="margin-bottom:3px">
									<div class="col" align="left">
										<div class="form-inline">

											<div class="input-group input-group-sm">
												<div class="input-group-prepend">
													<span class="input-group-text">학기</span>
												</div>
												<div class="input-group-append">
													<select name="sel1" class="form-control form-control-sm" onchange="javascript:load_lec();">
														<option value="2022" selected>2022</option>
														<option value="2021">2021</option>
														<option value="2020">2020</option>
														<option value="2019">2019</option>
														<option value='2018'>2018</option>
														<option value='2017'>2017</option>
														<option value='2016'>2016</option>
														<option value='2015'>2015</option>
													</select>
													&nbsp;
													<select name="sel2" class="form-control form-control-sm" onchange="javascript:load_lec();">
														<option value='1' selected>1학기</option>
														<option value='2'>2학기</option>
													</select>
												</div>
												&nbsp;<input type="button" class="btn btn-sm btn-primary" value="검색" onclick="load_lec();">
												&nbsp;<input type="button" class="btn btn-sm btn-primary" value="저장" onclick="save_lecture();">
											</div>

										</div>
									</div>
								</div>

								<div class="row" style="margin-bottom:3px">
									<div class="col" align="left">
										<div class="form-inline">

											<div class="input-group input-group-sm">
												<div class="input-group-prepend">
													<span class="input-group-text">과목</span>
												</div>
												<div class="input-group-append">
													<!-- 번호^학년^반^과목명 -->
													<select name="sel3" class="form-control form-control-sm" onchange="grade_change()">
														<c:forEach begin="1" end="${gradeSystem}" var="i">
															<option value="${i}"
																<c:if test="${i==1}">
																	selected
																</c:if>
															>${i}학년</option>
														</c:forEach>
													</select>
													&nbsp;
													<!-- id^강의id^학년^반^시간^요일^시작교시^시간^과목명^교수님번호^교수님^강의실번호^강의실 -->
													<select name="sel4" class="form-control form-control-sm" >
														<c:forEach begin="0" end="${lectures.size()}" var="index">
															<option value="${lectures[index]}">${lectureNames[index]}</option>
														</c:forEach>
													</select>
												</div>
											</div>
											&nbsp;<input type="button" class="btn btn-sm btn-warning" value="1h" onclick="make_lecture(1);">
											&nbsp;<input type="button" class="btn btn-sm btn-warning" value="2h" onclick="make_lecture(2);">
											&nbsp;<input type="button" class="btn btn-sm btn-warning" value="3h" onclick="make_lecture(3);">
											&nbsp;<input type="button" class="btn btn-sm btn-warning" value="4h" onclick="make_lecture(4);">
											&nbsp;<input type="button" class="btn btn-sm btn-primary" value="삭제" onclick="del_lecture();">

											<div class="input-group input-group-sm">
												<div class="input-group-prepend">
													&nbsp;&nbsp;&nbsp;<span class="input-group-text">강의실</span>
												</div>
												<div class="input-group-append">
													<select name="sel5" class="form-control form-control-sm" onchange="javascript:building_change();">
														<c:forEach var="building" items="${buildings}">
															<option value="${building.getId()}">${building.getName()}</option>
														</c:forEach>
													</select>
													&nbsp;
													<select name="sel6" class="form-control form-control-sm" >
														<c:forEach var="room" items="${rooms}">
															<option value="${room.getId()}^${room.getName()}">${room.getName()}</option>
														</c:forEach>
													</select>
												</div>
												&nbsp;<input type="button" class="btn btn-sm btn-primary" value="변경" onclick="change_room();">
											</div>

										</div>
									</div>
								</div>

								<input type="hidden" name="selpos" value="">

								</form>

								<table class="table table-bordered table-responsive mytable" style="width:100%">
									<tr class="mycolor1">
										<td><div id="firstpos" class='lecbox' ondrop='drop(event,tdis)' ondragover='dragEnter(event)'></div></td>
										<td colspan="6">월</td>
										<td colspan="6">화</td>
										<td colspan="6">수</td>
										<td colspan="6">목</td>
										<td colspan="6">금</td>
									</tr>
									<tr class="mycolor1">
										<td width="45">&nbsp;</td>
										<td width="45">1-A</td>
										<td width="45">1-B</td>
										<td width="45">2-A</td>
										<td width="45">2-B</td>
										<td width="45">3-A</td>
										<td width="45">3-B</td>
										<td width="45">1-A</td>
										<td width="45">1-B</td>
										<td width="45">2-A</td>
										<td width="45">2-B</td>
										<td width="45">3-A</td>
										<td width="45">3-B</td>
										<td width="45">1-A</td>
										<td width="45">1-B</td>
										<td width="45">2-A</td>
										<td width="45">2-B</td>
										<td width="45">3-A</td>
										<td width="45">3-B</td>
										<td width="45">1-A</td>
										<td width="45">1-B</td>
										<td width="45">2-A</td>
										<td width="45">2-B</td>
										<td width="45">3-A</td>
										<td width="45">3-B</td>
										<td width="45">1-A</td>
										<td width="45">1-B</td>
										<td width="45">2-A</td>
										<td width="45">2-B</td>
										<td width="45">3-A</td>
										<td width="45">3-B</td>
									</tr>
									<tr>
										<td bgcolor="#eeeeee"><b>1</b>&nbsp;<font size="1">( 9:00)</font></td>
										<td><div id="0111A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0111B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0112A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0112B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0113A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0113B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0121A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0121B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0122A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0122B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0123A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0123B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0131A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0131B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0132A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0132B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0133A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0133B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0141A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0141B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0142A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0142B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0143A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0143B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0151A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0151B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0152A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0152B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0153A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0153B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
									</tr>
									<tr>
										<td bgcolor="#eeeeee"><b>2</b>&nbsp;<font size="1">(10:00)</font></td>
										<td><div id="0211A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0211B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0212A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0212B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0213A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0213B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0221A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0221B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0222A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0222B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0223A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0223B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0231A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0231B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0232A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0232B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0233A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0233B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0241A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0241B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0242A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0242B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0243A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0243B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0251A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0251B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0252A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0252B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0253A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0253B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
									</tr>
									<tr>
										<td bgcolor="#eeeeee"><b>3</b>&nbsp;<font size="1">(11:00)</font></td>
										<td><div id="0311A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0311B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0312A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0312B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0313A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0313B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0321A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0321B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0322A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0322B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0323A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0323B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0331A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0331B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0332A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0332B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0333A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0333B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0341A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0341B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0342A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0342B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0343A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0343B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0351A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0351B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0352A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0352B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0353A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0353B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
									</tr>
									<tr>
										<td bgcolor="#eeeeee"><b>4</b>&nbsp;<font size="1">(12:00)</font></td>
										<td><div id="0411A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0411B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0412A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0412B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0413A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0413B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0421A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0421B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0422A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0422B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0423A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0423B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0431A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0431B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0432A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0432B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0433A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0433B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0441A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0441B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0442A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0442B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0443A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0443B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0451A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0451B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0452A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0452B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0453A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0453B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
									</tr>
									<tr>
										<td bgcolor="#eeeeee"><b>5</b>&nbsp;<font size="1">( 1:00)</font></td>
										<td><div id="0511A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0511B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0512A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0512B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0513A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0513B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0521A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0521B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0522A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0522B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0523A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0523B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0531A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0531B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0532A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0532B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0533A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0533B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0541A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0541B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0542A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0542B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0543A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0543B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0551A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0551B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0552A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0552B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0553A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0553B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
									</tr>
									<tr>
										<td bgcolor="#eeeeee"><b>6</b>&nbsp;<font size="1">( 2:00)</font></td>
										<td><div id="0611A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0611B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0612A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0612B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0613A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0613B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0621A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0621B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0622A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0622B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0623A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0623B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0631A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0631B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0632A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0632B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0633A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0633B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0641A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0641B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0642A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0642B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0643A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0643B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0651A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0651B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0652A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0652B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0653A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0653B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
									</tr>
									<tr>
										<td bgcolor="#eeeeee"><b>7</b>&nbsp;<font size="1">( 3:00)</font></td>
										<td><div id="0711A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0711B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0712A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0712B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0713A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0713B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0721A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0721B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0722A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0722B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0723A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0723B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0731A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0731B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0732A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0732B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0733A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0733B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0741A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0741B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0742A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0742B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0743A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0743B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0751A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0751B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0752A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0752B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0753A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0753B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
									</tr>
									<tr>
										<td bgcolor="#eeeeee"><b>8</b>&nbsp;<font size="1">( 4:00)</font></td>
										<td><div id="0811A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0811B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0812A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0812B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0813A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0813B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0821A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0821B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0822A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0822B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0823A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0823B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0831A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0831B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0832A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0832B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0833A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0833B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0841A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0841B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0842A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0842B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0843A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0843B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0851A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0851B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0852A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0852B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0853A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0853B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
									</tr>
									<tr>
										<td bgcolor="#eeeeee"><b>9</b>&nbsp;<font size="1">( 5:00)</font></td>
										<td><div id="0911A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0911B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0912A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0912B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0913A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0913B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0921A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0921B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0922A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0922B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0923A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0923B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0931A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0931B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0932A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0932B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0933A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0933B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0941A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0941B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0942A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0942B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0943A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0943B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0951A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0951B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0952A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0952B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0953A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="0953B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
									</tr>
									<tr>
										<td bgcolor="#eeeeee" colspan="30" style="height:3px;padding:0px"></td>
									</tr>
									<tr>
										<td bgcolor="#eeeeee"><b>10</b>&nbsp;<font size="1">(6:00)</font></td>
										<td><div id="1011A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1011B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1012A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1012B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1013A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1013B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1021A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1021B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1022A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1022B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1023A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1023B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1031A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1031B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1032A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1032B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1033A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1033B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1041A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1041B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1042A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1042B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1043A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1043B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1051A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1051B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1052A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1052B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1053A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1053B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
									</tr>
									<tr>
										<td bgcolor="#eeeeee"><b>11</b>&nbsp;<font size="1">(7:00)</font></td>
										<td><div id="1111A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1111B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1112A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1112B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1113A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1113B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1121A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1121B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1122A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1122B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1123A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1123B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1131A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1131B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1132A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1132B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1133A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1133B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1141A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1141B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1142A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1142B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1143A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1143B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1151A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1151B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1152A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1152B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1153A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1153B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
									</tr>
									<tr>
										<td bgcolor="#eeeeee"><b>12</b>&nbsp;<font size="1">(8:00)</font></td>
										<td><div id="1211A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1211B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1212A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1212B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1213A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1213B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1221A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1221B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1222A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1222B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1223A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1223B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1231A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1231B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1232A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1232B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1233A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1233B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1241A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1241B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1242A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1242B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1243A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1243B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1251A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1251B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1252A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1252B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1253A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1253B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
									</tr>
									<tr>
										<td bgcolor="#eeeeee"><b>13</b>&nbsp;<font size="1">(9:00)</font></td>
										<td><div id="1311A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1311B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1312A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1312B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1313A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1313B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1321A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1321B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1322A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1322B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1323A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1323B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1331A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1331B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1332A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1332B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1333A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1333B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1341A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1341B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1342A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1342B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1343A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1343B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1351A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1351B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1352A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1352B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1353A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1353B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
									</tr>
									<tr>
										<td bgcolor="#eeeeee"><b>14</b>&nbsp;<font size="1">(10:00)</font></td>
										<td><div id="1411A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1411B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1412A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1412B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1413A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1413B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1421A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1421B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1422A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1422B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1423A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1423B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1431A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1431B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1432A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1432B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1433A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1433B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1441A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1441B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1442A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1442B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1443A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1443B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1451A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1451B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1452A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1452B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1453A" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
										<td><div id="1453B" class="lecbox" ondrop="drop(event,this)" ondragover="dragEnter(event)"></div></td>
									</tr>
								</table>
							</div>		<!-- card body end -->
						</div>		<!-- card end -->
					</div>

				</div>	<!-- row end -->
<!------------------------------------------------------------------------------>
<!-- 내용 끝 -->
<!------------------------------------------------------------------------------>
<%@ include file="../section/footer.jsp" %>