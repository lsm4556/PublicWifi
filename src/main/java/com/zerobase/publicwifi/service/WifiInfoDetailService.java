package com.zerobase.publicwifi.service;

import com.zerobase.publicwifi.dao.WifiInfoDAO;
import com.zerobase.publicwifi.dto.WifiInfoDTO;

public class WifiInfoDetailService {
    private final WifiInfoDAO wifiInfoDAO = new WifiInfoDAO();

    public WifiInfoDTO getWifiInfoDetail(String managementNumber) {
        return wifiInfoDAO.selectWifiInfoDetailByManagementNumber(managementNumber);
    }
}
