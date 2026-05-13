import model.Kategoria;
import model.Seuranta;
import model.Tapahtuma;

void main() {

    Seuranta seuranta = new Seuranta();

    Kategoria ruoka = new Kategoria("Ruoka", true);

    Tapahtuma t1 = new Tapahtuma("Kauppa", -25.5, ruoka);
    Tapahtuma t2 = new Tapahtuma("Palkka", 2500, null);

    seuranta.lisaaKategoria(ruoka);

    seuranta.lisaaTapahtuma(t1);
    seuranta.lisaaTapahtuma(t2);

    System.out.println(seuranta.getTapahtumat());
    System.out.println(seuranta.laskeTulotYhteensa());
    System.out.println(seuranta.laskeMenotYhteensa());
}