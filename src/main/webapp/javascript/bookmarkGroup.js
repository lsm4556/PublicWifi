// bookmarkGroup.js

function getBookmarkGroupData() {
    $.ajax({
        url: '/bookmark-group',
        type: 'GET',
        success: function (bookmarkGroupData) {
            // 와이파이 상세 정보 테이블을 동적으로 생성하는 함수 호출
            updateBookmarkGroupTable(bookmarkGroupData);
        },
        error: function (error) {
            console.error('Error fetching bookmark group data:', error);
        }
    });
}

function getBookmarkGroupByIdData(id) {
    $.ajax({
        url: '/bookmark-group?id=' + id,
        type: 'GET',
        success: function (bookmarkGroupByIdData) {
            // 와이파이 상세 정보 테이블을 동적으로 생성하는 함수 호출
            createBookmarkGroupEditTable(bookmarkGroupByIdData);
        },
        error: function (error) {
            console.error('Error fetching bookmark group data:', error);
        }
    });
}

// 북마크 그룹 수정 정보 테이블을 동적으로 생성하는 함수
function createBookmarkGroupEditTable(data) {
    // 데이터가 없을 경우의 예외 처리
    if (!data || Object.keys(data).length === 0) {
        $('#bookmarkGroupEditTableBody').append('<tr><td colspan="2">데이터가 없습니다.</td></tr>');
    } else {
        const keyMappings = {
            'bookmarkName': '북마크 이름',
            'sequence': '순서',
        };

        // 테이블 생성
        $.each(keyMappings, function (key, label) {
            if (data[key] !== undefined) {
                let row = '<tr><td>' + label +
                    '</td><td><input type="text" id="' + key + '" value="' + data[key] + '"></td></tr>';
                $('#bookmarkGroupEditTableBody').append(row);
            }
        });

        // 수정 버튼과 돌아가기 버튼 추가
        $('#bookmarkGroupEditTableBody')
            .append('<tr><td colspan="2"><a href="/bookmark-group.jsp">돌아가기</a> | <button onclick="updateBookmarkGroup()">수정</button></td></tr>');

        // 뒤로 가기 함수
        function goBack() {
            history.back();
        }
    }
}

// 와이파이 상세 정보 테이블을 동적으로 생성하는 함수
function updateBookmarkGroupTable(data) {
    $('#bookmarkGroupTableBody').empty(); // 테이블 비우기

    if (data.length > 0) {
        // 데이터가 있을 경우 테이블에 추가
        $.each(data, function(index, bookmarkGroupInfo) {
            let row = $('<tr>').appendTo('#bookmarkGroupTableBody');

            // 기존 열 추가
            $('<td>').text(bookmarkGroupInfo.id).appendTo(row);
            $('<td>').text(bookmarkGroupInfo.bookmarkName).appendTo(row);
            $('<td>').text(bookmarkGroupInfo.sequence).appendTo(row);
            $('<td>').text(bookmarkGroupInfo.createdAt).appendTo(row);
            $('<td>').text(bookmarkGroupInfo.updatedAt).appendTo(row);

            // 수정 및 삭제 링크 추가
            let editLink = $('<a>').text('수정').attr('href', 'bookmark-group-edit.jsp?id=' + bookmarkGroupInfo.id)
                .addClass('edit-link');
            let deleteLink = $('<a>').text('삭제').attr('href', '#')
                .attr('data-id', bookmarkGroupInfo.id).addClass('delete-link');
            $('<td>').append(editLink).append(' | ').append(deleteLink).appendTo(row);
        });

        // 수정 및 삭제 링크에 클릭 이벤트 추가
        $('.edit-link').click(function(e) {
            e.preventDefault();
            // 수정 페이지로 이동
            window.location.href = $(this).attr('href');
        });

        $('.delete-link').click(function(e) {
            e.preventDefault();
            let id = $(this).data('id');

            // 확인 다이얼로그를 띄워 사용자에게 삭제 여부 확인
            if (confirm('정말로 삭제하시겠습니까?')) {
                // 서버에 DELETE 요청 보내기
                $.ajax({
                    url: '/bookmark-group?id=' + id,
                    type: 'DELETE',
                    contentType: 'application/json',
                    data: JSON.stringify({'id':id}),
                    success: function (response) {
                        // 성공적으로 삭제되었을 때의 처리
                        alert('북마크 그룹이 삭제되었습니다.');
                        // 페이지 새로고침
                        location.reload();
                    },
                    error: function (error) {
                        // 오류 발생 시의 처리
                        console.error('Error deleting bookmark group:', error);
                    }
                });
            }
        });
    } else {
        // 데이터가 없을 경우 메시지를 포함한 테이블 생성
        $('#bookmarkGroupTableBody').append('<tr class="no-data">' +
            '<td colspan="6">북마크 그룹을 추가한 후에 조회해 주세요</td>' +
            '</tr>');
    }
}


// 북마크 그룹 수정 함수
function updateBookmarkGroup() {
    // 현재 URL에서 ID 추출 (예: /bookmark-group?id=1)
    const urlParams = new URLSearchParams(window.location.search);
    const id = urlParams.get('id');

    // 입력 폼에서 데이터 추출
    const updatedData = {
        'id' : id,
        'bookmarkName': $('#bookmarkName').val(),
        'sequence': $('#sequence').val(),
        // 여기에 필요한 다른 데이터도 추가
    };

    // ID가 없다면 에러 처리 또는 사용자에게 메시지 표시

    // 서버에 수정 요청 보내기
    $.ajax({
        url: '/bookmark-group',
        type: 'PATCH',
        contentType: 'application/json',
        data: JSON.stringify(updatedData),
        success: function (response) {
            // 성공적으로 수정되었을 때의 처리 (예: 메시지 출력, 리다이렉트 등)
            alert('북마크 그룹이 수정되었습니다.');
            // 필요에 따라 리다이렉트 등 추가 처리
        },
        error: function (error) {
            // 오류 발생 시의 처리
            console.error('Error updating bookmark group:', error);
        }
    });
}

// 북마크 그룹 추가 함수
function addBookmarkGroup() {
    // 입력 폼에서 데이터 추출
    const bookmarkName = $('#bookmarkNameInput').val();
    const sequence = $('#sequenceInput').val();

    // 서버에 추가 요청 보내기 등의 로직 추가
    $.ajax({
        url: '/bookmark-group',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify({
            bookmarkName: bookmarkName,
            sequence: sequence
        }),
        success: function (response) {
            // 성공적으로 추가되었을 때의 처리
            alert('북마크 그룹이 추가되었습니다.');
            // 페이지 새로고침 또는 다른 적절한 동작 수행
            location.reload();
        },
        error: function (error) {
            // 오류 발생 시의 처리
            console.error('북마크 그룹 추가 중 오류 발생:', error);
        }
    });
}

function moveToAddBookmarkGroup() {
    // 클릭하면 해당 URL로 이동
    window.location.href = '/bookmark-group-add.jsp';
}