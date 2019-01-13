import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;


public class Lagerhalle implements Comparable<Lagerhalle>, Iterable<Paket> {

    private String adresse;
    private Map<String, Set<Paket>> paketeGruppiertNachAdresse;

    public Lagerhalle(final String adresse, final Map<String, Set<Paket>> pakete) {
        this.adresse = adresse;
        this.paketeGruppiertNachAdresse = pakete;
    }

    public Lagerhalle(final String adresse) {
        this.adresse = adresse;
        this.paketeGruppiertNachAdresse = new HashMap<>();
    }

    public Lagerhalle(final String adresse, final Collection<Paket> pakete) {
        this.adresse = adresse;
        this.paketeGruppiertNachAdresse = new HashMap<>();
        for (Paket p: pakete)
            add(p);
    }


    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(final String adresse) {
        this.adresse = adresse;
    }

    public Map<String, Set<Paket>> getPaketeGruppiertNachAdresse() {
        return paketeGruppiertNachAdresse;
    }

    public void setPaketeGruppiertNachAdresse(final Map<String, Set<Paket>> paketeGruppiertNachAdresse) {
        this.paketeGruppiertNachAdresse = paketeGruppiertNachAdresse;
    }

    @Override
    public int compareTo(final Lagerhalle paket) {
        if (paket.gewicht() < this.gewicht())
            return 1;
        else if (paket.gewicht() > this.gewicht())
            return -1;
        return 0;
    }

    @Override
    public Iterator<Paket> iterator() {
        final Set<Paket> allePakete = new HashSet<>();
        for (Set<Paket> pakete: paketeGruppiertNachAdresse.values())
            allePakete.addAll(pakete);
        return allePakete.iterator();
    }

    public int gewicht() {
        int gewicht = 0;
        for (Paket p: this)
            gewicht += p.getGewicht();
        return gewicht;
    }

    public void add(final Paket paket) {
        if (paketeGruppiertNachAdresse.containsKey(paket.getAdresse()))
            paketeGruppiertNachAdresse.get(paket.getAdresse()).add(paket);
        else
            paketeGruppiertNachAdresse.put(paket.getAdresse(), new HashSet<>(Arrays.asList(paket)));
    }

    @Override
    public String toString() {
        final StringBuilder str = new StringBuilder("Lagerhalle:\n  Adresse: " + adresse + "\n");
        for (Paket p: this)
            str.append(p.toString()).append("\n");
        return str.toString();
    }

    public static void main(final String[] args) {
        final Lagerhalle lagerhalle = new Lagerhalle("Pinguinmannstraße 3, 85748 Garching");
        lagerhalle.add(new Paket("Pinguinfraustraße 4, 85748 Garching", 100));
        lagerhalle.add(new Paket("Pingustraße 1, 88888 Pinghausen", 85));
        lagerhalle.add(new Paket("Pingustraße 1, 88888 Pinghausen", 73));
        lagerhalle.add(new Paket("Antarktisplatz 3, 12345 Arktis", 107));
        lagerhalle.add(new Paket("Pinguinvollversammlung, 81578 Malediven", 20));
        lagerhalle.add(new Paket("Pingustraat 100, 54321 Pingustan", 2));
        lagerhalle.add(new Paket("Pinguinfraustraße 4, 85748 Garching", 34));
        lagerhalle.add(new Paket("Tierpark Hellabrun, Tierparkstr. 30, 81543 München", 1));
        lagerhalle.add(new Paket("Tierpark Hellabrun, Tierparkstr. 30, 81543 München", 0));
        lagerhalle.add(new Paket("Pingustraße 1, 88888 Pinghausen", 6));

        final String msg1 = StreamSupport.stream(lagerhalle.spliterator(), false)
                .sorted()
                .map(Paket::toString)
                .reduce((adresse1, adresse2) -> adresse1 + "\n" + adresse2)
                .get();

        final String msg2 = StreamSupport.stream(lagerhalle.spliterator(), false)
                .map(Paket::getAdresse)
                .distinct()
                .map(adresse -> {
                    final Set<Paket> pakete = lagerhalle.getPaketeGruppiertNachAdresse().get(adresse);
                    final int anzahl = pakete.size();
                    final int gesamtgewicht = pakete.stream().mapToInt(Paket::getGewicht).sum();
                    return "Lagerhalle:\n  Adresse: " + adresse +
                            "\n# Pakete: " + anzahl +
                            "\nGesamtgewicht: " + gesamtgewicht;
                })
                .collect(Collectors.joining("\n"));

        System.out.println(lagerhalle);
        System.out.println("\n---\n");
        System.out.println(msg1);
        System.out.println("\n---\n");
        System.out.println(msg2);
        System.out.println("\n---\n");
    }
}