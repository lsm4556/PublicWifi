package com.zerobase.publicwifi.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import DTO.LocationHistoryDTO;
import com.zerobase.publicwifi.service.LocationHistoryService;
import com.zerobase.publicwifi.util.ResponseUtil;
import com.zerobase.publicwifi.util.ValidationUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "locationHistoryController", urlPatterns = {"/location-history"})
public class LocationHistoryController extends HttpServlet {
    private final LocationHistoryService locationHistoryService = new LocationHistoryService();

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 위치 정보 조회
        List<LocationHistoryDTO> locationHistoryList = locationHistoryService.getAllLocationHistory();

        // 가져온 WifiInfoDTO 리스트를 JSON 형태로 변환
        String jsonResponse = new Gson().toJson(locationHistoryList);

        // 응답 전송
        response.getWriter().write(jsonResponse);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        BufferedReader reader = request.getReader();

        // 문자열을 JsonObject 로 파싱하여 원하는 정보 추출
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);

        // 이제 jsonObject 에서 원하는 속성을 추출할 수 있음
        String xCoordinate = jsonObject.get("xCoordinate").getAsString();
        String yCoordinate = jsonObject.get("yCoordinate").getAsString();

        // 유효성 검사 추가
        if (!ValidationUtil.isNotEmpty(xCoordinate) || !ValidationUtil.isNotEmpty(yCoordinate)) {
            ResponseUtil.sendErrorResponse(response,
                    HttpServletResponse.SC_BAD_REQUEST, "Invalid coordinates provided.");
            return;
        }

        boolean success = locationHistoryService.createLocationHistory(xCoordinate, yCoordinate);

        if (success) {
            // 성공적인 응답을 JSON 형식으로 구성
            ResponseUtil.sendSuccessResponse(response, "Location history saved successfully.");
        } else {
            // 실패했을 때의 응답을 JSON 형식으로 구성
            ResponseUtil.sendErrorResponse(response,
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR,"Failed to save location history.");
        }
    }

    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        BufferedReader reader = request.getReader();

        // 문자열을 JsonObject 로 파싱하여 원하는 정보 추출
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);

        // 이제 jsonObject 에서 원하는 속성을 추출할 수 있음
        int id = jsonObject.get("id").getAsInt();

        // 유효성 검사 추가
        if (!ValidationUtil.isPositiveNumber(id)) {
            ResponseUtil.sendErrorResponse(response,
                    HttpServletResponse.SC_BAD_REQUEST, "Invalid ID provided.");
            return;
        }

        boolean success = locationHistoryService.deleteLocationHistory(id);

        if (success) {
            // 성공적인 응답을 JSON 형식으로 구성
            ResponseUtil.sendSuccessResponse(response, "Location history deleted successfully.");
        } else {
            // 실패했을 때의 응답을 JSON 형식으로 구성
            ResponseUtil.sendErrorResponse(response,
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR,"Failed to delete location history.");
        }
    }
}
