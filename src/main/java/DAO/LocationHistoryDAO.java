package DAO;

import com.zerobase.publicwifi.config.DatabaseConfig;
import DTO.LocationHistoryDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LocationHistoryDAO {
    String databaseUrl = DatabaseConfig.getDatabaseUrl();

    // 다른 메서드와 독립적으로 커넥션을 설정하는 메서드
    private Connection connect() throws SQLException {
        return DriverManager.getConnection(databaseUrl);
    }

    public void insertLocationHistory(String xCoordinate, String yCoordinate) {
        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO LocationHistory (xCoordinate, yCoordinate, createdAt) " +
                             "VALUES (?, ?, datetime('now', 'localtime'))"
             )) {
            // xCoordinate와 yCoordinate 값을 preparedStatement에 설정
            preparedStatement.setString(1, xCoordinate);
            preparedStatement.setString(2, yCoordinate);

            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }


    // id 받아서 삭제하는 메서드
    public void deleteLocationHistory(int id) {
        String query = "DELETE FROM LocationHistory WHERE id = ?";

        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    // 전체 LocationHistory 정보를 조회하는 메서드
    public List<LocationHistoryDTO> selectAllLocationHistory() {
        List<LocationHistoryDTO> locationHistoryList = new ArrayList<>();

        String query = "SELECT * FROM LocationHistory";

        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet rs = preparedStatement.executeQuery()) {

            while (rs.next()) {
                LocationHistoryDTO locationHistory = new LocationHistoryDTO(rs);
                locationHistoryList.add(locationHistory);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return locationHistoryList;
    }
}
