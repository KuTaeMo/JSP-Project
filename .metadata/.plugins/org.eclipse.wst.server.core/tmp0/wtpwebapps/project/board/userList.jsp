<%@page import="java.util.List"%>
<%@page import="com.cos.project.domain.User"%>
<%@page import="java.util.ArrayList"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>
<link href="https://fonts.googleapis.com/icon?family=Material+Icons"
	rel="stylesheet">
<div class="container">

	<hr>

	<c:forEach var="user" items="${users}">
		<div class="card col-md-12 m-2 "
			id="user-${user.id}">
			<div class="d-flex justify-content-end align-items-center">
				<div class="card-body">
					<h4 class="card-title">이름 : ${user.username}</h4>
					<h4 class="card-title">email : ${user.email}</h4>
					<h4 class="card-title">role : ${user.role}</h4>
				</div>
				<div class="m-2">
					<c:choose>
						<c:when test="${sessionScope.principal.role == 'admin'}">
							<i onclick="deleteUser(${user.id})" class="material-icons">delete</i>
						</c:when>
						<c:when test="${sessionScope.principal.id == user.id}">
							<i onclick="deleteUser(${user.id})" class="material-icons">delete</i>
						</c:when>
					</c:choose>
				</div>
			</div>
		</div>

	</c:forEach>


</div>

<script src="/project/js/userDelete.js"></script>

</body>
</html>