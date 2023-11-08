package PretVolum;

public class PretVolum {
    private String simbol;
    private double deschidere;
    private double max;
    private double min;
    private double inchidere;
    private long volum;

    public PretVolum(String simbol, double deschidere, double max, double min, double inchidere, long volum) {
        this.simbol = simbol;
        this.deschidere = deschidere;
        this.max = max;
        this.min = min;
        this.inchidere = inchidere;
        this.volum = volum;
    }

    public PretVolum(String linie) {
        this.simbol = linie.split(",")[0];
        this.deschidere = Double.parseDouble(linie.split(",")[1]);
        this.max = Double.parseDouble(linie.split(",")[2]);
        this.min = Double.parseDouble(linie.split(",")[3]);
        this.inchidere = Double.parseDouble(linie.split(",")[4]);
        this.volum = Long.parseLong(linie.split(",")[5]);
    }


    public String getSimbol() {
        return simbol;
    }

    public void setSimbol(String simbol) {
        this.simbol = simbol;
    }

    public double getDeschidere() {
        return deschidere;
    }

    public void setDeschidere(double deschidere) {
        this.deschidere = deschidere;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getInchidere() {
        return inchidere;
    }

    public void setInchidere(double inchidere) {
        this.inchidere = inchidere;
    }

    public long getVolum() {
        return volum;
    }

    public void setVolum(long volum) {
        this.volum = volum;
    }

    public double getValoare(){
        double valoare = this.getInchidere()*this.getInchidere();
        return valoare;
    }

    public double getDiferenta(){
        double diferenta = (this.getMax()-this.getMin())*100;
        return diferenta;
    }

    @Override
    public String toString() {
        return "Pret{" +
                "simbol='" + simbol + '\'' +
                ", deschidere=" + deschidere +
                ", max=" + max +
                ", min=" + min +
                ", inchidere=" + inchidere +
                ", volum=" + volum +
                '}';
    }


}
