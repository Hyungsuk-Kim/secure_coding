<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isErrorPage="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>	
<head>
	<meta charset="UTF-8">
	<title>${pageContext.errorData.throwable.message}</title>
</head>
<body>
    <div class="main">
        <p class="exceptionname">${pageContext.errorData.throwable.message}</p>
        <p>
            ${pageContext.errorData.throwable}<br>
            <br>
            This is the request URI: <br>
            <code>${pageContext.errorData.requestURI}</code>
            <br>
        </p>
    </div>
</body>
</html>
