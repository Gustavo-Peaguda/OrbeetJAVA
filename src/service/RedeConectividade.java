package service;

import model.DadosAmbientais;
import model.EstacaoSensor;
import java.util.ArrayList;
import java.util.List;

public class RedeConectividade {

    private List<EstacaoSensor> estacoes;
    private int totalMensagensTransmitidas;

    public RedeConectividade() {
        this.estacoes                   = new ArrayList<>();
        this.totalMensagensTransmitidas = 0;
    }

    public void adicionarEstacao(EstacaoSensor estacao) {
        estacoes.add(estacao);
    }

    public DadosAmbientais coletarDadosRede() {
        List<EstacaoSensor> online = estacoes.stream()
                .filter(e -> e.isOnline() && e.getUltimaLeitura() != null)
                .toList();
        if (online.isEmpty()) return null;

        double  temp = online.stream().mapToDouble(e -> e.getUltimaLeitura().getTemperatura()).average().orElse(0);
        double  umid = online.stream().mapToDouble(e -> e.getUltimaLeitura().getUmidade()).average().orElse(0);
        double  seca = online.stream().mapToDouble(e -> e.getUltimaLeitura().getIndiceSeca()).average().orElse(0);
        boolean onda = online.stream().anyMatch(e -> e.getUltimaLeitura().isOndaDeCalor());

        totalMensagensTransmitidas += online.size();
        return new DadosAmbientais(temp, umid, seca, onda);
    }

    public String gerarRelatorioRede() {
        long onlineCount = estacoes.stream().filter(EstacaoSensor::isOnline).count();
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Estacoes: %d total  |  %d online  |  %d offline\n\n",
                estacoes.size(), onlineCount, estacoes.size() - onlineCount));
        if (estacoes.isEmpty()) {
            sb.append("Nenhuma estacao cadastrada.");
        } else {
            estacoes.forEach(e -> sb.append(e.gerarRelatorio()).append("\n\n"));
        }
        return sb.toString();
    }

    public List<EstacaoSensor> getEstacoes()  { return estacoes; }
    public int getTotalMensagens()            { return totalMensagensTransmitidas; }
}