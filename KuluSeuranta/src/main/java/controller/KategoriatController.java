package controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Kategoria;
import model.KategoriaKokoelma;
import model.Tapahtuma;
import model.TapahtumaKokoelma;

import java.io.IOException;
import java.util.Optional;

public class KategoriatController {

    @FXML
    private TextField kategoriaText;

    @FXML
    private TableView<Kategoria> kategoriaTable;

    @FXML
    private CheckBox valttamatonCheck;

    @FXML
    private TableColumn<Kategoria, String> kategoriaColumn;

    private KategoriaKokoelma kategoriaKokoelma = new KategoriaKokoelma();
    private TapahtumaKokoelma tapahtumaKokoelma = new TapahtumaKokoelma();


    @FXML
    public void initialize() {

        kategoriaColumn.setCellValueFactory(cellData -> {
            Kategoria kategoria = cellData.getValue();

            String teksti = kategoria.getNimi();

            if (kategoria.isValttamaton()) {
                teksti += " *";
            }

            return new SimpleStringProperty(teksti);
        });

        kategoriaKokoelma.lataa();
        tapahtumaKokoelma.lataa();

        kategoriaTable.setItems(kategoriaKokoelma.getKategoriat());


        kategoriaTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, vanha, valittu) -> {

                    if (valittu != null) {
                        kategoriaText.setText(valittu.getNimi());
                        valttamatonCheck.setSelected(valittu.isValttamaton());
                    }
                }
        );
    }

    @FXML
    public void handleLisaaKategoria(ActionEvent event) {

        String nimi = kategoriaText.getText();

        if (nimi == null || nimi.isBlank()) {
            IO.println("Kategorian nimi puuttuu");
            return;
        }

        boolean valttamaton = valttamatonCheck.isSelected();

        kategoriaKokoelma.lisaaKategoria(nimi, valttamaton);

        kategoriaText.clear();
        valttamatonCheck.setSelected(false);

        IO.println("Lisättiin kategoria: " + nimi);
    }

    @FXML
    public void handleMuokkaaKategoria() {

        Kategoria valittu = kategoriaTable.getSelectionModel().getSelectedItem();

        if (valittu == null) {
            IO.println("Valitse muokattava kategoria");
            return;
        }

        String uusiNimi = kategoriaText.getText();

        if (uusiNimi == null || uusiNimi.isBlank()) {
            IO.println("Nimi puuttuu");
            return;
        }

        valittu.setNimi(uusiNimi);
        valittu.setValttamaton(valttamatonCheck.isSelected());

        kategoriaTable.refresh();
        kategoriaKokoelma.tallenna();

        IO.println("Kategoria muokattu");
    }

    @FXML
    public void handlePoistaKategoria() {
        Kategoria valittu = kategoriaTable.getSelectionModel().getSelectedItem();

        if (valittu == null) {
            IO.println("Valitse ensin poistettava kategoria");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Poista kategoria");
        alert.setHeaderText("Haluatko varmasti poistaa kategorian?");
        alert.setContentText("Kategoria: " + valittu.getNimi());

        Optional<ButtonType> vastaus = alert.showAndWait();

        if (vastaus.isPresent() && vastaus.get() == ButtonType.OK) {
            for (Tapahtuma tapahtuma : tapahtumaKokoelma.getTapahtumat()) {
                if (tapahtuma.getKategoria() != null &&
                        tapahtuma.getKategoria().getNimi().equals(valittu.getNimi())) {
                    tapahtuma.setKategoria(null);
                }
            }

            tapahtumaKokoelma.tallenna();
            kategoriaKokoelma.poistaKategoria(valittu);

            kategoriaText.clear();
            valttamatonCheck.setSelected(false);

            IO.println("Poistettiin kategoria: " + valittu.getNimi());
        }
    }

    @FXML
    public void handleTakaisin(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TapahtumatView.fxml"));
        Scene scene = new Scene(loader.load());

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

        IO.println("Palattiin tapahtumat-näkymään");
    }
}