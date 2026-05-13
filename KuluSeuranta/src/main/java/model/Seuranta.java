package model;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class Seuranta {

    private final ObservableList<Kategoria> kategoriat = FXCollections.observableArrayList();
    private final ObservableList<Tapahtuma> tapahtumat = FXCollections.observableArrayList();

    public ObservableList<Kategoria> getKategoriat() {
        return kategoriat;
    }

    public ObservableList<Tapahtuma> getTapahtumat() {
        return tapahtumat;
    }

    public void lisaaKategoria(Kategoria kategoria) {
        kategoriat.add(kategoria);
    }

    public void poistaKategoria(Kategoria kategoria) {
        kategoriat.remove(kategoria);

        for (Tapahtuma tapahtuma : tapahtumat) {
            if (tapahtuma.getKategoria() == kategoria) {
                tapahtuma.setKategoria(null);
            }
        }
    }

    public void lisaaTapahtuma(Tapahtuma tapahtuma) {
        tapahtumat.add(tapahtuma);
    }

    public void poistaTapahtuma(Tapahtuma tapahtuma) {
        tapahtumat.remove(tapahtuma);
    }

    public List<Tapahtuma> haeKategorianTapahtumat(Kategoria kategoria) {
        return tapahtumat.stream()
                .filter(t -> t.getKategoria() == kategoria)
                .toList();
    }

    public double laskeTulotYhteensa() {
        return tapahtumat.stream()
                .filter(t -> t.getSumma() > 0)
                .mapToDouble(Tapahtuma::getSumma)
                .sum();
    }

    public double laskeMenotYhteensa() {
        return tapahtumat.stream()
                .filter(t -> t.getSumma() < 0)
                .mapToDouble(Tapahtuma::getSumma)
                .sum();
    }
}