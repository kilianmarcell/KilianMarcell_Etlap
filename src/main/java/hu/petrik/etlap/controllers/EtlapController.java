package hu.petrik.etlap.controllers;

import hu.petrik.etlap.Etlap;
import hu.petrik.etlap.EtlapDB;
import hu.petrik.etlap.Kategoria;
import hu.petrik.etlap.KategoriaDB;
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
    public ChoiceBox<String> szuresChoiceBox;
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
    @FXML
    public TableColumn<Kategoria, String> kategoriaTableCol;
    @FXML
    public TableView<Kategoria> kategoriaTableView;

    private EtlapDB db;
    private KategoriaDB kdb;
    private List<Kategoria> kategoriaLista;

    public void initialize() {
        szuresChoiceBox.getItems().add("összes");
        try {
            kdb = new KategoriaDB();
            kategoriaLista = kdb.getKategoria();
            for (Kategoria k : kategoriaLista) {
                szuresChoiceBox.getItems().add(k.getNev());
            }
        } catch (SQLException e) {
            hibaKiir(e);
        }

        db = new EtlapDB();
        kdb = new KategoriaDB();

        nevCol.setCellValueFactory(new PropertyValueFactory<>("nev"));
        kategoriaCol.setCellValueFactory(new PropertyValueFactory<>("kategoria"));
        arCol.setCellValueFactory(new PropertyValueFactory<>("ar"));

        kategoriaTableCol.setCellValueFactory(new PropertyValueFactory<>("nev"));

        etlapListaUjratolt();
        kategoriaListaUjratolt();
        szures();
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
        int emeles = szazalekEmelesSpinner.getValue();

        int selectedIndex = dbTableView.getSelectionModel().getSelectedIndex();
        Etlap emelesEtel = dbTableView.getSelectionModel().getSelectedItem();

        if (selectedIndex == -1) {
            if (!confirm("Biztos emelni szeretné az összes étel árát " + emeles + "%-kal?")) {
                return;
            }
            try {
                db.emelSzazalekOsszes(emeles);
                alert("Sikeres emelés!");
                etlapListaUjratolt();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            if (!confirm("Biztos emelni szeretné " + emeles + "%-kal az étel árát?")) {
                return;
            }
            try {
                db.emelSzazalek(emelesEtel.getId(), emeles);
                alert("Sikeres emelés!");
                etlapListaUjratolt();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void forintEmelesButton(ActionEvent actionEvent) {

        int emeles = forintEmelesSpinner.getValue();

        int selectedIndex = dbTableView.getSelectionModel().getSelectedIndex();
        Etlap emelEtel = dbTableView.getSelectionModel().getSelectedItem();

        if (selectedIndex == -1) {
            if (!confirm("Biztos emelni szeretné az összes étel árát " + emeles + " forinttal?")) {
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
            if (!confirm("Biztos emelni szeretné " + emeles + " forinttal az étel árát?")) {
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
            List<Etlap> etlapLista = db.getEtlap();
            dbTableView.getItems().clear();

            for (Etlap e : etlapLista) {
                dbTableView.getItems().add(e);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void kategoriaListaUjratolt() {
        try {
            List<Kategoria> kategoriaLista = kdb.getKategoria();
            kategoriaTableView.getItems().clear();

            for (Kategoria k : kategoriaLista) {
                kategoriaTableView.getItems().add(k);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void kategoriaHozzaadasButton(ActionEvent actionEvent) {
        try {
            Controller kategoriaHozzadas = ujAblak("kategoria-hozzaad-view.fxml", "Kategória hozzáadása", 250, 150);
            kategoriaHozzadas.getStage().setOnCloseRequest(event -> kategoriaListaUjratolt());
            kategoriaHozzadas.getStage().show();
        } catch (Exception e) {
            hibaKiir(e);
        }
    }

    public void kategoriaTorlesButton(ActionEvent actionEvent) {
        int selectedIndex = kategoriaTableView.getSelectionModel().getSelectedIndex();
        if (selectedIndex == -1) {
            alert("A törléshez válasszon ki egy elemet a táblázatból!");
            return;
        }
        Kategoria torlendoKategoria = kategoriaTableView.getSelectionModel().getSelectedItem();
        if (!confirm("Biztosan törölni szeretnéd a kategóriát?")) {
            return;
        }
        try {
            kdb.kategoriaTorles(torlendoKategoria.getId());
            alert("Sikeres törlés!");
            kategoriaListaUjratolt();
        } catch (SQLException e) {
            hibaKiir(e);
        }
    }

    public void szures() {
        szuresChoiceBox.getSelectionModel().selectedItemProperty().addListener( (v, oldValue, newValue) -> {
            try {
                if (newValue.equals("összes")) {
                    etlapListaUjratolt();
                } else {
                    List<Etlap> szurtEtlapLista = db.getSzurtEtlap(newValue);
                    dbTableView.getItems().clear();
                    for (Etlap etlap : szurtEtlapLista) {
                        dbTableView.getItems().add(etlap);
                    }
                }
            } catch (SQLException e) {
                hibaKiir(e);
            }
        });
    }
}