package hu.petrik.etlap;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class KategoriaDB {
    Connection con;

    public KategoriaDB() {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/etlapdb", "root", "");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Kategoria> getKategoria() throws SQLException {
        List<Kategoria> kategoriak = new ArrayList<>();
        Statement stmt = con.createStatement();
        String sql = "SELECT * FROM kategoria";
        ResultSet result = stmt.executeQuery(sql);
        while (result.next()) {
            int id = result.getInt("kategoria.id");
            String nev = result.getString("kategoria.nev");
            Kategoria kategoria = new Kategoria(id, nev);
            kategoriak.add(kategoria);
        }
        return kategoriak;
    }

    public int kategoriaHozzaadas(String nev) throws SQLException {
        String sql = "INSERT INTO kategoria (nev) VALUES (?)";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1, nev);
        return stmt.executeUpdate();
    }

    public boolean kategoriaTorles(int id) throws SQLException {
        String sql = "DELETE FROM kategoria WHERE id = ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, id);
        int erintettSorok = stmt.executeUpdate();
        return erintettSorok == 1;
    }
}
