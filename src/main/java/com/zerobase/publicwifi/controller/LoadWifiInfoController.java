package com.zerobase.publicwifi.controller;

import DTO.WifiInfoDTO;
import com.zerobase.publicwifi.service.LoadWifiInfoService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "loadWifiInfoController", value = "/load-wifi")
public class LoadWifiInfoController extends HttpServlet {

    private final LoadWifiInfoService loadWifiInfoService = new LoadWifiInfoService();

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        loadWifiInfoService.deleteWifiInfo();

        int pageSize = 1000;  // 한 페이지에 가져올 데이터 개수
        int currentPage = 1; // 현재 페이지

        try {
            while (true) {
                // LoadWifiInfoService 를 이용하여 와이파이 정보를 가져오고 저장
                List<WifiInfoDTO> wifiInfoList = loadWifiInfoService.loadAndSaveWifiInfo(currentPage, pageSize);

                if (wifiInfoList.isEmpty()) {
                    break;  // 더 이상 가져올 데이터가 없으면 종료
                }

                currentPage++;  // 다음 페이지로 이동
            }
            int listTotalCount = loadWifiInfoService.getLastWifiInfoIndex();

            // list_total_count 값을 세션에 저장
            request.getSession().setAttribute("list_total_count", listTotalCount);

            // JSP 페이지로 리다이렉트
            response.sendRedirect("/load-wifi.jsp");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
