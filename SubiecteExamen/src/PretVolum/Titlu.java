package PretVolum;

public class Titlu {
    private String simbol;
    private String denumire;
    private double diferenta;

    public double getDiferenta() {
        return diferenta;
    }

    public void setDiferenta(double diferenta) {
        this.diferenta = diferenta;
    }

    public Titlu(String simbol, String denumire) {
        this.simbol = simbol;
        this.denumire = denumire;
    }

    public String getSimbol() {
        return simbol;
    }

    public void setSimbol(String simbol) {
        this.simbol = simbol;
    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    @Override
    public String toString() {
        return "Titlu{" +
                "simbol='" + simbol + '\'' +
                ", denumire='" + denumire + '\'' +
                '}';
    }
}

