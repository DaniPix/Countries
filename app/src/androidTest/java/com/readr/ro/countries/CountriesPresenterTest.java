package com.readr.ro.countries;

import com.readr.ro.countries.presenter.CountriesPresenter;
import com.readr.ro.countries.presenter.CountryPresenter;

import junit.framework.TestCase;

/**
 * Created by Domnica on 11/2/2016.
 */

public class CountriesPresenterTest extends TestCase {

    public void testLoadCountries() throws Exception {
        CountriesPresenter countriesPresenter = new CountriesPresenter(null);
        countriesPresenter.loadCountries();
    }

    public void testLoadCountry() throws Exception {
        CountryPresenter countryPresenter = new CountryPresenter(null);
        countryPresenter.fetchCountryDetails(null);
    }
}
