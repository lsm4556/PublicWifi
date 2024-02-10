package com.zerobase.publicwifi.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.zerobase.publicwifi.dto.BookmarkGroupDTO;
import com.zerobase.publicwifi.service.BookmarkGroupService;
import com.zerobase.publicwifi.util.ResponseUtil;
import com.zerobase.publicwifi.util.ValidationUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "bookmarkGroupController", urlPatterns = {"/bookmark-group"})
public class BookmarkGroupController extends HttpServlet {
    private final BookmarkGroupService bookmarkGroupService = new BookmarkGroupService();

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        // 쿼리 매개변수에서 id 값 추출
        String idParam = request.getParameter("id");

        // 전체 리스트 가져오기
        if (idParam == null || idParam.isEmpty()) {
            List<BookmarkGroupDTO> bookmarkGroupList = bookmarkGroupService.getAllBookmarkGroup();
            // JSON 형식으로 응답 전송
            String jsonResponse = new Gson().toJson(bookmarkGroupList);
            response.getWriter().write(jsonResponse);
        }
        // 특정 ID로 가져오기
        else {
            try {
                int id = Integer.parseInt(idParam);
                BookmarkGroupDTO bookmarkGroup = bookmarkGroupService.getBookmarkGroupById(id);

                if (bookmarkGroup != null) {
                    // JSON 형식으로 응답 전송
                    String jsonResponse = new Gson().toJson(bookmarkGroup);
                    response.getWriter().write(jsonResponse);
                } else {
                    // ID에 해당하는 그룹이 없는 경우 404 응답 전송
                    ResponseUtil.sendErrorResponse(response,
                            HttpServletResponse.SC_NOT_FOUND,"Bookmark group not found.");
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
        String bookmarkName = jsonObject.get("bookmarkName").getAsString();
        int sequence = jsonObject.get("sequence").getAsInt();

        // 유효성 검사
        if (!ValidationUtil.isNotEmpty(bookmarkName) || !ValidationUtil.isPositiveNumber(sequence)) {
            ResponseUtil.sendErrorResponse(response,
                    HttpServletResponse.SC_BAD_REQUEST,"Invalid input data.");
            return;
        }

        boolean success = bookmarkGroupService.createBookmarkGroup(bookmarkName, sequence);

        if (success) {
            // 성공적인 응답을 JSON 형식으로 구성
            ResponseUtil.sendSuccessResponse(response, "Bookmark group saved successfully.");
        } else {
            // 실패했을 때의 응답을 JSON 형식으로 구성
            ResponseUtil.sendErrorResponse(response,
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR,"Failed to save bookmark group.");
        }
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if ("PATCH".equalsIgnoreCase(request.getMethod())) {
            doPatch(request, response);
        } else {
            super.service(request, response);
        }
    }

    public void doPatch(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        BufferedReader reader = request.getReader();

        // 문자열을 JsonObject 로 파싱하여 원하는 정보 추출
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);

        // 이제 jsonObject 에서 원하는 속성을 추출할 수 있음
        String bookmarkName = jsonObject.get("bookmarkName").getAsString();
        int sequence = jsonObject.get("sequence").getAsInt();
        int id = jsonObject.get("id").getAsInt();

        // 유효성 검사 추가
        if (!ValidationUtil.isNotEmpty(bookmarkName)
                || !ValidationUtil.isPositiveNumber(sequence) || !ValidationUtil.isPositiveNumber(id)) {
            ResponseUtil.sendErrorResponse(response,
                    HttpServletResponse.SC_BAD_REQUEST, "Invalid input data.");
            return;
        }

        boolean success = bookmarkGroupService.updateBookmarkGroup(bookmarkName, sequence, id);

        if (success) {
            // 성공적인 응답을 JSON 형식으로 구성
            ResponseUtil.sendSuccessResponse(response, "Bookmark group updated successfully.");
        } else {
            // 실패했을 때의 응답을 JSON 형식으로 구성
            ResponseUtil.sendErrorResponse(response,
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR,"Failed to update bookmark group.");
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
                    HttpServletResponse.SC_BAD_REQUEST, "Invalid input data.");
            return;
        }

        boolean success = bookmarkGroupService.deleteBookmarkGroup(id);

        if (success) {
            // 성공적인 응답을 JSON 형식으로 구성
            ResponseUtil.sendSuccessResponse(response, "Bookmark group deleted successfully.");
        } else {
            // 실패했을 때의 응답을 JSON 형식으로 구성
            ResponseUtil.sendErrorResponse(response,
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR,"Failed to delete bookmark group.");
        }
    }
}
