<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>



<!-- footer begin -->

<script>
// Script to open and close sidenav


  function delete_image() {

      var img = document.getElementById('delete_img');
      img.submit();
  }

  function goSearch() {
      form2.submit();
    }
  
  function register(){
	  
	  var frm = document.getElementById("clear_form"); 
   	  if(frm.amount.value == "" ||  frm.amount.value.match(/^-?[0-9]+$/gi)==null){
        alert("최종금액을 숫자로입력해주세요");
      }else if(frm.account_num.value == ""){
    	alert("계좌번호를 입력해주세요");
      }else{
    	  showMessageRight("최종 판매금액의 구매를 결정하시겠습니까?", getTimeStamp(), 0, 1);
    	  var message = {
					type : "escrow",
					trade_no : frm.trade_no.value,
					friend_email : "${friend_email}",
					amount : frm.amount.value,
					account_num : frm.account_num.value,
					producttitle : frm.producttitle.value,
					bank_type : frm.bank_type.value,
					noti : "1"
		};
		ws.send(JSON.stringify(message));
		$("#myModal").modal("hide");
		scroll();
      }
  }
  
  
  /* function register(){
	  if(amount == ""){
	        alert("최종금액을 입력해주세요");
	      }
	  clear_form.submit();
  }
     */

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
   
   

function w3_open() {
    document.getElementById("mySidenav").style.display = "block";
    document.getElementById("myOverlay").style.display = "block";
}
 
function w3_close() {
    document.getElementById("mySidenav").style.display = "none";
    document.getElementById("myOverlay").style.display = "none";
}


function myFunction() {
    var x = document.getElementById("search");
    if (x.className.indexOf("w3-show") == -1) {
        x.className += " w3-show";
    } else { 
        x.className = x.className.replace(" w3-show", "");
    }
}
 $(document).ready(function(){
    $("#money").click(function(){
    	if("${login_email}" == "${chat_room.buyer_email}") {
    		alert("판매자만 등록가능");
    	} else{
    		scroll();
    		$("#myModal").modal();
    		
    	}
    });
});


</script>


</body>
</html>
<!-- footer end -->