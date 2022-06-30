//--------------------------------------------
// 시간표에 강의 그리기
//--------------------------------------------
function show_lecture(kind, str)
{
	//   0    1     2         3         4       5         6         7
	// 학년^반^요일^시작교시^시간^과목명^교수님^강의실
	lec_id=str;
	str = str.split("^");
	lec_grade=str[0];
	lec_ban=str[1];
	lec_week=str[2];
	lec_start=str[3];
	lec_hours=str[4];
	lec_name=str[5];
	lec_teacher=str[6];
	lec_room=str[7];
	lec_caption="<font color='blue'>"+lec_name+"</font><br>"+lec_grade+"-"+lec_ban+"</font><br>"+lec_teacher+"<br><font color='red'>"+lec_room+"</font>";

	hh=(25+34*( lec_hours-1) )+"px";

	if (kind==0)	// 학생, 교수 개인 시간표 표시
	{
		pos=lec_start+lec_week;
		pos = (lec_start<10) ? "0"+pos : pos;
		document.getElementById( pos ).innerHTML="<div class='lecbox0_text' style='height:"+hh+"'>"+lec_caption+"</div>";
	} 
	else if (kind==1)	// 학년별 반별 시간표 표시
	{
		pos=lec_start+lec_week+lec_grade+lec_ban;
		pos = (lec_start<10) ? "0"+pos : pos;
		document.getElementById( pos ).innerHTML="<div class='lecbox_text' style='height:"+hh+"'>"+lec_caption+"</div>";
	}
	else if (kind==2)		// 휴강날짜 선택인 경우
	{
		pos=lec_start+lec_week;
		pos = (lec_start<10) ? "0"+pos : pos;
		document.getElementById( pos ).innerHTML="<div class='lecbox_text' style='height:"+hh+"' id='"+lec_id+"' onclick='sel_lecture(\""+lec_id+"\")'>"+lec_caption+"</div>";
	}
	else if (kind==3)		// 보강날짜 선택인 경우
	{
		document.getElementById( "firstpos" ).innerHTML="<div class='lecbox_text' style='height:"+hh+"' id='"+lec_id+"' draggable='true' ondragstart='drag(event)'   onclick='sel_lecture(\""+lec_id+"\")'>"+lec_caption+"</div>";
	}
}

//--------------------------------------------
// 휴보강 주 이동시 날짜 표시
//--------------------------------------------
function show_week(dir)
{
	var w=['일','월','화','수','목','금','토'];
	dir = dir==1 ? 7 : ( dir==-1 ? -7 : 0);
	ssday=form1.startday.value;
	ssyear=ssday.substr(0,4);
	for (i=0; i<7; i++)
	{
		wday=get_day(ssday, (i+dir))
		if (i==0)	form1.startday.value=wday;
		if (wday>=ssyear+"-03-01" && wday<=ssyear+"-06-30")
			document.getElementById("w"+i ).innerHTML=w[i]+"<br>"+wday;
		else
			document.getElementById("w"+i ).innerHTML="&nbsp;";
	}
}

//--------------------------------------------
// 시간표내의 모든 강의 선택 취소
//--------------------------------------------
function clear_lecture()	
{	
	for (h=1;h<=14 ;h++){		// 시간(1-10)
	for (w=1;w<=6 ;w++) {		// 요일(1-5)
		pos=String(h)+String(w);
		if (h<10) pos="0"+pos;
		if (typeof(document.getElementById( pos ).childNodes[0]) != 'undefined')
		{
			document.getElementById( pos ).childNodes[0].style.borderColor="#cccccc";
		}
	}}
}

//--------------------------------------------
// (날짜 + w) 한 날짜 구하기
//--------------------------------------------
function get_day(d,w)
{
	sday=new Date(d);
	sday.setDate( sday.getDate() + w);
	return get_yyyymmdd( sday );
}

//--------------------------------------------
// 날짜를 yyyy-mm-dd 형식으로 변환
//--------------------------------------------
function get_yyyymmdd(t)
{
	y=t.getFullYear();
	m=t.getMonth()+1;	if (m<10) m="0"+m;
	d=t.getDate();			if (d<10) d="0"+d;
	return y+"-"+m+"-"+d;
}


