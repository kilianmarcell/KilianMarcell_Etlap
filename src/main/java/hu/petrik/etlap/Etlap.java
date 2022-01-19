package hu.petrik.etlap;

public class Etlap {
    private int id, ar;
    private String nev, leiras, kategoria;

    public Etlap(int id, int ar, String nev, String leiras, String kategoria) {
        this.id = id;
        this.ar = ar;
        this.nev = nev;
        this.leiras = leiras;
        this.kategoria = kategoria;
    }

    public int getId() {
        return id;
    }

    public int getAr() {
        return ar;
    }

    public String getNev() {
        return nev;
    }

    public String getLeiras() {
        return leiras;
    }

    public String getKategoria() {
        return kategoria;
    }

    public void setAr(int ar) {
        this.ar = ar;
    }

    public void setNev(String nev) {
        this.nev = nev;
    }

    public void setLeiras(String leiras) {
        this.leiras = leiras;
    }

    public void setKategoria(String kategoria) {
        this.kategoria = kategoria;
    }
}
