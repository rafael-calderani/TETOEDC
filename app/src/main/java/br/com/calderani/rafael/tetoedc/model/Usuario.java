package br.com.calderani.rafael.tetoedc.model;

/**
 * Created by Rafael on 09/07/2017.
 */

public class Usuario {
    private String id;
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    private String login;
    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }

    private String senha;
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    private String nome;
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    private String funcao;
    public String getFuncao() { return funcao; }
    public void setFuncao(String funcao) { this.funcao = funcao; }

    private String email;
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    private String fone;
    public String getFone() { return fone; }
    public void setFone(String fone) { this.fone = fone; }

    private String comunidadeId;
    public String getComunidadeId() { return comunidadeId; }
    public void getComunidadeId(String comunidadeId) { this.comunidadeId = comunidadeId; }
}
