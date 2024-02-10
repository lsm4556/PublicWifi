package com.zerobase.publicwifi.controller;

import com.google.gson.Gson;
import com.zerobase.publicwifi.dto.WifiInfoDTO;
import com.zerobase.publicwifi.service.NearByWifiService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@WebServlet(name = "nearbyWifiController", urlPatterns = {"/nearby-wifi"})
public class NearbyWifiController extends HttpServlet {

    private final NearByWifiService nearByWifiService = new NearByWifiService();

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");

        // 주소에서 쿼리 스트링 파라미터 값 읽어오기
        String lat = request.getParameter("lat");
        String lnt = request.getParameter("lnt");

        if (lat != null && lnt != null && !lat.isEmpty() && !lnt.isEmpty()) {
            // 근처 WIFI 정보 조회
            List<WifiInfoDTO> nearByWifiInfo = nearByWifiService.getNearByWifiInfo(lat, lnt);

            // 가져온 WifiInfoDTO 리스트를 JSON 형태로 변환
            String jsonResponse = new Gson().toJson(nearByWifiInfo);

            // 응답 전송
            response.getWriter().write(jsonResponse);
        } else {
            // 필수 파라미터가 없는 경우 에러 응답
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Missing required parameters");
        }
    }
}
