<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>와이파이 정보 조회</title>
    <link rel="stylesheet" type="text/css" href="./css/style.css">
    <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
    <script src="./javascript/history.js"></script>
    <script>
        // 페이지 초기화 시 JSON 데이터를 기반으로 테이블 생성
        $(document).ready(function () {
            // location-history 데이터 가져오기
            getLocationHistoryData();
        });
    </script>
</head>

<body>

<h2>위치 히스토리 목록</h2>

<div>
    <ul>
        <li><a href="http://localhost:8080">홈</a></li>
        <li class="separator">|</li>
        <li><a href="">위치 히스토리 목록</a></li>
        <li class="separator">|</li>
        <li><a href="/load-wifi">OPEN API 와이파이 정보 가져오기</a></li>
        <li class="separator">|</li>
        <li><a href="/bookmark-list.jsp">북마크 보기</a></li>
        <li class="separator">|</li>
        <li><a href="/bookmark-group.jsp">북마크 그룹 관리</a></li>
    </ul>
</div>

<table border="1" class="center-align">
    <thead>
    <tr>
        <th>ID</th>
        <th>X좌표</th>
        <th>Y좌표</th>
        <th>조회일자</th>
        <th>비고</th>
    </tr>
    </thead>
    <tbody id="historyTableBody">
    <!-- 서버에서 받아온 위치 히스토리 데이터를 동적으로 표시 -->
    </tbody>
</table>

</body>
</html>
