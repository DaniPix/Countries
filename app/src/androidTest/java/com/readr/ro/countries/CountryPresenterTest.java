package com.readr.ro.countries;

import com.readr.ro.countries.presenter.CountryPresenter;

import junit.framework.TestCase;

/**
 * Created by Domnica on 11/2/2016.
 */

public class CountryPresenterTest extends TestCase {

    public void testLoadCountry() throws Exception {
        CountryPresenter countryPresenter = new CountryPresenter(null);
        countryPresenter.fetchCountryDetails(null);
    }
}
