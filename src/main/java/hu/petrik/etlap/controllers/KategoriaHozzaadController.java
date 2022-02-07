package hu.petrik.etlap.controllers;

import hu.petrik.etlap.Kategoria;
import hu.petrik.etlap.KategoriaDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.sql.SQLException;
import java.util.List;

public class KategoriaHozzaadController extends Controller {

    @FXML
    public TextField kategoriaNevTextField;

    private KategoriaDB kdb;
    private List<Kategoria> kategoriaLista;

    public void initialize() {
        try {
            kdb = new KategoriaDB();
            kategoriaLista = kdb.getKategoria();
        } catch (SQLException e) {
            hibaKiir(e);
        }
    }

    public void kategoriaHozzaadButton(ActionEvent actionEvent) {
        String nev = kategoriaNevTextField.getText().toString().trim();

        if (nev.isEmpty()) {
            alert("Név megadása kötelező!");
            return;
        }

        try {
            int i = 0;
            int listaHossza = kategoriaLista.size();

            while (i < listaHossza && !kategoriaLista.get(i).getNev().equals(nev.toLowerCase())) {
                i++;
            }

            if (i < listaHossza) {
                alert("A " + nev + " név már benne van a listában!");
                kategoriaNevTextField.setText("");
            } else {
                KategoriaDB kdb = new KategoriaDB();
                int siker = kdb.kategoriaHozzaadas(nev.toLowerCase());
                if (siker == 1) {
                    alert("Kategória hozzáadása sikeres!");
                    kategoriaNevTextField.setText("");
                } else {
                    alert("Kategória hozzáadása sikertelen!");
                }
            }
        } catch (SQLException e) {
            hibaKiir(e);
        }
    }
}
