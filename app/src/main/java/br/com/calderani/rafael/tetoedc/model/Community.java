package br.com.calderani.rafael.tetoedc.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Rafael on 09/07/2017.
 */

public class Community implements Parcelable {
    private String id;
    public String getId() { return id; }

    private String name;
    public String getName() { return name; }

    private String zone;
    public String getZone() { return zone; }

    private String uf;
    public String getUf() { return uf; }

    private String city;
    public String getCity() { return city; }

    private String entranceAddress;
    public String getEntranceAddress() { return entranceAddress; }

    private String latitude;
    public String getLatitude() { return latitude; }

    private String longitude;
    public String getLongitude() { return longitude; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(zone);
        dest.writeString(latitude);
        dest.writeString(longitude);
    }

    protected Community(Parcel in) {
        name = in.readString();
        zone = in.readString();
        latitude = in.readString();
        longitude = in.readString();
    }

    public static final Creator<Community> CREATOR = new Creator<Community>() {
        @Override
        public Community createFromParcel(Parcel in) {
            return new Community(in);
        }

        @Override
        public Community[] newArray(int size) {
            return new Community[size];
        }
    };
}
