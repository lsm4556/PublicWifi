package com.zerobase.publicwifi.DAO;

import com.zerobase.publicwifi.config.DatabaseConfig;
import com.zerobase.publicwifi.DTO.WifiInfoDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WifiInfoDAO {

    String databaseUrl = DatabaseConfig.getDatabaseUrl();

    // 다른 메서드와 독립적으로 커넥션을 설정하는 메서드
    private Connection connect() throws SQLException {
        return DriverManager.getConnection(databaseUrl);
    }

    public void batchInsertWifiInfo(List<WifiInfoDTO> wifiInfoList) {
        // 데이터베이스에 대량으로 데이터 삽입하는 코드
        try (Connection connection = connect();

             PreparedStatement preparedStatement = connection.prepareStatement(
                     " INSERT INTO WifiInfo ( " +
                             " managementNumber, district, wifiName, roadAddress, detailAddress, " +
                             " installationFloor, installationType, installationAgency, serviceType, " +
                             " networkType, installationYear, indoorOutdoorType, wifiAccessEnvironment, " +
                             " xCoordinate, yCoordinate, workDate) " +
                             " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) "
             )) {
            for (WifiInfoDTO wifiInfo : wifiInfoList) {
                // WifiInfo 객체의 필드 값을 preparedStatement에 설정

                preparedStatement.setString(1, wifiInfo.getManagementNumber());
                preparedStatement.setString(2, wifiInfo.getDistrict());
                preparedStatement.setString(3, wifiInfo.getWifiName());
                preparedStatement.setString(4, wifiInfo.getRoadAddress());
                preparedStatement.setString(5, wifiInfo.getDetailAddress());
                preparedStatement.setString(6, wifiInfo.getInstallationFloor());
                preparedStatement.setString(7, wifiInfo.getInstallationType());
                preparedStatement.setString(8, wifiInfo.getInstallationAgency());
                preparedStatement.setString(9, wifiInfo.getServiceType());
                preparedStatement.setString(10, wifiInfo.getNetworkType());
                preparedStatement.setString(11, wifiInfo.getInstallationYear());
                preparedStatement.setString(12, wifiInfo.getIndoorOutdoorType());
                preparedStatement.setString(13, wifiInfo.getWifiAccessEnvironment());
                preparedStatement.setString(14, wifiInfo.getXCoordinate());
                preparedStatement.setString(15, wifiInfo.getYCoordinate());
                preparedStatement.setString(16, wifiInfo.getWorkDate());
                preparedStatement.addBatch();

                preparedStatement.executeBatch();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // 기존 Wifi 정보를 전체 삭제하는 메서드
    public void deleteWifiInfo() {
        String query = "DELETE FROM WifiInfo";

        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // AutoIncrement 초기화 메서드
    public void resetAutoIncrement(String tableName) {
        String query = "UPDATE sqlite_sequence SET seq = 0 WHERE name = ?";

        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, tableName);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 마지막 와이파이 id를 조회하는 메서드
    public int selectLastWifiInfoIndex() {
        String query = "SELECT id FROM WifiInfo ORDER BY id DESC LIMIT 1";

        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet rs = preparedStatement.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("id");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0; // 예외가 발생하거나 데이터가 없는 경우 0을 반환
    }


    // 전체 Wifi 정보를 조회하는 메서드
    public List<WifiInfoDTO> selectAllWifiInfo() {
        List<WifiInfoDTO> wifiInfoList = new ArrayList<>();

        String query = "SELECT * FROM WifiInfo";

        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet rs = preparedStatement.executeQuery()) {

            while (rs.next()) {
                WifiInfoDTO wifiInfo = new WifiInfoDTO(rs);
                wifiInfoList.add(wifiInfo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return wifiInfoList;
    }

    // wifi 정보를 상세 조회하는 메서드
    public WifiInfoDTO selectWifiInfoDetailByManagementNumber(String managementNumber) {
        WifiInfoDTO wifiInfoDetail = null;

        String query = "SELECT * FROM WifiInfo WHERE managementNumber = ?";

        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, managementNumber); // managementNumber를 설정

            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    wifiInfoDetail = new WifiInfoDTO(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return wifiInfoDetail;
    }

}
