package abstracts;

import model.DadosAmbientais;
import model.Regiao;


public abstract class MonitoramentoBase {

    protected Regiao regiao;
    protected String nomeServico;

    public MonitoramentoBase(Regiao regiao, String nomeServico) {
        this.regiao = regiao;
        this.nomeServico = nomeServico;
    }

    // Método abstrato — cada subclasse implementa sua análise
    public abstract String analisar(DadosAmbientais dados);

    // Método concreto compartilhado por todas
    public String gerarCabecalho() {
        return "=== " + nomeServico + " | Região: " + regiao.getNome() + " ===";
    }

    public Regiao getRegiao() { return regiao; }
    public String getNomeServico() { return nomeServico; }
}