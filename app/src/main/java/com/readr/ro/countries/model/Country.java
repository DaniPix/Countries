package com.readr.ro.countries.model;

import com.google.gson.annotations.SerializedName;


/**
 * Created by Domnica on 11/1/2016.
 */

public class Country {

    @SerializedName("alpha2Code")
    private String countryCode;
    @SerializedName("alpha3Code")
    private String alternativeCountryCode;
    @SerializedName("callingCodes")
    private String[] callingCodes;
    @SerializedName("name")
    private String name;
    @SerializedName("capital")
    private String capital;
    @SerializedName("region")
    private String region;
    @SerializedName("subregion")
    private String subRegion;
    @SerializedName("population")
    private String population;
    @SerializedName("latlng")
    private String[] location;
    @SerializedName("area")
    private String area;

    private boolean downloading;
    private boolean downloaded;

    public String getCountryCode() {
        return countryCode;
    }

    public String[] getCallingCodes() {
        return callingCodes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCapital() {
        return capital;
    }

    public String getRegion() {
        return region;
    }

    public String getSubRegion() {
        return subRegion;
    }

    public String[] getLocation() {
        return location;
    }

    public String getPopulation() {
        return population;
    }

    public String getArea() {
        return area;
    }

    public String getAlternativeCountryCode() {
        return alternativeCountryCode;
    }

    public boolean isDownloaded() {
        return downloaded;
    }

    public void setDownloaded(boolean downloaded) {
        this.downloaded = downloaded;
    }

    public boolean isDownloading() {
        return downloading;
    }

    public void setDownloading(boolean downloading) {
        this.downloading = downloading;
    }
}
