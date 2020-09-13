package com.sample.android.lib.preview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.sample.android.lib.R;
import com.sample.android.lib.model.MediaMeta;
import com.sample.android.lib.model.loader.Loader;
import com.sample.android.lib.model.loader.PictureLoader;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PreviewActivity extends AppCompatActivity {

    ViewPager viewPager;
    PreviewPageAdapter previewPageAdapter;

    ExecutorService executorService;
    Loader albumLoader;

    public static void startActivity(Context context, MediaMeta mediaMeta) {
        Intent intent = new Intent(context, PreviewActivity.class);
        intent.putExtra("mediaMeta", mediaMeta);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_preview_layout);

        viewPager = findViewById(R.id.ph_view_pager);
        initViewPager();
        initData();
    }

    private void initViewPager() {
        previewPageAdapter = new PreviewPageAdapter(getSupportFragmentManager());
        viewPager.setAdapter(previewPageAdapter);
    }

    private void initData() {
        executorService = Executors.newSingleThreadExecutor();
        albumLoader = new PictureLoader();
        executorService.submit(() -> {
            List<MediaMeta> mediaMetas = albumLoader.load(this);
            if (isDestroyed() || getIntent() == null || getIntent().getParcelableExtra("mediaMeta") == null) {
                return;
            }
            runOnUiThread(() -> {
                onLoadAlbumResponse(mediaMetas);
            });
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (executorService != null) {
            executorService.shutdown();
        }
    }

    private void onLoadAlbumResponse(List<MediaMeta> mediaMetas) {
        if (previewPageAdapter != null) {
            previewPageAdapter.addNewData(mediaMetas);

            MediaMeta mediaMeta = getIntent().getParcelableExtra("mediaMeta");

            viewPager.setCurrentItem(mediaMetas.indexOf(mediaMeta), false);
        }
    }
}
