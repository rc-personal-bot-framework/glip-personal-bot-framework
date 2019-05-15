package com.sheet;

import com.object.SheetRequest;
import com.object.User;

import java.sql.*;

class MysqlCon {
    public static void main(String args[]) {
        try {
            Connection con = getConn();
            User u = getUser("lewei");
            System.out.println(u.getDocId());
            System.out.println(u.getUserId());
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

    public static User getUser(String userId) throws SQLException {
        Connection con = getConn();
        PreparedStatement pstmt = con.prepareStatement("select * from gb_user where user_id = ?");
        pstmt.setString(1, userId);
        ResultSet rs = pstmt.executeQuery();
        User u = new User();
        while (rs.next()) {
            u.setUserId(rs.getString(1));
            u.setDocId(rs.getString(2));
            u.setQaText(rs.getString(3));
        }
        con.close();

        return u;
    }

    public static User saveUser(SheetRequest request) throws SQLException {
        Connection con = getConn();
        User u = new User();
        PreparedStatement pstmt = con.prepareStatement("INSERT INTO gb_user (user_id, doc_id, qa_text) VALUES (?, ?, ?)");
        pstmt.setString(1, request.getUserId());
        pstmt.setString(2, request.getDocId());
        pstmt.setString(3, request.getQaText());
        int rs = pstmt.executeUpdate();
        con.close();

        return u;
    }
}