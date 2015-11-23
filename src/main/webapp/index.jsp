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
	<c:if test="${empty sessionScope.loginMember}">
		<a href="signin.jsp">로그인</a>
	</c:if>
	<c:if test="${not empty sessionScope.loginMember}">
		<a href="test?action=signout">로그아웃</a>
	</c:if>
</body>
</html>