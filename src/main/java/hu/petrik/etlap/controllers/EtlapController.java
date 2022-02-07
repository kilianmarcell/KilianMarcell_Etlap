package hu.petrik.etlap.controllers;

import hu.petrik.etlap.Etlap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class EtlapController extends Controller {

    @FXML
    public Spinner<Integer> forintEmelesSpinner;
    @FXML
    public Spinner<Integer> szazalekEmelesSpinner;
    @FXML
    private TableView<Etlap> dbTableView;
    @FXML
    private TableColumn<Etlap, Integer> arCol;
    @FXML
    private TableColumn<Etlap, String> nevCol;
    @FXML
    private TableColumn<Etlap, String> kategoriaCol;
    @FXML
    private TextArea elemLeirasArea;

    private EtlapDB db;

    public void initialize() {
        nevCol.setCellValueFactory(new PropertyValueFactory<>("nev"));
        kategoriaCol.setCellValueFactory(new PropertyValueFactory<>("kategoria"));
        arCol.setCellValueFactory(new PropertyValueFactory<>("ar"));

        try {
            db = new EtlapDB();
            List<Etlap> lista = db.getEtlap();

            for (Etlap e : lista) {
                dbTableView.getItems().add(e);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @FXML
    public void torlesButton(ActionEvent actionEvent) {
        int selectedIndex = dbTableView.getSelectionModel().getSelectedIndex();
        if (selectedIndex == -1) {
            alert("A törléshez előbb válasszon ki egy elemet a táblázatból");
            return;
        }

        if (!confirm("Biztosan törölni szeretné az ételt?")) {
            return;
        }

        Etlap torlendoEtel = dbTableView.getSelectionModel().getSelectedItem();
        try {
            db.deleteEtel(torlendoEtel.getId());
            alert("Sikeres törlés");
            etlapListaUjratolt();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void etelFelvetelButton(ActionEvent actionEvent) {
        try {
            Controller felvetel = ujAblak("hozzaad-view.fxml", "Étel hozzáadása", 300, 400);
            felvetel.getStage().setOnCloseRequest(event -> etlapListaUjratolt());
            felvetel.getStage().show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void szazalekEmelesButton(ActionEvent actionEvent) {
    }

    @FXML
    public void forintEmelesButton(ActionEvent actionEvent) {
        int emeles = forintEmelesSpinner.getValue();

        int selectedIndex = dbTableView.getSelectionModel().getSelectedIndex();
        Etlap emelEtel = dbTableView.getSelectionModel().getSelectedItem();

        if (selectedIndex == -1) {
            if (!confirm("Biztos emelni szeretné az összes étel árát?")) {
                return;
            }
            try {
                db.emelForintOsszes(emeles);
                alert("Sikeres emelés!");
                etlapListaUjratolt();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            if (!confirm("Biztos emelni szeretné az étel árát?")) {
                return;
            }
            try {
                db.emelForint(emelEtel.getId(), emeles);
                alert("Sikeres emelés!");
                etlapListaUjratolt();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void etelClick(MouseEvent mouseEvent) {
        Etlap etel = dbTableView.getSelectionModel().getSelectedItem();
        elemLeirasArea.setText(etel.getLeiras());
    }

    private void etlapListaUjratolt() {
        try {
            List<Etlap> lista =db.getEtlap();
            dbTableView.getItems().clear();

            for (Etlap e : lista) {
                dbTableView.getItems().add(e);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}