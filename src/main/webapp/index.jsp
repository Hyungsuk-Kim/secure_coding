<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>SKU</title>
</head>
<body>
	<a href="registerMember.jsp">회원가입</a>
	<a href="registerMemberWeak.jsp">회원가입(W)</a>
	<br/>
	<c:if test="${empty sessionScope.loginMember}">
		<a href="signin.jsp">로그인</a>
		<a href="signinWeak.jsp">로그인(W)</a>
		<br/>
	</c:if>
	<c:if test="${not empty sessionScope.loginMember}">
		<a href="test?action=signout">로그아웃</a>
		<a href="test?action=signout">로그아웃(W)</a>
	</c:if>
</body>
</html>