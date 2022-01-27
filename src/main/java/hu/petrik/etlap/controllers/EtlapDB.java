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

    public List<Etlap> getEtlap() throws SQLException {
        List<Etlap> etelek = new ArrayList<>();
        Statement stmt = con.createStatement();
        String sql = "SELECT * FROM etlap;";
        ResultSet result = stmt.executeQuery(sql);
        while (result.next()) {
            int id = result.getInt("id");
            String nev = result.getString("nev");
            String leiras = result.getString("leiras");
            int ar = result.getInt("ar");
            String kategoria = result.getString("kategoria");
            Etlap etlap = new Etlap(id, ar, nev, leiras, kategoria);
            etelek.add(etlap);
        }
        return etelek;
    }
}
