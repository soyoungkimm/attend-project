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
								<li class="breadcrumb-item active">학과별 시간표</li>
							</ol>
							<div class="clearfix"></div>
						</div>
					</div>
				</div>

				<!--- 시간표 관련 JS  ---------------------------------------------->
				<script>
					function draw_lecture(str)	// 시간표에 강의 그리기
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

						pos=lec_start+lec_week+lec_grade+lec_ban;
						pos = (lec_start<10) ? "0"+pos : pos;

						hh=(25+34*( lec_hours-1) )+"px";

						document.getElementById( pos ).innerHTML="<div  class='lecbox_text' style='height:"+hh+"' id='"+lec_id+"' draggable='true' ondragstart='drag(event)' onclick='sel_lecture(\""+lec_id+"\")'>"+lec_caption+"</div>";
					}

					function clear_lecture()	// 시간표내의 모든 강의 선택 삭제
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
					}

					function postMsg(url, msg, fn) {
						let request = new XMLHttpRequest();
						request.open("POST", url, true);
						request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
						request.send(msg);
						request.onreadystatechange = function () {
							fn(request)
						};
					}

					let state = 0;
					let term = "2022^1", depart;
					// id^강의id^학년^반^시간^요일^시작교시^시간^과목명^교수님번호^교수님^강의실번호^강의실
					let timetable = [];
					function load_lec(s)			// 해당학기 시간표읽어 모두 표시
					{
						if (state == 1) { //이미 검색 되었음
							if (form1.sel1.value + "^" + form1.sel2.value == term && depart == form1.sel3.value)
								return;
							state = 0; //다시 검색하기
						}

						if (state == 0 || s) { //데이터 가져오기
							clear_lecture();
							function fn(request) {
								if (request.readyState == 4 && request.status == 200) {
									timetable = request.responseText.split("/");
									//그리기
									for (const str of timetable) {
										if (str == "") continue;
										draw_lecture(str);
									}
									state = 1;
								}
							}
							term = form1.sel1.value + "^" + form1.sel2.value;
							depart = form1.sel3.value;
							postMsg("/astime/search.do", "term="+term+"&departId="+depart, fn);
							state = 1;
						}
					}

					window.onload = function () {
						load_lec();
					}
				</script>

				<div class="row">
				
					<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 col-xl-12">
						<div class="card mb-3">
							<div class="card-header mycolor3" style="padding:10px">
								<h3><i class="fa fa-table"></i> 학과별 시간표</h3>
							</div>
								
							<div class="card-body" style="padding:10px">

								<form name="form1" action="" method="post">
								<div class="row" style="margin-bottom:3px">
									<div class="col" align="left">
										<div class="form-inline" style="line-height:0.2rem">

											<div class="input-group input-group-sm">
												<div class="input-group-prepend">
													<span class="input-group-text">년도</span>
												</div>
												<div class="input-group-append">
													<select name="sel1" class="form-control form-control-sm" onchange="javascript:load_lec()">
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
													<select name="sel2" class="form-control form-control-sm" onchange="javascript:load_lec()">
														<option value='1' selected>1학기</option>
														<option value='2'>2학기</option>
													</select>
												</div>
											</div>
											&nbsp;
											<div class="input-group input-group-sm">
												<div class="input-group-prepend">
													<span class="input-group-text">학과</span>
												</div>
												<div class="input-group-append">
													<select name="sel3" id="sel3" class="form-control form-control-sm" onchange="javascript:load_lec()">
														<c:forEach var="item" items="${departLists}">
															<option value="${item.getId()}"
																<c:if test="${item.getName()==departName}">
																	selected
																</c:if>
															>${item.getName()}</option>
														</c:forEach>
													</select>
												</div>
												<input type="button" id="search" class="btn btn-sm btn-primary" value="검색" onclick="javascript:load_lec(true);">
											</div>

										</div>
									</div>
								</div>

								</form>

								<table class="table table-bordered table-responsive mytable" style="width:100%;padding:0px">
									<tr class="mycolor1">
										<td></td>
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
										<td><div id="0111A" class="lecbox"></div></td>
										<td><div id="0111B" class="lecbox"></div></td>
										<td><div id="0112A" class="lecbox"></div></td>
										<td><div id="0112B" class="lecbox"></div></td>
										<td><div id="0113A" class="lecbox"></div></td>
										<td><div id="0113B" class="lecbox"></div></td>
										<td><div id="0121A" class="lecbox"></div></td>
										<td><div id="0121B" class="lecbox"></div></td>
										<td><div id="0122A" class="lecbox"></div></td>
										<td><div id="0122B" class="lecbox"></div></td>
										<td><div id="0123A" class="lecbox"></div></td>
										<td><div id="0123B" class="lecbox"></div></td>
										<td><div id="0131A" class="lecbox"></div></td>
										<td><div id="0131B" class="lecbox"></div></td>
										<td><div id="0132A" class="lecbox"></div></td>
										<td><div id="0132B" class="lecbox"></div></td>
										<td><div id="0133A" class="lecbox"></div></td>
										<td><div id="0133B" class="lecbox"></div></td>
										<td><div id="0141A" class="lecbox"></div></td>
										<td><div id="0141B" class="lecbox"></div></td>
										<td><div id="0142A" class="lecbox"></div></td>
										<td><div id="0142B" class="lecbox"></div></td>
										<td><div id="0143A" class="lecbox"></div></td>
										<td><div id="0143B" class="lecbox"></div></td>
										<td><div id="0151A" class="lecbox"></div></td>
										<td><div id="0151B" class="lecbox"></div></td>
										<td><div id="0152A" class="lecbox"></div></td>
										<td><div id="0152B" class="lecbox"></div></td>
										<td><div id="0153A" class="lecbox"></div></td>
										<td><div id="0153B" class="lecbox"></div></td>
									</tr>
									<tr>
										<td bgcolor="#eeeeee"><b>2</b>&nbsp;<font size="1">(10:00)</font></td>
										<td><div id="0211A" class="lecbox"></div></td>
										<td><div id="0211B" class="lecbox"></div></td>
										<td><div id="0212A" class="lecbox"></div></td>
										<td><div id="0212B" class="lecbox"></div></td>
										<td><div id="0213A" class="lecbox"></div></td>
										<td><div id="0213B" class="lecbox"></div></td>
										<td><div id="0221A" class="lecbox"></div></td>
										<td><div id="0221B" class="lecbox"></div></td>
										<td><div id="0222A" class="lecbox"></div></td>
										<td><div id="0222B" class="lecbox"></div></td>
										<td><div id="0223A" class="lecbox"></div></td>
										<td><div id="0223B" class="lecbox"></div></td>
										<td><div id="0231A" class="lecbox"></div></td>
										<td><div id="0231B" class="lecbox"></div></td>
										<td><div id="0232A" class="lecbox"></div></td>
										<td><div id="0232B" class="lecbox"></div></td>
										<td><div id="0233A" class="lecbox"></div></td>
										<td><div id="0233B" class="lecbox"></div></td>
										<td><div id="0241A" class="lecbox"></div></td>
										<td><div id="0241B" class="lecbox"></div></td>
										<td><div id="0242A" class="lecbox"></div></td>
										<td><div id="0242B" class="lecbox"></div></td>
										<td><div id="0243A" class="lecbox"></div></td>
										<td><div id="0243B" class="lecbox"></div></td>
										<td><div id="0251A" class="lecbox"></div></td>
										<td><div id="0251B" class="lecbox"></div></td>
										<td><div id="0252A" class="lecbox"></div></td>
										<td><div id="0252B" class="lecbox"></div></td>
										<td><div id="0253A" class="lecbox"></div></td>
										<td><div id="0253B" class="lecbox"></div></td>
									</tr>
									<tr>
										<td bgcolor="#eeeeee"><b>3</b>&nbsp;<font size="1">(11:00)</font></td>
										<td><div id="0311A" class="lecbox"></div></td>
										<td><div id="0311B" class="lecbox"></div></td>
										<td><div id="0312A" class="lecbox"></div></td>
										<td><div id="0312B" class="lecbox"></div></td>
										<td><div id="0313A" class="lecbox"></div></td>
										<td><div id="0313B" class="lecbox"></div></td>
										<td><div id="0321A" class="lecbox"></div></td>
										<td><div id="0321B" class="lecbox"></div></td>
										<td><div id="0322A" class="lecbox"></div></td>
										<td><div id="0322B" class="lecbox"></div></td>
										<td><div id="0323A" class="lecbox"></div></td>
										<td><div id="0323B" class="lecbox"></div></td>
										<td><div id="0331A" class="lecbox"></div></td>
										<td><div id="0331B" class="lecbox"></div></td>
										<td><div id="0332A" class="lecbox"></div></td>
										<td><div id="0332B" class="lecbox"></div></td>
										<td><div id="0333A" class="lecbox"></div></td>
										<td><div id="0333B" class="lecbox"></div></td>
										<td><div id="0341A" class="lecbox"></div></td>
										<td><div id="0341B" class="lecbox"></div></td>
										<td><div id="0342A" class="lecbox"></div></td>
										<td><div id="0342B" class="lecbox"></div></td>
										<td><div id="0343A" class="lecbox"></div></td>
										<td><div id="0343B" class="lecbox"></div></td>
										<td><div id="0351A" class="lecbox"></div></td>
										<td><div id="0351B" class="lecbox"></div></td>
										<td><div id="0352A" class="lecbox"></div></td>
										<td><div id="0352B" class="lecbox"></div></td>
										<td><div id="0353A" class="lecbox"></div></td>
										<td><div id="0353B" class="lecbox"></div></td>
									</tr>
									<tr>
										<td bgcolor="#eeeeee"><b>4</b>&nbsp;<font size="1">(12:00)</font></td>
										<td><div id="0411A" class="lecbox"></div></td>
										<td><div id="0411B" class="lecbox"></div></td>
										<td><div id="0412A" class="lecbox"></div></td>
										<td><div id="0412B" class="lecbox"></div></td>
										<td><div id="0413A" class="lecbox"></div></td>
										<td><div id="0413B" class="lecbox"></div></td>
										<td><div id="0421A" class="lecbox"></div></td>
										<td><div id="0421B" class="lecbox"></div></td>
										<td><div id="0422A" class="lecbox"></div></td>
										<td><div id="0422B" class="lecbox"></div></td>
										<td><div id="0423A" class="lecbox"></div></td>
										<td><div id="0423B" class="lecbox"></div></td>
										<td><div id="0431A" class="lecbox"></div></td>
										<td><div id="0431B" class="lecbox"></div></td>
										<td><div id="0432A" class="lecbox"></div></td>
										<td><div id="0432B" class="lecbox"></div></td>
										<td><div id="0433A" class="lecbox"></div></td>
										<td><div id="0433B" class="lecbox"></div></td>
										<td><div id="0441A" class="lecbox"></div></td>
										<td><div id="0441B" class="lecbox"></div></td>
										<td><div id="0442A" class="lecbox"></div></td>
										<td><div id="0442B" class="lecbox"></div></td>
										<td><div id="0443A" class="lecbox"></div></td>
										<td><div id="0443B" class="lecbox"></div></td>
										<td><div id="0451A" class="lecbox"></div></td>
										<td><div id="0451B" class="lecbox"></div></td>
										<td><div id="0452A" class="lecbox"></div></td>
										<td><div id="0452B" class="lecbox"></div></td>
										<td><div id="0453A" class="lecbox"></div></td>
										<td><div id="0453B" class="lecbox"></div></td>
									</tr>
									<tr>
										<td bgcolor="#eeeeee"><b>5</b>&nbsp;<font size="1">( 1:00)</font></td>
										<td><div id="0511A" class="lecbox"></div></td>
										<td><div id="0511B" class="lecbox"></div></td>
										<td><div id="0512A" class="lecbox"></div></td>
										<td><div id="0512B" class="lecbox"></div></td>
										<td><div id="0513A" class="lecbox"></div></td>
										<td><div id="0513B" class="lecbox"></div></td>
										<td><div id="0521A" class="lecbox"></div></td>
										<td><div id="0521B" class="lecbox"></div></td>
										<td><div id="0522A" class="lecbox"></div></td>
										<td><div id="0522B" class="lecbox"></div></td>
										<td><div id="0523A" class="lecbox"></div></td>
										<td><div id="0523B" class="lecbox"></div></td>
										<td><div id="0531A" class="lecbox"></div></td>
										<td><div id="0531B" class="lecbox"></div></td>
										<td><div id="0532A" class="lecbox"></div></td>
										<td><div id="0532B" class="lecbox"></div></td>
										<td><div id="0533A" class="lecbox"></div></td>
										<td><div id="0533B" class="lecbox"></div></td>
										<td><div id="0541A" class="lecbox"></div></td>
										<td><div id="0541B" class="lecbox"></div></td>
										<td><div id="0542A" class="lecbox"></div></td>
										<td><div id="0542B" class="lecbox"></div></td>
										<td><div id="0543A" class="lecbox"></div></td>
										<td><div id="0543B" class="lecbox"></div></td>
										<td><div id="0551A" class="lecbox"></div></td>
										<td><div id="0551B" class="lecbox"></div></td>
										<td><div id="0552A" class="lecbox"></div></td>
										<td><div id="0552B" class="lecbox"></div></td>
										<td><div id="0553A" class="lecbox"></div></td>
										<td><div id="0553B" class="lecbox"></div></td>
									</tr>
									<tr>
										<td bgcolor="#eeeeee"><b>6</b>&nbsp;<font size="1">( 2:00)</font></td>
										<td><div id="0611A" class="lecbox"></div></td>
										<td><div id="0611B" class="lecbox"></div></td>
										<td><div id="0612A" class="lecbox"></div></td>
										<td><div id="0612B" class="lecbox"></div></td>
										<td><div id="0613A" class="lecbox"></div></td>
										<td><div id="0613B" class="lecbox"></div></td>
										<td><div id="0621A" class="lecbox"></div></td>
										<td><div id="0621B" class="lecbox"></div></td>
										<td><div id="0622A" class="lecbox"></div></td>
										<td><div id="0622B" class="lecbox"></div></td>
										<td><div id="0623A" class="lecbox"></div></td>
										<td><div id="0623B" class="lecbox"></div></td>
										<td><div id="0631A" class="lecbox"></div></td>
										<td><div id="0631B" class="lecbox"></div></td>
										<td><div id="0632A" class="lecbox"></div></td>
										<td><div id="0632B" class="lecbox"></div></td>
										<td><div id="0633A" class="lecbox"></div></td>
										<td><div id="0633B" class="lecbox"></div></td>
										<td><div id="0641A" class="lecbox"></div></td>
										<td><div id="0641B" class="lecbox"></div></td>
										<td><div id="0642A" class="lecbox"></div></td>
										<td><div id="0642B" class="lecbox"></div></td>
										<td><div id="0643A" class="lecbox"></div></td>
										<td><div id="0643B" class="lecbox"></div></td>
										<td><div id="0651A" class="lecbox"></div></td>
										<td><div id="0651B" class="lecbox"></div></td>
										<td><div id="0652A" class="lecbox"></div></td>
										<td><div id="0652B" class="lecbox"></div></td>
										<td><div id="0653A" class="lecbox"></div></td>
										<td><div id="0653B" class="lecbox"></div></td>
									</tr>
									<tr>
										<td bgcolor="#eeeeee"><b>7</b>&nbsp;<font size="1">( 3:00)</font></td>
										<td><div id="0711A" class="lecbox"></div></td>
										<td><div id="0711B" class="lecbox"></div></td>
										<td><div id="0712A" class="lecbox"></div></td>
										<td><div id="0712B" class="lecbox"></div></td>
										<td><div id="0713A" class="lecbox"></div></td>
										<td><div id="0713B" class="lecbox"></div></td>
										<td><div id="0721A" class="lecbox"></div></td>
										<td><div id="0721B" class="lecbox"></div></td>
										<td><div id="0722A" class="lecbox"></div></td>
										<td><div id="0722B" class="lecbox"></div></td>
										<td><div id="0723A" class="lecbox"></div></td>
										<td><div id="0723B" class="lecbox"></div></td>
										<td><div id="0731A" class="lecbox"></div></td>
										<td><div id="0731B" class="lecbox"></div></td>
										<td><div id="0732A" class="lecbox"></div></td>
										<td><div id="0732B" class="lecbox"></div></td>
										<td><div id="0733A" class="lecbox"></div></td>
										<td><div id="0733B" class="lecbox"></div></td>
										<td><div id="0741A" class="lecbox"></div></td>
										<td><div id="0741B" class="lecbox"></div></td>
										<td><div id="0742A" class="lecbox"></div></td>
										<td><div id="0742B" class="lecbox"></div></td>
										<td><div id="0743A" class="lecbox"></div></td>
										<td><div id="0743B" class="lecbox"></div></td>
										<td><div id="0751A" class="lecbox"></div></td>
										<td><div id="0751B" class="lecbox"></div></td>
										<td><div id="0752A" class="lecbox"></div></td>
										<td><div id="0752B" class="lecbox"></div></td>
										<td><div id="0753A" class="lecbox"></div></td>
										<td><div id="0753B" class="lecbox"></div></td>
									</tr>
									<tr>
										<td bgcolor="#eeeeee"><b>8</b>&nbsp;<font size="1">( 4:00)</font></td>
										<td><div id="0811A" class="lecbox"></div></td>
										<td><div id="0811B" class="lecbox"></div></td>
										<td><div id="0812A" class="lecbox"></div></td>
										<td><div id="0812B" class="lecbox"></div></td>
										<td><div id="0813A" class="lecbox"></div></td>
										<td><div id="0813B" class="lecbox"></div></td>
										<td><div id="0821A" class="lecbox"></div></td>
										<td><div id="0821B" class="lecbox"></div></td>
										<td><div id="0822A" class="lecbox"></div></td>
										<td><div id="0822B" class="lecbox"></div></td>
										<td><div id="0823A" class="lecbox"></div></td>
										<td><div id="0823B" class="lecbox"></div></td>
										<td><div id="0831A" class="lecbox"></div></td>
										<td><div id="0831B" class="lecbox"></div></td>
										<td><div id="0832A" class="lecbox"></div></td>
										<td><div id="0832B" class="lecbox"></div></td>
										<td><div id="0833A" class="lecbox"></div></td>
										<td><div id="0833B" class="lecbox"></div></td>
										<td><div id="0841A" class="lecbox"></div></td>
										<td><div id="0841B" class="lecbox"></div></td>
										<td><div id="0842A" class="lecbox"></div></td>
										<td><div id="0842B" class="lecbox"></div></td>
										<td><div id="0843A" class="lecbox"></div></td>
										<td><div id="0843B" class="lecbox"></div></td>
										<td><div id="0851A" class="lecbox"></div></td>
										<td><div id="0851B" class="lecbox"></div></td>
										<td><div id="0852A" class="lecbox"></div></td>
										<td><div id="0852B" class="lecbox"></div></td>
										<td><div id="0853A" class="lecbox"></div></td>
										<td><div id="0853B" class="lecbox"></div></td>
									</tr>
									<tr>
										<td bgcolor="#eeeeee"><b>9</b>&nbsp;<font size="1">( 5:00)</font></td>
										<td><div id="0911A" class="lecbox"></div></td>
										<td><div id="0911B" class="lecbox"></div></td>
										<td><div id="0912A" class="lecbox"></div></td>
										<td><div id="0912B" class="lecbox"></div></td>
										<td><div id="0913A" class="lecbox"></div></td>
										<td><div id="0913B" class="lecbox"></div></td>
										<td><div id="0921A" class="lecbox"></div></td>
										<td><div id="0921B" class="lecbox"></div></td>
										<td><div id="0922A" class="lecbox"></div></td>
										<td><div id="0922B" class="lecbox"></div></td>
										<td><div id="0923A" class="lecbox"></div></td>
										<td><div id="0923B" class="lecbox"></div></td>
										<td><div id="0931A" class="lecbox"></div></td>
										<td><div id="0931B" class="lecbox"></div></td>
										<td><div id="0932A" class="lecbox"></div></td>
										<td><div id="0932B" class="lecbox"></div></td>
										<td><div id="0933A" class="lecbox"></div></td>
										<td><div id="0933B" class="lecbox"></div></td>
										<td><div id="0941A" class="lecbox"></div></td>
										<td><div id="0941B" class="lecbox"></div></td>
										<td><div id="0942A" class="lecbox"></div></td>
										<td><div id="0942B" class="lecbox"></div></td>
										<td><div id="0943A" class="lecbox"></div></td>
										<td><div id="0943B" class="lecbox"></div></td>
										<td><div id="0951A" class="lecbox"></div></td>
										<td><div id="0951B" class="lecbox"></div></td>
										<td><div id="0952A" class="lecbox"></div></td>
										<td><div id="0952B" class="lecbox"></div></td>
										<td><div id="0953A" class="lecbox"></div></td>
										<td><div id="0953B" class="lecbox"></div></td>
									</tr>
									<tr>
										<td bgcolor="#dddddd" colspan="31" style="height:3px;padding:0px"></td>
									</tr>
									<tr>
										<td bgcolor="#eeeeee"><b>10</b>&nbsp;<font size="1">(6:00)</font></td>
										<td><div id="1011A" class="lecbox"></div></td>
										<td><div id="1011B" class="lecbox"></div></td>
										<td><div id="1012A" class="lecbox"></div></td>
										<td><div id="1012B" class="lecbox"></div></td>
										<td><div id="1013A" class="lecbox"></div></td>
										<td><div id="1013B" class="lecbox"></div></td>
										<td><div id="1021A" class="lecbox"></div></td>
										<td><div id="1021B" class="lecbox"></div></td>
										<td><div id="1022A" class="lecbox"></div></td>
										<td><div id="1022B" class="lecbox"></div></td>
										<td><div id="1023A" class="lecbox"></div></td>
										<td><div id="1023B" class="lecbox"></div></td>
										<td><div id="1031A" class="lecbox"></div></td>
										<td><div id="1031B" class="lecbox"></div></td>
										<td><div id="1032A" class="lecbox"></div></td>
										<td><div id="1032B" class="lecbox"></div></td>
										<td><div id="1033A" class="lecbox"></div></td>
										<td><div id="1033B" class="lecbox"></div></td>
										<td><div id="1041A" class="lecbox"></div></td>
										<td><div id="1041B" class="lecbox"></div></td>
										<td><div id="1042A" class="lecbox"></div></td>
										<td><div id="1042B" class="lecbox"></div></td>
										<td><div id="1043A" class="lecbox"></div></td>
										<td><div id="1043B" class="lecbox"></div></td>
										<td><div id="1051A" class="lecbox"></div></td>
										<td><div id="1051B" class="lecbox"></div></td>
										<td><div id="1052A" class="lecbox"></div></td>
										<td><div id="1052B" class="lecbox"></div></td>
										<td><div id="1053A" class="lecbox"></div></td>
										<td><div id="1053B" class="lecbox"></div></td>
									</tr>
									<tr>
										<td bgcolor="#eeeeee"><b>11</b>&nbsp;<font size="1">(7:00)</font></td>
										<td><div id="1111A" class="lecbox"></div></td>
										<td><div id="1111B" class="lecbox"></div></td>
										<td><div id="1112A" class="lecbox"></div></td>
										<td><div id="1112B" class="lecbox"></div></td>
										<td><div id="1113A" class="lecbox"></div></td>
										<td><div id="1113B" class="lecbox"></div></td>
										<td><div id="1121A" class="lecbox"></div></td>
										<td><div id="1121B" class="lecbox"></div></td>
										<td><div id="1122A" class="lecbox"></div></td>
										<td><div id="1122B" class="lecbox"></div></td>
										<td><div id="1123A" class="lecbox"></div></td>
										<td><div id="1123B" class="lecbox"></div></td>
										<td><div id="1131A" class="lecbox"></div></td>
										<td><div id="1131B" class="lecbox"></div></td>
										<td><div id="1132A" class="lecbox"></div></td>
										<td><div id="1132B" class="lecbox"></div></td>
										<td><div id="1133A" class="lecbox"></div></td>
										<td><div id="1133B" class="lecbox"></div></td>
										<td><div id="1141A" class="lecbox"></div></td>
										<td><div id="1141B" class="lecbox"></div></td>
										<td><div id="1142A" class="lecbox"></div></td>
										<td><div id="1142B" class="lecbox"></div></td>
										<td><div id="1143A" class="lecbox"></div></td>
										<td><div id="1143B" class="lecbox"></div></td>
										<td><div id="1151A" class="lecbox"></div></td>
										<td><div id="1151B" class="lecbox"></div></td>
										<td><div id="1152A" class="lecbox"></div></td>
										<td><div id="1152B" class="lecbox"></div></td>
										<td><div id="1153A" class="lecbox"></div></td>
										<td><div id="1153B" class="lecbox"></div></td>
									</tr>
									<tr>
										<td bgcolor="#eeeeee"><b>12</b>&nbsp;<font size="1">(8:00)</font></td>
										<td><div id="1211A" class="lecbox"></div></td>
										<td><div id="1211B" class="lecbox"></div></td>
										<td><div id="1212A" class="lecbox"></div></td>
										<td><div id="1212B" class="lecbox"></div></td>
										<td><div id="1213A" class="lecbox"></div></td>
										<td><div id="1213B" class="lecbox"></div></td>
										<td><div id="1221A" class="lecbox"></div></td>
										<td><div id="1221B" class="lecbox"></div></td>
										<td><div id="1222A" class="lecbox"></div></td>
										<td><div id="1222B" class="lecbox"></div></td>
										<td><div id="1223A" class="lecbox"></div></td>
										<td><div id="1223B" class="lecbox"></div></td>
										<td><div id="1231A" class="lecbox"></div></td>
										<td><div id="1231B" class="lecbox"></div></td>
										<td><div id="1232A" class="lecbox"></div></td>
										<td><div id="1232B" class="lecbox"></div></td>
										<td><div id="1233A" class="lecbox"></div></td>
										<td><div id="1233B" class="lecbox"></div></td>
										<td><div id="1241A" class="lecbox"></div></td>
										<td><div id="1241B" class="lecbox"></div></td>
										<td><div id="1242A" class="lecbox"></div></td>
										<td><div id="1242B" class="lecbox"></div></td>
										<td><div id="1243A" class="lecbox"></div></td>
										<td><div id="1243B" class="lecbox"></div></td>
										<td><div id="1251A" class="lecbox"></div></td>
										<td><div id="1251B" class="lecbox"></div></td>
										<td><div id="1252A" class="lecbox"></div></td>
										<td><div id="1252B" class="lecbox"></div></td>
										<td><div id="1253A" class="lecbox"></div></td>
										<td><div id="1253B" class="lecbox"></div></td>
									</tr>
									<tr>
										<td bgcolor="#eeeeee"><b>13</b>&nbsp;<font size="1">(9:00)</font></td>
										<td><div id="1311A" class="lecbox"></div></td>
										<td><div id="1311B" class="lecbox"></div></td>
										<td><div id="1312A" class="lecbox"></div></td>
										<td><div id="1312B" class="lecbox"></div></td>
										<td><div id="1313A" class="lecbox"></div></td>
										<td><div id="1313B" class="lecbox"></div></td>
										<td><div id="1321A" class="lecbox"></div></td>
										<td><div id="1321B" class="lecbox"></div></td>
										<td><div id="1322A" class="lecbox"></div></td>
										<td><div id="1322B" class="lecbox"></div></td>
										<td><div id="1323A" class="lecbox"></div></td>
										<td><div id="1323B" class="lecbox"></div></td>
										<td><div id="1331A" class="lecbox"></div></td>
										<td><div id="1331B" class="lecbox"></div></td>
										<td><div id="1332A" class="lecbox"></div></td>
										<td><div id="1332B" class="lecbox"></div></td>
										<td><div id="1333A" class="lecbox"></div></td>
										<td><div id="1333B" class="lecbox"></div></td>
										<td><div id="1341A" class="lecbox"></div></td>
										<td><div id="1341B" class="lecbox"></div></td>
										<td><div id="1342A" class="lecbox"></div></td>
										<td><div id="1342B" class="lecbox"></div></td>
										<td><div id="1343A" class="lecbox"></div></td>
										<td><div id="1343B" class="lecbox"></div></td>
										<td><div id="1351A" class="lecbox"></div></td>
										<td><div id="1351B" class="lecbox"></div></td>
										<td><div id="1352A" class="lecbox"></div></td>
										<td><div id="1352B" class="lecbox"></div></td>
										<td><div id="1353A" class="lecbox"></div></td>
										<td><div id="1353B" class="lecbox"></div></td>
									</tr>
									<tr>
										<td bgcolor="#eeeeee"><b>14</b>&nbsp;<font size="1">(10:00)</font></td>
										<td><div id="1411A" class="lecbox"></div></td>
										<td><div id="1411B" class="lecbox"></div></td>
										<td><div id="1412A" class="lecbox"></div></td>
										<td><div id="1412B" class="lecbox"></div></td>
										<td><div id="1413A" class="lecbox"></div></td>
										<td><div id="1413B" class="lecbox"></div></td>
										<td><div id="1421A" class="lecbox"></div></td>
										<td><div id="1421B" class="lecbox"></div></td>
										<td><div id="1422A" class="lecbox"></div></td>
										<td><div id="1422B" class="lecbox"></div></td>
										<td><div id="1423A" class="lecbox"></div></td>
										<td><div id="1423B" class="lecbox"></div></td>
										<td><div id="1431A" class="lecbox"></div></td>
										<td><div id="1431B" class="lecbox"></div></td>
										<td><div id="1432A" class="lecbox"></div></td>
										<td><div id="1432B" class="lecbox"></div></td>
										<td><div id="1433A" class="lecbox"></div></td>
										<td><div id="1433B" class="lecbox"></div></td>
										<td><div id="1441A" class="lecbox"></div></td>
										<td><div id="1441B" class="lecbox"></div></td>
										<td><div id="1442A" class="lecbox"></div></td>
										<td><div id="1442B" class="lecbox"></div></td>
										<td><div id="1443A" class="lecbox"></div></td>
										<td><div id="1443B" class="lecbox"></div></td>
										<td><div id="1451A" class="lecbox"></div></td>
										<td><div id="1451B" class="lecbox"></div></td>
										<td><div id="1452A" class="lecbox"></div></td>
										<td><div id="1452B" class="lecbox"></div></td>
										<td><div id="1453A" class="lecbox"></div></td>
										<td><div id="1453B" class="lecbox"></div></td>
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