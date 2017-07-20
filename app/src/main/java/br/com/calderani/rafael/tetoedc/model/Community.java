package br.com.calderani.rafael.tetoedc.model;

import java.util.List;

/**
 * Created by Rafael on 09/07/2017.
 */

public class Community {
    private List<User> equipeFixaParticipants;
    public List<User> getEquipeFixaParticipants() {
        return equipeFixaParticipants;
    }
    public void setEquipeFixaParticipants(List<User> equipeFixaParticipants) {
        this.equipeFixaParticipants = equipeFixaParticipants;
    }

    private String id;
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    private String name;
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    private String region;
    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }

    private String uf;
    public String getUf() { return uf; }
    public void setUf(String uf) { this.uf = uf; }

    private String city;
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    private String entranceAddress;
    public String getEntranceAddress() { return entranceAddress; }
    public void setEntranceAddress(String entranceAddress) { this.entranceAddress = entranceAddress; }

    private String latitude;
    public String getLatitude() { return latitude; }
    public void setLatitude(String latitude) { this.latitude = latitude; }

    private String longitude;
    public String getLongitude() { return longitude; }
    public void setLongitude(String longitude) { this.longitude = longitude; }
}
