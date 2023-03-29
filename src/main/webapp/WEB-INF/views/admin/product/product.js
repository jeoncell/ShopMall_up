/**
 * 
 */


function go_wrt() {
	var form = document.getElementById("prod_form");
	form.action = "admin_product_write_form";
	form.submit();
}

function go_search() {
	var form = document.getElementById("prod_form");
	form.action = "admin_product_list";
	form.submit();
}

function go_total() {
	var form = document.getElementById("prod_form");
	document.getElementById("key").value = "";
	form.action = "admin_product_list";
	form.submit();
}

// 순익(price3) = 판매가(price2) - 원가(price1) 를 계산하는 함수
function go_ab() {
	var price2= document.getElementById("price2").value;
	var price1= document.getElementById("price1").value;
	
	document.getElementById("price3").value = price2 - price1;
}

// 상품 등록 요청
function go_save() {
	if (document.getElementById("kind").value==""){
		alert("상품 종류를 입력하세요");
		document.getElementById("kind").focus();
		return false;
	} else if (document.getElementById("name").value==""){
		alert("상품명을 입력하세요");
		document.getElementById("name").focus();
		return false;
	} else if (document.getElementById("price1").value==""){
		alert("상품원가를 입력하세요");
		document.getElementById("price1").focus();
		return false;
	} else if (document.getElementById("price2").value==""){
		alert("판매가를 입력하세요");
		document.getElementById("price2").focus();
		return false;
	} else if (document.getElementById("price3").value==""){
		alert("판매순익을 입력하세요");
		document.getElementById("price3").focus();
		return false;
	} else if (document.getElementById("content").value==""){
		alert("상품설명을 입력하세요");
		document.getElementById("content").focus();
		return false;
	} else {
		var form = document.getElementById("write_form");
		form.enctype = "multipart/form-data";
		form.action = "admin_product_write";
		form.submit();
	}
		
}//go_save

// 상품 상세보기
function go_detail(pseq) {
	var form = document.getElementById("prod_form");
	
	form.action = ("admin_product_detail?pseq=" + pseq);
	form.submit();
	
}

function go_list() {
	var form = document.getElementById("detail_form");
	form.action = "admin_product_list"; //?pageNum=1&rowsPerPage=10
	form.submit();
}

function go_mod(pseq) {
	var form = document.getElementById("detail_form");
	form.action = "admin_product_update_form?pseq="+pseq;
	form.submit();
}

function go_mod_save(pseq) {
	if (document.getElementById("kind").value==""){
		alert("상품 종류를 입력하세요");
		document.getElementById("kind").focus();
		return false;
	} else if (document.getElementById("name").value==""){
		alert("상품명을 입력하세요");
		document.getElementById("name").focus();
		return false;
	} else if (document.getElementById("price1").value==""){
		alert("상품원가를 입력하세요");
		document.getElementById("price1").focus();
		return false;
	} else if (document.getElementById("price2").value==""){
		alert("판매가를 입력하세요");
		document.getElementById("price2").focus();
		return false;
	} else if (document.getElementById("price3").value==""){
		alert("판매순익을 입력하세요");
		document.getElementById("price3").focus();
		return false;
	} else if (document.getElementById("content").value==""){
		alert("상품설명을 입력하세요");
		document.getElementById("content").focus();
		return false;
	} else {
		var form = document.getElementById("update_form");
		form.enctype = "multipart/form-data";
		form.action = "admin_product_update";
		form.submit();
		
	}
	
	
	
	
}

