<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>	
<head>
	<meta charset="UTF-8">
	<title>Login Page</title>
</head>
<body>
    <%-- 시스템 정보 : 어쩌구저쩌구 --%>
    <!-- 시스템 정보 : 어쩌구저쩌구 -->
    <c:if test="${not empty requestScope.loginErrorMsg}" >
    <ul id="loginerrormsg">
        <li>${requestScope.loginErrorMsg}</li>
    </ul>
    </c:if>
    <form action="<c:url value='/weak?action=signin'/>" method="POST">
        <table id="logintable">
            <tr>
                <td class="label">E-mail</td>
                <td><input type="text" name="email" size="30" maxlength="100"></td>
            </tr>
            <tr>
                <td class="label">패스워드</td>
                <td><input type="password" name="password" size="30" maxlength="100"></td>
            </tr>
            <tr>
                <td></td>
                <td><input type="submit" name="login" value="로그인(W)"></td>
            </tr>
        </table>
    </form>
</body>
</html>
