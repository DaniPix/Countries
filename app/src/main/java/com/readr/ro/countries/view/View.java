package com.readr.ro.countries.view;

import android.content.Context;

/**
 * Created by Domnica on 11/1/2016.
 */

public interface View {
    Context getContext();

    void showProgressDialog();

    void dismissProgressDialog();
}
