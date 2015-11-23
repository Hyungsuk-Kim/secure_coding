<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 리스트</title>
<script src="../js/board.js"></script>
</head>
<body>
	<div class="tableContainer">
        <div class="tableRow">
			<div class="memberpage">
				<table id="listtable" class="maintable">
			 		<caption>회원 목록</caption>
					<thead>
						<tr>
							<th class="email">E-mail</th>
							<th class="name">이름</th>
						</tr>
					</thead>
					<tbody>
						<c:if test="${empty requestScope.memberList}">
							<tr>
								<td colspan="5">등록된 회원이 없습니다.</td>
							</tr>
						</c:if>
						<c:forEach var="member" items="${requestScope.memberList}">
							<tr>
								<td class="email">
									<a href="test?action=select&email=${member.email}">${member.email}</a>
								</td>
								<td class="name">${member.name}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<div class="buttonbar">
					<form name="searchForm" action="list" method="GET" onsubmit="return searchCheck();">
						<select name="searchType">
							<option value="all" selected="selected">전체검색</option>
							<option value="email">E-mail</option>
							<option value="name">이름</option>
						</select> 
						<input id="searchinput" type="text" name="searchText">
						<input type="submit" value="검색"> 
						<input type="button" value="목록" onclick="goUrl('list.jsp');">
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>