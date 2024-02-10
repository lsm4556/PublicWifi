package com.zerobase.publicwifi.service;

import com.zerobase.publicwifi.model.BookmarkGroupDAO;
import com.zerobase.publicwifi.model.BookmarkGroupDTO;

import java.util.List;

public class BookmarkGroupService {
    private final BookmarkGroupDAO bookmarkGroupDAO = new BookmarkGroupDAO();

    public boolean createBookmarkGroup(String bookmarkName, int sequence) {
        try {
            // DAO에 북마크 그룹 정보 저장 요청
            bookmarkGroupDAO.insertBookmarkGroup(bookmarkName, sequence);

            // 성공적으로 저장되었을 경우 true 반환
            return true;
        } catch (Exception e) {
            // 실패 시 예외 처리 및 false 반환
            e.printStackTrace();  // 실제로는 로깅 등으로 변경해야 합니다.
            return false;
        }
    }

    public boolean deleteBookmarkGroup(int id) {
        try {
            // DAO에 북마크 그룹 정보 삭제 요청
            bookmarkGroupDAO.deleteBookmarkGroup(id);

            // 성공적으로 저장되었을 경우 true 반환
            return true;
        } catch (Exception e) {
            // 실패 시 예외 처리 및 false 반환
            e.printStackTrace();  // 실제로는 로깅 등으로 변경해야 합니다.
            return false;
        }
    }

    public boolean updateBookmarkGroup(String bookmarkName, int sequence, int id) {
        try {
            // DAO에 북마크 그룹 정보 수정 요청
            bookmarkGroupDAO.updateBookmarkGroup(bookmarkName, sequence, id);

            // 성공적으로 저장되었을 경우 true 반환
            return true;
        } catch (Exception e) {
            // 실패 시 예외 처리 및 false 반환
            e.printStackTrace();  // 실제로는 로깅 등으로 변경해야 합니다.
            return false;
        }
    }

    // 북마크 그룹 정보를 조회하는 메서드
    public BookmarkGroupDTO getBookmarkGroupById(int id) {
        return bookmarkGroupDAO.selectBookmarkGroupById(id);
    }

    // 북마크 그룹 정보 전체를 조회하는 메서드
    public List<BookmarkGroupDTO> getAllBookmarkGroup() {
        return bookmarkGroupDAO.selectAllBookmarkGroup();
    }
}
