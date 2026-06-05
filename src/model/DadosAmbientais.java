package model;

public class DadosAmbientais {

    private double temperatura;
    private double umidade;
    private double indiceSeca;
    private boolean ondaDeCalor;

    public DadosAmbientais(double temperatura, double umidade,
                           double indiceSeca, boolean ondaDeCalor) {
        this.temperatura  = temperatura;
        this.umidade      = umidade;
        this.indiceSeca   = indiceSeca;
        this.ondaDeCalor  = ondaDeCalor;
    }

    // Sobrecarga: sem onda de calor
    public DadosAmbientais(double temperatura, double umidade, double indiceSeca) {
        this(temperatura, umidade, indiceSeca, false);
    }

    public double  getTemperatura()  { return temperatura; }
    public double  getUmidade()      { return umidade; }
    public double  getIndiceSeca()   { return indiceSeca; }
    public boolean isOndaDeCalor()   { return ondaDeCalor; }

    public void setTemperatura(double v)  { this.temperatura = v; }
    public void setUmidade(double v)      { this.umidade = v; }
    public void setIndiceSeca(double v)   { this.indiceSeca = v; }
    public void setOndaDeCalor(boolean v) { this.ondaDeCalor = v; }

    @Override
    public String toString() {
        return String.format("Temp: %.1f C  |  Umidade: %.1f%%  |  Seca: %.1f  |  Onda de Calor: %s",
                temperatura, umidade, indiceSeca, ondaDeCalor ? "SIM" : "NAO");
    }
}