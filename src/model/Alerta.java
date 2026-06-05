package model;

public class Alerta {

    public enum NivelRisco { BAIXO, MODERADO, ALTO, CRITICO }

    private String     titulo;
    private String     descricao;
    private NivelRisco nivel;
    private String     recomendacao;
    private Regiao     regiao;

    public Alerta(String titulo, String descricao,
                  NivelRisco nivel, String recomendacao, Regiao regiao) {
        this.titulo        = titulo;
        this.descricao     = descricao;
        this.nivel         = nivel;
        this.recomendacao  = recomendacao;
        this.regiao        = regiao;
    }

    public String     getTitulo()       { return titulo; }
    public String     getDescricao()    { return descricao; }
    public NivelRisco getNivel()        { return nivel; }
    public String     getRecomendacao() { return recomendacao; }
    public Regiao     getRegiao()       { return regiao; }

    @Override
    public String toString() {
        return String.format("[%s] %s — %s", nivel, titulo, recomendacao);
    }
}