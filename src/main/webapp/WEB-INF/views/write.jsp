<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page session="false" %>
<%@include file="./include/header.jsp" %>

<html>

<head>
   <title>중고나라</title>
</head>

<script type="text/javascript">

	function onupload(){
			var upload_title = document.getElementById("title").value;
			var upload_price = document.getElementById("price").value;
			var upload_content=document.getElementById("content").value;
			var upload_file=document.getElementById("file").value;
			// 숫자만인지 체크하는 정규식
			//regNumber = /^[0-9]*$/;  
		    if(upload_title == ""){
		       alert("상품명을 입력해주세요.");
		    }else if(upload_price =="" || upload_price.match(/^-?[0-9]+$/gi)==null ){
		       alert("희망가격을 숫자로 입력해주세요.");
		    }else if(upload_content==""){
		       alert("상세설명을 입력해주세요.");
		    }else if(upload_file==""){
		       alert("사진을 한장 이상등록해주세요.");
		    }else{
		       document.upload_product.submit();
		    }
		}
</script>
<body>
<div class="container">
 <div class="page-header">
    <h1>상품 등록</h1>
  </div>

   <form action="/trade" method="post" name="upload_product" id="upload_product" enctype="multipart/form-data" class="form-horizontal" role="form">
         <div class="form-group">
               <div class="col-sm-2">
                  <select id="category" name="category" class="form-control">
                     <option value="의류">의류</option>
                     <option value="가전제품">가전제품</option>
                     <option value="기타">기타</option>
                  </select>
               </div>
      </div>
      <div class="form-group">
            <div class="col-sm-5">
               <input class="form-control" id="title" name="title"  type="text" placeholder="상품명을 입력하세요">
            </div>
      </div>
      <div class="form-group">
            <div class="col-sm-5">
               <input class="form-control" id="price" name="price"  type="text"  style="ime-mode:disabled"  placeholder="희망가격을 입력하세요(숫자만 입력)">
            </div>
      </div>
        <div class="form-group">
              <div class="col-sm-5">
               <textarea  class="form-control" rows="10"  cols="100" title="내용" id="content" name="content" placeholder="상세설명을 입력하세요"></textarea>
            </div>
      </div>
      <div class="form-group">
         <div class="col-sm-5">
            <label for="exampleInputFile">파일 업로드</label> 
            <input type="file" name="file" id="file" multiple="multiple" />
            <p class="help-block">다중 이미지 업로드 가능</p>
         </div>
      </div>
      <div class="form-group">
              <div class="col-sm-1">
               <button  class="w3-btn w3-white w3-border"  type="button" onclick="onupload()">상품등록</button>
                <input type="hidden" id="member_email" name="member_email" value="${vo.email}"/>
            </div>
      </div>
   </form>
</div>
<%@include file="./include/footer.jsp" %>