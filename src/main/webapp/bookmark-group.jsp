<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <title>북마크 그룹 정보</title>
    <link rel="stylesheet" type="text/css" href="./css/style.css">
    <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
    <script src="./javascript/bookmarkGroup.js"></script>
    <script>
        $(document).ready(function () {
            // bookmark-group 데이터 가져오기
            getBookmarkGroupData();
        });
    </script>

    <!-- 추가로 필요한 스크립트나 스타일시트가 있다면 여기에 추가할 수 있습니다 -->
</head>

<body>

<h2>북마크 그룹</h2>

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

<!-- 북마크 추가 버튼 -->
<button id="moveAddBookmarkGroupBtn" onclick="moveToAddBookmarkGroup()">북마크 그룹 이름 추가</button>

<table border="1">
    <thead>
    <tr>
        <th>ID</th>
        <th>북마크 이름</th>
        <th>순서</th>
        <th>등록일자</th>
        <th>수정일자</th>
        <th>비고</th>
    </tr>
    </thead>
    <tbody id="bookmarkGroupTableBody" class="center-align">
    <!-- 테이블 데이터가 여기에 동적으로 추가될 것입니다 -->
    <tr class="no-data">
        <td colspan="6">북마크 그룹을 추가한 후에 조회해 주세요</td>
    </tr>
    </tbody>
</table>

</body>
</html>
