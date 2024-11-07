package vn.com.iuh.fit.backend.configs;

import java.sql.Connection;

public class ConnectDatabase {
    public static final String URL = "jdbc:mariadb://localhost:3306/works";
    public static final String USER = "root";
    public static final String PASSWORD = "123456";
    public static final String DRIVER = "org.mariadb.jdbc.Driver";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName(DRIVER);
            connection = java.sql.DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }
}
