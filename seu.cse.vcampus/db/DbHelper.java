package db;

import java.sql.*;

public class DbHelper {
    private static final String DB_DRIVER = "net.ucanaccess.jdbc.UcanaccessDriver";
    private static final String DB_URL = "jdbc:ucanaccess://vCampus.accdb";
    private static final String DB_USER = "";
    private static final String DB_PWD = "";

    /**
     * 链接数据库
     * @return
     */
    public static Connection getConnection() {
        Connection conn = null;

        try {
            Class.forName(DB_DRIVER);
            conn = DriverManager.getConnection(DB_URL);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return conn;
    }

    /**
     * 增 删 改 【Add Delete Update】
     * @param sql
     * @return
     */
    public static int executeNonQuery(String sql) {
        int result = 0;
        Connection conn = null;
        Statement stmt = null;

        try {
            conn = getConnection();
            stmt = conn.createStatement();
            result = stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            free(null, stmt, conn);
        } finally {
            free(null, stmt, conn);
        }

        return result;
    }

    /**
     * 查 【Query】
     * @param sql
     * @return
     */
    public static ResultSet executeQuery(String sql) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            free(rs, stmt, conn);
        }

        return rs;
    }

    public static void free(Statement st) {
        try {
            if(st != null) {
                st.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void free(ResultSet rs) {
        try {
            if(rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void free(Connection conn) {
        try {
            if(conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void free(ResultSet rs, Statement st, Connection conn) {
        free(rs);
        free(st);
        free(conn);
    }

}
