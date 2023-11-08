package Operatiuni;

import java.io.Serializable;

public class Operatiune implements Serializable {
    private int cantitate;
    private String tip;

    public Operatiune(int cantitate, String tip) {
        this.cantitate = cantitate;
        this.tip = tip;
    }

    public int getCantitate() {
        return cantitate;
    }

    public void setCantitate(int cantitate) {
        this.cantitate = cantitate;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    @Override
    public String toString() {
        return "Operatiune{" +
                "cantitate=" + cantitate +
                ", tip='" + tip + '\'' +
                '}';
    }
}
