package com.zerobase.publicwifi.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.zerobase.publicwifi.model.BookmarkDTO;
import com.zerobase.publicwifi.service.BookmarkService;
import com.zerobase.publicwifi.util.ResponseUtil;
import com.zerobase.publicwifi.util.ValidationUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "bookmarkController", urlPatterns = {"/bookmark"})
public class BookmarkController extends HttpServlet {
    private final BookmarkService bookmarkService = new BookmarkService();

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        // 쿼리 매개변수에서 id 값 추출
        String idParam = request.getParameter("id");

        // 전체 리스트 가져오기
        if (idParam == null || idParam.isEmpty()) {
            List<BookmarkDTO> bookmarkList = bookmarkService.getAllBookmark();
            // JSON 형식으로 응답 전송
            String jsonResponse = new Gson().toJson(bookmarkList);
            response.getWriter().write(jsonResponse);
        }
        // 특정 ID로 가져오기
        else {
            try {
                int id = Integer.parseInt(idParam);
                BookmarkDTO bookmark = bookmarkService.getBookmarkById(id);

                if (bookmark != null) {
                    // JSON 형식으로 응답 전송
                    String jsonResponse = new Gson().toJson(bookmark);
                    response.getWriter().write(jsonResponse);
                } else {
                    // ID에 해당하는 북마크가 없는 경우 404 응답 전송
                    ResponseUtil.sendErrorResponse(response,
                            HttpServletResponse.SC_NOT_FOUND,"Bookmark not found.");
                }
            } catch (NumberFormatException e) {
                // 숫자로 변환할 수 없는 ID 값인 경우 400 응답 전송
                ResponseUtil.sendErrorResponse(response,
                        HttpServletResponse.SC_BAD_REQUEST, "Invalid ID format.");
            }
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        BufferedReader reader = request.getReader();

        // 문자열을 JsonObject 로 파싱하여 원하는 정보 추출
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);

        // 이제 jsonObject 에서 원하는 속성을 추출할 수 있음
        int bookmarkGroupId = jsonObject.get("bookmarkGroupId").getAsInt();
        String wifiManagementNumber = jsonObject.get("wifiManagementNumber").getAsString();

        // 유효성 검사
        if (!ValidationUtil.isPositiveNumber(bookmarkGroupId) ||
                !ValidationUtil.isNotEmpty(wifiManagementNumber) ) {
            ResponseUtil.sendErrorResponse(response,
                    HttpServletResponse.SC_BAD_REQUEST,"Invalid ID format.");
            return;
        }

        boolean success = bookmarkService.createBookmark(bookmarkGroupId, wifiManagementNumber);

        if (success) {
            // 성공적인 응답을 JSON 형식으로 구성
            ResponseUtil.sendSuccessResponse(response, "Bookmark saved successfully.");
        } else {
            // 실패했을 때의 응답을 JSON 형식으로 구성
            ResponseUtil.sendErrorResponse(response,
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR,"Failed to save bookmark.");
        }
    }

    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        BufferedReader reader = request.getReader();

        // 문자열을 JsonObject 로 파싱하여 원하는 정보 추출
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);

        // 이제 jsonObject 에서 원하는 속성을 추출할 수 있음
        int id = jsonObject.get("id").getAsInt();

        // 유효성 검사 추가
        if (!ValidationUtil.isPositiveNumber(id)) {
            ResponseUtil.sendErrorResponse(response,
                    HttpServletResponse.SC_BAD_REQUEST, "Invalid ID format.");
            return;
        }

        boolean success = bookmarkService.deleteBookmark(id);

        if (success) {
            // 성공적인 응답을 JSON 형식으로 구성
            ResponseUtil.sendSuccessResponse(response, "Bookmark deleted successfully.");
        } else {
            // 실패했을 때의 응답을 JSON 형식으로 구성
            ResponseUtil.sendErrorResponse(response,
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR,"Failed to delete bookmark.");
        }
    }
}
