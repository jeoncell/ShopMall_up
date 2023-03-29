/**
 * 
 */

function go_cart(){
	var qty = document.getElementById("quantity").value;
	
	if(qty == "") {
		alert("수량을 입력해주세요.")
		document.getElementById("quantity").focus();
		return false;
	} else if (qty > 30){
		alert("수량이 너무 많습니다.")
		document.getElementById("quantity").focus();
		return false;
	} else {
		var form = document.getElementById("theform");
		form.action = "cart_insert";
		form.submit();
	}
}

//장바구니 상품 삭제하기
function go_cart_delete() {
	const query = 'input[name="cseq"]:checked';
	//체크한 항목의 갯수를 얻어옴.
	var len = document.querySelectorAll(query).length;
	console.log(len);
	
	if(len==0){
		alert("삭제할 항목을 선택해 주세요.")
	} else {
		var form = document.getElementById("theform");
		form.action = "cart_delete";
		form.submit();
	}
}


// 장바구니 내역을 주문처리
function go_order_insert() {
	var form = document.getElementById("theform");
	
	form.action = "order_insert";
	form.submit();
}


