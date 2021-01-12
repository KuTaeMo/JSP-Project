function deleteUser(id){
	$.ajax({
		type : "post",
		url : "/project/user?cmd=delete&id="+id,
		dataType : "json"
	}).done(function(result) { //  { "statusCode" : 1 }
		if (result.statusCode == 1) {
			console.log(result);
			$("#reply-"+id).remove();
			location.reload();
		} else {
			alert("유저리스트 삭제 실패");
		}
	});
	
}