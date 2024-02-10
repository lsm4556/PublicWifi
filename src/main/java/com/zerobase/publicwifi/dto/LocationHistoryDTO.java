package com.zerobase.publicwifi.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LocationHistoryDTO {
    private int id;
    private String xCoordinate;
    private String yCoordinate;
    private String createdAt;

    public int getId() {
        return id;
    }

    public String getxCoordinate() {
        return xCoordinate;
    }

    public String getyCoordinate() {
        return yCoordinate;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public LocationHistoryDTO(ResultSet rs) throws SQLException {
        this.id = rs.getInt("id");
        this.xCoordinate = rs.getString("xCoordinate");
        this.yCoordinate = rs.getString("yCoordinate");
        this.createdAt = rs.getString("createdAt");
    }
}
