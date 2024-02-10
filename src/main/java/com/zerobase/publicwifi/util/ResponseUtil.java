package com.zerobase.publicwifi.util;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ResponseUtil {

    public static void sendErrorResponse(HttpServletResponse response, int statusCode, String message) throws IOException {
        String errorResponse = "{ \"success\": false, \"message\": \"" + message + "\" }";
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(statusCode);
        PrintWriter out = response.getWriter();
        out.print(errorResponse);
    }

    public static void sendSuccessResponse(HttpServletResponse response, String message) throws IOException {
        String successResponse = "{ \"success\": true, \"message\": \"" + message + "\" }";
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_OK);
        PrintWriter out = response.getWriter();
        out.print(successResponse);
    }
}
