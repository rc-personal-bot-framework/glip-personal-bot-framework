package core;

import java.sql.*;

class MysqlCon {
    public static void main(String args[]) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = getConn();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from gb_user");
            while (rs.next())
                System.out.println(rs.getInt(1) + "  " + rs.getString(2) + "  " + rs.getString(3));
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static Connection getConn() {
        try {
            return DriverManager.getConnection(
                    "jdbc:mysql://glipbot-mysql.c3rpmzn5chk9.us-east-1.rds.amazonaws.com:3306/glipbot", "glipbot", "Test!123");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
//
//    public static User getUser(String userId) throws SQLException {
//        Connection con = getConn();
//        Statement stmt = con.createStatement();
//        ResultSet rs = stmt.executeQuery("select * from gb_user where userId = :1");
//
//    }
}