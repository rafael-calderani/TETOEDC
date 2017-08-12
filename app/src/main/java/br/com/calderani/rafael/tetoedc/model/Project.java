package br.com.calderani.rafael.tetoedc.model;

/**
 * Created by Rafael on 10/07/2017.
 * Classe que representa os projetos levantados pelos Moradores + Equipe
 */

public class Project {
    private String id;
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    private String name;
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    private String description;
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    private String responsaveisEquipe;
    public String getResponsaveisEquipe() { return responsaveisEquipe; }
    public void setResponsaveisEquipe(String responsaveisEquipe) { this.responsaveisEquipe = responsaveisEquipe; }


    private String responsaveisComunidade;
    public String getResponsaveisComunidade() { return responsaveisComunidade; }
    public void setResponsaveisComunidade(String responsaveisComunidade) { this.responsaveisEquipe = responsaveisComunidade; }

    private String createdOn;
    public String getCreatedOn() { return createdOn; }
    public void setCreatedOn(String createdOn) { this.createdOn = createdOn; }

    private String modifiedOn;
    public String getModifiedOn() { return modifiedOn; }
    public void setModifiedOn(String modifiedOn) { this.modifiedOn = modifiedOn; }

    private String completedOn;
    public String getCompletedOn() { return completedOn; }
    public void setCompletedOn(String completedOn) { this.createdOn = completedOn; }

    private String status;
    public String getStatus() { return status; }
    public void setStatus(String status) { this.createdOn = status; }
}
