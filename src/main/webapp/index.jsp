<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <title>와이파이 정보 조회</title>
    <link rel="stylesheet" type="text/css" href="./css/style.css">
    <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
    <script src="./javascript/function.js"></script>
</head>

<body>

<h2>와이파이 정보 조회</h2>

<div>
    <ul>
        <li><a href="">홈</a></li>
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


<form>
    LAT: <input type="text" id="lat" value="0.0">
    LNT: <input type="text" id="lnt" value="0.0">
    <button type="button" onclick="getLocation()">내 위치 가져오기</button>
    <button type="button" onclick="loadNearbyWifiInfo()">근처 WIFI 정보 보기</button>
</form>

<table border="1">
    <thead>
    <tr>
        <th>거리(Km)</th>
        <th>관리번호</th>
        <th>자치구</th>
        <th>와이파이명</th>
        <th>도로명주소</th>
        <th>상세주소</th>
        <th>설치위치(층)</th>
        <th>설치유형</th>
        <th>설치기관</th>
        <th>서비스구분</th>
        <th>망종류</th>
        <th>설치년도</th>
        <th>실내외구분</th>
        <th>WIFI접속환경</th>
        <th>X좌표</th>
        <th>Y좌표</th>
        <th>작업일자</th>
    </tr>
    </thead>
    <tbody id="wifiTableBody">
    <!-- 테이블 데이터가 여기에 동적으로 추가될 것입니다 -->
            <tr class="no-data">
                <td colspan="17">위치 정보를 입력한 후에 조회해 주세요</td>
            </tr>
    </tbody>
</table>

</body>
</html>