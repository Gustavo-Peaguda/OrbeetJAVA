package model;

import abstracts.EstacaoBase;
import interfaces.Monitoravel;


public class EstacaoSensor extends EstacaoBase implements Monitoravel {

    private String          descricao;
    private DadosAmbientais ultimaLeitura;
    private int             totalLeituras;

    public EstacaoSensor(String id, String nome, String endereco, String descricao) {
        super(id, nome, endereco);
        this.descricao     = descricao;
        this.totalLeituras = 0;
    }

    @Override
    public void monitorar(DadosAmbientais dados) {
        this.ultimaLeitura = dados;
        this.totalLeituras++;
    }

    @Override
    public String getStatusAtual() {
        if (ultimaLeitura == null) return "Aguardando leitura...";
        return ultimaLeitura.toString();
    }

    @Override
    public String gerarRelatorio() {
        return String.format(
                "Estacao : %s (%s)\n   Endereco : %s\n   Descricao: %s\n   Leituras : %d\n   Status   : %s",
                nome, id, endereco, descricao, totalLeituras, getStatusAtual());
    }

    public String          getDescricao()      { return descricao; }
    public DadosAmbientais getUltimaLeitura()  { return ultimaLeitura; }
    public int             getTotalLeituras()  { return totalLeituras; }
}