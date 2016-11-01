package com.readr.ro.countries.service;

import rx.Scheduler;
import rx.schedulers.Schedulers;

/**
 * Created by Domnica on 11/1/2016.
 */

public class CountriesApplication {

    private CountriesService mCountriesService;
    private Scheduler mSubscribeScheduler;

    public CountriesService getService() {
        if (mCountriesService == null) {
            mCountriesService = CountriesService.Factory.create();
        }
        return mCountriesService;
    }

    public Scheduler getSubscribeScheduler() {
        if (mSubscribeScheduler == null) {
            mSubscribeScheduler = Schedulers.io();
        }
        return mSubscribeScheduler;
    }
}
