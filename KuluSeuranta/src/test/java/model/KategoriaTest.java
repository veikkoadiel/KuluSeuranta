package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KategoriaTest {

    @Test
    void konstruktoriAsettaaArvotOikein() {
        Kategoria kategoria = new Kategoria("Ruoka", true);

        assertEquals("Ruoka", kategoria.getNimi());
        assertTrue(kategoria.isValttamaton());
    }

    @Test
    void setteritMuuttavatArvoja() {
        Kategoria kategoria = new Kategoria();

        kategoria.setNimi("Palkka");
        kategoria.setValttamaton(false);

        assertEquals("Palkka", kategoria.getNimi());
        assertFalse(kategoria.isValttamaton());
    }

    @Test
    void toStringPalauttaaNimen() {
        Kategoria kategoria = new Kategoria("Auto", false);

        assertEquals("Auto", kategoria.toString());
    }
}