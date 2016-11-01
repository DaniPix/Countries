package com.readr.ro.countries.service;

/**
 * Created by Domnica on 11/1/2016.
 */

public class CountriesApplication {

    private CountriesService mCountriesService;

    public CountriesService getService() {
        if (mCountriesService == null) {
            mCountriesService = CountriesService.Factory.create();
        }
        return mCountriesService;
    }
}
