package model;

public class ZonaCultivo {

    public enum TipoCultura { SOJA, MILHO, CAFE, FRUTAS, HORTALICAS, OUTRO }

    private String      id;
    private String      nome;
    private double      areaHectares;
    private TipoCultura cultura;
    private String      endereco;
    private double      irrpAtual;
    private String      statusPolinizacao;

    public ZonaCultivo(String id, String nome, double areaHectares,
                       TipoCultura cultura, String endereco) {
        this.id                = id;
        this.nome              = nome;
        this.areaHectares      = areaHectares;
        this.cultura           = cultura;
        this.endereco          = endereco;
        this.irrpAtual         = 0.0;
        this.statusPolinizacao = "Aguardando analise";
    }

    public void atualizarIRRP(double irrp) {
        this.irrpAtual = irrp;
        if      (irrp >= 75) this.statusPolinizacao = "CRITICO";
        else if (irrp >= 50) this.statusPolinizacao = "ALTO";
        else if (irrp >= 25) this.statusPolinizacao = "MODERADO";
        else                 this.statusPolinizacao = "NORMAL";
    }

    public String      getId()                { return id; }
    public String      getNome()              { return nome; }
    public double      getAreaHectares()      { return areaHectares; }
    public TipoCultura getCultura()           { return cultura; }
    public String      getEndereco()          { return endereco; }
    public double      getIrrpAtual()         { return irrpAtual; }
    public String      getStatusPolinizacao() { return statusPolinizacao; }

    @Override
    public String toString() {
        return String.format("[%s] %s | %s | %.1f ha | IRRP: %.1f | %s",
                id, nome, cultura, areaHectares, irrpAtual, statusPolinizacao);
    }
}