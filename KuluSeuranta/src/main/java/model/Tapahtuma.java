package model;

public class Tapahtuma {
    private String nimi;
    private double summa;
    private Kategoria kategoria;

    public Tapahtuma(String nimi, double summa, Kategoria kategoria) {
        this.nimi = nimi;
        this.summa = summa;
        this.kategoria = kategoria;
    }

    public String getNimi() {
        return nimi;
    }

    public double getSumma() {
        return summa;
    }

    public Kategoria getKategoria() {
        return kategoria;
    }

    public void setKategoria(Kategoria kategoria) {
        this.kategoria = kategoria;
    }

    public boolean onTulo() {
        return getSumma() > 0;
    }

    public boolean onMeno() {
        return getSumma() < 0;
    }
}
