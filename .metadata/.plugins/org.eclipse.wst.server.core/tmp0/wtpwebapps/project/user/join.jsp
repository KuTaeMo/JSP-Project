<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<div class="container">
	<form action="/project/user?cmd=join" method="post"
		onsubmit="return valid()" >
		<div class="d-flex justify-content-end">
			<button type="button" class="btn btn-info" onClick="usernameCheck()">중복체크</button>
		</div>
		<div class="form-group">
			<input type="text" name="username" id="username" class="form-control"
				placeholder="Enter Username" required />
		</div>

		<div class="form-group">
			<input type="password" name="password" class="form-control"
				placeholder="Enter Password" required />
		</div>

		<div class="form-group">
			<input type="email" name="email" class="form-control"
				placeholder="Enter Email" required />
		</div>

		<div class="form-group">
			<input type="text" name="role" id="role" class="form-control"
				value="user" placeholder="Default role : USER" required readonly />
		</div>
		<br />
		<button type="submit" class="btn btn-primary">회원가입완료</button>
	</form>
</div>
<script>
	var isChecking = false; //중복체크 변수
	
	function valid() {
		if (isChecking == false) {
			alert("아이디 중복체크를 해주세요");
		}
		return isChecking;
	}
	function usernameCheck() {
		// DB에서 확인해서 정상이면 isChecking = true
		var username = $("#username").val();
		$.ajax({
			type : "POST",
			url : "/project/user?cmd=usernameCheck",
			data : username,
			contentType : "text/plain; charset=utf-8",
			dataType : "text" // 응답 받을 데이터의 타입을 적으면 자바스크립트 오브젝트로 파싱해줌.
		}).done(function(data) {
			if (data === 'ok') { // 유저네임 있다는 것
				isChecking = false; //false로 바꿔줘야 중복아닌 것 체크하고 나서 다시 체크할 때
									//검사가능
				alert('유저네임이 중복되었습니다.')
			} else {
				isChecking = true;
				$("#username").attr("readonly", "readonly");
				alert("해당 유저네임을 사용할 수 있습니다.")
			}
		});
	}
</script>
</body>
</html>