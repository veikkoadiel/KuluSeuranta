package model;

public class Kategoria {
    private String nimi;
    private boolean valttamaton;

    public Kategoria(String nimi, boolean valttamaton) {
        this.nimi = nimi;
        this.valttamaton = valttamaton;
    }

    public String getNimi() {
        return nimi;
    }

    public boolean isValttamaton() {
        return valttamaton;
    }

}
