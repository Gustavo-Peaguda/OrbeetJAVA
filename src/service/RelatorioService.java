package service;

import model.Produtor;
import model.ZonaCultivo;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class RelatorioService {

    private static final String SEP = "-".repeat(50);

    public String gerarRelatorioCompleto(
            Produtor produtor,
            List<ZonaCultivo> zonas,
            RedeConectividade rede) {

        String hora = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));

        StringBuilder sb = new StringBuilder();
        sb.append(SEP).append("\n");
        sb.append("  ORBEET  --  RELATORIO COMPLETO\n");
        sb.append("  Gerado em: ").append(hora).append("\n");
        sb.append(SEP).append("\n\n");

        // Produtor
        sb.append("PRODUTOR\n");
        sb.append("  Nome  : ").append(produtor.getNome()).append("\n");
        sb.append("  Email : ").append(produtor.getEmail()).append("\n");
        sb.append("  Regiao: ").append(produtor.getRegiao()).append("\n");

        // Notificacoes
        if (!produtor.getHistoricoNotificacoes().isEmpty()) {
            sb.append("\n  Notificacoes:\n");
            produtor.getHistoricoNotificacoes()
                    .forEach(n -> sb.append("    - ").append(n).append("\n"));
        }

        // Zonas
        sb.append("\n").append(SEP).append("\n");
        sb.append("ZONAS DE CULTIVO (").append(zonas.size()).append(")\n");
        sb.append(SEP).append("\n");
        if (zonas.isEmpty()) {
            sb.append("  Nenhuma zona cadastrada.\n");
        } else {
            zonas.forEach(z -> sb.append("  ").append(z).append("\n"));
        }

        // Rede de estacoes
        sb.append("\n").append(SEP).append("\n");
        sb.append("REDE DE ESTACOES\n");
        sb.append(SEP).append("\n");
        sb.append(rede.gerarRelatorioRede());

        return sb.toString();
    }
}