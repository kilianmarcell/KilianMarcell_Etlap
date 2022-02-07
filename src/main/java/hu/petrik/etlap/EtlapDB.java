package hu.petrik.etlap;

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
        String sql = "SELECT * FROM etlap INNER JOIN kategoria ON (etlap.kategoria_id = kategoria.id)";
        ResultSet result = stmt.executeQuery(sql);
        while (result.next()) {
            int id = result.getInt("etlap.id");
            String nev = result.getString("etlap.nev");
            String leiras = result.getString("etlap.leiras");
            int ar = result.getInt("etlap.ar");
            String kategoria = result.getString("kategoria.nev");
            Etlap etlap = new Etlap(id, ar, nev, leiras, kategoria);
            etelek.add(etlap);
        }
        return etelek;
    }

    public int addEtel(String nev, String leiras, int kategoria, int ar) throws SQLException {
        String sql = "INSERT INTO etlap (nev, leiras,  kategoria_id, ar) VALUES (?, ?, ?, ?)";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1, nev);
        stmt.setString(2, leiras);
        stmt.setInt(3, kategoria);
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

    public List<Etlap> getSzurtEtlap(String kategoriaMegkotes) throws SQLException {
        List<Etlap> etlapok = new ArrayList<>();
        String sql = "SELECT * FROM etlap INNER JOIN kategoria ON (etlap.kategoria_id = kategoria.id) WHERE kategoria.nev = ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1,kategoriaMegkotes);
        ResultSet result = stmt.executeQuery();
        while (result.next()) {
            int id = result.getInt("etlap.id");
            String nev = result.getString("etlap.nev");
            String leiras = result.getString("etlap.leiras");
            int ar = result.getInt("etlap.ar");
            String kategoria = result.getString("kategoria.nev");
            Etlap etlap = new Etlap(id, ar, nev, leiras, kategoria);
            etlapok.add(etlap);
        }
        return etlapok;
    }
}
