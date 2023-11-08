package Spitale;

public class Pacient {
    private long CNP;
    private String nume;
    private int varsta;
    private int cod;


    public Pacient(long CNP, String nume, int varsta, int cod) {
        this.CNP = CNP;
        this.nume = nume;
        this.varsta = varsta;
        this.cod = cod;

    }

    public long getCNP() {
        return CNP;
    }

    public void setCNP(long CNP) {
        this.CNP = CNP;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public int getVarsta() {
        return varsta;
    }

    public void setVarsta(int varsta) {
        this.varsta = varsta;
    }

    @Override
    public String toString() {
        return "Pacient{" +
                "CNP=" + CNP +
                ", nume='" + nume + '\'' +
                ", varsta=" + varsta +
                ", cod=" + cod +
                '}';
    }
}
