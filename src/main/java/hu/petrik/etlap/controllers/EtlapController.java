package hu.petrik.etlap.controllers;

import hu.petrik.etlap.Etlap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.util.List;

public class EtlapController {

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
    }

    @FXML
    public void szazalekEmelesButton(ActionEvent actionEvent) {
    }

    @FXML
    public void forintEmelesButton(ActionEvent actionEvent) {
    }
}