package model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class TapahtumaTest {

    @Test
    void konstruktoriAsettaaArvotOikein() {
        Kategoria kategoria = new Kategoria("Ruoka", true);
        LocalDate paivamaara = LocalDate.of(2026, 6, 3);

        Tapahtuma tapahtuma =
                new Tapahtuma("Kauppa", -12.50, paivamaara, kategoria);

        assertEquals("Kauppa", tapahtuma.getNimi());
        assertEquals(-12.50, tapahtuma.getSumma());
        assertEquals(paivamaara, tapahtuma.getPaivamaara());
        assertEquals(kategoria, tapahtuma.getKategoria());
    }

    @Test
    void setteritMuuttavatArvoja() {
        Tapahtuma tapahtuma = new Tapahtuma();

        Kategoria kategoria = new Kategoria("Palkka", false);

        tapahtuma.setNimi("Kesätyö");
        tapahtuma.setSumma(1000.0);
        tapahtuma.setPaivamaara(LocalDate.of(2026, 6, 4));
        tapahtuma.setKategoria(kategoria);

        assertEquals("Kesätyö", tapahtuma.getNimi());
        assertEquals(1000.0, tapahtuma.getSumma());
        assertEquals(kategoria, tapahtuma.getKategoria());
    }

    @Test
    void positiivinenSummaOnTulo() {
        Tapahtuma tapahtuma = new Tapahtuma("Palkka", 1000.0, LocalDate.now(), new Kategoria("Tulot", false));

        assertTrue(tapahtuma.onTulo());
        assertFalse(tapahtuma.onMeno());
    }

    @Test
    void negatiivinenSummaOnMeno() {
        Tapahtuma tapahtuma = new Tapahtuma("Kauppa", -25.0, LocalDate.now(), new Kategoria("Ruoka", true));

        assertTrue(tapahtuma.onMeno());
        assertFalse(tapahtuma.onTulo());
    }

    @Test
    void nollaEiOleTuloEikaMeno() {
        Tapahtuma tapahtuma = new Tapahtuma("Nolla", 0.0, LocalDate.now(), new Kategoria("Muu", false));

        assertFalse(tapahtuma.onTulo());
        assertFalse(tapahtuma.onMeno());
    }

    @Test
    void toStringPalauttaaNimen() {
        Tapahtuma tapahtuma = new Tapahtuma("Kauppa", -12.50, LocalDate.now(), new Kategoria("Ruoka", true));

        assertEquals("Kauppa", tapahtuma.toString());
    }
}