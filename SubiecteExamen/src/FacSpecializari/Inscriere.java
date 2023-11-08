package FacSpecializari;

public class Inscriere {
    private long cnp;
    private String nume_candidat;
    private Double nota_bacalaureat;
    private int cod_specializare_aleasa;

    public Inscriere(long cnp, String nume_candidat, Double nota_bacalaureat, int cod_specializare_aleasa) {
        this.cnp = cnp;
        this.nume_candidat = nume_candidat;
        this.nota_bacalaureat = nota_bacalaureat;
        this.cod_specializare_aleasa = cod_specializare_aleasa;
    }

    public long getCnp() {
        return cnp;
    }

    public void setCnp(long cnp) {
        this.cnp = cnp;
    }

    public String getNume_candidat() {
        return nume_candidat;
    }

    public void setNume_candidat(String nume_candidat) {
        this.nume_candidat = nume_candidat;
    }

    public Double getNota_bacalaureat() {
        return nota_bacalaureat;
    }

    public void setNota_bacalaureat(Double nota_bacalaureat) {
        this.nota_bacalaureat = nota_bacalaureat;
    }

    public int getCod_specializare_aleasa() {
        return cod_specializare_aleasa;
    }

    public void setCod_specializare_aleasa(int cod_specializare_aleasa) {
        this.cod_specializare_aleasa = cod_specializare_aleasa;
    }

    @Override
    public String toString() {
        return "Inscriere{" +
                "cnp=" + cnp +
                ", nume_candidat='" + nume_candidat + '\'' +
                ", nota_bacalaureat=" + nota_bacalaureat +
                ", cod_specializare_aleasa=" + cod_specializare_aleasa +
                '}';
    }
}
