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
            // JDBC 드라이버 로드만 수행
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
