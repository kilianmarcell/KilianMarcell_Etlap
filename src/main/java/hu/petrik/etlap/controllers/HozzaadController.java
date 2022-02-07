package hu.petrik.etlap.controllers;

import hu.petrik.etlap.Etlap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class HozzaadController extends Controller {

    @FXML
    public TextField nevTextField;
    @FXML
    public TextField leirasTextField;
    @FXML
    public ChoiceBox<String> kategoriaChoiceBox;
    @FXML
    public Spinner<Integer> arSpinner;

    public void initialize() {
        kategoriaChoiceBox.getItems().addAll("előétel", "főétel", "desszert");
    }

    public void hozzaadButton(ActionEvent actionEvent) {
        String nev = nevTextField.getText().toString();
        String leiras = leirasTextField.getText().toString();
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
    }
}
