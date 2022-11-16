<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="dao.DataDao"%>
<%@ page import="dao.Customer"%>

<%@ page import="java.util.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi"
	crossorigin="anonymous">
<style>
.login.wrapper, .myPage.wrapper, .orderDetails.wrapper, .payment-process.wrapper,
	.cart.payment-process.wrapper, .shopCustomerGradePolicy.wrapper {
	position: relative;
	width: 100%;
	max-width: 960px;
	min-height: 300px;
	height: auto;
	margin: 0 auto;
	padding: 60px 16px 70px;
}

html, body, div, span, applet, object, iframe, h1, h2, h3, h4, h5, h6, p,
	blockquote, pre, a, abbr, acronym, address, big, cite, code, del, dfn,
	img, ins, kbd, q, s, samp, small, strike, sub, sup, tt, var, b, u, i,
	center, dl, dt, dd, ol, ul, li, fieldset, form, label, legend, table,
	caption, tbody, tfoot, thead, tr, th, td, article, aside, canvas,
	details, embed, figure, figcaption, footer, header, hgroup, menu, nav,
	output, ruby, section, summary, time, mark, audio, video {
	margin: 0;
	padding: 0;
	border: 0;
	font-size: 100%;
	font: inherit;
	vertical-align: baseline;
}

* {
	color: inherit;
	font-size: 1em;
	font-family: inherit;
	line-height: inherit;
	box-sizing: inherit;
	-webkit-box-sizing: inherit;
	-moz-box-sizing: inherit;
	margin: 0;
}

div {
	display: block;
}

body, input, textarea, select, button {
	font-family: sans-serif;
	-webkit-font-smoothing: antialiased;
}

body {
	word-wrap: break-word;
	word-break: break-word;
}

.column-wrapper.two-column {
	zoom: 1;
}

.column-wrapper.two-column:before, .column-wrapper.two-column:after {
	content: '\0020';
	display: block;
	overflow: hidden;
	visibility: hidden;
	width: 0;
	height: 0;
}

.column-wrapper.two-column:after {
	clear: both;
}

.column-wrapper.two-column:before, .column-wrapper.two-column:after {
	content: '\0020';
	display: block;
	overflow: hidden;
	visibility: hidden;
	width: 0;
	height: 0;
}

.column-wrapper.two-column .field:first-child {
	margin-right: 14%;
}

.column-wrapper.two-column .field {
	width: 43%;
	display: inline-block;
	vertical-align: top;
	float: left;
}

.wrapper>.header, .wrapper .field .header {
	width: 100%;
	margin-bottom: 40px;
}

.designSettingElement.text-title {
	color: #000000;
	font-family: Noto Sans, sans-serif;
	font-weight: 400;
	letter-spacing: 0em;
}

.item-element .no-content-msg, .wrapper .no-content-msg {
	text-align: center;
	padding: 100px 0;
	line-height: 1.5em;
}

.designSettingElement.text-body {
	color: #404040;
	font-family: Noto Sans, sans-serif;
	font-weight: 400;
	letter-spacing: 0em;
}

.wrapper .field .header.otherTitle {
	margin-top: 70px;
}

.wrapper>.header, .wrapper .field .header {
	width: 100%;
	margin-bottom: 40px;
}

.myPage.wrapper .memberInfo.field .function.logout, .myPage.wrapper .infoList.field .function.point
	{
	text-align: right;
	margin-bottom: 18px;
}

.wrapper .field .content .row {
	margin-bottom: 20px;
}

.wrapper .field .row .title, .wrapper .field .row .identityNumber-title,
	.payment-process.wrapper .signup .content .row .title {
	display: block;
	font-size: 14px;
	line-height: 17px;
	margin-bottom: 12px;
}

label {
	cursor: default;
}

.wrapper .field .row input[type=text], .wrapper .field .row input[type=search],
	.wrapper .field .row input[type=password], .wrapper .field .row input[type=number],
	.wrapper .field .row input[type=tel], .wrapper .field .row input[type=email]
	{
	background-color: transparent;
	-webkit-appearance: none;
	-moz-appearance: none;
}

.wrapper .field .row input[type=text], .wrapper .field .row input[type=search],
	.wrapper .field .row input[type=password], .wrapper .field .row input[type=number],
	.wrapper .field .row input[type=tel], .wrapper .field .row input[type=email],
	.wrapper .field .row select {
	font-size: 14px;
	line-height: 23px;
	width: 100%;
	height: 45px;
	border: 1px solid #ddd;
	padding: 10px 15px;
}

.designSettingElement.shape, .designSettingElement.shape .cke_contents {
	border-color: #CCCCCC !important;
}

input[readonly], input[readonly="readonly"], select[disabled], select[disabled="disabled"],
	.boardPost-wrapper .infoInput .postImageSettingRow button,
	.readonly-textarea-wrapper {
	background-color: rgba(175, 175, 175, 0.05) !important;
}

input[type=text], input[type=password], input[type=number], input[type=tel],
	input[type=email], input[type=search], select, textarea {
	border-radius: 0 !important;
}

body, input, textarea, select, button {
	font-family: sans-serif;
	-webkit-font-smoothing: antialiased;
}

a, a:hover, a:active, a:focus, a:visited, input, button, select {
	text-decoration: none;
	outline: none !important;
}

.myPage.wrapper .field .content .row.contact input:not(.payletter-phone),
	.login.wrapper .field .content .row.contact input:not(.payletter-phone),
	.orderDetails.wrapper .column-wrapper.two-column .field .content .row.contact input:not(.payletter-phone)
	{
	display: inline-block;
	width: 30%;
}

body.button-shape-roundedSquare .section-wrapper .section .item-wrapper.customForm .customFormSubmitButton,
	#body.button-shape-roundedSquare .section-wrapper .section .item-wrapper.button .item-element,
	#body.button-shape-roundedSquare .section-wrapper .section .item-wrapper.review .review-button,
	#body.button-shape-roundedSquare .item-wrapper.productList .productListWrapper .product-cart-elem,
	#body.button-shape-roundedSquare .item-wrapper.productListSlide .productListWrapper .product-cart-elem,
	#body.button-shape-roundedSquare .designSettingElement.button {
	border-radius: 3px;
}

body.button-type-normal .designSettingElement.button {
	color: #FFFFFF !important;
	background-color: #222222 !important;
	border-color: #222222 !important;
}

body .designSettingElement.button {
	border-width: 1px;
	background-color: #000 !important;
	color: #ffffff !important;
	border-radius: 3px;
	display: inline-block;
	width: auto;
	height: auto;
	padding: 12px 20px;
	white-space: nowrap;
	line-height: 1.25;
	cursor: pointer;
}
</style>
</head>
<body>
	<%@ include file="navBar.jsp"%>



	<div id="myPage" class="myPage wrapper">
		<div class="column-wrapper two-column">
			<div class="infoList field">
				<div class="header designSettingElement text-title">
					<span class="title">주문 내역</span>
				</div>
				<div id="shopCustomerOrderListDiv"
					class="content orderList designSettingElement text-body hide">
					<div class="tb-title designSettingElement shape clearfix">
						<span class="date">일자</span> <span class="product">상품 정보</span> <span
							class="total">가격</span> <span class="status">상태</span>
					</div>
					<div id="shopCustomerOrderList" class="tb-content"></div>
					<div id="shopCustomerOrderPageCount"
						class="pagination_div designSettingElement"></div>
				</div>
				<div id="noOrderMsg"
					class="content no-content-msg designSettingElement text-body hide">
					주문 내역이 없습니다.</div>
				<!--  my posts -->
				<div class="js-section-myposts hide">
					<div
						class="header designSettingElement text-title otherTitle myPostsTitle myPosts">
						<span class="title">내가 쓴 글</span>
					</div>
					<div id="shopProductMyPostDiv"
						class="content myPostList designSettingElement text-body hide">
						<div class="tb-title designSettingElement shape clearfix">
							<span class="date">일자</span> <span class="title">제목</span> <span
								class="type">유형</span>
						</div>
						<div id="shopProductMyPosts" class="tb-content"></div>
						<div id="shopCustomerMyPostsCount"
							class="pagination_div designSettingElement"></div>
					</div>
					<div id="noShopCustomerMyPostsMsg"
						class="content no-content-msg designSettingElement text-body">
						내가 쓴 글이 없습니다.</div>
				</div>











			</div>
			<div class="memberInfo field">
				<div class="header designSettingElement text-title">
					<span class="title">회원 정보</span>
				</div>
				<div class="content designSettingElement text-body">
					<div class="function logout">
						<form action="/_shop/customer/logout" method="POST">
							<a class="text designSettingElement text-assi"
								onclick="this.parentNode.submit()">로그아웃</a> <input type="hidden"
								name="memberNo" value="95268"> <input type="hidden"
								name="customerNo" value="10315853">
						</form>
					</div>

					<%
					
					DataDao dao = new DataDao();
					Customer cus = new Customer();
					cus = dao.selectCustomerInfoInMypage("asd");
// 					out.println(cus.getId());
// 					out.println(cus.getEmail());
// 					out.println(cus.getCustomer_name());
// 					out.println(cus.getPhone_num());
// 					out.println(cus.getAddress());
					

					%>

					<div class="row">
						<label for="customerId" class="title">아이디</label> <input
							type="text" id="customerId" class="designSettingElement shape"
							readonly value="<%=cus.getId()%>">
					
					</div>
					
					<div class="row">
						<label for="customerId" class="title">비밀번호</label> <input
							type="text" id="customerId" class="designSettingElement shape"
							readonly value="<%=cus.getPassword()%>">
					
					</div>

					<div class="row">
						<label for="customerEmail" class="title">이메일</label> <input
							type="email" id="customerEmail"
							class="designSettingElement shape"
							value="<%=cus.getEmail()%>">
					</div>


					<div class="row name">



						<span class="title">이름</span> <input id="customerName" type="text"
							class="designSettingElement shape" readonly
							value="<%=cus.getCustomer_name()%>">


					</div>


					<div class="row contact">



						<label for="customerPhone1" class="title">휴대폰 번호</label> <input
							type="tel" id="customerPhone1"
							class="designSettingElement shape phoneNumber js-inputOnlyNumber"
							maxlength="11"
							value="<%=cus.getPhone_num()%>">



					</div>

				</div>



				<div class="row address">



					<label class="title">주소</label> <input type="text" id="post2"
						class="designSettingElement shape addressInfo" readonly="readonly"
						value="<%=cus.getAddress()%>">


				</div>

				<br />


				<div class="function">

<!-- 					<a href="/changePassword" -->
<!-- 						class="text designSettingElement text-assi">비밀번호 변경하기</a>  -->
					
							<a href="/withdrawal" class="text designSettingElement text-assi">탈퇴하기</a>


				</div>
				<br />
				
					<input type="submit" id="updateBtn" class="designSettingElement button"
						onclick="location.href='updateCustomerInfo_proc.jsp?id=asd'" value="수정"/>
						
			
				<input type="hidden" name="member_name_cert_flag"
					id="member_name_cert_flag" val="N" /> <input type="hidden"
					name="customerDi" id="customerDi" /> <input type="hidden"
					name="customerCi" id="customerCi" />
			</div>
		</div>
	</div>
	</div>


	<script
	
	document.getElementById('updateBtn').addEventListener('click',(e)=>{
e.preventDefault();
let form = document.personDetailForm;
if(form.personName.value == ""){ //이름이없는경우
alert('이름은 필수입니다.');
form.personName.focus();
return false;
}

else { //이름이 있는 경우
if(confirm('수정하시겠습니까?')){
form.action = "updatePerson_proc.jsp";
form.submit();
}
}
});
	
	
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-OERcA2EqjJCMA+/3y+gxIOqMEjwtxJY7qPCqsdltbNJuaOe923+mo//f6V8Qbsw3"
		crossorigin="anonymous"></script>
</body>
</html>