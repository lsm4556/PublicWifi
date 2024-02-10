<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <title>북마크 그룹 수정</title>
    <link rel="stylesheet" type="text/css" href="./css/style.css">
    <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
    <script src="./javascript/bookmarkList.js"></script>
    <script>
        $(document).ready(function () {
            let urlParams = new URLSearchParams(window.location.search);
            let id = urlParams.get('id');

            // ID를 이용하여 bookmark-group 데이터 가져오기
            getBookmarkInfoByIdData(id);
        });
    </script>

    <!-- 추가로 필요한 스크립트나 스타일시트가 있다면 여기에 추가할 수 있습니다 -->
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

<br>

<table border="1">
    <!-- 상세 정보를 동적으로 표시할 부분 -->
    <tbody id="bookmarkDeleteTableBody" class="center-align"></tbody>
</table>

</body>
</html>
