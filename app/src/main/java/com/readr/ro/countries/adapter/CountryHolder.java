package com.readr.ro.countries.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.readr.ro.countries.R;
import com.readr.ro.countries.service.DownloadProgress;
import com.readr.ro.countries.ui.CircularProgress;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CountryHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.countryFlag)
    ImageView flag;
    @BindView(R.id.countryDetails)
    TextView details;
    @BindView(R.id.download)
    public ImageView download;
    @BindView(R.id.circularProgress)
    public CircularProgress progress;

    public CountryHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }


    public void addProgress(DownloadProgress dp) {
        progress.setVisibility(View.VISIBLE);
        download.setVisibility(View.INVISIBLE);
        progress.setProgress(dp.getProgress());
        dp.addListener(progress);
    }

    public void removeProgress(DownloadProgress dp) {
        progress.setProgress(0);
        progress.setVisibility(View.INVISIBLE);
        download.setVisibility(View.VISIBLE);
        dp.removeListener(progress);
    }


}
