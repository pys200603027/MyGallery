package com.sample.android.lib.ui.preview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.viewpager.widget.ViewPager;

import com.sample.android.lib.R;
import com.sample.android.lib.model.LoaderExecutor;
import com.sample.android.lib.model.MediaMeta;
import com.sample.android.lib.model.loader.PictureLoader;
import com.sample.android.lib.ui.selection.SelectionCollection;

import java.util.List;

public class PreviewActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    ViewPager viewPager;
    PreviewPageAdapter previewPageAdapter;

    CheckBox checkBox;

    MediaMeta mediaMeta;
    SelectionCollection selectionCollection = new SelectionCollection();

    LoaderExecutor loaderExecutor;

    public static void startActivity(Context context, MediaMeta mediaMeta, SelectionCollection selectionCollection) {
        Intent intent = new Intent(context, PreviewActivity.class);
        intent.putExtra("mediaMeta", mediaMeta);
        intent.putExtra(SelectionCollection.KEY_SELECTION, selectionCollection.getExtraBundle());
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_preview_layout);

        if (getIntent() == null) {
            return;
        }
        if (savedInstanceState == null) {
            selectionCollection.onCreate(getIntent().getBundleExtra(SelectionCollection.KEY_SELECTION));
        } else {
            selectionCollection.onCreate(savedInstanceState);
        }

        mediaMeta = getIntent().getParcelableExtra("mediaMeta");


        checkBox = findViewById(R.id.ph_preview_check);
        if (selectionCollection.isSelected(mediaMeta)) {
            checkBox.setChecked(true);
        }
        checkBox.setOnClickListener(v -> {
            boolean checked = checkBox.isChecked();
            MediaMeta currentMediaMeta = previewPageAdapter.getMediaMeta(viewPager.getCurrentItem());
            if (checked) {
                selectionCollection.add(currentMediaMeta);
            } else {
                selectionCollection.remove(currentMediaMeta);
            }

            //Notify
            Intent intent = new Intent(SelectionCollection.ACTION_SELECTION_BROADCAST);
            intent.putExtra(SelectionCollection.KEY_SELECTION, selectionCollection.getExtraBundle());
            intent.putExtra(SelectionCollection.KEY_SELECTION_CHECKED, currentMediaMeta);
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        });
        viewPager = findViewById(R.id.ph_view_pager);
        initViewPager();
        initData();
    }

    private void initViewPager() {
        previewPageAdapter = new PreviewPageAdapter(getSupportFragmentManager());
        viewPager.setAdapter(previewPageAdapter);
        viewPager.addOnPageChangeListener(this);
    }

    private void initData() {

        loaderExecutor = new LoaderExecutor(new PictureLoader());
        loaderExecutor.load(this, mediaMetas -> {
            if (isDestroyed() || getIntent() == null || mediaMeta == null) {
                return;
            }
            runOnUiThread(() -> {
                onLoadAlbumResponse(mediaMetas);
            });
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        if (selectionCollection != null) {
            selectionCollection.onSaveInstanceState(outState);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (loaderExecutor != null) {
            loaderExecutor.shutDown();
        }
    }

    private void onLoadAlbumResponse(List<MediaMeta> mediaMetas) {
        if (previewPageAdapter != null) {
            previewPageAdapter.addNewData(mediaMetas);

            MediaMeta mediaMeta = getIntent().getParcelableExtra("mediaMeta");
            viewPager.setCurrentItem(mediaMetas.indexOf(mediaMeta), false);
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (position < 0 || position >= previewPageAdapter.getCount()) {
            return;
        }

        MediaMeta currentMediaMeta = previewPageAdapter.getMediaMeta(position);
        if (selectionCollection.isSelected(currentMediaMeta)) {
            checkBox.setChecked(true);
        } else {
            checkBox.setChecked(false);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
