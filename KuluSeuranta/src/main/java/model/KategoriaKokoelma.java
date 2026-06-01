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


public class KategoriaKokoelma {

    private ObservableList<Kategoria> kategoriat =
            FXCollections.observableArrayList();

    public KategoriaKokoelma() {
        kategoriat.addListener((ListChangeListener<Kategoria>) change -> {
            tallenna();
        });
    }

    public ObservableList<Kategoria> getKategoriat() {
        return kategoriat;
    }

    public void lisaaKategoria(String nimi, boolean valttamaton) {
        if (nimi == null || nimi.isBlank()) {
            return;
        }

        kategoriat.add(new Kategoria(nimi.trim(), valttamaton));
    }

    public void poistaKategoria(Kategoria kategoria) {
        if (kategoria == null) {
            return;
        }

        kategoriat.remove(kategoria);
    }

    public void tallenna() {
        ObjectMapper mapper = new ObjectMapper();

        try {
            mapper.writeValue(Path.of("kategoriat.json").toFile(), kategoriat);
        } catch (JacksonException je) {
            IO.println("Kategorioiden tallennus epäonnistui: " + je.getMessage());
        }
    }

    public void lataa() {
        Path path = Path.of("kategoriat.json");

        if (Files.notExists(path)) {
            return;
        }

        try {
            ObjectMapper mapper = new ObjectMapper();

            List<Kategoria> kaikkiKategoriat =
                    mapper.readValue(path.toFile(), new TypeReference<>() {});

            kategoriat.addAll(kaikkiKategoriat);

        } catch (JacksonException je) {
            IO.println("JSONin lukeminen epäonnistui: " + je.getMessage());
        }
    }
}