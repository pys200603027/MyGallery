package com.sample.android.lib.model;

import android.content.Context;

import com.sample.android.lib.model.loader.Loader;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LoaderExecutor {

    ExecutorService executorService;
    Loader albumLoader;

    public LoaderExecutor(Loader albumLoader) {
        this.albumLoader = albumLoader;
        executorService = Executors.newSingleThreadExecutor();
    }

    public void load(Context context, OnLoaderListener onLoaderListener) {
        executorService.execute(() -> {
            List<MediaMeta> mediaMetas = albumLoader.load(context);
            if (onLoaderListener != null) {
                onLoaderListener.onLoadAlbumResponse(mediaMetas);
            }
        });
    }

    public void shutDown() {
        if (executorService != null) {
            executorService.shutdownNow();
        }
    }


    public interface OnLoaderListener {
        void onLoadAlbumResponse(List<MediaMeta> mediaMetas);
    }

}
