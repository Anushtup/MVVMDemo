package com.example.mvvmdemo.model;

import com.google.gson.annotations.SerializedName;

public class CountryModel {
    @SerializedName("flagPNG")
    private String flag;
    /*
    Here if the name from api and string defined in this class matches then we do not need to specify the serialized annotation
     */
    private String capital;
    @SerializedName("name")
    private String countryName;

    public CountryModel(String countryName,String capital,String flag) {
        this.flag = flag;
        this.capital = capital;
        this.countryName = countryName;
    }

    public String getFlag() {
        return flag;
    }

    public String getCapital() {
        return capital;
    }


    public String getCountryName() {
        return countryName;
    }

}
