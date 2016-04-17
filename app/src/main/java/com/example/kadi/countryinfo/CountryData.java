package com.example.kadi.countryinfo;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.io.Serializable;

/**
 * Created by Kadi on 05/04/2016.
 */
public class CountryData implements Parcelable{
    private String name;
    private String countryCode;
    private String toponymName;
    private String fclName;
    private String fcodeName;
    private String wikipediaLink;
    private int population;
    private Double lng;
    private Double lat;

    public  CountryData(){

    }

    public CountryData(String name, String countryCode, String toponymName, String fclName, String fcodeName,
                       String wikipediaLink, int population, Double lng, Double lat) {
        this.name = name;
        this.countryCode = countryCode;
        this.toponymName = toponymName;
        this.fclName = fclName;
        this.fcodeName = fcodeName;
        this.wikipediaLink = wikipediaLink;
        this.population = population;
        this.lng = lng;
        this.lat = lat;
    }
    private CountryData(Parcel in){
        name = in.readString();
        countryCode = in.readString();
        toponymName = in.readString();
        fclName = in.readString();
        fclName = in.readString();
        wikipediaLink = in.readString();
        population = in.readInt();
        lng = in.readDouble();
        lat = in.readDouble();


    }
    public static final Parcelable.Creator<CountryData> CREATOR = new Parcelable.Creator<CountryData>() {
        public CountryData createFromParcel(Parcel in) {

            return new CountryData(in);
        }

        public CountryData[] newArray(int size) {

            return new CountryData[size];

        }
    };

    public void setName(String name) {
        this.name = name;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public void setToponymName(String toponymName) {
        this.toponymName = toponymName;
    }

    public void setFclName(String fclName) {
        this.fclName = fclName;
    }

    public void setFcodeName(String fcodeName) {
        this.fcodeName = fcodeName;
    }

    public void setWikipediaLink(String wikipediaLink) {
        this.wikipediaLink = wikipediaLink;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    @Override
    public String toString() {
        return "CountryData{" +
                "name='" + name + '\'' +
                ", countryCode='" + countryCode + '\'' +
                ", toponymName='" + toponymName + '\'' +
                ", fclName='" + fclName + '\'' +
                ", fcodeName='" + fcodeName + '\'' +
                ", wikipediaLink='" + wikipediaLink + '\'' +
                ", population=" + population +
                ", lng=" + lng +
                ", lat=" + lat +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CountryData that = (CountryData) o;

        if (population != that.population) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (countryCode != null ? !countryCode.equals(that.countryCode) : that.countryCode != null)
            return false;
        if (toponymName != null ? !toponymName.equals(that.toponymName) : that.toponymName != null)
            return false;
        if (fclName != null ? !fclName.equals(that.fclName) : that.fclName != null) return false;
        if (fcodeName != null ? !fcodeName.equals(that.fcodeName) : that.fcodeName != null)
            return false;
        if (wikipediaLink != null ? !wikipediaLink.equals(that.wikipediaLink) : that.wikipediaLink != null)
            return false;
        if (lng != null ? !lng.equals(that.lng) : that.lng != null) return false;
        return !(lat != null ? !lat.equals(that.lat) : that.lat != null);

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (countryCode != null ? countryCode.hashCode() : 0);
        result = 31 * result + (toponymName != null ? toponymName.hashCode() : 0);
        result = 31 * result + (fclName != null ? fclName.hashCode() : 0);
        result = 31 * result + (fcodeName != null ? fcodeName.hashCode() : 0);
        result = 31 * result + (wikipediaLink != null ? wikipediaLink.hashCode() : 0);
        result = 31 * result + population;
        result = 31 * result + (lng != null ? lng.hashCode() : 0);
        result = 31 * result + (lat != null ? lat.hashCode() : 0);
        return result;
    }

    public String getName() {
        return name;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getToponymName() {
        return toponymName;
    }

    public String getFclName() {
        return fclName;
    }

    public String getFcodeName() {
        return fcodeName;
    }

    public String getWikipediaLink() {
        return wikipediaLink;
    }

    public int getPopulation() {
        return population;
    }

    public Double getLng() {
        return lng;
    }

    public Double getLat() {
        return lat;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(countryCode);
        dest.writeString(name);
        dest.writeString(toponymName);
        dest.writeString(fclName);
        dest.writeString(fcodeName);
        dest.writeString(wikipediaLink);
        dest.writeInt(population);
        dest.writeDouble(lng);
        dest.writeDouble(lat);

    }
}
