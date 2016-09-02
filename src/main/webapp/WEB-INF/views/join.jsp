<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
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
        padding: 15px;
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
	function passwordSecurity() {
		var strongRegex = new RegExp(
				"^(?=.{8,})(?=.*[A-Z]) (?=.*[a-z])(?=.*[0-9])(?=.*\\W).*$", "g");

		var mediumRegex = new RegExp(
				"^(?=.{7,})(((?=.*[A-Z])(?=.*[a-z]))|((?=.*[A-Z])(?=.*[0-9]))|((?=.*[a-z])(?=.*[0-9]))).*$",
				"g");

		var enoughRegex = new RegExp("(?=.{6,}).*", "g");

		var pwd = document.getElementById("pw");

		if (strongRegex.test(pwd.value)) {

			document.getElementById('pwspan').innerHTML = '<b><span style="color:green">보안성 짱이네요</span><br/>';

		} else if (mediumRegex.test(pwd.value)) {

			document.getElementById('pwspan').innerHTML = '</b><b><span style="color:orange">적당한 패스워드</span><br/>';

		} else {

			document.getElementById('pwspan').innerHTML = '</b><b><span style="color:red">위험한 패스워드</span><br/>';

		}
	}

	function passwordCheck() {
		if (document.join_form.pw.value != document.join_form.pwcheck.value) {
			document.getElementById('pwcheckspan').innerHTML = '</b><b><span style="color:red">패스워드가 일치하지 않습니다.</span><br/>';
		}else{
			document.getElementById('pwcheckspan').innerHTML = '</b><b><span style="color:green">패스워드가 일치합니다.</span><br/>';
		}
	}

	function goJoin() {
		if (document.join_form.email.value == "") {

			alert("이메일을 입력해주세요.");
			document.join_form.email.focus();

		} else if (document.join_form.name.value == "") {

			alert("이름을 입력해주세요.");
			document.join_form.name.focus();

		}else if (document.join_form.nickname.value == "") {

			alert("닉네임을 입력해주세요.");
			document.join_form.nickname.focus();

		} else if (document.join_form.pw.value == "") {

			alert("비밀번호를 입력해주세요.");
			document.join_form.pw.focus();

		} else if (document.join_form.pwcheck.value == "") {

			alert("비밀번호확인을 입력해주세요.");
			document.join_form.pwcheck.focus();

		} else if (document.join_form.phone_num.value == "") {

			alert("폰번호를 입력해주세요.");
			document.join_form.phone_num.focus();

		} else if (document.join_form.pw.value != document.join_form.pwcheck.value) {

			alert("비밀번호를 확인해주세요.");
			document.join_form.pwcheck.focus();

		} else {
			document.join_form.submit();
		}
	}

	function previewImage(targetObj, previewId) {
		var preview = document.getElementById(previewId); //div id   
		var ua = window.navigator.userAgent;

		document.getElementById('imgg').style.display = 'none';
		if (ua.indexOf("MSIE") > -1) { //ie일때
			targetObj.select();
			try {

				var src = document.selection.createRange().text; // get file full path 
				var ie_preview_error = document
						.getElementById("ie_preview_error_" + previewId);
				if (ie_preview_error) {
					preview.removeChild(ie_preview_error); //error가 있으면 delete
				}
				var img = document.getElementById(previewId); //이미지가 뿌려질 곳
				img.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(src='"
						+ src + "', sizingMethod='scale')"; //이미지 로딩, sizingMethod는 div에 맞춰서 사이즈를 자동조절 하는 역할

			} catch (e) {

				if (!document.getElementById("ie_preview_error_" + previewId)) {
					var info = document.createElement("<p>");
					info.id = "ie_preview_error_" + previewId;
					info.innerHTML = "a";
					preview.insertBefore(info, null);
				}

			}
		} else { //ie가 아닐때
			var files = targetObj.files;
			for (var i = 0; i < files.length; i++) {
				var file = files[i];
				var imageType = /image.*/; //이미지 파일일경우만.. 뿌려준다.

				if (!file.type.match(imageType)) {
					continue;
				}
				var prevImg = document.getElementById("prev_" + previewId); //이전에 미리보기가 있다면 삭제

				if (prevImg) {
					preview.removeChild(prevImg);
				}
				var img = document.createElement("img"); //크롬은 div에 이미지가 뿌려지지 않는다. 그래서 자식Element를 만든다.

				img.id = "prev_" + previewId;
				img.classList.add("obj");
				img.file = file;
				img.style.width = '50px'; //기본설정된 div의 안에 뿌려지는 효과를 주기 위해서 div크기와 같은 크기를 지정해준다.
				img.style.height = '50px';

				preview.appendChild(img);
				if (window.FileReader) { // FireFox, Chrome, Opera 확인.

					var reader = new FileReader();
					reader.onloadend = (function(aImg) {
						return function(e) {
							aImg.src = e.target.result;
						};
					})(img);

					reader.readAsDataURL(file);

				} else { // safari is not supported FileReader
					//alert('not supported FileReader');
					if (!document.getElementById("sfr_preview_error_"
							+ previewId)) {
						var info = document.createElement("p");
						info.id = "sfr_preview_error_" + previewId;
						info.innerHTML = "not supported FileReader";
						preview.insertBefore(info, null);
					}
				}
			}
		}
	}
</script>

<div class="login-page">
	  	<div class="container">
		      <form class="form-login" name="join_form" action="/" method="POST" enctype="multipart/form-data">
		        <h2 class="form-login-heading">회원가입</h2>
		        <div class="login-wrap">
		        	프로필<br/>
		        	<div class="col-lg-2">
							<div id='previewId' style='width:50px; height:50px; left:0px; top:200px;'></div>
							<div id="imgg" style=" position:absolute; height:100; width:60; background-repeat:no-repeat; 
							z-index:2; background-image:url(/resources/profile/noImage.jpg); left:0px; top:10px; display: block">
							<input type=file id="" name=file onchange="previewImage(this,'previewId')" style="width:0; height:50; filter:alpha(opacity=0); ">
							</div>
					</div>
		            <br/><br/><br/><br/>이메일<input type="text" class="form-control" id="email" name="email" placeholder="Email" autofocus>
		            비밀번호<input type="password" class="form-control" id="pw" name="pw" placeholder="Password" onkeyup="passwordSecurity();"/><span id="pwspan"></span>
		         비밀번호 확인<input type="password" class="form-control" id="pwcheck" name="pwcheck" placeholder="Password" onkeyup="passwordCheck()" /><span id="pwcheckspan"></span>
		            <!-- <input type="password" class="form-control" id="pw_check" name="pw_check" onkeypress="if(event.keyCode==13) {goLogin();}" placeholder="비밀번호확인">
		            <br/> -->
		            이름<input type="text" class="form-control" id="name" name="name" placeholder="Name" />
				 닉네임<input type="text" class="form-control" id="nickname" name="nickname" placeholder="NickName" />
		            핸드폰<input type="text" class="form-control" id="phone_num" name="phone_num" placeholder="Phone" />
		            <br/>
		            <input type="button" class="btn btn-theme btn-block" onclick="goJoin()" value="가입">
		            
		        </div>
		      </form>	  	
	  	
	  	</div>
</div>
<%@include file="./include/footer.jsp"%>