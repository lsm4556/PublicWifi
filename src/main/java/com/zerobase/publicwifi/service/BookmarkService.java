package com.zerobase.publicwifi.service;

import com.zerobase.publicwifi.DAO.BookmarkDAO;
import com.zerobase.publicwifi.DTO.BookmarkDTO;

import java.util.List;

public class BookmarkService {
    private final BookmarkDAO bookmarkDAO = new BookmarkDAO();

    public boolean createBookmark(int bookmarkGroupId, String wifiManagementNumber) {
        try {
            // DAO에 북마크 정보 저장 요청
            bookmarkDAO.insertBookmark(bookmarkGroupId, wifiManagementNumber);

            // 성공적으로 저장되었을 경우 true 반환
            return true;
        } catch (Exception e) {
            // 실패 시 false 반환
            return false;
        }
    }

    public boolean deleteBookmark(int id) {
        try {
            // DAO에 북마크 정보 삭제 요청
            bookmarkDAO.deleteBookmark(id);

            // 성공적으로 저장되었을 경우 true 반환
            return true;
        } catch (Exception e) {
            // 실패 시 false 반환
            return false;
        }
    }

    // 북마크 정보를 조회하는 메서드
    public BookmarkDTO getBookmarkById(int id) {
        return bookmarkDAO.selectBookmarkById(id);
    }

    // 북마크 정보 전체를 조회하는 메서드
    public List<BookmarkDTO> getAllBookmark() {
        return bookmarkDAO.selectAllBookmark();
    }
}
