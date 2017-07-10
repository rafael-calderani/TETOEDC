package br.com.calderani.rafael.tetoedc.model;

import java.util.List;

/**
 * Created by Rafael on 09/07/2017.
 */

public class Comunidade {
    private List<Usuario> participantesEquipeFixa;
    public List<Usuario> getParticipantesEquipeFixa() {
        return participantesEquipeFixa;
    }
    public void setParticipantesEquipeFixa(List<Usuario> participantesEquipeFixa) {
        this.participantesEquipeFixa = participantesEquipeFixa;
    }

    private String id;
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    private String nome;
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    private String pais;
    public String getPais() { return pais; }
    public void setPais(String pais) { this.pais = pais; }

    private String uf;
    public String getUf() { return uf; }
    public void setUf(String uf) { this.uf = uf; }

    private String cidade;
    public String getCidade() { return cidade; }
    public void setCidade(String cidade) { this.cidade = cidade; }

    private String enderecoEntrada;
    public String getEnderecoEntrada() { return enderecoEntrada; }
    public void setEnderecoEntrada(String enderecoEntrada) { this.enderecoEntrada = enderecoEntrada; }

    private String latitude;
    public String getLatitude() { return latitude; }
    public void setLatitude(String latitude) { this.latitude = latitude; }

    private String longitude;
    public String getLongitude() { return longitude; }
    public void setLongitude(String longitude) { this.longitude = longitude; }
}
