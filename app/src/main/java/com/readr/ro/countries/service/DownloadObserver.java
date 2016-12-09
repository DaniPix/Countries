package com.readr.ro.countries.service;

/**
 * Created by Domnica on 12/8/2016.
 */

public interface DownloadObserver {

    interface DownloadProgressListener {
        void notifyProgressUpdate(int progress);
    }

    interface DownloadStatusListener {
        void notifyDownloadCancelled();

        void notifyDownloadFinished();

        void notifyDownloadFailed();
    }
}
