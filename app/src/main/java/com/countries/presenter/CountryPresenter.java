package com.readr.ro.countries.presenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;


import com.readr.ro.countries.model.Country;
import com.readr.ro.countries.service.CountriesApplication;
import com.readr.ro.countries.service.CountriesService;
import com.readr.ro.countries.view.CountryView;

import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Domnica on 11/1/2016.
 */

public class CountryPresenter implements Presenter<CountryView> {

    private CountryView mCountryView;
    private Context mContext;
    private Subscription mSubscription;
    private Country mCountry;
    private ProgressDialog mProgressDialog;

    public CountryPresenter(Context context) {
        mContext = context;
    }

    @Override
    public void attachView(CountryView view) {
        this.mCountryView = view;
    }

    @Override
    public void detachView() {
        this.mCountryView = null;
        if (mSubscription != null) {
            mSubscription.unsubscribe();
        }
    }

    public void fetchCountryDetails(String countryCodeId) {
        if (mSubscription != null) {
            mSubscription.unsubscribe();
        }
        CountriesApplication app = new CountriesApplication();
        final CountriesService service = app.getService();

        mProgressDialog = new ProgressDialog(mContext, ProgressDialog.STYLE_SPINNER);
        mProgressDialog.show();

        Observable<List<Country>> countryObservable = service.fetchCountry(countryCodeId);
        mSubscription = countryObservable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Observer<List<Country>>() {
                    @Override
                    public void onCompleted() {
                        mCountryView.displayCountryDetails(mCountry);
                        mProgressDialog.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(getClass().getName(), e.getMessage(), e);
                    }

                    @Override
                    public void onNext(List<Country> country) {
                        mCountry = country.get(0);
                    }
                });
    }
}
