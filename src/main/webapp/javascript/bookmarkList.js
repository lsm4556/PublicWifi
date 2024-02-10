// bookmarkList.js

function getBookmarkListData() {
    $.ajax({
        url: '/bookmark',
        type: 'GET',
        success: function (bookmarkListData) {
            // 와이파이 상세 정보 테이블을 동적으로 생성하는 함수 호출
            updateBookmarkListTable(bookmarkListData);
        },
        error: function (error) {
            console.error('Error fetching bookmark group data:', error);
        }
    });
}

// 북마크 테이블을 동적으로 생성하는 함수
function updateBookmarkListTable(data) {
    $('#bookmarkListTableBody').empty(); // 테이블 비우기

    if (data.length > 0) {
        // 데이터가 있을 경우 테이블에 추가
        $.each(data, function(index, bookmarkListInfo) {
            let row = $('<tr>').appendTo('#bookmarkListTableBody');

            // 기존 열 추가
            $('<td>').text(bookmarkListInfo.id).appendTo(row);
            $('<td>').text(bookmarkListInfo.bookmarkName).appendTo(row);
            $('<td>').text(bookmarkListInfo.wifiName).appendTo(row);
            $('<td>').text(bookmarkListInfo.createdAt).appendTo(row);

            // 수정 및 삭제 링크 추가
            let deleteLink = $('<a>').text('삭제').attr('href', 'bookmark-delete.jsp?id=' + bookmarkListInfo.id)
                .addClass('delete-link');
            $('<td>').append(deleteLink).appendTo(row);
        });

        // 수정 및 삭제 링크에 클릭 이벤트 추가
        $('.edit-link').click(function(e) {
            e.preventDefault();
            // 수정 페이지로 이동
            window.location.href = $(this).attr('href');
        });

    } else {
        // 데이터가 없을 경우 메시지를 포함한 테이블 생성
        $('#bookmarkListTableBody').append('<tr class="no-data">' +
            '<td colspan="5">북마크 그룹을 추가한 후에 조회해 주세요</td>' +
            '</tr>');
    }
}

function getBookmarkInfoByIdData(id) {
    $.ajax({
        url: '/bookmark?id=' + id,
        type: 'GET',
        success: function (bookmarkInfoByIdData) {
            // 와이파이 상세 정보 테이블을 동적으로 생성하는 함수 호출
            createBookmarkDeleteTable(bookmarkInfoByIdData);
        },
        error: function (error) {
            console.error('Error fetching bookmark info data:', error);
        }
    });
}

// 북마크 삭제 정보 테이블을 동적으로 생성하는 함수
function createBookmarkDeleteTable(data) {
    // 데이터가 없을 경우의 예외 처리
    if (!data || Object.keys(data).length === 0) {
        $('#bookmarkDeleteTableBody').append('<tr><td colspan="2">데이터가 없습니다.</td></tr>');
    } else {
        const keyMappings = {
            'bookmarkName': '북마크 이름',
            'wifiName': '와이파이명',
            'createdAt': '등록일자',
        };

        // 테이블 생성
        $.each(keyMappings, function (key, label) {
            if (data[key] !== undefined) {
                let row = '<tr><td>' + label + '</td><td id="' + key + '">' + data[key] + '</td></tr>';
                $('#bookmarkDeleteTableBody').append(row);
            }
        });

        // 삭제 버튼과 돌아가기 버튼 추가
        $('#bookmarkDeleteTableBody')
            .append('<tr><td colspan="2"><a href="/bookmark-list.jsp">돌아가기</a> ' +
                '| <button onclick="deleteBookmark()">삭제</button></td></tr>');
    }
}
// 북마크 그룹 수정 함수
function deleteBookmark() {
    // 현재 URL에서 ID 추출 (예: /bookmark-delete.jsp?id=1)
    const urlParams = new URLSearchParams(window.location.search);
    const id = urlParams.get('id');

    // 입력 폼에서 데이터 추출
    const deletedData = {
        'id' : id
        // 여기에 필요한 다른 데이터도 추가
    };

    // 서버에 수정 요청 보내기
    $.ajax({
        url: '/bookmark',
        type: 'DELETE',
        contentType: 'application/json',
        data: JSON.stringify(deletedData),
        success: function (response) {
            // 성공적으로 수정되었을 때의 처리 (예: 메시지 출력, 리다이렉트 등)
            alert('북마크가 삭제되었습니다.');
            // 필요에 따라 리다이렉트 등 추가 처리
        },
        error: function (error) {
            // 오류 발생 시의 처리
            console.error('Error delete bookmark :', error);
        }
    });
}