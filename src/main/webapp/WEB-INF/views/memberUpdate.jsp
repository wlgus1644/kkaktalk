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
        width: 100px;
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

			if (!document.getElementById("ie_preview_error_"
					+ previewId)) {
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
   
   function onChange(){
      document.getElementById("image").remove();
   }
   
   function onthumbnailDelete(){
      document.getElementById("image").remove();
      document.getElementById("thumbnail").value = "";
   }
   
   function onupdate(){
      var pw = document.getElementById("pw").value;
      if(pw == ""){
         alert("비밀번호를 입력해주세요.");
      }else{
         document.update_form.submit();
      }
   }
   
</script> 

<div class="login-page">
	  	<div class="container">
		      <form class="form-login" name="update_form" action="/memberUpdate" method="POST" enctype="multipart/form-data">
		      <c:forEach items="${memberinfo }" var="vo">
		        <h2 class="form-login-heading">프로필 수정</h2>
		        <div class="login-wrap">
		        	<div class="col-lg-10">
							<div id='previewId' style='width:50px; height:50px; font-size:9pt; left:7px; top:80px;'></div>
							<div id="imgg" style=" position:absolute; height:70px; width:50px; background-repeat:no-repeat; 
							z-index:2; background-image:url(/resources/profile/${vo.thumbnail}); background-size:100%; left:10px; top:0px; display: block">
							<input type=file id="" name=file onchange="previewImage(this,'previewId')" style="width:0; height:100; filter:alpha(opacity=0); ">
							</div><br/>
					</div>
					<input type="hidden" id="thumbnail" name="thumbnail" value="${vo.thumbnail}" />
		          <input type="text" class="form-control" id="email" name="email" value="${vo.email }" readonly>
		            비밀번호<input type="password" class="form-control" id="pw" name="pw" placeholder="Password">
		            <!-- <input type="password" class="form-control" id="pw_check" name="pw_check" onkeypress="if(event.keyCode==13) {goLogin();}" placeholder="비밀번호확인">
		            <br/> -->
		            이름<input type="text" class="form-control" id="name" name="name" value="${vo.name }" readonly/>
				닉네임<input type="text" class="form-control" id="nickname" name="nickname" value="${vo.nickname }" />
		            핸드폰<input type="text" class="form-control" id="phone_num" name="phone_num" value="${vo.phone_num }" />
		            <br/>

		            계좌 :  <select id="bank_type" name="bank_type" value="${vo.bank_type}" >
                     <c:if test="${vo.bank_type=='국민은행'}">
                         <option value="${vo.bank_type}" selected="selected">국민은행</option>
                      <option value="농협은행">농협은행</option>
                      <option value="우리은행">우리은행</option>
                      <option value="하나은행">하나은행</option>
                      <option value="신한은행">신한은행</option>
                      <option value="기업은행">기업은행</option>                     
                   	</c:if>
                      <c:if test="${vo.bank_type=='농협은행'}">
                         <option value="국민은행">국민은행</option>
                      <option value="${vo.bank_type}" selected="selected">농협은행</option>
                      <option value="우리은행">우리은행</option>
                      <option value="하나은행">하나은행</option>
                      <option value="신한은행">신한은행</option>
                      <option value="기업은행">기업은행</option>        
                     </c:if>
                     <c:if test="${vo.bank_type=='우리은행'}">
                         <option value="국민은행">국민은행</option>
                      <option value="농협은행">농협은행</option>
                      <option value="${vo.bank_type}" selected="selected">우리은행</option>
                      <option value="하나은행">하나은행</option>
                      <option value="신한은행">신한은행</option>
                      <option value="기업은행">기업은행</option>        
                     </c:if>
                     <c:if test="${vo.bank_type=='하나은행'}">
                         <option value="국민은행">국민은행</option>
                      <option value="농협은행">농협은행</option>
                      <option value="하나은행">우리은행</option>
                      <option value="${vo.bank_type}" selected="selected">하나은행</option>
                      <option value="신한은행">신한은행</option>
                      <option value="기업은행">기업은행</option>        
                     </c:if>
                     <c:if test="${vo.bank_type=='신한은행'}">
                         <option value="국민은행">국민은행</option>
                      <option value="농협은행">농협은행</option>
                      <option value="하나은행">우리은행</option>
                      <option value="하나은행">하나은행</option>
                      <option value="${vo.bank_type}" selected="selected">신한은행</option>
                      <option value="기업은행">기업은행</option>        
                     </c:if>
                     <c:if test="${vo.bank_type=='기업은행'}">
                         <option value="국민은행">국민은행</option>
                      <option value="농협은행">농협은행</option>
                      <option value="하나은행">우리은행</option>
                      <option value="하나은행">하나은행</option>
                      <option value="기업은행">신한은행</option>
                      <option value="${vo.bank_type}" selected="selected">기업은행</option>        
                     </c:if>
                  </select>
          <input type="text" id="account_num" name="account_num" placeholder="-포함 입력하세요" value="${vo.account_num }"/><br/>
          		            <input type="button" class="btn btn-theme btn-block" onclick="onupdate()" value="수정">
		        </div>
		        </c:forEach>
		      </form>
		      <br/>	  	
	  	
	  	</div>
</div>
<%@include file="./include/footer.jsp" %>