package service;

import model.Alerta;
import model.Alerta.NivelRisco;
import model.DadosAmbientais;
import model.Produtor;
import model.Regiao;

public class SistemaAlertas {

    public Alerta gerarAlerta(double irrp, Regiao regiao, DadosAmbientais dados) {
        NivelRisco nivel;
        String titulo, descricao, recomendacao;

        if (irrp >= 75) {
            nivel         = NivelRisco.CRITICO;
            titulo        = "Risco Critico de Reducao da Polinizacao";
            descricao     = "As condicoes ambientais estao severamente desfavoraveis para as abelhas.";
            recomendacao  = "Instale sombreamento urgente e forneca alimentacao suplementar as colmeias.";
        } else if (irrp >= 50) {
            nivel         = NivelRisco.ALTO;
            titulo        = "Risco Alto de Reducao da Polinizacao";
            descricao     = "Temperatura e/ou seca podem impactar significativamente a atividade das abelhas.";
            recomendacao  = "Monitore as colmeias diariamente e prepare areas de sombra.";
        } else if (irrp >= 25) {
            nivel         = NivelRisco.MODERADO;
            titulo        = "Risco Moderado";
            descricao     = "Condicoes levemente desfavoraveis. Atencao recomendada.";
            recomendacao  = "Plante vegetacao auxiliar e mantenha agua disponivel para as abelhas.";
        } else {
            nivel         = NivelRisco.BAIXO;
            titulo        = "Condicoes Favoraveis";
            descricao     = "Ambiente adequado para a atividade das abelhas.";
            recomendacao  = "Continue o monitoramento regular.";
        }

        return new Alerta(titulo, descricao, nivel, recomendacao, regiao);
    }

    public void notificarProdutor(Produtor produtor, Alerta alerta) {
        produtor.enviarAlerta(alerta);
        produtor.registrarNotificacao(
                "Notificado em: " + java.time.LocalDateTime.now());
    }
}