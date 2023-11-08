package Operatiuni;

import java.util.List;

public class Depozit {
    private List<Operatiune> operatiuni;
    private int cod;
    private int totalIntrari;
    private int stocNet;

    public int getStocNet() {
        return stocNet;
    }

    public void setStocNet(int stocNet) {
        this.stocNet = stocNet;
    }

    public int getTotalIntrari() {
        return totalIntrari;
    }

    public void setTotalIntrari(int totalIntrari) {
        this.totalIntrari = totalIntrari;
    }

    public Depozit(List<Operatiune> operatiuni, int cod) {
        this.operatiuni = operatiuni;
        this.cod = cod;
    }

    public List<Operatiune> getOperatiuni() {
        return operatiuni;
    }

    public void setOperatiuni(List<Operatiune> operatiuni) {
        this.operatiuni = operatiuni;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    @Override
    public String toString() {
        return "Depozit{" +
                "operatiuni=" + operatiuni +
                ", cod=" + cod +
                '}';
    }
}
