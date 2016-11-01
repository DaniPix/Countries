package com.readr.ro.countries.presenter;

/**
 * Created by Domnica on 11/1/2016.
 */

public interface Presenter<V> {
    void attachView(V view);

    void detachView();
}
