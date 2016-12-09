package com.readr.ro.countries.service;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.readr.ro.countries.service.DownloadManagerListener.CompletionStatus;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;

/**
 * Created by Domnica on 12/9/2016.
 */

public final class DownloadManager {


    private static DownloadManager instance;

    private Context context;
    private final Lock readLock;
    private final Lock writeLock;

    private Map<Object, DownloadProgress> map = new HashMap<>();
    private final LinkedBlockingDeque<Runnable> workQueue;
    private final ExecutorService executorService;

    private List<DownloadManagerListener> listeners = new ArrayList<>();

    void postProgressNotification(final Object mid, final CompletionStatus status) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                for (DownloadManagerListener l : listeners) {
                    l.onMediaComplete(mid, status);
                }
            }
        });
    }

    public void addListener(DownloadManagerListener l) {
        listeners.add(l);
    }

    public void removeListener(DownloadManagerListener l) {
        listeners.remove(l);
    }


    public final static DownloadManager getInstanceWithContext(Context ctx) {
        if (instance == null) {
            instance = new DownloadManager(ctx);
        }
        return instance;
    }

    private DownloadManager(Context context) {
        this.context = context;
        ReadWriteLock lock = new ReentrantReadWriteLock();
        readLock = lock.readLock();
        writeLock = lock.writeLock();
        workQueue = new LinkedBlockingDeque<>(100);
        executorService = new ThreadPoolExecutor(3, 6, 10, TimeUnit.MINUTES, workQueue);
    }


    public DownloadProgress startDownload(final Object mid, final String source, final String destination) {

        DownloadProgress _download;
        try {
            writeLock.lock();
            _download = map.get(mid);
            if (_download == null) {
                _download = new DownloadProgress();
                map.put(mid, _download);
                final DownloadProgress progress = _download;
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        File file = new File(destination);

                        try {
                            OkHttpClient client = new OkHttpClient();
                            Request request = new Request.Builder().url(source).build();
                            Call callRequest = client.newCall(request);
                            Response response = callRequest.execute();


                            if (response.isSuccessful()) {
                                ResponseBody responseBody = response.body();
                                long contentLength = responseBody.contentLength();
                                BufferedSource bufferedSource = responseBody.source();
                                BufferedSink sink = Okio.buffer(Okio.sink(file));
                                double total = 0;
                                long read;
                                long startTime = System.currentTimeMillis();
                                int timeCount = 1;
//                                DownloadProgress progress = new DownloadProgress();
                                while ((read = bufferedSource.read(sink.buffer(), 2048)) != -1) {
                                    if (progress.isCancel()) {
                                        postProgressNotification(mid, CompletionStatus.CANCELED);
                                        try {
                                            sink.close();
                                            bufferedSource.close();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                        return;

                                    }
                                    total += read;
                                    progress.setProgress((int) ((total / contentLength) * 100));
                                    sink.flush();

                                    long currentTime = System.currentTimeMillis() - startTime;
                                    if (currentTime > 500 * timeCount) {
//                                        progress.setProgress(progress.intValue());
                                        sendProgressNotification(progress);
                                        timeCount++;
                                    }
                                }
                                sink.flush();
                                sink.close();
                                bufferedSource.close();
                                postProgressNotification(mid, CompletionStatus.SUCCESS);
                            }
                        } catch (IOException e) {
                            if (file.exists() && file.delete()) {
                                Log.d(getClass().getName(), "File deleted successfully.");
                            }
                            Log.e(getClass().getName(), e.getMessage(), e);
                            postProgressNotification(mid, CompletionStatus.FAILED);

                        } finally {
                            writeLock.lock();
                            try {
                                map.remove(mid);
                            } finally {
                                writeLock.unlock();
                            }
                        }
                    }
                });
            }
        } finally {
            writeLock.unlock();
        }
        return _download;
    }

    private void sendProgressNotification(final DownloadProgress download) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                download.postProgressNotification();
            }
        });
    }

    private void sendCompleteNotification(final DownloadProgress download, boolean status) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                download.postCompleteNotification();
            }
        });
    }

    public DownloadProgress progressForMedia(Object mid) {
        try {
            readLock.lock();
            return map.get(mid);
        } finally {
            readLock.unlock();
        }
    }


}
