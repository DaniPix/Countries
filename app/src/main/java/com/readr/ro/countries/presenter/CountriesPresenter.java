package com.readr.ro.countries.presenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.readr.ro.countries.model.Country;
import com.readr.ro.countries.service.CountriesApplication;
import com.readr.ro.countries.service.CountriesService;
import com.readr.ro.countries.view.CountriesView;

import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Domnica on 11/1/2016.
 */

public class CountriesPresenter implements Presenter<CountriesView> {

    private Context mContext;
    private ProgressDialog mProgressDialog;
    private CountriesView mCountriesView;
    private Subscription mSubscription;
    private List<Country> mCountries;


    public CountriesPresenter(Context context) {
        this.mContext = context;
    }


    @Override
    public void attachView(CountriesView view) {
        this.mCountriesView = view;
    }

    @Override
    public void detachView() {
        this.mCountriesView = null;
        if (mSubscription != null) {
            mSubscription.unsubscribe();
        }
    }

    public void loadCountries() {
        if (mSubscription != null) {
            mSubscription.unsubscribe();
        }


        mProgressDialog = new ProgressDialog(mContext, ProgressDialog.STYLE_SPINNER);
        mProgressDialog.show();

        CountriesApplication app = new CountriesApplication();
        final CountriesService service = app.getService();


        Observable<List<Country>> countriesObservable = service.fetchCountries();
        mSubscription = countriesObservable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Observer<List<Country>>() {
                    @Override
                    public void onCompleted() {
                        mCountriesView.displayCountries(mCountries);
                        mProgressDialog.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(getClass().getName(), e.getMessage(), e);
                    }

                    @Override
                    public void onNext(List<Country> countries) {
                        CountriesPresenter.this.mCountries = countries;
                    }
                });
    }
}
