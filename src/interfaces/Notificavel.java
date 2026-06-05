package interfaces;

import model.Alerta;

public interface Notificavel {
    void enviarAlerta(Alerta alerta);
    void registrarNotificacao(String mensagem);
}
