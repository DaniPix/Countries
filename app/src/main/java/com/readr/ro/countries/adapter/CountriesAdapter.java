package com.readr.ro.countries.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.readr.ro.countries.R;
import com.readr.ro.countries.model.Country;
import com.readr.ro.countries.service.DownloadManager;
import com.readr.ro.countries.service.DownloadProgress;
import com.readr.ro.countries.util.Utils;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Domnica on 11/1/2016.
 */

public class CountriesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<Country> mCountries;
    private int mLayout;
    private LayoutInflater mInflater;
    private Picasso mImageLoader;

    public CountriesAdapter(Context context, List<Country> countries, int layout) {
        this.mInflater = LayoutInflater.from(context);
        this.mContext = context;
        this.mCountries = countries;
        this.mLayout = layout;
        this.mImageLoader = Picasso.with(context);
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
        int resId = Utils.fetchFlagFromAlphaCode(country.getCountryCode(), country.getAlternativeCountryCode(), mContext);
        if (resId != 0) {
            mImageLoader.load(resId).into(holder.flag);
        } else {
            mImageLoader.load(R.drawable.flag_placeholder).into(holder.flag);
        }

        String detailsText = country.getName() + ", " + country.getRegion();
        holder.details.setText(detailsText);

        DownloadProgress progress = DownloadManager.getInstanceWithContext(mContext).progressForMedia(country.getCountryCode());
        if (progress != null) {
            holder.addProgress(progress);
        }

    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {

        int adapterPosition = holder.getAdapterPosition();
        if (adapterPosition == -1) {
            return;
        }
        Country country = mCountries.get(adapterPosition);
        DownloadProgress progress = DownloadManager.getInstanceWithContext(mContext).progressForMedia(country.getCountryCode());
        if (progress != null) {
            CountryHolder h = (CountryHolder) holder;
            h.removeProgress(progress);
        }
    }
}


