/**
 * 이 페이지 자체를 document라고 한다.
 */

// 약관 동의여부 확인
function go_next(){
	if (document.formm.okon1[0].checked == true) { // 동의함이 선택된 경우
		document.formm.action = "join_form"; // 요청 URL
		document.formm.submit(); // Controller로 요청
	} else if (document.formm.okon1[1].checked == true) {
		alert("약관에 동의 버튼을 눌러주세요.");
	}
	
}

//사용자 ID 중복체크 화면 표시
function idcheck() {
	
	if(document.getElementById("id").value == "") {
		alert("아이디를 입력해주세요.");
		document.getElementById("id").focus(); // 입력 대기
		
		return false; // 정상적으로 완료되지 않음.
	}
	// ID 중복확인 화면 요청
	//  url : controller 요청하는 url, 
	// _blank_ : 새로운 윈도우를 생성하여 표시
	var url = "id_check_form?id=" + document.getElementById("id").value;
	window.open(url, "_blank_", "toolbar=no, menubar=no, scrollbars=yes, resizable=no, width=450, height=350");
	
}

// 우편번호 찾기 화면요청
function post_zip() {
	var url = "find_zip_num";
	window.open(url, "_blank_", "toolbar=no, menubar=no, scrollbars=yes, resizable=no, width=700, height=500");
}

// 회원 가입시 필수입력 확인
function go_save() {
	if (document.getElementById("id").value == "") {
		alert("사용자 아이디를 입력해 주세요.")
		document.getElementById("id").focus();
		return false;
	} else if (document.getElementById("reid").value == "") {
		alert("아이디 중복체크를 해주세요.")
		document.getElementById("id").focus();
		return false;
	} else if (document.getElementById("pwd").value == "") {
		alert("비밀번호를 입력해주세요.")
		document.getElementById("pwd").focus();
		return false;
	} else if (document.getElementById("pwd").value !=
				document.getElementById("pwdCheck").value) {
		alert("비밀번호가 일치하지 않습니다.")
		document.getElementById("pwd").focus();
		return false;
	} else if (document.getElementById("name").value == "") {
		alert("이름을 입력해주세요.")
		document.getElementById("name").focus();
		return false;
	} else if (document.getElementById("email").value == "") {
		alert("이메일을 입력해주세요.")
		document.getElementById("email").focus();
		return false;
	} else {
		document.getElementById("join").action = "join";
		document.getElementById("join").submit();
	}
	
}

// 아이디, 비밀번호 찾기 ..화면 요청
function find_id_form() {
	var url = "find_id_form";
	window.open(url, "_blank_", "toolbar=no, menubar=no, scrollbars=yes, resizable=no, width=550, height=450");
}

function findMemberId() {
	if (document.getElementById("name").value=="") {
		alert("이름을 입력해 주세요");
		document.getElementById("name").focus();
		return false;
	} else if (document.getElementById("email").value=="") {
		alert("이메일을 입력해 주세요");
		document.getElementById("email").focus();
		return false;
	} else {
		var form = document.getElementById("findId");
		form.action = "find_id";
		form.submit();
	}
}


function findPassword() {
	if (document.getElementById("id2").value=="") {
		alert("아이디를 입력해 주세요");
		document.getElementById("id2").focus();
		return false;
	} else if (document.getElementById("name2").value=="") {
		alert("이름을 입력해 주세요");
		document.getElementById("name2").focus();
		return false;
	} else if (document.getElementById("email2").value=="") {
		alert("이메일을 입력해 주세요");
		document.getElementById("email2").focus();
		return false;
	} else {
		var form = document.getElementById("findPW");
		form.action = "find_pwd";
		form.submit();
	}
	
	
}

function changePassword(){
	if (document.getElementById("pwd").value == ""){
		alert("비밀번호를 입력해주세요.");
		document.getElementById("pwd").focus();
		return false;
	} else if (document.getElementById("pwd").value
			 	!= document.getElementById("pwdcheck").value){
		alert("비밀번호가 일치하지 않습니다.");
		document.getElementById("pwd").focus();
		return false;
	} else {
		var form = document.getElementById("pwd_form");
		form.action = "change_pwd";
		form.submit();
	}
}









