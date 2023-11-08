package Autovehicule;

public class Revizie {
    private String nrInmatriculare;
    private String data_revizie;
    private String tip_revizie;
    private int km;
    private double cost_revizie;

    public Revizie(String nrInmatriculare, String data_revizie, String tip_revizie, int km, double cost_revizie) {
        this.nrInmatriculare = nrInmatriculare;
        this.data_revizie = data_revizie;
        this.tip_revizie = tip_revizie;
        this.km = km;
        this.cost_revizie = cost_revizie;
    }

    public String getNrInmatriculare() {
        return nrInmatriculare;
    }

    public void setNrInmatriculare(String nrInmatriculare) {
        this.nrInmatriculare = nrInmatriculare;
    }

    public String getData_revizie() {
        return data_revizie;
    }

    public void setData_revizie(String data_revizie) {
        this.data_revizie = data_revizie;
    }

    public String getTip_revizie() {
        return tip_revizie;
    }

    public void setTip_revizie(String tip_revizie) {
        this.tip_revizie = tip_revizie;
    }

    public int getKm() {
        return km;
    }

    public void setKm(int km) {
        this.km = km;
    }

    public double getCost_revizie() {
        return cost_revizie;
    }

    public void setCost_revizie(double cost_revizie) {
        this.cost_revizie = cost_revizie;
    }

    @Override
    public String toString() {
        return "Autobuze.Revizie{" +
                "nrInmatriculare='" + nrInmatriculare + '\'' +
                ", data_revizie='" + data_revizie + '\'' +
                ", tip_revizie='" + tip_revizie + '\'' +
                ", km=" + km +
                ", cost_revizie=" + cost_revizie +
                '}';
    }
}
