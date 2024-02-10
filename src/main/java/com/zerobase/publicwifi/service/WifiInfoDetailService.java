package com.zerobase.publicwifi.service;

import DAO.WifiInfoDAO;
import DTO.WifiInfoDTO;

public class WifiInfoDetailService {
    private final WifiInfoDAO wifiInfoDAO = new WifiInfoDAO();

    public WifiInfoDTO getWifiInfoDetail(String managementNumber) {
        return wifiInfoDAO.selectWifiInfoDetailByManagementNumber(managementNumber);
    }
}
