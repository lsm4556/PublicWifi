package DTO;

import com.fasterxml.jackson.databind.JsonNode;

import java.sql.ResultSet;
import java.sql.SQLException;

public class WifiInfoDTO implements Comparable<WifiInfoDTO> {
    private double distance;
    private String managementNumber;
    private String district;
    private String wifiName;
    private String roadAddress;
    private String detailAddress;
    private String installationFloor;
    private String installationType;
    private String installationAgency;
    private String serviceType;
    private String networkType;
    private String installationYear;
    private String indoorOutdoorType;
    private String wifiAccessEnvironment;
    private String xCoordinate;
    private String yCoordinate;
    private String workDate;

    public WifiInfoDTO(JsonNode wifiInfoNode) {
        this.managementNumber = wifiInfoNode.path("X_SWIFI_MGR_NO").asText();
        this.district = wifiInfoNode.path("X_SWIFI_WRDOFC").asText();
        this.wifiName = wifiInfoNode.path("X_SWIFI_MAIN_NM").asText();
        this.roadAddress = wifiInfoNode.path("X_SWIFI_ADRES1").asText();
        this.detailAddress = wifiInfoNode.path("X_SWIFI_ADRES2").asText();
        this.installationFloor = wifiInfoNode.path("X_SWIFI_INSTL_FLOOR").asText();
        this.installationType = wifiInfoNode.path("X_SWIFI_INSTL_TY").asText();
        this.installationAgency = wifiInfoNode.path("X_SWIFI_INSTL_MBY").asText();
        this.serviceType = wifiInfoNode.path("X_SWIFI_SVC_SE").asText();
        this.networkType = wifiInfoNode.path("X_SWIFI_CMCWR").asText();
        this.installationYear = wifiInfoNode.path("X_SWIFI_CNSTC_YEAR").asText();
        this.indoorOutdoorType = wifiInfoNode.path("X_SWIFI_INOUT_DOOR").asText();
        this.wifiAccessEnvironment = wifiInfoNode.path("X_SWIFI_REMARS3").asText();
        this.xCoordinate = wifiInfoNode.path("LAT").asText();
        this.yCoordinate = wifiInfoNode.path("LNT").asText();
        this.workDate = wifiInfoNode.path("WORK_DTTM").asText();
    }

    public WifiInfoDTO(ResultSet rs) throws SQLException {
        this.managementNumber = rs.getString("managementNumber");
        this.district = rs.getString("district");
        this.wifiName = rs.getString("wifiName");
        this.roadAddress = rs.getString("roadAddress");
        this.detailAddress = rs.getString("detailAddress");
        this.installationFloor = rs.getString("installationFloor");
        this.installationType = rs.getString("installationType");
        this.installationAgency = rs.getString("installationAgency");
        this.serviceType = rs.getString("serviceType");
        this.networkType = rs.getString("networkType");
        this.installationYear = rs.getString("installationYear");
        this.indoorOutdoorType = rs.getString("indoorOutdoorType");
        this.wifiAccessEnvironment = rs.getString("wifiAccessEnvironment");
        this.xCoordinate = rs.getString("xCoordinate");
        this.yCoordinate = rs.getString("yCoordinate");
        this.workDate = rs.getString("workDate");
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getManagementNumber() {
        return managementNumber;
    }

    public String getDistrict() {
        return district;
    }

    public String getWifiName() {
        return wifiName;
    }

    public String getRoadAddress() {
        return roadAddress;
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public String getInstallationFloor() {
        return installationFloor;
    }

    public String getInstallationType() {
        return installationType;
    }

    public String getInstallationAgency() {
        return installationAgency;
    }

    public String getServiceType() {
        return serviceType;
    }

    public String getNetworkType() {
        return networkType;
    }

    public String getInstallationYear() {
        return installationYear;
    }

    public String getIndoorOutdoorType() {
        return indoorOutdoorType;
    }

    public String getWifiAccessEnvironment() {
        return wifiAccessEnvironment;
    }

    public String getXCoordinate() {
        return xCoordinate;
    }

    public String getYCoordinate() {
        return yCoordinate;
    }

    public String getWorkDate() {
        return workDate;
    }

    @Override
    public int compareTo(WifiInfoDTO other) {
        // 거리를 기준으로 오름차순 정렬
        return Double.compare(this.distance, other.distance);
    }
}
