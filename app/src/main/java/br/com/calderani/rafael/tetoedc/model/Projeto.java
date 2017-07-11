package br.com.calderani.rafael.tetoedc.model;

/**
 * Created by Rafael on 10/07/2017.
 * Classe que representa os projetos levantados pelos Moradores + Equipe
 */

public class Projeto {
    private String id;
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    private String nome;
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    private String responsaveisEquipe;
    public String getResponsaveisEquipe() { return responsaveisEquipe; }
    public void setResponsaveisEquipe(String responsaveisEquipe) { this.responsaveisEquipe = responsaveisEquipe; }


    private String responsaveisComunidade;
    public String getResponsaveisComunidade() { return responsaveisComunidade; }
    public void setResponsaveisComunidade(String responsaveisComunidade) { this.responsaveisEquipe = responsaveisComunidade; }

    private String dataInicio;
    public String getDataInicio() { return dataInicio; }
    public void setDataInicio(String dataInicio) { this.dataInicio = dataInicio; }

    private String dataFim;
    public String getDataFim() { return dataFim; }
    public void setDataFim(String dataFim) { this.dataInicio = dataFim; }

    private String status;
    public String getStatus() { return status; }
    public void setStatus(String status) { this.dataInicio = status; }
}
