<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page session="false" %>
<%@include file="./include/header.jsp" %>

<html>
<div class="container">
 <div class="page-header">
<h1>상품 수정</h1>
</div>

 <script>
   function updateClear() {
      var frm=document.getElementById("frm");
      var title=document.getElementById("title");
      var price=document.getElementById("price");
      var content=document.getElementById("content");
      
       if(title.value==""){
             alert("상품명을 입력해주세요.");
          }else if(price.value =="" || price.value.match(/^-?[0-9]+$/gi)==null ){
             alert("희망가격을 숫자로 입력해주세요.");
          }else if(content.value==""){
             alert("상세설명을 입력해주세요.");
          }else{
             frm.submit();
          }
   }

      function create_addFile(){
          var pp = document.createElement("P");
          var btn = document.createElement("input");
              btn.setAttribute('type', 'file');
              btn.setAttribute('name', 'file');
              btn.setAttribute('multiple', 'multiple');
              pp.appendChild(btn);
              document.getElementById("addFile").appendChild(pp);
              $("#plus").hide();
      }
</script> 

   <form action="/trade/update" method="post"  id="frm" name="frm" enctype="multipart/form-data" class="form-horizontal" role="form">
   <input type="hidden" id="no" name="no" value="${vo.no }">
      <div class="row">
         <label>No. ${vo.no}</label>  
    </div>
       
            <div class="form-group">
               <div class="col-sm-3">
                  <select id="category" name="category" class="form-control" value="${vo.category}" >
               <c:if test="${vo.category=='의류'}">
                  <option value="가전제품">가전제품</option>
                  <option value="${vo.category}" selected="selected">의류</option>
                  <option value="기타">기타</option>
               </c:if>
               <c:if test="${vo.category=='가전제품'}">
                  <option value="${vo.category}" selected="selected">가전제품</option>
                  <option value="의류">의류</option>
                  <option value="기타">기타</option>
               </c:if>
               <c:if test="${vo.category=='기타'}">
                  <option value="가전제품">가전제품</option>
                  <option value="의류">의류</option>
                  <option value="${vo.category}" selected="selected">기타</option>
               </c:if>
            </select>
               </div>
         </div>
             <div class="form-group">
               <div class="col-sm-5">
                  <input class="form-control" id="title" name="title"  type="text" value="${vo.title}" placeholder="제품명을 입력하세요."/>
               </div>
         </div>
           <div class="form-group">
            <div class="col-sm-5">
               <input class="form-control" id="price" name="price"  type="text" value="${vo.price}" placeholder="가격을 입력하세요."/>
            </div>
         </div>
          <div class="form-group">
              <div class="col-sm-5">
               <textarea  class="form-control" rows="10"  cols="100" title="내용" id="content" name="content"  placeholder="상세설명을 입력하세요.">${vo.content}</textarea>
            </div>
         </div>

      <div id="addFile" ></div>
         <input type="hidden" id="no" name="no" value="${vo.no }"/>
         <input type="hidden" id="addFile" name="addFile" value=""/>
   </form>
   <div class="form-group">
         <div class="col-sm-5">
         <label>첨부파일</label>
         <c:forEach items="${vo2}" var="vo2">
            <c:if test="${vo.no==vo2.trade_no}">
               <div class="well well-sm">
               <form action="/trade/${vo.no}/${vo2.image_no}" method="post" id="delete_img" name="delete_img">
                  ${vo2.image} 
                  <input type="hidden" id="image_no" name="image_no" value="${vo2.image_no}" /> 
                  <input type="button"   class="w3-btn w3-white w3-border" value="삭제" onclick="delete_image()" />
               </form>  
               </div>
            </c:if>
         </c:forEach>
         </div>
      </div>
		<input type="button" id="plus" class="w3-btn w3-white w3-border" value="파일추가" onclick="create_addFile()" />
		<input type="button"  class="w3-btn w3-white w3-border"  onclick="updateClear()" value="수정완료"> 
    </div>
<%@include file="./include/footer.jsp" %>
</body>
</html>