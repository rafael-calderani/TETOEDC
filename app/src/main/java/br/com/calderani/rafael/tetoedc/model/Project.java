package br.com.calderani.rafael.tetoedc.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Rafael on 10/07/2017.
 * Classe que representa os projetos levantados pelos Moradores + Equipe
 */

public class Project implements Parcelable {
    private String name;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    private String description;
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    private String managersFromTeam;
    public String getManagersFromTeam() { return managersFromTeam; }
    public void setManagersFromTeam(String managersFromTeam) { this.managersFromTeam = managersFromTeam; }

    private String managersFromCommunity;
    public String getManagersFromCommunity() { return managersFromCommunity; }
    public void setManagersFromCommunity(String managersFromCommunity) { this.managersFromCommunity = managersFromCommunity; }

    private String status;
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    private String createdOn;
    public String getCreatedOn() { return createdOn; }
    public void setCreatedOn(String createdOn) { this.createdOn = createdOn; }

    private String modifiedOn;
    public String getModifiedOn() { return modifiedOn; }
    public void setModifiedOn(String modifiedOn) { this.modifiedOn = modifiedOn; }

    private String completedOn;
    public String getCompletedOn() { return completedOn; }
    public void setCompletedOn(String completedOn) { this.completedOn = completedOn; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(managersFromTeam);
        dest.writeString(managersFromCommunity);
        dest.writeString(status);
        dest.writeString(createdOn);
        dest.writeString(modifiedOn);
        dest.writeString(completedOn);
    }

    public Project(Parcel in) {
        name = in.readString();
        description = in.readString();
        managersFromTeam = in.readString();
        managersFromCommunity = in.readString();
        status = in.readString();
        createdOn = in.readString();
        modifiedOn = in.readString();
        completedOn = in.readString();
    }

    public Project() { }

    public static final Creator<Project> CREATOR = new Creator<Project>() {
        @Override
        public Project createFromParcel(Parcel in) {
            return new Project(in);
        }

        @Override
        public Project[] newArray(int size) {
            return new Project[size];
        }
    };
}
