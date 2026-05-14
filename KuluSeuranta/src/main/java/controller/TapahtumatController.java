package controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import javax.swing.*;
import javafx.event.ActionEvent;
import model.Kategoria;
import model.Tapahtuma;

import java.io.IOException;
import java.time.LocalDate;

public class TapahtumatController {

    @FXML
    private TableView<Tapahtuma> tapahtumaTable;

    @FXML
    private TableColumn<Tapahtuma, String> nimiColumn;

    @FXML
    private TableColumn<Tapahtuma, String> summaColumn;

    @FXML
    private TableColumn<Tapahtuma, String> pvmColumn;

    @FXML
    private TableColumn<Tapahtuma, String> kategoriaColumn;

    @FXML
    private TextField nimiText;

    @FXML
    private TextField summaText;

    @FXML
    private DatePicker pvmPicker;

    @FXML
    private ComboBox<Kategoria> lisaysKategoriaCombo;

    @FXML
    private Label tulotLabel;

    @FXML
    private Label menotLabel;

    @FXML
    private Label saldoLabel;

    @FXML
    private ComboBox<Kategoria> kategoriaCombo;

    @FXML
    private DatePicker alkuPvmPicker;

    @FXML
    private DatePicker loppuPvmPicker;

    private final ObservableList<Tapahtuma> tapahtumat =
            FXCollections.observableArrayList();

    @FXML
    public void initialize() {

        Kategoria palkka = new Kategoria("Palkka", false);
        Kategoria ruoka = new Kategoria("Ruoka", true);
        Kategoria harrastus = new Kategoria("Harrastus", false);

        lisaysKategoriaCombo.getItems().addAll(palkka, ruoka, harrastus);
        kategoriaCombo.getItems().addAll(palkka, ruoka, harrastus);

        pvmPicker.setValue(LocalDate.now());

        nimiColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getNimi())
        );

        summaColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getSumma() + " €")
        );

        pvmColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getPaivamaara().toString())
        );

        kategoriaColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getKategoria().getNimi())
        );

        tapahtumaTable.setItems(tapahtumat);

        paivitaSaldo();
    }

    @FXML
    public void handleLisaaTulo() {
        lisaaTapahtuma(true);
    }

    @FXML
    public void handleLisaaMeno() {
        lisaaTapahtuma(false);
    }

    private void lisaaTapahtuma(boolean onTulo) {

        String nimi = nimiText.getText();
        String summaTeksti = summaText.getText();
        LocalDate paivamaara = pvmPicker.getValue();
        Kategoria kategoria = lisaysKategoriaCombo.getValue();

        if (nimi == null || nimi.isBlank()) {
            IO.println("Nimi puuttuu");
            return;
        }

        if (summaTeksti == null || summaTeksti.isBlank()) {
            IO.println("Summa puuttuu");
            return;
        }

        if (paivamaara == null) {
            IO.println("Päivämäärä puuttuu");
            return;
        }

        if (kategoria == null) {
            IO.println("Kategoria puuttuu");
            return;
        }

        double summa;

        try {
            summa = Double.parseDouble(summaTeksti.replace(",", "."));
        } catch (NumberFormatException e) {
            IO.println("Summa ei ole numero");
            return;
        }

        if (summa < 0) {
            summa = Math.abs(summa);
        }

        if (!onTulo) {
            summa = -summa;
        }

        Tapahtuma uusi = new Tapahtuma(nimi, summa, paivamaara, kategoria);

        tapahtumat.add(uusi);
        paivitaSaldo();

        nimiText.clear();
        summaText.clear();
        pvmPicker.setValue(LocalDate.now());
        lisaysKategoriaCombo.setValue(null);

        IO.println("Lisättiin tapahtuma: " + nimi);
    }

    @FXML
    public void handleMuokkaa() {
        IO.println("Muokataan...");
    }

    @FXML
    public void handlePoista() {
        Tapahtuma valittu = tapahtumaTable.getSelectionModel().getSelectedItem();

        if (valittu == null) {
            IO.println("Valitse ensin poistettava tapahtuma");
            return;
        }

        tapahtumat.remove(valittu);
        paivitaSaldo();

        IO.println("Poistettiin tapahtuma: " + valittu.getNimi());
    }

    @FXML
    public void handleKategoriat(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/KategoriatView.fxml"));
        Scene scene = new Scene(loader.load());

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

        IO.println("Siirrytään kategoriat-näkymään");
    }

    @FXML
    public void handleHae() {
        IO.println("Haetaan...");
    }

    public void paivitaSaldo() {
        double tulot = 0;
        double menot = 0;

        for (Tapahtuma tapahtuma : tapahtumat) {
            if (tapahtuma.onTulo()) {
                tulot += tapahtuma.getSumma();
            } else if (tapahtuma.onMeno()) {
                menot += tapahtuma.getSumma();
            }
        }

        double saldo = tulot + menot;

        tulotLabel.setText(String.format("%.2f €", tulot));
        menotLabel.setText(String.format("%.2f €", menot));
        saldoLabel.setText(String.format("%.2f €", saldo));

    }
}