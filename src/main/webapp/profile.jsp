<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:useBean id="selectedMember" class="sku.secure.business.domain.Member" scope="request"/>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>회원 정보 수정</title>
</head>
<body>
    
    <div class="tableContainer">
        <div class="tableRow">
            <!-- START of main content-->
            <div class="main">
                <h4>[회원 정보 수정]</h4>
                <form action="test?action=update" method="POST">
                    <table class="updatetable">
                        <tr>
                            <td class="label">E-mail :</td>
                            <td>${requestScope.selectedMember.email}<input type="hidden" name="email" value="${requestScope.selectedMember.email}"></td>
                        </tr>
                        <tr>
                            <td class="label">비밀번호 :</td>
                            <td><input type="password" name="password" size="20" maxlength="20" value="${requestScope.selectedMember.password}"></td>
                        </tr>
                        <tr>
                            <td class="label">이름 :</td>
                            <td><input type="text" name="name" size="20" maxlength="20" value="${requestScope.selectedMember.name}"></td>
                        </tr>
                        <tr>
                            <td class="label">나이 :</td>
                            <td><input type="text" name="age" size="30" maxlength="20" value="${requestScope.selectedMember.age}"></td>
                        </tr>
                        <tr>
                            <td class="label">성별 :</td>
                            <td>
                            	<input type="radio" name="gender" value="male" checked>남자
  								<br>
  								<input type="radio" name="gender" value="female">여자
                            </td>
                        </tr>
                        <tr>
                            <td class="label">전화번호 :</td>
                            <td><input type="text" name="tel" size="20" maxlength="20" value="${requestScope.selectedMember.tel}"></td>
                        </tr>
                        <tr>
                            <td class="label">우편번호 :</td>
                            <td><input type="text" name="zipcode" size="15" maxlength="7" value="${requestScope.selectedMember.zipcode}"></td>
                        </tr>
                        <tr>
                            <td class="label">주소 :</td>
                            <td><input type="text" name="address" size="50" maxlength="100" value="${requestScope.selectedMember.address}"></td>
                        </tr>
                        <c:if test="${sessionScope.loginMember.role eq 1}">
                        	<tr>
	                            <td class="label">권한 :</td>
	                            <td>
		                            <select name="role">
										<option value="0" selected="selected">일반회원</option>
										<option value="1">관리자</option>
									</select>
								</td>
                        	</tr>
                        </c:if>
                        <tr>
                            <td colspan="2">
                        </tr>
                        <tr>
                            <td></td>
                            <td><input type="submit" value="정보수정"> <input type="reset" value="취소"></td>
                        </tr>
                    </table>
                </form>    
            </div>
            <!-- END of main content-->
        </div>
    </div>                
</body>
</html>
