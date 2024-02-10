package com.zerobase.publicwifi.service;

import com.zerobase.publicwifi.model.LocationHistoryDAO;
import com.zerobase.publicwifi.model.LocationHistoryDTO;

import java.util.List;

public class LocationHistoryService {
    private final LocationHistoryDAO locationHistoryDAO = new LocationHistoryDAO();

    public boolean createLocationHistory(String xCoordinate, String yCoordinate) {
        try {
            // DAO에 위치 정보 저장 요청
            locationHistoryDAO.insertLocationHistory(xCoordinate, yCoordinate);

            // 성공적으로 저장되었을 경우 true 반환
            return true;
        } catch (Exception e) {
            // 실패 시 예외 처리 및 false 반환
            e.printStackTrace();  // 실제로는 로깅 등으로 변경해야 합니다.
            return false;
        }
    }

    public boolean deleteLocationHistory(int id) {
        try {
            // DAO에 위치 정보 삭제 요청
            locationHistoryDAO.deleteLocationHistory(id);

            // 성공적으로 저장되었을 경우 true 반환
            return true;
        } catch (Exception e) {
            // 실패 시 예외 처리 및 false 반환
            e.printStackTrace();  // 실제로는 로깅 등으로 변경해야 합니다.
            return false;
        }
    }

    // 위치 정보를 조회하는 메서드
    public List<LocationHistoryDTO> getAllLocationHistory() {
        return locationHistoryDAO.selectAllLocationHistory();
    }
}
