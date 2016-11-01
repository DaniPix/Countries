package com.readr.ro.countries.service;

import com.readr.ro.countries.constants.RestConstants;
import com.readr.ro.countries.model.Country;

import java.util.List;
import java.util.Map;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by Domnica on 11/1/2016.
 */

public interface CountriesService {

    @GET(RestConstants.FETCH_ALL_COUNTRIES)
    Observable<List<Country>> fetchCountries();

    @GET(RestConstants.FETCH_COUNTRY)
    Observable<List<Country>> fetchCountry(@Path("callingCodeId") String code);


    class Factory {

        private Factory() {
            // private constructor
        }

        public static CountriesService create() {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(RestConstants.BASE_ENDPOINT)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();

            return retrofit.create(CountriesService.class);
        }
    }
}