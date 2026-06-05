package model;

import interfaces.Notificavel;
import java.util.ArrayList;
import java.util.List;


public class Produtor implements Notificavel {

    private String       nome;
    private String       email;
    private Regiao       regiao;
    private List<String> historicoNotificacoes;

    public Produtor(String nome, String email, Regiao regiao) {
        this.nome                  = nome;
        this.email                 = email;
        this.regiao                = regiao;
        this.historicoNotificacoes = new ArrayList<>();
    }

    @Override
    public void enviarAlerta(Alerta alerta) {
        String msg = "Alerta enviado para " + nome + ": " + alerta.getTitulo();
        historicoNotificacoes.add(msg);
    }

    @Override
    public void registrarNotificacao(String mensagem) {
        historicoNotificacoes.add(mensagem);
    }

    public List<String> getHistoricoNotificacoes() { return historicoNotificacoes; }
    public String getNome()   { return nome; }
    public String getEmail()  { return email; }
    public Regiao getRegiao() { return regiao; }
}