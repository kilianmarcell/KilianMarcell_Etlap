package hu.petrik.etlap.controllers;

import hu.petrik.etlap.Etlap;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EtlapDB {

    Connection con;

    public EtlapDB() {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/etlapdb", "root", "");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Etlap> getEtel() throws SQLException {
        List<Etlap> etelek = new ArrayList<>();
        Statement stmt = con.createStatement();
        String sql = "SELECT * FROM etlap;";
        ResultSet result = stmt.executeQuery(sql);
        while (result.next()) {
            int id = result.getInt("id");
        }
    }
}
