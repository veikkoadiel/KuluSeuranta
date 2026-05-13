package controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.swing.*;
import javafx.event.ActionEvent;
import java.io.IOException;

public class TapahtumatController {

    public void handleLisaaTulo() {
        IO.println("Lisätään tuloa...");
    }

    public void handleLisaaMeno() {
        IO.println("Lisätään menoa...");
    }

    public void handleMuokkaa() {
        IO.println("Muokataan...");
    }

    public void handlePoista() {
        IO.println("Poistetaan...");
    }

    public void handleKategoriat(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/KategoriatView.fxml"));
        Scene scene = new Scene(loader.load());

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

        IO.println("Siirrytään kategoriat-näkymään");
    }

    public void handleHae() {
        IO.println("Haetaan...");
    }
}