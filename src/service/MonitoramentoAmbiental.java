package service;

import abstracts.MonitoramentoBase;
import interfaces.Monitoravel;
import model.DadosAmbientais;
import model.Regiao;


public class MonitoramentoAmbiental extends MonitoramentoBase implements Monitoravel {

    private String           statusAtual;
    private double           ultimoIRRP;
    private final CalculadoraIRRP calculadora;

    public MonitoramentoAmbiental(Regiao regiao) {
        super(regiao, "Monitoramento Ambiental ORBEET");
        this.calculadora = new CalculadoraIRRP();
        this.statusAtual = "Aguardando dados...";
    }

    @Override
    public void monitorar(DadosAmbientais dados) {
        ultimoIRRP  = calculadora.calcularIndice(dados);
        String nivel = calculadora.classificarRisco(ultimoIRRP);
        statusAtual  = "IRRP: " + String.format("%.1f", ultimoIRRP) + " | Risco: " + nivel;
    }

    @Override
    public String getStatusAtual() { return statusAtual; }

    @Override
    public String analisar(DadosAmbientais dados) {
        monitorar(dados);
        return gerarCabecalho() + "\n" + statusAtual + "\n"
                + calculadora.gerarRecomendacao(ultimoIRRP);
    }

    public double           getUltimoIRRP()    { return ultimoIRRP; }
    public CalculadoraIRRP  getCalculadora()   { return calculadora; }
}