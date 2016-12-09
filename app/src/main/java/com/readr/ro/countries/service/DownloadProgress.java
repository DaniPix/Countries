package com.readr.ro.countries.service;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Domnica on 12/8/2016.
 */

public class DownloadProgress {

    private int progress;
    private int currentFileSize;
    private long totalFileSize;
    private boolean cancel;

    private List<DownloadProgressListener> downloadProgressListenerList = new ArrayList<>();

    public DownloadProgress() {

    }

    void postProgressNotification() {
        for (DownloadProgressListener l : downloadProgressListenerList) {
            l.onProgressChanged(this.progress);
        }
    }
    void postCompleteNotification() {
        for (DownloadProgressListener l : downloadProgressListenerList) {
            l.onComplete(true);
        }
    }

    public void addListener(DownloadProgressListener l) {
        downloadProgressListenerList.add(l);
    }

    public void removeListener(DownloadProgressListener l) {
        downloadProgressListenerList.remove(l);
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public int getCurrentFileSize() {
        return currentFileSize;
    }

    public void setCurrentFileSize(int currentFileSize) {
        this.currentFileSize = currentFileSize;
    }

    public long getTotalFileSize() {
        return totalFileSize;
    }

    public void setTotalFileSize(long totalFileSize) {
        this.totalFileSize = totalFileSize;
    }

    public boolean isCancel() {
        return cancel;
    }

    public void setCancel(boolean cancel) {
        this.cancel = cancel;
    }
}
