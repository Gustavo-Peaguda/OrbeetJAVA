package abstracts;

public abstract class EstacaoBase {

    protected String id;
    protected String nome;
    protected String endereco;
    protected boolean online;

    public EstacaoBase(String id, String nome, String endereco) {
        this.id       = id;
        this.nome     = nome;
        this.endereco = endereco;
        this.online   = true;
    }

    public abstract String gerarRelatorio();

    public String getId()            { return id; }
    public String getNome()          { return nome; }
    public String getEndereco()      { return endereco; }
    public boolean isOnline()        { return online; }
    public void setOnline(boolean v) { this.online = v; }
}