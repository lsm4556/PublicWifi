package com.zerobase.publicwifi.config;

public class DatabaseConfig {
    private static final String dbFile = "/Users/isangmin/Desktop/PublicWifi/wifi.db";
    private static final String DATABASE_URL = "jdbc:sqlite:" + dbFile;

    public static String getDatabaseUrl() {
        return DATABASE_URL;
    }
}
