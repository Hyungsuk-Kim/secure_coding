<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="sku.secure.business.domain.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>	
	<head>
		<meta charset="UTF-8">
		<title>등록(변경) 성공</title>
	</head>
	<body>
	    <div class="tableContainer">
	        <div class="tableRow">
	            <!-- START of main content-->
	           <div class="main">
	               <h4>[회원정보 등록(수정)결과]</h4>
	               <table>
	                   <tr>
	                       <td class="label">회원 E-mail : </td>
	                       <td><b>${sessionScope.loginMember.email}</b></td>
	                   </tr>
	                   <tr>
	                       <td class="label">회원이름 : </td>
	                       <td><b>${sessionScope.loginMember.name}님이 등록(변경) 되었습니다.</b></td>
	                   </tr>
	               </table>
	               <ul>
	               	<li><a href="test?action=profile">회원정보</a></li>
	               	<li><a href="test?action=list">회원 리스트</a></li>
	               	<li><a href="test?action=signout">로그아웃</a></li>
	               	<li><a href="test?action=remove">회원탈퇴</a></li>
	               </ul>
	           </div>
	           <!-- END of main content -->
	        </div>
	    </div>
	</body>
</html>
