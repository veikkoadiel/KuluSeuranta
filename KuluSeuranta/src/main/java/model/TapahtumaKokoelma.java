package model;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import tools.jackson.core.JacksonException;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class TapahtumaKokoelma {

    private ObservableList<Tapahtuma> tapahtumat =
            FXCollections.observableArrayList();

    public TapahtumaKokoelma() {
        tapahtumat.addListener((ListChangeListener<Tapahtuma>) change -> {
            tallenna();
        });
    }

    public ObservableList<Tapahtuma> getTapahtumat() {
        return tapahtumat;
    }

    public void lisaaTapahtuma(Tapahtuma tapahtuma) {
        if (tapahtuma == null) {
            return;
        }

        tapahtumat.add(tapahtuma);
    }

    public void poistaTapahtuma(Tapahtuma tapahtuma) {
        if (tapahtuma == null) {
            return;
        }

        tapahtumat.remove(tapahtuma);
    }

    public void tallenna() {
        ObjectMapper mapper = new ObjectMapper();

        try {
            mapper.writeValue(Path.of("tapahtumat.json").toFile(), tapahtumat);
        } catch (JacksonException je) {
            IO.println("Tapahtumien tallennus epäonnistui: " + je.getMessage());
        }
    }

    public void lataa() {
        Path path = Path.of("tapahtumat.json");

        if (Files.notExists(path)) {
            return;
        }

        try {
            ObjectMapper mapper = new ObjectMapper();

            List<Tapahtuma> kaikkiTapahtumat =
                    mapper.readValue(path.toFile(), new TypeReference<>() {});

            tapahtumat.addAll(kaikkiTapahtumat);

        } catch (JacksonException je) {
            IO.println("Tapahtumien lukeminen epäonnistui: " + je.getMessage());
        }
    }
}