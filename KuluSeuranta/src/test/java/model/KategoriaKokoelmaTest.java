package model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class KategoriaKokoelmaTest {

    @AfterEach
    void poistaTestitiedosto() throws Exception {
        Files.deleteIfExists(Path.of("kategoriat.json"));
    }

    @Test
    void uusiKokoelmaOnAluksiTyhja() {
        KategoriaKokoelma kokoelma = new KategoriaKokoelma();

        assertEquals(0, kokoelma.getKategoriat().size());
    }

    @Test
    void lisaaKategoriaLisaaKategorianKokoelmaan() {
        KategoriaKokoelma kokoelma = new KategoriaKokoelma();

        kokoelma.lisaaKategoria("Ruoka", true);

        assertEquals(1, kokoelma.getKategoriat().size());
        assertEquals("Ruoka", kokoelma.getKategoriat().get(0).getNimi());
        assertTrue(kokoelma.getKategoriat().get(0).isValttamaton());
    }

    @Test
    void lisaaKategoriaTrimmaaNimen() {
        KategoriaKokoelma kokoelma = new KategoriaKokoelma();

        kokoelma.lisaaKategoria("  Ruoka  ", false);

        assertEquals("Ruoka", kokoelma.getKategoriat().get(0).getNimi());
    }

    @Test
    void tyhjaaTaiNullNimeaEiLisata() {
        KategoriaKokoelma kokoelma = new KategoriaKokoelma();

        kokoelma.lisaaKategoria("", true);
        kokoelma.lisaaKategoria("   ", true);
        kokoelma.lisaaKategoria(null, true);

        assertEquals(0, kokoelma.getKategoriat().size());
    }

    @Test
    void poistaKategoriaPoistaaKategorianKokoelmasta() {
        KategoriaKokoelma kokoelma = new KategoriaKokoelma();

        kokoelma.lisaaKategoria("Ruoka", true);
        Kategoria kategoria = kokoelma.getKategoriat().get(0);

        kokoelma.poistaKategoria(kategoria);

        assertEquals(0, kokoelma.getKategoriat().size());
    }

    @Test
    void nullKategorianPoistaminenEiRikoKokoelmaa() {
        KategoriaKokoelma kokoelma = new KategoriaKokoelma();

        kokoelma.poistaKategoria(null);

        assertEquals(0, kokoelma.getKategoriat().size());
    }
}