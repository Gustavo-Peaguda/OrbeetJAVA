package abstracts;

public abstract class ServicoInteligente {

    protected String nomeServico;

    public ServicoInteligente(String nomeServico) {
        this.nomeServico = nomeServico;
    }

    public abstract String gerarRecomendacao(double indiceRisco);

    public String classificarRisco(double indiceRisco) {
        if (indiceRisco >= 75) return "CRITICO";
        if (indiceRisco >= 50) return "ALTO";
        if (indiceRisco >= 25) return "MODERADO";
        return "BAIXO";
    }
}