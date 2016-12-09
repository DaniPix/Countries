package com.readr.ro.countries.service;

/**
 * Created by Domnica on 12/9/2016.
 */

public interface DownloadManagerListener {
    enum CompletionStatus {
        SUCCESS, FAILED, CANCELED
    }


    void onMediaComplete(Object mid, CompletionStatus status);
}


