package model;


public class Regiao {

    private String nome;
    private String estado;
    private double latitude;
    private double longitude;


    public Regiao(String nome, String estado, double latitude, double longitude) {
        this.nome      = nome;
        this.estado    = estado;
        this.latitude  = latitude;
        this.longitude = longitude;
    }


    public Regiao(String nome, String estado) {
        this(nome, estado, 0.0, 0.0);
    }

    public String getNome()      { return nome; }
    public String getEstado()    { return estado; }
    public double getLatitude()  { return latitude; }
    public double getLongitude() { return longitude; }

    @Override
    public String toString() {
        return nome + " - " + estado;
    }
}