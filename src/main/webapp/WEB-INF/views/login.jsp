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

	function goLogin(){
		if(document.login_form.login_email.value==""){
			alert("이메일을 입력해주세요.");
			document.login_form.login_email.focus();
		}else if(document.login_form.login_pw.value==""){
			alert("패스워드를 입력해주세요.");
			document.login_form.login_pw.focus();
		}else{
			document.login_form.submit();
		}
	}
	function goJoin(){
		document.join_form.submit();
	}
	
	$(document).ready(function(){
	      setTimeout("timer()",1000)
	      
	   });
	   
	   function timer(){
	   
	      $("#main_img").fadeOut('slow',function(){
	         
	      })
	   }


</script>

<div>

<img id="main_img" style="position: absolute; 
      width: 380px;
      height:550px;
     z-index: 2;" src="/resources/profile/kkt_img.png">

<div class="login-page" style="position: relative; z-index: 1;" class="modal show" tabindex="-1" role="dialog" aria-hidden="true">
	  	<div class="container">
		      <form class="form-login" name="login_form" action="/friend_list" method="POST">
		        <h2 class="form-login-heading"><img src="/resources/profile/kkt_stamp2.png" style="width:40px; height:40px;"></h2>
		        <div class="login-wrap">
		            <input type="text" class="form-control" id="login_email" name="login_email" placeholder="Email" autofocus>
		            <br>
		            <input type="password" class="form-control" id="login_pw" name="login_pw"  onkeypress="if(event.keyCode==13) {goLogin();}" placeholder="Password">
		            <label class="checkbox">
		                <span class="pull-right">
		                	<a data-toggle="modal" href="/idSearch">&nbsp&nbsp&nbsp이메일을 잊으셨습니까?</a><br/>
		                    <a data-toggle="modal" href="/pwSearch">비밀번호를 잊으셨습니까?</a>
		                </span>
		            </label>
		            <br/>
		            <button class="btn btn-theme btn-block" type="button" onclick="goLogin()"><i class="fa fa-lock"></i>&nbsp로그인</button>
		            <hr>

		            <div class="registration">
		                아직 계정이 없으십니까?<br/>
		                <a href="/agree">
		                    회원가입
		                </a>
		            </div>
		
		        </div>
		      </form>	  	
	  	
	  	</div>
</div>
</div>
<%@include file="./include/footer.jsp" %>