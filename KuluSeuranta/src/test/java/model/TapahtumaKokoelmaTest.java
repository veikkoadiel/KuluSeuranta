package model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class TapahtumaKokoelmaTest {

    @AfterEach
    void poistaTestitiedosto() throws Exception {
        Files.deleteIfExists(Path.of("tapahtumat.json"));
    }

    @Test
    void uusiKokoelmaOnAluksiTyhja() {
        TapahtumaKokoelma kokoelma = new TapahtumaKokoelma();

        assertEquals(0, kokoelma.getTapahtumat().size());
    }

    @Test
    void lisaaTapahtumaLisaaTapahtumanKokoelmaan() {
        TapahtumaKokoelma kokoelma = new TapahtumaKokoelma();
        Tapahtuma tapahtuma = new Tapahtuma(
                "Kauppa",
                -12.50,
                LocalDate.of(2026, 6, 3),
                new Kategoria("Ruoka", true)
        );

        kokoelma.lisaaTapahtuma(tapahtuma);

        assertEquals(1, kokoelma.getTapahtumat().size());
        assertTrue(kokoelma.getTapahtumat().contains(tapahtuma));
    }

    @Test
    void nullTapahtumaaEiLisata() {
        TapahtumaKokoelma kokoelma = new TapahtumaKokoelma();

        kokoelma.lisaaTapahtuma(null);

        assertEquals(0, kokoelma.getTapahtumat().size());
    }

    @Test
    void poistaTapahtumaPoistaaTapahtumanKokoelmasta() {
        TapahtumaKokoelma kokoelma = new TapahtumaKokoelma();
        Tapahtuma tapahtuma = new Tapahtuma(
                "Kauppa",
                -12.50,
                LocalDate.of(2026, 6, 3),
                new Kategoria("Ruoka", true)
        );

        kokoelma.lisaaTapahtuma(tapahtuma);
        kokoelma.poistaTapahtuma(tapahtuma);

        assertEquals(0, kokoelma.getTapahtumat().size());
        assertFalse(kokoelma.getTapahtumat().contains(tapahtuma));
    }

    @Test
    void nullTapahtumanPoistaminenEiRikoKokoelmaa() {
        TapahtumaKokoelma kokoelma = new TapahtumaKokoelma();

        kokoelma.poistaTapahtuma(null);

        assertEquals(0, kokoelma.getTapahtumat().size());
    }

    @Test
    void tallennaLuoTiedoston() {
        TapahtumaKokoelma kokoelma = new TapahtumaKokoelma();

        kokoelma.lisaaTapahtuma(new Tapahtuma(
                "Kauppa",
                -12.50,
                LocalDate.of(2026, 6, 3),
                new Kategoria("Ruoka", false)
        ));

        assertTrue(Files.exists(Path.of("tapahtumat.json")));
    }
}