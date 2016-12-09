package com.readr.ro.countries.service;

/**
 * Created by Domnica on 12/9/2016.
 */

public interface DownloadProgressListener {


    void onProgressChanged(int newProgress);

    void onComplete(boolean success);

}
