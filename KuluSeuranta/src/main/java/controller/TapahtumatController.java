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

import javafx.event.ActionEvent;
import model.Kategoria;
import model.KategoriaKokoelma;
import model.Tapahtuma;
import model.TapahtumaKokoelma;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

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

    private KategoriaKokoelma kategoriaKokoelma = new KategoriaKokoelma();
    private TapahtumaKokoelma tapahtumaKokoelma = new TapahtumaKokoelma();

    @FXML
    public void initialize() {

        kategoriaKokoelma.lataa();
        tapahtumaKokoelma.lataa();

        lisaysKategoriaCombo.setItems(kategoriaKokoelma.getKategoriat());
        kategoriaCombo.setItems(kategoriaKokoelma.getKategoriat());

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

        kategoriaColumn.setCellValueFactory(cellData -> {
            Kategoria kategoria = cellData.getValue().getKategoria();

            if (kategoria == null) {
                return new SimpleStringProperty("-");
            }

            return new SimpleStringProperty(kategoria.getNimi());
        });

        tapahtumaTable.setItems(tapahtumaKokoelma.getTapahtumat());

        tapahtumaTable.getSelectionModel().selectedItemProperty().addListener(
            (observable, vanha, valittu) -> {
                if (valittu != null) {
                    nimiText.setText(valittu.getNimi());
                    summaText.setText(String.valueOf(Math.abs(valittu.getSumma())));
                    pvmPicker.setValue(valittu.getPaivamaara());

                    if (valittu.getKategoria() != null) {
                        for (Kategoria kategoria : kategoriaKokoelma.getKategoriat()) {
                            if (kategoria.getNimi().equals(valittu.getKategoria().getNimi())) {
                                lisaysKategoriaCombo.setValue(kategoria);
                                break;
                            }
                        }
                    } else {
                        lisaysKategoriaCombo.setValue(null);
                    }
                }
            }
        );

        paivitaSaldo();
    }

    @FXML
    public void handleLisaaTulo() {
        lisaaTapahtuma(true);
    }

    @FXML
    public void handleLisaaMeno() { lisaaTapahtuma(false); }

    private Double validoiTapahtuma(
            String nimi,
            String summaTeksti,
            LocalDate paivamaara,
            Kategoria kategoria) {

        if (nimi == null || nimi.isBlank()) {
            IO.println("Nimi puuttuu");
            return null;
        }

        nimi = nimi.trim();

        if (nimi.length() > 50) {
            IO.println("Nimi on liian pitkä");
            return null;
        }

        if (paivamaara == null) {
            IO.println("Päivämäärä puuttuu");
            return null;
        }

        if (paivamaara.isAfter(LocalDate.now().plusYears(1))) {
            IO.println("Päivämäärä on liian pitkällä");
            return null;
        }

        if (kategoria == null) {
            IO.println("Kategoria puuttuu");
            return null;
        }

        if (summaTeksti == null || summaTeksti.isBlank()) {
            IO.println("Summa puuttuu");
            return null;
        }

        double summa;

        try {
            summa = Double.parseDouble(summaTeksti.replace(",", "."));
        } catch (NumberFormatException e) {
            IO.println("Summa ei ole kelvollinen numero");
            return null;
        }

        if (summa <= 0) {
            IO.println("Summan täytyy olla suurempi kuin nolla");
            return null;
        }

        return summa;
    }

    private void lisaaTapahtuma(boolean onTulo) {

        String nimi = nimiText.getText();
        String summaTeksti = summaText.getText();
        LocalDate paivamaara = pvmPicker.getValue();
        Kategoria kategoria = lisaysKategoriaCombo.getValue();


        Double luettuSumma =
                validoiTapahtuma(nimi, summaTeksti, paivamaara, kategoria);

        if (luettuSumma == null) {
            return;
        }

        nimi = nimi.trim();

        double summa = luettuSumma;

        if (!onTulo) {
            summa = -summa;
        }

        Tapahtuma uusi = new Tapahtuma(nimi, summa, paivamaara, kategoria);

        tapahtumaKokoelma.lisaaTapahtuma(uusi);
        paivitaSaldo();

        nimiText.clear();
        summaText.clear();
        pvmPicker.setValue(LocalDate.now());
        lisaysKategoriaCombo.setValue(null);

        IO.println("Lisättiin tapahtuma: " + nimi);
    }

    @FXML
    public void handleMuokkaa() {

        Tapahtuma valittu = tapahtumaTable.getSelectionModel().getSelectedItem();

        if (valittu == null) {
            IO.println("Valitse muokattava tapahtuma");
            return;
        }

        String nimi = nimiText.getText();
        String summaTeksti = summaText.getText();
        LocalDate paivamaara = pvmPicker.getValue();
        Kategoria kategoria = lisaysKategoriaCombo.getValue();


        Double luettuSumma =
                validoiTapahtuma(nimi, summaTeksti, paivamaara, kategoria);

        if (luettuSumma == null) {
            return;
        }

        nimi = nimi.trim();

        double summa = luettuSumma;

        if (valittu.onMeno()) {
            summa = -summa;
        }

        valittu.setNimi(nimi);
        valittu.setSumma(summa);
        valittu.setPaivamaara(paivamaara);
        valittu.setKategoria(kategoria);

        tapahtumaTable.refresh();
        tapahtumaKokoelma.tallenna();
        paivitaSaldo();

        IO.println("Tapahtuma muokattu");
    }

    @FXML
    public void handlePoista() {
        Tapahtuma valittu = tapahtumaTable.getSelectionModel().getSelectedItem();

        if (valittu == null) {
            IO.println("Valitse poistettava tapahtuma");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Poista tapahtuma");
        alert.setHeaderText("Haluatko varmasti poistaa tapahtuman?");
        alert.setContentText("Tapahtuma: " + valittu.getNimi());

        Optional<ButtonType> vastaus = alert.showAndWait();

        if (vastaus.isPresent() && vastaus.get() == ButtonType.OK) {
            tapahtumaKokoelma.poistaTapahtuma(valittu);
            paivitaSaldo();

            IO.println("Poistettiin tapahtuma: " + valittu.getNimi());
        }
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

        ObservableList<Tapahtuma> tulokset =
                FXCollections.observableArrayList();

        Kategoria valittuKategoria = kategoriaCombo.getValue();
        LocalDate alku = alkuPvmPicker.getValue();
        LocalDate loppu = loppuPvmPicker.getValue();

        if (alku != null && loppu != null && alku.isAfter(loppu)) {
            IO.println("Alkupäivämäärä ei voi olla loppupäivämäärän jälkeen");
            return;
        }

        for (Tapahtuma tapahtuma : tapahtumaKokoelma.getTapahtumat()) {

            boolean kelpaa = true;

            if (valittuKategoria != null) {

                if (tapahtuma.getKategoria() == null ||
                        !tapahtuma.getKategoria().getNimi()
                                .equals(valittuKategoria.getNimi())) {

                    kelpaa = false;
                }
            }

            if (alku != null &&
                    tapahtuma.getPaivamaara().isBefore(alku)) {

                kelpaa = false;
            }

            if (loppu != null &&
                    tapahtuma.getPaivamaara().isAfter(loppu)) {

                kelpaa = false;
            }

            if (kelpaa) {
                tulokset.add(tapahtuma);
            }
        }

        tapahtumaTable.setItems(tulokset);

        IO.println("Hakutuloksia: " + tulokset.size());
    }

    @FXML
    public void handleTyhjenna() {

        kategoriaCombo.setValue(null);
        alkuPvmPicker.setValue(null);
        loppuPvmPicker.setValue(null);

        tapahtumaTable.setItems(
                tapahtumaKokoelma.getTapahtumat()
        );
    }

    public void paivitaSaldo() {
        double tulot = 0;
        double menot = 0;

        for (Tapahtuma tapahtuma : tapahtumaKokoelma.getTapahtumat()) {
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