package interfaces;

import model.DadosAmbientais;

public interface Monitoravel {
    void monitorar(DadosAmbientais dados);
    String getStatusAtual();
}