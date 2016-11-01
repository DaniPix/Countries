package com.readr.ro.countries.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.BinderThread;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.readr.ro.countries.R;
import com.readr.ro.countries.constants.Constants;
import com.readr.ro.countries.model.Country;
import com.readr.ro.countries.presenter.CountryPresenter;
import com.readr.ro.countries.util.ImageUtils;
import com.readr.ro.countries.view.CountryView;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Domnica on 11/1/2016.
 */

public class CountryFragment extends Fragment implements CountryView {

    private CountryPresenter mCountryPresenter;
    private Country mCountry;
    private String countryCodeId;

    @BindView(R.id.countryDetailsFlag)
    ImageView flag;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.subTitle)
    TextView subTitle;
    @BindView(R.id.capital)
    TextView capital;
    @BindView(R.id.population)
    TextView population;
    @BindView(R.id.area)
    TextView area;
    @BindView(R.id.location)
    TextView location;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCountryPresenter = new CountryPresenter(getActivity());
        mCountryPresenter.attachView(this);
        Bundle bundle = getArguments();
        if (bundle != null) {
            countryCodeId = bundle.getString(Constants.COUNTRY_CODE_ID);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_country_details, container, false);
        ButterKnife.bind(this, view);
        mCountryPresenter.fetchCountryDetails(countryCodeId);
        return view;
    }

    @Override
    public void displayCountryDetails(Country country) {

        int resId = ImageUtils.fetchFlagFromAlphaCode(country.getCountryCode(), country.getAlternativeCountryCode(), getActivity());
        if (resId != 0) {
            Picasso.with(getActivity()).load(resId).into(flag);
        }

        title.setText(country.getName());

        subTitle.setText(country.getRegion() + ", " + country.getSubRegion());

        capital.setText(country.getCapital());

        population.setText(country.getPopulation());

        area.setText(country.getArea());

        location.setText(getActivity().getString(R.string.latitude) + country.getLocation()[0] + ", " + getActivity().getString(R.string.longitude) + country.getLocation()[1]);
    }
}
