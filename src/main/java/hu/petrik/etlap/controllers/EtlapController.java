package hu.petrik.etlap.controllers;

import hu.petrik.etlap.Etlap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class EtlapController extends Controller {

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