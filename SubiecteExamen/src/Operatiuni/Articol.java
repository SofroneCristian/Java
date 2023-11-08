package Operatiuni;

public class Articol {
    private int cod;
    private String denumire;

    public Articol(int cod, String denumire) {
        this.cod = cod;
        this.denumire = denumire;
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

    @Override
    public String toString() {
        return "Articol{" +
                "cod=" + cod +
                ", denumire='" + denumire + '\'' +
                '}';
    }
}
