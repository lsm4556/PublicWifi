package com.zerobase.publicwifi.controller;

import com.google.gson.Gson;
import com.zerobase.publicwifi.model.WifiInfoDTO;
import com.zerobase.publicwifi.service.WifiInfoDetailService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "getWifiDetailController", urlPatterns = {"/wifi-detail"})
public class GetWifiDetailController extends HttpServlet {

    private final WifiInfoDetailService wifiInfoDetailService = new WifiInfoDetailService();

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=UTF-8");

        // 주소에서 쿼리 스트링 파라미터 값 읽어오기
        String managementNumber = request.getParameter("managementNumber");

        if (managementNumber != null && !managementNumber.isEmpty()) {
            // 근처 WIFI 정보 조회
            WifiInfoDTO wifiInfoDetail = wifiInfoDetailService.getWifiInfoDetail(managementNumber);

            // 가져온 WifiInfoDTO를 JSON 형태로 변환
            String jsonResponse = new Gson().toJson(wifiInfoDetail);

            // 응답 전송
            response.getWriter().write(jsonResponse);
        } else {
            // 필수 파라미터가 없는 경우 에러 응답
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Missing required parameters");
        }
    }

}
