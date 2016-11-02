package com.readr.ro.countries.constants;

/**
 * Created by Domnica on 11/1/2016.
 */

public class RestConstants {
    // Rest Constants for Retrofit
    public static final String BASE_ENDPOINT = "https://restcountries.eu/rest/v1/";

    public static final String FETCH_ALL_COUNTRIES = "all";
    public static final String FETCH_COUNTRY = "callingcode/{callingCodeId}";

    private RestConstants(){
        // override default constructor
    }
}
