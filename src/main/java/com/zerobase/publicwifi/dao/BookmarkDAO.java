package com.zerobase.publicwifi.dao;

import com.zerobase.publicwifi.config.DatabaseConfig;
import com.zerobase.publicwifi.dto.BookmarkDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookmarkDAO {
    String databaseUrl = DatabaseConfig.getDatabaseUrl();

    // 다른 메서드와 독립적으로 커넥션을 설정하는 메서드
    private Connection connect() throws SQLException {
        return DriverManager.getConnection(databaseUrl);
    }

    public void insertBookmark(int bookmarkGroupId, String wifiManagementNumber) {
        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO Bookmark (bookmarkGroupId, wifiManagementNumber, createdAt) " +
                             "VALUES (?, ?, datetime('now', 'localtime'))"
             )) {
            // xCoordinate와 yCoordinate 값을 preparedStatement에 설정
            preparedStatement.setInt(1, bookmarkGroupId);
            preparedStatement.setString(2, wifiManagementNumber);

            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }


    // id 받아서 삭제하는 메서드
    public void deleteBookmark(int id) {
        String query = "DELETE FROM Bookmark WHERE id = ?";

        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Bookmark id로 조회하는 메서드
    public BookmarkDTO selectBookmarkById(int id) {
        BookmarkDTO bookmarkById = null;

        String query = "SELECT B.id as id, wifiName, bookmarkName, B.createdAt " +
                "FROM Bookmark B " +
                "    left join WifiInfo WI on WI.managementNumber = B.wifiManagementNumber " +
                "    left join BookmarkGroup BG on BG.id = B.bookmarkGroupId " +
                "WHERE B.id = ?";

        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // ?에 값 설정
            preparedStatement.setInt(1, id);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    bookmarkById = new BookmarkDTO(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bookmarkById;
    }


    // 전체 Bookmark 정보를 조회하는 메서드
    public List<BookmarkDTO> selectAllBookmark() {
        List<BookmarkDTO> bookmarkList = new ArrayList<>();

        String query = "SELECT B.id as id, wifiName, bookmarkName, B.createdAt " +
                "FROM Bookmark B " +
                "    left join WifiInfo WI on WI.managementNumber = B.wifiManagementNumber " +
                "    left join BookmarkGroup BG on BG.id = B.bookmarkGroupId;";

        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet rs = preparedStatement.executeQuery()) {

            while (rs.next()) {
                BookmarkDTO bookmark = new BookmarkDTO(rs);
                bookmarkList.add(bookmark);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bookmarkList;
    }
}
