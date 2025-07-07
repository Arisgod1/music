package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author 唐明迪
 * @date 2025-07-07
 * @description 数据库连接工具类，负责建立和关闭数据库连接
 */
public class DBUtil {
    private static final String URL = "jdbc:mysql://localhost:3306";
    private static final String USER = "root";  // 修改为你的数据库用户名
    private static final String PASSWORD = "";  // 修改为你的数据库密码

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");  // 加载MySQL驱动
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
