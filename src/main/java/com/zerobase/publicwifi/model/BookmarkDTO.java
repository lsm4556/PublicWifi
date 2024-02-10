package com.zerobase.publicwifi.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookmarkDTO {
    private int id;
    private String bookmarkName;
    private String wifiName;
    private String createdAt;

    public BookmarkDTO(ResultSet rs) throws SQLException {
        this.id = rs.getInt("id");
        this.bookmarkName = rs.getString("bookmarkName");
        this.wifiName = rs.getString("wifiName");
        this.createdAt = rs.getString("createdAt");
    }
}
