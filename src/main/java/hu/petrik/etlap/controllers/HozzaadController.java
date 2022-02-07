package hu.petrik.etlap.controllers;

import hu.petrik.etlap.EtlapDB;
import hu.petrik.etlap.Kategoria;
import hu.petrik.etlap.KategoriaDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.sql.SQLException;
import java.util.List;

public class HozzaadController extends Controller {

    @FXML
    public TextField nevTextField;
    @FXML
    public TextArea leirasTextArea;
    @FXML
    public ChoiceBox<String> kategoriaChoiceBox;
    @FXML
    public Spinner<Integer> arSpinner;

    private KategoriaDB kdb;
    private List<Kategoria> kategoriaLista;

    public void initialize() {
        try {
            kdb = new KategoriaDB();
            kategoriaLista = kdb.getKategoria();
            for (Kategoria kategoria : kategoriaLista) {
                kategoriaChoiceBox.getItems().add(kategoria.getNev());
            }
        } catch (SQLException e) {
            hibaKiir(e);
        }
    }

    public void hozzaadButton(ActionEvent actionEvent) {
        String nev = nevTextField.getText().toString();
        String leiras = leirasTextArea.getText().toString();
        int kategoria = kategoriaChoiceBox.getSelectionModel().getSelectedIndex();
        int ar;

        if (nev.isEmpty()) {
            alert("Név megadása kötelező!");
            return;
        }

        if (leiras.isEmpty()) {
            alert("Leírás megadása kötelező!");
            return;
        }

        if (kategoria == -1) {
            alert("Kategória megadása kötelező!");
            return;
        }

        String kSzoveg = kategoriaChoiceBox.getValue().toString();

        try {
            ar = arSpinner.getValue();
        } catch (NullPointerException e) {
            alert("Ár megadása kötelező");
            return;
        }

        try {
            EtlapDB etlapDB = new EtlapDB();
            int siker = etlapDB.addEtel(nev, leiras, kSzoveg, ar);
            if (siker == 1) {
                alert("Étetl hozzáadása sikeres!");
            } else {
                alert("Étel hozzáadása sikertelen!");
            }
        } catch (Exception e) {
            hibaKiir(e);
        }

        nevTextField.setText("");
        leirasTextArea.setText("");
        kategoriaChoiceBox.setValue("");
        arSpinner.getValueFactory().setValue(0);
    }
}
