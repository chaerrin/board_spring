//ajaxSetup():  실행될  AJAX 요청에 대한 기본 속성을 정의해 재사용 
$.ajaxSetup({
	success: function(result) {
		alert(result);
	},
	error : function(jqXHR) {
				//HttpServletResponse.SC_UNAUTHORIZED 401 error 인증이 필요한 ajax 요청을 비로그인상태에서 했을때 발생 
				if (jqXHR.status == 401 || jqXHR.status == 403) {
					alert("인증 및 권한이 필요한 서비스입니다");
					//기존페이지를 새로운 페이지로 변경시킨다. 참고 - location.href="url"; 는 새로운 페이지로 이동시킨다 
					//href는 페이지를 이동하므로 뒤로가기 버튼을 누르면 이전 페이지로 이동이 가능,
					//replace는 현재 페이지를 새로운 페이지로 대체하므로 이전 페이지로 이동이 불가 
					location.replace("/loginForm");//로그인 폼이 있는 home으로 페이지를 대체한다 
			    }else{
			   //post 방식 때 csrf 토큰이 없거나 기타 error 발생시 
			   	alert("jqXHR status code:"+jqXHR.status+" message:"+jqXHR.responseText);
			   }
		}
});//ajaxSetup
