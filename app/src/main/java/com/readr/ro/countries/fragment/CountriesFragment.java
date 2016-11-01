package com.readr.ro.countries.fragment;

import android.app.Fragment;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.readr.ro.countries.R;
import com.readr.ro.countries.adapter.CountriesAdapter;
import com.readr.ro.countries.model.Country;
import com.readr.ro.countries.presenter.CountriesPresenter;
import com.readr.ro.countries.view.CountriesView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A placeholder fragment containing a simple view.
 */
public class CountriesFragment extends Fragment implements CountriesView {


    @BindView(R.id.countriesList)
    RecyclerView mCountriesList;

    private CountriesPresenter mCountriesPresenter;
    private List<Country> mCountries;

    public CountriesFragment() {
        // default constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCountriesPresenter = new CountriesPresenter(getActivity());
        mCountriesPresenter.attachView(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCountriesPresenter.detachView();
    }

    @Override
    public void displayCountries(List<Country> countries) {
        if (countries != null && !countries.isEmpty()) {
            mCountries = countries;
            mCountriesList.setAdapter(new CountriesAdapter(getActivity(), countries, R.layout.fragment_countries_item));
            mCountriesList.setLayoutManager(new LinearLayoutManager(getActivity()));
        } else {
            Toast.makeText(getActivity(), "Something went wrong.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_countries, container, false);
        ButterKnife.bind(this, view);
        mCountriesPresenter.loadCountries();
        if (mCountries != null) {
            mCountriesList.setAdapter(new CountriesAdapter(getActivity(), mCountries, R.layout.fragment_countries_item));
            mCountriesList.setLayoutManager(new LinearLayoutManager(getActivity()));
        }
        return view;
    }
}
