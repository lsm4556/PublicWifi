package com.zerobase.publicwifi.service;

import com.zerobase.publicwifi.DAO.WifiInfoDAO;
import com.zerobase.publicwifi.DTO.WifiInfoDTO;

public class WifiInfoDetailService {
    private final WifiInfoDAO wifiInfoDAO = new WifiInfoDAO();

    public WifiInfoDTO getWifiInfoDetail(String managementNumber) {
        return wifiInfoDAO.selectWifiInfoDetailByManagementNumber(managementNumber);
    }
}
