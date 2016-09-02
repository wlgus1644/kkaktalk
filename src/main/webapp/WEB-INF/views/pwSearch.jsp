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

	function search(){
		if(document.pw_form.email.value==""){
			alert("이메일을 입력해주세요.");
			document.pw_form.email.focus();
		}else if(document.pw_form.name.value==""){
			alert("이름을 입력해주세요.");
			document.pw_form.name.focus();
		}else if(document.pw_form.phone_num.value==""){
			alert("핸드폰번호를 입력해주세요.");
			document.pw_form.phone_num.focus();
		}else{
			document.pw_form.submit();
		}
	}
	
</script>

<div class="login-page">
	  	<div class="container">
		      <form class="form-login" name="pw_form" action="/pwSearch" method="POST">
		        <h2 class="form-login-heading">비밀번호찾기</h2>
		        <div class="login-wrap">
		        	<label> 이메일 </label>
		            <input type="text" class="form-control" id="email" name="email" placeholder="Email" autofocus>
		            <br>
		        	<label> 이름 </label>
		            <input type="text" class="form-control" id="name" name="name" placeholder="Name" autofocus>
		            <br>
		            <label> 핸드폰번호 </label>
		            <input type="text" class="form-control" id="phone_num" name="phone_num"  onkeypress="if(event.keyCode==13) {search();}" placeholder="PhoneNumber">
		            <br/>
		            <input class="btn btn-theme btn-block" type="button" onclick="search()" value="찾기" />
		            <hr>
		
		        </div>
		      </form>	  	
	  	
	  	</div>
</div>

<%@include file="./include/footer.jsp" %>