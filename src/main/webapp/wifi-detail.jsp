<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <title>와이파이 상세 정보</title>
    <link rel="stylesheet" type="text/css" href="./css/style.css">
    <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
    <script src="./javascript/wifiDetail.js"></script>
    <script>
        $(document).ready(function () {
            // managementNumber 가져오기
            let managementNumber = getParameterByName('managementNumber');

            // managementNumber를 이용하여 wifi-detail 데이터 가져오기
            getWifiDetailData(managementNumber);

            // bookmark-group 데이터 가져오기
            getBookmarkGroupData();
        });
    </script>

    <!-- 추가로 필요한 스크립트나 스타일시트가 있다면 여기에 추가할 수 있습니다 -->
</head>

<body>

<h2>와이파이 상세 정보</h2>

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

<!-- 북마크 그룹 선택 -->
<select id="bookmarkGroup" name="bookmarkGroup">
    <option value="">북마크 그룹 이름 선택</option>
    <!-- 동적으로 옵션을 추가할 위치 -->
</select>

<!-- 북마크 추가 버튼 -->
<button id="addBookmarkBtn" onclick="createAddBookmarkFunction()">북마크 추가하기</button>

<br>

<table border="1">
    <!-- 상세 정보를 동적으로 표시할 부분 -->
    <tbody id="wifiDetailTableBody" class="center-align"></tbody>
</table>
</body>
</html>
