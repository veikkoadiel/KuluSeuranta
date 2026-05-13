package controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import java.io.IOException;

public class KategoriatController {

    public void handleLisaaKategoria() {
        IO.println("Lisätään...");
    }

    public void handleMuokkaaKategoria() {
        IO.println("Muokataan...");
    }

    public void handlePoistaKategoria() {
        IO.println("Poistetaan...");
    }

    public void handleTakaisin(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TapahtumatView.fxml"));
        Scene scene = new Scene(loader.load());

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

        IO.println("Palattiin tapahtumat-näkymään");
    }
}