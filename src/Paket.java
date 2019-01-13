public class Paket implements Comparable<Paket> {
    private String adresse;
    private int gewicht;

    public Paket(final String adresse, final int gewicht) {
        this.adresse = adresse;
        this.gewicht = gewicht;
    }

    public String getAdresse() {
        return adresse;
    }

    public int getGewicht() {
        return gewicht;
    }

    public void setGewicht(final int gewicht) {
        this.gewicht = gewicht;
    }

    public void setAdresse(final String adresse) {
        this.adresse = adresse;
    }

    public boolean equals(final Paket paket) {
        return adresse.equals(paket.getAdresse()) && gewicht == paket.getGewicht();
    }

    public String toString() {
        return "Paket\n  Adressat: " + adresse + "\n  Gewicht: " + gewicht;
    }


    @Override
    public int compareTo(final Paket paket) {
        if (paket.getGewicht() < this.getGewicht())
            return 1;
        else if (paket.getGewicht() > this.getGewicht())
            return -1;
        return 0;
    }
}