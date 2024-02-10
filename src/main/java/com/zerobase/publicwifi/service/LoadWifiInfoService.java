package com.zerobase.publicwifi.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zerobase.publicwifi.model.WifiInfoDAO;
import com.zerobase.publicwifi.model.WifiInfoDTO;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.*;

public class LoadWifiInfoService {

    private final WifiInfoDAO wifiInfoDAO = new WifiInfoDAO();
    private final String authKey = "5068416d6a6c736d36394341756247";

    public List<WifiInfoDTO> loadAndSaveWifiInfo(int currentPage, int pageSize) {
        // API 호출을 위한 코드
        String apiUrl = String
                .format("http://openapi.seoul.go.kr:8088/%s/json/TbPublicWifiInfo/%d/%d",
                        authKey,
                        (currentPage - 1) * pageSize + 1, currentPage * pageSize);

        // 실제로 API 호출하고 데이터를 가져오는 코드
        // OkHttp 클라이언트 생성
        OkHttpClient client = new OkHttpClient();

        // HTTP 요청 생성
        Request httpRequest = new Request.Builder()
                .url(apiUrl)
                .get()
                .build();

        String responseData = null;
        // HTTP 요청 보내기
        try (Response httpResponse = client.newCall(httpRequest).execute()) {
            if (httpResponse.isSuccessful()) {
                // 응답 데이터 읽기
                responseData = httpResponse.body().string();

            } else {
                System.out.println("HTTP 요청 실패: " + httpResponse.code());
                return Collections.emptyList(); // 예외 발생 시 빈 리스트 반환
            }
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList(); // 예외 발생 시 빈 리스트 반환
        }

        // 반환 전에 데이터가 없는 경우 빈 리스트 반환
        List<WifiInfoDTO> wifiInfoList = convertAndSaveToDatabase(responseData);

        return wifiInfoList.isEmpty() ? Collections.emptyList() : wifiInfoList;
    }

    private List<WifiInfoDTO> convertAndSaveToDatabase(String apiResponse) {
        // Jackson ObjectMapper를 사용하여 JSON 파싱
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode;

        try {
            jsonNode = objectMapper.readTree(apiResponse);
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }

        // RESULT 항목이 있는지 확인
        if (jsonNode.has("RESULT")) {
            // RESULT 안의 CODE가 INFO-200이면 데이터가 없는 경우
            if (jsonNode.get("RESULT").has("CODE") && "INFO-200"
                    .equals(jsonNode.get("RESULT").get("CODE").asText())) {
                return Collections.emptyList();
            }
        }

        // TbPublicWifiInfo 안의 list_total_count 추출
        JsonNode tbPublicWifiInfoNode = jsonNode.path("TbPublicWifiInfo");
        JsonNode wifiInfoArrayNode = tbPublicWifiInfoNode.path("row");

        List<WifiInfoDTO> wifiInfoList = new ArrayList<>();

        for (JsonNode wifiInfoNode : wifiInfoArrayNode) {
            // WifiInfoDTO 객체 생성
            WifiInfoDTO wifiInfo = new WifiInfoDTO(wifiInfoNode);

            // 리스트에 추가
            wifiInfoList.add(wifiInfo);
        }


        // 데이터베이스에 저장
        wifiInfoDAO.batchInsertWifiInfo(wifiInfoList);

        return wifiInfoList;
    }

    // 기존 Wifi 정보를 전체 삭제하고 새로운 데이터를 추가하는 메서드
    public void deleteWifiInfo() {
        wifiInfoDAO.deleteWifiInfo();  // 기존 데이터 삭제
        wifiInfoDAO.resetAutoIncrement("WifiInfo");
    }

    public int getLastWifiInfoIndex() {
        return wifiInfoDAO.selectLastWifiInfoIndex();
    }

}
