package Spitale;

public class Sectie {
    private int cod;
    private String denumire;
    private int numar_locuri;
    private double varsta_medie;

    public double getVarsta_medie() {
        return varsta_medie;
    }

    public void setVarsta_medie(double varsta_medie) {
        this.varsta_medie = varsta_medie;
    }

    public Sectie(int cod, String denumire, int numar_locuri) {
        this.cod = cod;
        this.denumire = denumire;
        this.numar_locuri = numar_locuri;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public int getNumar_locuri() {
        return numar_locuri;
    }

    public void setNumar_locuri(int numar_locuri) {
        this.numar_locuri = numar_locuri;
    }

    @Override
    public String toString() {
        return "Sectie{" +
                "cod=" + cod +
                ", denumire='" + denumire + '\'' +
                ", numar_locuri=" + numar_locuri +
                '}';
    }
}
