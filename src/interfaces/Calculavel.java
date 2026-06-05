package interfaces;

import model.DadosAmbientais;

public interface Calculavel {
    double calcularIndice(DadosAmbientais dados);
    double calcularIndice(DadosAmbientais dados, double pesoTemperatura);
}