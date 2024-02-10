package com.zerobase.publicwifi.listener;

import com.zerobase.publicwifi.config.DatabaseConfig;

import java.sql.*;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class AppInitializer implements ServletContextListener {
    String databaseUrl = DatabaseConfig.getDatabaseUrl();

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // 애플리케이션 초기화 코드
        initializeDatabaseTables();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // 애플리케이션 종료 코드
    }

    private void initializeDatabaseTables() {
        try {
            // JDBC 드라이버 로드 및 데이터베이스 연결
            Class.forName("org.sqlite.JDBC");
            Connection connection = DriverManager.getConnection(databaseUrl);
            Statement statement = connection.createStatement();

            // 테이블 생성
            statement.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS WifiInfo (" +
                            " id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            " managementNumber VARCHAR(30), " +
                            " district VARCHAR(30), " +
                            " wifiName VARCHAR(30), " +
                            " roadAddress VARCHAR(30), " +
                            " detailAddress VARCHAR(30), " +
                            " installationFloor VARCHAR(30), " +
                            " installationType VARCHAR(30), " +
                            " installationAgency VARCHAR(30), " +
                            " serviceType VARCHAR(30), " +
                            " networkType VARCHAR(30), " +
                            " installationYear VARCHAR(30), " +
                            " indoorOutdoorType VARCHAR(30), " +
                            " wifiAccessEnvironment VARCHAR(30), " +
                            " xCoordinate VARCHAR(30), " +
                            " yCoordinate VARCHAR(30), " +
                            " workDate TEXT" +
                            ")"
            );

            // 다른 초기화 작업 수행...

            // 연결 종료
            statement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
