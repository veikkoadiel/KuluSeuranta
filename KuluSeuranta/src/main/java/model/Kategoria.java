package model;

public class Kategoria {

    private String nimi;
    private boolean valttamaton;

    public Kategoria() {}

    public Kategoria(String nimi, boolean valttamaton) {
        this.nimi = nimi;
        this.valttamaton = valttamaton;
    }

    public String getNimi() {
        return nimi;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

    public void setValttamaton(boolean valttamaton) {
        this.valttamaton = valttamaton;
    }

    public boolean isValttamaton() {
        return valttamaton;
    }

    @Override
    public String toString() {
        return nimi;
    }
}