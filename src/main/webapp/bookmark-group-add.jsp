<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <title>북마크 그룹 수정</title>
    <link rel="stylesheet" type="text/css" href="./css/style.css">
    <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
    <script src="./javascript/bookmarkGroup.js"></script>
</head>

<body>

<h2>북마크 그룹 수정</h2>

<div>
    <ul>
        <li><a href="http://localhost:8080">홈</a></li>
        <li class="separator">|</li>
        <li><a href="/history.jsp">위치 히스토리 목록</a></li>
        <li class="separator">|</li>
        <li><a href="/load-wifi">OPEN API 와이파이 정보 가져오기</a></li>
        <li class="separator">|</li>
        <li><a href="/bookmark-list.jsp">북마크 보기</a></li>
        <li class="separator">|</li>
        <li><a href="/bookmark-group.jsp">북마크 그룹 관리</a></li>
    </ul>
</div>

<table border="1">
    <!-- 북마크 그룹 추가를 위한 입력 폼 -->
    <tbody id="bookmarkGroupAddTableBody" class="center-align">
    <tr>
        <td>북마크 이름</td>
        <td><input type="text" id="bookmarkNameInput"></td>
    </tr>
    <tr>
        <td>순서</td>
        <td><input type="text" id="sequenceInput"></td>
    </tr>
    <tr>
        <td colspan="2">
            <button id="addBookmarkGroupBtn" onclick="addBookmarkGroup()">추가</button>
        </td>
    </tr>
    </tbody>
</table>



</body>
</html>
