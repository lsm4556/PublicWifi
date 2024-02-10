package com.zerobase.publicwifi.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookmarkGroupDTO {
    private int id;
    private String bookmarkName;
    private int sequence;
    private String createdAt;
    private String updatedAt;

    public BookmarkGroupDTO(ResultSet rs) throws SQLException {
        this.id = rs.getInt("id");
        this.bookmarkName = rs.getString("bookmarkName");
        this.sequence = rs.getInt("sequence");
        this.createdAt = rs.getString("createdAt");
        this.updatedAt = rs.getString("updatedAt");
    }
}
