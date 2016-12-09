package com.readr.ro.countries.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.readr.ro.countries.R;
import com.readr.ro.countries.adapter.CountriesAdapter;
import com.readr.ro.countries.adapter.CountryHolder;
import com.readr.ro.countries.constants.Constants;
import com.readr.ro.countries.model.Country;
import com.readr.ro.countries.presenter.CountriesPresenter;
import com.readr.ro.countries.service.DownloadManager;
import com.readr.ro.countries.service.DownloadManagerListener;
import com.readr.ro.countries.service.DownloadProgress;
import com.readr.ro.countries.service.DownloadObserver;

import com.readr.ro.countries.util.Utils;
import com.readr.ro.countries.view.CountriesView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.readr.ro.countries.util.Utils.isCallingCodeCorrect;

/**
 * Fragment containing a simple view for displaying all the countries fetched from the rest call
 */
public class CountriesFragment extends Fragment implements CountriesView, DownloadManagerListener {

    public static final String MESSAGE_PROGRESS = "message_progress";

    @BindView(R.id.countriesList)
    RecyclerView mCountriesList;

    private CountriesPresenter mCountriesPresenter;
    private List<Country> mCountries;
    private List<Country> mFilteredCountries;
    private CountriesAdapter mAdapter;
    ProgressDialog mProgressDialog;
    DownloadObserver.DownloadProgressListener listener;
    private DownloadManager downloadManager;


    public CountriesFragment() {
        // default constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCountriesPresenter = new CountriesPresenter(getActivity());
        mCountriesPresenter.attachView(this);
        setRetainInstance(true);
        downloadManager = DownloadManager.getInstanceWithContext(getActivity());
        downloadManager.addListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        // We are using the same toolbar, another option is to use different toolbar for each fragment
        ActionBar mActionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (mActionBar != null) {
            mActionBar.setTitle(R.string.title_activity_countries);
            mActionBar.setDisplayHomeAsUpEnabled(false);
        }
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
    public void onDestroy() {
        super.onDestroy();
        mCountriesPresenter.detachView();
    }

    @Override
    public void displayCountries(List<Country> countries) {
        if (countries != null && !countries.isEmpty()) {
            mCountries = countries;
            mFilteredCountries = countries;
            mAdapter = new CountriesAdapter(getActivity(), countries, R.layout.fragment_countries_item);
            mCountriesList.setAdapter(mAdapter);
            mCountriesList.setLayoutManager(new LinearLayoutManager(getActivity()));
        } else {
            Toast.makeText(getActivity(), "Something went wrong.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_countries, container, false);
        ButterKnife.bind(this, view);

        if (mFilteredCountries != null) {
            mAdapter = new CountriesAdapter(getActivity(), mFilteredCountries, R.layout.fragment_countries_item);
            mCountriesList.setAdapter(mAdapter);
            mCountriesList.setLayoutManager(new LinearLayoutManager(getActivity()));
        } else {
            mCountriesPresenter.loadCountries();
        }
        setHasOptionsMenu(true);

        mCountriesList.addOnItemTouchListener(new CountriesClickListener(getActivity(), mCountriesList, new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                if (!Utils.isOnline(getActivity())) {
                    Toast.makeText(getActivity(), R.string.internet_connection_message, Toast.LENGTH_SHORT).show();
                    return;
                }

                Country country = mFilteredCountries.get(position);

                if (country.isDownloaded() || true) {
                    String source = "http://download.blender.org/peach/bigbuckbunny_movies/BigBuckBunny_320x180.mp4";
                    String destination = getActivity().getExternalFilesDir(null).getPath() + "/file.mp4";

                    DownloadProgress progress = downloadManager.progressForMedia(country.getCountryCode());
                    if (progress != null) {
                        progress.setCancel(true);
                    } else {
                        DownloadProgress progress1 = downloadManager.startDownload(country.getCountryCode(), source, destination);
//
                        CountryHolder holder = (CountryHolder) mCountriesList.findViewHolderForAdapterPosition(position);
                        holder.download.setVisibility(View.GONE);
                        holder.progress.setVisibility(View.VISIBLE);

                        progress1.addListener(holder.progress);
                    }

                } else if (country.isDownloading()) {
                    // stop download
                } else {
                    FragmentManager fm = getFragmentManager();
                    Fragment fragment = new CountryFragment();
                    Bundle bundle = new Bundle();
                    if (!isCallingCodeCorrect(country.getCallingCodes())) {
                        Toast.makeText(getActivity(), R.string.missing_calling_codes, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    bundle.putString(Constants.COUNTRY_CODE_ID, country.getCallingCodes()[0]);
                    bundle.putString(Constants.COUNTRY_NAME, country.getName());
                    fragment.setArguments(bundle);
                    fm.beginTransaction()
                            .setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right, R.animator.slide_in_right, R.animator.slide_out_left)
                            .replace(R.id.container, fragment, CountryFragment.class.getName())
                            .addToBackStack(null)
                            .commit();
                }
            }
        }));

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_countries, menu);
        MenuItem item = menu.findItem(R.id.menu_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                String query = newText.toLowerCase();
                mFilteredCountries = new ArrayList<>();
                if (mCountries == null || mCountries.isEmpty()) {
                    return false;
                }
                for (Country country : mCountries) {
                    if (country.getName().toLowerCase().contains(query)) {
                        mFilteredCountries.add(country);
                    }
                }
                mAdapter = new CountriesAdapter(getActivity(), mFilteredCountries, R.layout.fragment_countries_item);
                mCountriesList.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
                return true;
            }
        });
    }


    @Override
    public void onMediaComplete(Object mid, CompletionStatus status) {
        if (status == CompletionStatus.CANCELED) {
            ///
        }
    }
}
