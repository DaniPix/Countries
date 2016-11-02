package com.readr.ro.countries.fragment;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.readr.ro.countries.R;
import com.readr.ro.countries.constants.Constants;
import com.readr.ro.countries.model.Country;
import com.readr.ro.countries.presenter.CountryPresenter;
import com.readr.ro.countries.util.Utils;
import com.readr.ro.countries.view.CountryView;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Intent.ACTION_VIEW;

/**
 * Created by Domnica on 11/1/2016.
 * Fragment displaying a simple list of details for the selected country
 */

public class CountryFragment extends Fragment implements CountryView {

    private CountryPresenter mCountryPresenter;
    private String countryCodeId;
    private String countryName;
    private ProgressDialog mProgressDialog;

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
        countryName = getString(R.string.title_activity_countries);
        if (bundle != null) {
            countryCodeId = bundle.getString(Constants.COUNTRY_CODE_ID);
            // we are sending the country name for a smother transition (if it's available)
            countryName = bundle.getString(Constants.COUNTRY_NAME);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCountryPresenter.detachView();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_country_details, container, false);
        ButterKnife.bind(this, view);
        mCountryPresenter.fetchCountryDetails(countryCodeId);
        setHasOptionsMenu(true);
        ActionBar mActionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (mActionBar != null) {
            mActionBar.setDisplayHomeAsUpEnabled(true);
            mActionBar.setTitle(countryName);
        }
        return view;
    }

    @Override
    public void displayCountryDetails(final Country country) {
        title.setText(country.getName());

        int resId = Utils.fetchFlagFromAlphaCode(country.getCountryCode(), country.getAlternativeCountryCode(), getActivity());
        if (resId != 0) {
            Picasso.with(getActivity()).load(resId).into(flag);
        } else {
            Picasso.with(getActivity()).load(R.drawable.flag_placeholder).into(flag);
        }
        subTitle.setText(country.getRegion() + ", " + country.getSubRegion());
        capital.setText(country.getCapital());
        population.setText(country.getPopulation());
        area.setText(country.getArea());


        location.setText(Html.fromHtml("<u>" + getActivity().getString(R.string.latitude) + country.getLocation()[0] + ", " + getActivity().getString(R.string.longitude) + country.getLocation()[1] + "</u>"));

        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ACTION_VIEW, Uri.parse("http://maps.google.com/maps?q=" + country.getLocation()[0] + "," + country.getLocation()[1]));
                startActivity(intent);
            }
        });
    }

    @Override
    public void showProgressDialog() {
        mProgressDialog = new ProgressDialog(getActivity(), R.style.ProgressDialogTheme);
        mProgressDialog.show();
    }

    @Override
    public void dismissProgressDialog() {
        mProgressDialog.dismiss();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            super.getActivity().onBackPressed();

        }

        return super.onOptionsItemSelected(item);
    }

    //Bug known from the support library (workaround if you turn the screen while having animations)
    @Override
    public Animator onCreateAnimator(int transit, boolean enter, int nextAnim) {
        if (enter) {
            return AnimatorInflater.loadAnimator(getActivity(), R.animator.slide_in_right);
        } else {
            return AnimatorInflater.loadAnimator(getActivity(), R.animator.slide_out_left);
        }
    }
}
