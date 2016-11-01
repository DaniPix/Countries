package com.readr.ro.countries.view;

import com.readr.ro.countries.model.Country;

import java.util.List;

/**
 * Created by Domnica on 11/1/2016.
 */

public interface CountriesView extends View{
    void displayCountries(List<Country> countries);
}
