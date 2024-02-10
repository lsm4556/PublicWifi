package com.zerobase.publicwifi.model;

import com.zerobase.publicwifi.config.DatabaseConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookmarkGroupDAO {
    String databaseUrl = DatabaseConfig.getDatabaseUrl();

    // 다른 메서드와 독립적으로 커넥션을 설정하는 메서드
    private Connection connect() throws SQLException {
        return DriverManager.getConnection(databaseUrl);
    }

    public void insertBookmarkGroup(String bookmarkName, int sequence) {
        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO BookmarkGroup (bookmarkName, sequence, createdAt) " +
                             "VALUES (?, ?, datetime('now', 'localtime'))"
             )) {
            // xCoordinate와 yCoordinate 값을 preparedStatement에 설정
            preparedStatement.setString(1, bookmarkName);
            preparedStatement.setInt(2, sequence);

            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }


    // id 받아서 삭제하는 메서드
    public void deleteBookmarkGroup(int id) {
        String query = "DELETE FROM BookmarkGroup WHERE id = ?";

        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // id 받아서 수정하는 메서드
    public void updateBookmarkGroup(String bookmarkName, int sequence, int id) {
        String query = "UPDATE BookmarkGroup " +
                "SET bookmarkName = ?, sequence = ?, updatedAt = datetime('now', 'localtime') " +
                "WHERE id = ?";

        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, bookmarkName);
            preparedStatement.setInt(2, sequence);
            preparedStatement.setInt(3, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // BookmarkGroup id로 조회하는 메서드
    public BookmarkGroupDTO selectBookmarkGroupById(int id) {
        BookmarkGroupDTO bookmarkGroupById = null;

        String query = "SELECT * FROM BookmarkGroup WHERE id = ?";

        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // ?에 값 설정
            preparedStatement.setInt(1, id);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    bookmarkGroupById = new BookmarkGroupDTO(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bookmarkGroupById;
    }


    // 전체 BookmarkGroup 정보를 조회하는 메서드
    public List<BookmarkGroupDTO> selectAllBookmarkGroup() {
        List<BookmarkGroupDTO> bookmarkGroupList = new ArrayList<>();

        String query = "SELECT * FROM BookmarkGroup";

        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet rs = preparedStatement.executeQuery()) {

            while (rs.next()) {
                BookmarkGroupDTO bookmarkGroup = new BookmarkGroupDTO(rs);
                bookmarkGroupList.add(bookmarkGroup);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bookmarkGroupList;
    }
}
