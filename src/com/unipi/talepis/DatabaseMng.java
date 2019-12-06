package com.unipi.talepis;

import java.sql.*;

public class DatabaseMng {

    private Connection connect() {
        // SQLite connection string
        String url = "jdbc:sqlite:backchain.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public void insert(int aa_id, int code,String title,String dtime,int cost,String desc,String categ,String hash,String prvhash,String data,long timestamp,int nonce) {
        String sql = "INSERT INTO blockchain(aa_id,code,title,dtime,cost,desc,categ,hash,prvhash,data,timestamp,nonce) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, aa_id);
            pstmt.setInt(2, code);
            pstmt.setString(3, title);
            pstmt.setString(4, dtime);
            pstmt.setInt(5, cost);
            pstmt.setString(6, desc);
            pstmt.setString(7, categ);
            pstmt.setString(8, hash);
            pstmt.setString(9, prvhash);
            pstmt.setString(10, data);
            pstmt.setLong(11, timestamp);
            pstmt.setInt(12, nonce);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }



    public void viewProtucts() {
        String sql = "SELECT DISTINCT title FROM blockchain";
        String url = "jdbc:sqlite:backchain.db";
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getString("title"));
                //System.out.println(rs.getInt("id") + "\t" + rs.getString("name") + "\t" + rs.getDouble("capacity"));

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public void viewStat(String title) {
        String sql = "SELECT dtime, cost FROM blockchain WHERE title = '"+title+"'";
        String url = "jdbc:sqlite:backchain.db";
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            // loop through the result set
            while (rs.next()) {
                System.out.println("Timestamp :"+rs.getString("dtime")+ "\t Cost :" + rs.getString("cost"));


            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
}

    public void productStat(int result) {
        //Result is 1=Product Code and 2=Product Title
        if (result==1){


        }else{

        }
    }

}