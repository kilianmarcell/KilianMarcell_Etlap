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

    public int addEtel(String nev, String leiras, String kategoria, int ar) throws SQLException {
        String sql = "INSERT INTO etlap (nev, leiras,  kategoria, ar) VALUES (?, ?, ?, ?)";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1, nev);
        stmt.setString(2, leiras);
        stmt.setString(3, kategoria);
        stmt.setInt(4, ar);
        return stmt.executeUpdate();
    }

    public boolean deleteEtel(int id) throws SQLException {
        String sql = "DELETE FROM etlap WHERE id = ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, id);
        int sor = stmt.executeUpdate();
        return sor == 1;
    }

    public boolean emelSzazalek(int id, int szazalek) throws SQLException {
        String sql = "UPDATE etlap SET ar = ar * ((100 + ?) / 100) WHERE id = ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, szazalek);
        stmt.setInt(2, id);
        int erintettSorok = stmt.executeUpdate();
        return erintettSorok == 1;
    }

    public boolean emelSzazalekOsszes(int szazalek) throws SQLException {
        String sql = "UPDATE etlap SET ar = ar * ((100 + ?) / 100)";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, szazalek);
        int erintettSorok = stmt.executeUpdate();
        return erintettSorok == 1;
    }

    public boolean emelForint(int id, int ft) throws SQLException {
        String sql = "UPDATE etlap SET ar = ar + ? WHERE id = ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, ft);
        stmt.setInt(2, id);
        int erintettSorok = stmt.executeUpdate();
        return erintettSorok == 1;
    }

    public boolean emelForintOsszes(int ft) throws SQLException {
        String sql = "UPDATE etlap SET ar = ar + ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, ft);
        int erintettSorok = stmt.executeUpdate();
        return erintettSorok == 1;
    }
}
