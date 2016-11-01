package com.readr.ro.countries.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.readr.ro.countries.R;
import com.readr.ro.countries.model.Country;
import com.readr.ro.countries.util.ImageUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Domnica on 11/1/2016.
 */

public class CountriesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<Country> mCountries;
    private int mLayout;
    private LayoutInflater mInflater;

    public CountriesAdapter(Context context, List<Country> countries, int layout) {
        this.mInflater = LayoutInflater.from(context);
        this.mContext = context;
        this.mCountries = countries;
        this.mLayout = layout;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(mLayout, parent, false);


        return new CountryHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof CountryHolder) {
            CountryHolder countryHolder = (CountryHolder) holder;
            Country country = mCountries.get(position);
            setUpItemRow(countryHolder, country);
        }
    }

    @Override
    public int getItemCount() {
        return mCountries.size();
    }


    private void setUpItemRow(CountryHolder holder, Country country) {

        int resId = ImageUtils.fetchFlagFromAlphaCode(country.getCountryCode(), country.getAlternativeCountryCode(), mContext);
        if (resId != 0) {
            Picasso.with(mContext)
                    .load(resId)
                    .into(holder.flag);
        }
        String detailsText = country.getName() + ", " + country.getRegion();
        holder.details.setText(detailsText);
    }


    class CountryHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.countryFlag)
        ImageView flag;
        @BindView(R.id.countryDetails)
        TextView details;

        private CountryHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
