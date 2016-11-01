package com.readr.ro.countries.view;

import com.readr.ro.countries.model.Country;

/**
 * Created by Domnica on 11/1/2016.
 */

public interface CountryView extends View {
    void displayCountryDetails(Country country);
}
