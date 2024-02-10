package com.zerobase.publicwifi.service;

import com.zerobase.publicwifi.DAO.WifiInfoDAO;
import com.zerobase.publicwifi.DTO.WifiInfoDTO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NearByWifiService {

    private final WifiInfoDAO wifiInfoDAO = new WifiInfoDAO();

    // 근처 Wifi 정보를 조회하는 메서드
    public List<WifiInfoDTO> getNearByWifiInfo(String lat, String lnt) {
        double userLat = Double.parseDouble(lat);
        double userLnt = Double.parseDouble(lnt);

        List<WifiInfoDTO> allWifiInfoList = wifiInfoDAO.selectAllWifiInfo();

        for (WifiInfoDTO wifiInfo : allWifiInfoList) {
            double wifiLat = Double.parseDouble(wifiInfo.getXCoordinate());
            double wifiLnt = Double.parseDouble(wifiInfo.getYCoordinate());

            double distance = calculateDistance(userLat, userLnt, wifiLat, wifiLnt);

            wifiInfo.setDistance(distance);
        }

        Collections.sort(allWifiInfoList);

        List<WifiInfoDTO> nearByWifiInfoList = new ArrayList<>();

        for (int i = 0; i < 30; i++) {
            nearByWifiInfoList.add(allWifiInfoList.get(i));
        }

        return nearByWifiInfoList;
    }


    private double calculateDistance(double userLat, double userLnt, double wifiLat, double wifiLnt) {
        // 거리 계산 로직을 구현하여 거리 반환
        double theta = userLnt - wifiLnt;
        double dist = Math.sin(deg2rad(userLat)) * Math.sin(deg2rad(wifiLat))
                + Math.cos(deg2rad(userLat)) * Math.cos(deg2rad(wifiLat)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515 * 1609.344;
        return Math.round(dist * 10.0) / 10000.0;
    }

    //10진수를 radian(라디안)으로 변환
    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    //radian(라디안)을 10진수로 변환
    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }
}
