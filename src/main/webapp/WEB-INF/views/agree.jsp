<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>
<%@include file="./include/header.jsp" %>
<style type="text/css">

	.container {
	    margin-top: 70px;
	    background: #FFFFFF;
	    padding-left: 0px;
	    padding-right: 0px;
	    width: 350px;
	}
	html {
      font-size: 10px;
      -webkit-tap-highlight-color: rgba(0, 0, 0, 0);
	}
	html {
      font-family: sans-serif;
      -webkit-text-size-adjust: 100%;
      -ms-text-size-adjust: 100%;
	}
	div {
      display: block;
	}
	body {
      color: #797979;
      background: #f2f2f2;
      font-family: 'Ruda', sans-serif;
      padding: 0px !important;
      margin: 0px !important;
      font-size: 13px;
      line-height: 1.42857143;
	}
	.form-login h2.form-login-heading {
      margin: 0;
      padding: 25px 20px;
      text-align: center;
      background: #68dff0;
      border-radius: 5px 5px 0 0;
      -webkit-border-radius: 5px 5px 0 0;
      color: #fff;
      font-size: 20px;
      text-transform: uppercase;
      font-weight: 300;
	}

	.login-wrap {
      padding: 20px;
	}
	.form-control {
      display: block;
      width: 100%;
      height: 34px;
      padding: 6px 12px;
      font-size: 14px;
      line-height: 1.42857143;
      color: #555;
      background-color: #fff;
      background-image: none;
      border: 1px solid #ccc;
      border-radius: 4px;
      box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075);
	}
	.radio, .checkbox {
      position: relative;
      display: block;
      min-height: 20px;
      margin-top: 10px;
      margin-bottom: 10px;
	}
	label {
      font-weight: 400;
      cursor: default;
	}
	.pull-right {
      float: right !important;
	}
	a, a:hover, a:focus {
      text-decoration: none;
      outline: none;
	}
	::selection {
      background: #68dff0;
      color: #fff;
	}
	.btn {
      margin-bottom: 5px;
	}
	.btn-theme {
      color: #fff;
      background-color: #68dff0;
      border-color: #48bcb4;
	}
	.btn-block {
      display: block;
      width: 100%;
	}
	.btn {
      background-image: none;
      border: 1px solid transparent;
      border-radius: 4px;
	}
	.login-wrap .registration {
      text-align: center;
	}
	a, a:hover, a:focus {
	   text-decoration: none;
	   outline: none;
	}
	a {
	    color: #428bca;
	    text-decoration: none;
	}
	a {
	    background: transparent;
	}

</style>

<script type="text/javascript">
   function onAgree(){
      var agree1 = document.getElementById("agree1").checked;
      var agree2 = document.getElementById("agree2").checked;
      
      if(agree1+agree2=="2"){
         location.href = "/join";
      }
   }
</script>

<div class="login-page">
 	<div class="container">
		<form class="form-login" name="login_form" action="/friend_list" method="POST">
			<h2 class="form-login-heading">깎톡</h2>
	      	<div class="login-wrap">
			<div class="agree-wrap">
				<textarea rows="5" cols="37.5">이용약관</textarea><br>
				<input type="checkbox" id="agree1" onclick="onAgree()" /><label for="agree1">동의합니다.</label><br>
				<textarea rows="5" cols="37.5">개인정보 수집 및 이용안내</textarea><br>
				<input type="checkbox" id="agree2" onclick="onAgree()" /><label for="agree2">동의하고 계속 진행합니다.</label><br>
			</div>
		</div>
		</form>	  	
	</div>
</div>
<%@include file="./include/footer.jsp" %>