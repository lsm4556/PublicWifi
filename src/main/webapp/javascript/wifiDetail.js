// wifiDetail.js

// URL에서 특정 매개변수의 값을 가져오는 함수
function getParameterByName(name, url) {
    if (!url) url = window.location.href;
    name = name.replace(/[\[\]]/g, '\\$&');
    let regex = new RegExp('[?&]' + name + '(=([^&#]*)|&|#|$)'),
        results = regex.exec(url);
    if (!results) return null;
    if (!results[2]) return '';
    return decodeURIComponent(results[2].replace(/\+/g, ' '));
}

function getWifiDetailData(managementNumber) {
    $.ajax({
        url: '/wifi-detail?managementNumber=' + managementNumber,
        type: 'GET',
        success: function (wifiDetailData) {
            // 와이파이 상세 정보 테이블을 동적으로 생성하는 함수 호출
            createWifiDetailTable(wifiDetailData);
        },
        error: function (error) {
            console.error('Error fetching wifi detail data:', error);
        }
    });
}

function getBookmarkGroupData() {
    $.ajax({
        url: '/bookmark-group',
        type: 'GET',
        success: function (bookmarkGroupData) {
            // 가져온 데이터를 이용하여 옵션을 동적으로 생성
            for (let i = 0; i < bookmarkGroupData.length; i++) {
                $('#bookmarkGroup').append('<option value="' + bookmarkGroupData[i].id + '">'
                    + bookmarkGroupData[i].bookmarkName + '</option>');
            }
        },
        error: function (error) {
            console.error('Error fetching bookmark group data:', error);
        }
    });
}

// 와이파이 상세 정보 테이블을 동적으로 생성하는 함수
function createWifiDetailTable(wifiDetailData) {
    // 데이터가 없을 경우의 예외 처리
    if (!wifiDetailData || Object.keys(wifiDetailData).length === 0) {
        $('#wifiDetailTableBody').append('<tr><td colspan="2">데이터가 없습니다.</td></tr>');
    } else {
        // 거리, 관리번호 등의 테이블에 표시할 항목과 한글 키 값
        const keyMappings = {
            'distance': '거리',
            'managementNumber': '관리번호',
            'district': '자치구',
            'wifiName': '와이파이명',
            'roadAddress': '도로명주소',
            'detailAddress': '상세주소',
            'installationFloor': '설치위치(층)',
            'installationType': '설치유형',
            'installationAgency': '설치기관',
            'serviceType': '서비스구분',
            'networkType': '망종류',
            'installationYear': '설치년도',
            'indoorOutdoorType': '실내외구분',
            'wifiAccessEnvironment': 'WIFI접속환경',
            'xCoordinate': 'X좌표',
            'yCoordinate': 'Y좌표',
            'workDate': '작업일자'
        };

        // 테이블 생성
        $.each(keyMappings, function (key, label) {
            if (wifiDetailData[key] !== undefined) {
                let row = '<tr><td>' + label + '</td><td>' + wifiDetailData[key] + '</td></tr>';
                $('#wifiDetailTableBody').append(row);
            }
        });
    }
}

// 북마크 추가 함수
function createAddBookmarkFunction() {
    let selectedBookmarkGroupId = $('#bookmarkGroup').val();
    let urlParams = new URLSearchParams(window.location.search);
    let managementNumber = urlParams.get('managementNumber');
    // 서버에 POST 요청 보내기
    $.ajax({
        url: '/bookmark',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify({
            bookmarkGroupId: selectedBookmarkGroupId,
            wifiManagementNumber: managementNumber,
            // 다른 필요한 데이터 추가
        }),
        success: function (response) {
            // 성공 시 처리 로직
            console.log(response);
        },
        error: function (error) {
            // 에러 시 처리 로직
            console.error('Error adding bookmark:', error);
        }
    });
}



