function updateWifiTable(data) {
    $('#wifiTableBody').empty(); // 테이블 비우기

    if (data.length > 0) {
        // 데이터가 있을 경우 테이블에 추가
        $.each(data, function(index, wifiInfo) {
            let row = $('<tr>').appendTo('#wifiTableBody');

            // 기존 열 추가
            $('<td>').text(wifiInfo.distance).appendTo(row);
            $('<td>').text(wifiInfo.managementNumber).appendTo(row);
            $('<td>').text(wifiInfo.district).appendTo(row);

            // 클릭 가능한 링크를 추가한 열
            let wifiNameCell = $('<td>').text(wifiInfo.wifiName).appendTo(row);
            wifiNameCell.css('cursor', 'pointer');  // 커서를 포인터로 변경
            wifiNameCell.click(function() {
                let managementNumber = $(this).closest('tr').find('td:eq(1)').text();
                window.location.href = 'wifi-detail.jsp?managementNumber=' + managementNumber;
            });

            // 나머지 열 추가
            $('<td>').text(wifiInfo.roadAddress).appendTo(row);
            $('<td>').text(wifiInfo.detailAddress).appendTo(row);
            $('<td>').text(wifiInfo.installationFloor).appendTo(row);
            $('<td>').text(wifiInfo.installationType).appendTo(row);
            $('<td>').text(wifiInfo.installationAgency).appendTo(row);
            $('<td>').text(wifiInfo.serviceType).appendTo(row);
            $('<td>').text(wifiInfo.networkType).appendTo(row);
            $('<td>').text(wifiInfo.installationYear).appendTo(row);
            $('<td>').text(wifiInfo.indoorOutdoorType).appendTo(row);
            $('<td>').text(wifiInfo.wifiAccessEnvironment).appendTo(row);
            $('<td>').text(wifiInfo.xCoordinate).appendTo(row);
            $('<td>').text(wifiInfo.yCoordinate).appendTo(row);
            $('<td>').text(wifiInfo.workDate).appendTo(row);
        });

    } else {
        // 데이터가 없을 경우 메시지를 포함한 테이블 생성
        $('#wifiTableBody').append('<tr class="no-data">' +
            '<td colspan="17">위치 정보를 입력한 후에 조회해 주세요</td>' +
            '</tr>');
    }
}

function loadNearbyWifiInfo() {
    // 근처 WIFI 정보를 가져오는 비슷한 방식의 AJAX 요청을 수행
    // 이 함수를 서버에서 근처 WIFI 정보를 가져오는 데 필요한 API로 수정해야 합니다.
    let lat = document.getElementById("lat").value;
    let lnt = document.getElementById("lnt").value;

    $.ajax({
        url: '/nearby-wifi?lat=' + lat + '&lnt=' + lnt, // 실제 API 엔드포인트의 경로
        method: 'GET',
        success: function(data) {
            // 성공적으로 데이터를 받아왔을 때 테이블 업데이트
            updateWifiTable(data);
        },
        error: function() {
            console.log('Error fetching nearby wifi data');
        }
    });
}

function getLocation() {
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(
            function(position) {
                let lat = position.coords.latitude;
                let lnt = position.coords.longitude;

                // 가져온 위치 정보를 화면에 출력
                document.getElementById("lat").value = lat;
                document.getElementById("lnt").value = lnt;

                // 서버에 POST 요청 보내기
                saveLocation(lat, lnt);
            },
            showError
        );
    } else {
        alert("Geolocation is not supported by this browser.");
    }
}
function saveLocation(lat, lnt) {
    // 서버에 보낼 데이터
    const data = {
        xCoordinate: lat,
        yCoordinate: lnt
    };

    // 서버에 POST 요청 보내기
    fetch('/location-history', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
        .then(response => response.json())
        .then(data => {
            console.log('Success:', data);
            // 성공적인 경우의 처리
        })
        .catch((error) => {
            console.error('Error:', error);
            // 에러 발생 시의 처리
        });
}

function showError(error) {
    switch (error.code) {
        case error.PERMISSION_DENIED:
            alert("User denied the request for Geolocation.");
            break;
        case error.POSITION_UNAVAILABLE:
            alert("Location information is unavailable.");
            break;
        case error.TIMEOUT:
            alert("The request to get user location timed out.");
            break;
        default:
            alert("An unknown error occurred.");
            break;
    }
}