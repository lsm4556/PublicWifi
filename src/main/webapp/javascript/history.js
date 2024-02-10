function getLocationHistoryData() {
    $.ajax({
        url: '/location-history',  // 요청할 서버의 URL
        method: 'GET',  // GET 방식으로 요청
        dataType: 'json',  // 응답 데이터 타입은 JSON
        success: function (data) {
            // 성공적으로 데이터를 받아왔을 때 테이블 업데이트
            createHistoryTable(data);
        },
        error: function () {
            console.log('Error fetching location history data');
        }
    });
}

function createHistoryTable(locationHistoryList) {
    // 기존 테이블 내용 비우기
    $('#historyTableBody').empty();

    // 서버에서 받아온 JSON 데이터를 기반으로 테이블 행 추가
    $.each(locationHistoryList, function(index, history) {
        var row = '<tr>' +
            '<td>' + history.id + '</td>' +
            '<td>' + history.xCoordinate + '</td>' +
            '<td>' + history.yCoordinate + '</td>' +
            '<td>' + history.createdAt + '</td>' +
            '<td><button class="delete-button">삭제</button></td>' +
            '</tr>';

        $('#historyTableBody').append(row);
    });

    // 삭제 버튼에 클릭 이벤트 리스너 추가
    $('.delete-button').on('click', function() {
        // 클릭된 버튼이 속한 행의 id 값 가져오기
        let id = $(this).closest('tr').find('td:first-child').text();

        // id를 이용하여 삭제 작업 수행
        deleteHistory(id);
    });
}

function deleteHistory(historyId) {
    // AJAX를 사용하여 서버로 삭제 요청을 보냄
    $.ajax({
        url: '/location-history',  // 서버의 삭제 API 엔드포인트 경로
        method: 'DELETE',
        contentType: 'application/json',
        data: JSON.stringify({ id: historyId }),  // JSON 형식으로 id 전달
        success: function (response) {
            // 서버에서 성공적인 응답을 받았을 때 수행할 동작
            if (response.success) {
                location.reload();
            } else {
                console.error(response.message);
            }
        },
        error: function () {
            // 서버 요청 실패 시 수행할 동작
            console.error('Failed to send delete request.');
        }
    });
}
