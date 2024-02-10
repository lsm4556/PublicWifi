package com.zerobase.publicwifi.service;

import com.zerobase.publicwifi.model.WifiInfoDAO;
import com.zerobase.publicwifi.model.WifiInfoDTO;

public class WifiInfoDetailService {
    private final WifiInfoDAO wifiInfoDAO = new WifiInfoDAO();

    public WifiInfoDTO getWifiInfoDetail(String managementNumber) {
        return wifiInfoDAO.selectWifiInfoDetailByManagementNumber(managementNumber);
    }
}
