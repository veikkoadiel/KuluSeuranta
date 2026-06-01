package model;

import java.time.LocalDate;

public class Tapahtuma {
    private String nimi;
    private double summa;
    private LocalDate paivamaara;
    private Kategoria kategoria;


    public Tapahtuma() {}

    public Tapahtuma(String nimi, double summa, LocalDate paivamaara, Kategoria kategoria) {
        this.nimi = nimi;
        this.summa = summa;
        this.paivamaara = paivamaara;
        this.kategoria = kategoria;
    }

    public String getNimi() {
        return nimi;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

    public double getSumma() {
        return summa;
    }

    public void setSumma(double summa) {
        this.summa = summa;
    }

    public LocalDate getPaivamaara() {
        return paivamaara;
    }

    public void setPaivamaara(LocalDate paivamaara) {
        this.paivamaara = paivamaara;
    }

    public Kategoria getKategoria() {
        return kategoria;
    }

    public void setKategoria(Kategoria kategoria) {
        this.kategoria = kategoria;
    }

    public boolean onTulo() {
        return summa > 0;
    }

    public boolean onMeno() {
        return summa < 0;
    }

    @Override
    public String toString() {
        return nimi;
    }
}
