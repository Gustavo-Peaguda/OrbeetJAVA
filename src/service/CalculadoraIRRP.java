package service;

import abstracts.ServicoInteligente;
import interfaces.Calculavel;
import model.DadosAmbientais;


public class CalculadoraIRRP extends ServicoInteligente implements Calculavel {

    public CalculadoraIRRP() {
        super("Calculadora IRRP");
    }

    @Override
    public double calcularIndice(DadosAmbientais dados) {
        return calcularIndice(dados, 1.0);
    }


    @Override
    public double calcularIndice(DadosAmbientais dados, double pesoTemperatura) {
        double risco = 0.0;

        if (dados.getTemperatura() > 35) {
            risco += (dados.getTemperatura() - 35) * 3.0 * pesoTemperatura;
        }
        if (dados.getUmidade() < 30) {
            risco += (30 - dados.getUmidade()) * 1.5;
        }
        risco += dados.getIndiceSeca() * 4.0;
        if (dados.isOndaDeCalor()) {
            risco += 20.0;
        }

        return Math.min(risco, 100.0);
    }

    @Override
    public String gerarRecomendacao(double indiceRisco) {
        return switch (classificarRisco(indiceRisco)) {
            case "CRITICO"  -> "Instale sombreamento urgente e forneca alimentacao suplementar as colmeias.";
            case "ALTO"     -> "Monitore as colmeias diariamente e prepare areas de sombra.";
            case "MODERADO" -> "Plante vegetacao auxiliar e mantenha agua disponivel para as abelhas.";
            default         -> "Condicoes favoraveis. Continue o monitoramento regular.";
        };
    }
}