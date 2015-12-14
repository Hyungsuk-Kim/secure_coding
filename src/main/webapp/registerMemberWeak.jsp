<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>회원 가입</title>
</head>
<body>
    <div class="tableContainer">
        <div class="tableRow">
            <div class="main">
                <h4>[회원 가입]</h4>
                <form action="weak?action=register" method="POST">
                    <table class="registertable">
                        <tr>
                            <td class="label">E-mail :</td>
                            <td><input type="email" name="email" size="20" maxlength="60"></td>
                        </tr>
                        <tr>
                            <td class="label">비밀번호 :</td>
                            <td><input type="password" name="password" size="20" maxlength="20"></td>
                        </tr>
                        <tr>
                            <td class="label">이름 :</td>
                            <td><input type="text" name="name" size="20" maxlength="20"></td>
                        </tr>
                        <tr>
                            <td class="label">나이 :</td>
                            <td><input type="text" name="age" size="30" maxlength="20"></td>
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
                            <td><input type="text" name="tel" size="20" maxlength="20"></td>
                        </tr>
                        <tr>
                            <td class="label">우편번호 :</td>
                            <td><input type="text" name="zipcode" size="15" maxlength="7"></td>
                        </tr>
                        <tr>
                            <td class="label">주소 :</td>
                            <td><input type="text" name="address" size="50" maxlength="100"></td>
                        </tr>
                        <tr>
                            <td colspan="2">
                        </tr>
                        <tr>
                            <td></td>
                            <td><input type="submit" value="회원가입(W)"> <input type="reset" value="취소"></td>
                        </tr>
                    </table>
                </form>    
            </div>
        </div>
    </div>
</body>
</html>