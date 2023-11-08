package Autovehicule;

public class Autovehicul {
    private String nrInmatriculare;
    private String dataInmatriculare;
    private int nrKm;

    public Autovehicul(String nrInmatriculare, String dataInmatriculare, int nrKm) {
        this.nrInmatriculare = nrInmatriculare;
        this.dataInmatriculare = dataInmatriculare;
        this.nrKm = nrKm;
    }

    public String getNrInmatriculare() {
        return nrInmatriculare;
    }

    public void setNrInmatriculare(String nrInmatriculare) {
        this.nrInmatriculare = nrInmatriculare;
    }

    public String getDataInmatriculare() {
        return dataInmatriculare;
    }

    public void setDataInmatriculare(String dataInmatriculare) {
        this.dataInmatriculare = dataInmatriculare;
    }

    public int getNrKm() {
        return nrKm;
    }

    public void setNrKm(int nrKm) {
        this.nrKm = nrKm;
    }

    @Override
    public String toString() {
        return "Autobuze.Autovehicul{" +
                "nrInmatriculare='" + nrInmatriculare + '\'' +
                ", dataInmatriculare='" + dataInmatriculare + '\'' +
                ", nrKm=" + nrKm +
                '}';
    }
}
