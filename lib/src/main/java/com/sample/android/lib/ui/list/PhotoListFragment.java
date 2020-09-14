package com.sample.android.lib.ui.list;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sample.android.lib.R;
import com.sample.android.lib.model.LoaderExecutor;
import com.sample.android.lib.model.MediaMeta;
import com.sample.android.lib.model.loader.PictureLoader;
import com.sample.android.lib.ui.preview.PreviewActivity;
import com.sample.android.lib.ui.selection.SelectionCollection;
import com.sample.android.lib.ui.selection.SelectionConfirmView;
import com.sample.android.lib.utils.DpUtils;
import com.sample.android.lib.utils.MediaGridInset;

import java.util.List;

public class PhotoListFragment extends Fragment {

    RecyclerView recyclerView;
    PhotoListAdapter adapter;

    SelectionConfirmView selectionConfirmView;

    SelectionCollection selectionCollection = new SelectionCollection();
    SelectionReceiver selectionReceiver;

    LoaderExecutor loaderExecutor;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.photo_list_fragment_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        selectionCollection.onCreate(savedInstanceState);
        initView(view);
        initData();

    }

    private void initView(View view) {
        adapter = new PhotoListAdapter(selectionCollection);
        adapter.setOnItemClickListener(new PhotoListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(MediaMeta mediaMeta) {
                PreviewActivity.startActivity(getContext(), mediaMeta, selectionCollection);
            }

            @Override
            public void onItemSelection(boolean isChecked, MediaMeta mediaMeta) {
                updateSelectionView();
            }
        });
        recyclerView = view.findViewById(R.id.list);
        int spanCount = 3;
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), spanCount));
        recyclerView.addItemDecoration(new MediaGridInset(spanCount, DpUtils.dp2px(getContext(), 4), false));
        recyclerView.setAdapter(adapter);
        selectionConfirmView = view.findViewById(R.id.selection);

        loaderExecutor = new LoaderExecutor(new PictureLoader());

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(SelectionCollection.ACTION_SELECTION_BROADCAST);
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(selectionReceiver = new SelectionReceiver(), intentFilter);
        Log.w("123", "registerReceiver");
    }

    private void initData() {
        loaderExecutor.load(getContext(), mediaMetas -> {
            if (isDetached()) {
                return;
            }
            new Handler(Looper.getMainLooper()).post(() -> {
                onLoadAlbumResponse(mediaMetas);
            });
        });
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        if (selectionCollection != null) {
            selectionCollection.onSaveInstanceState(outState);
        }
        super.onSaveInstanceState(outState);
    }


    @Override
    public void onDestroyView() {
        try {
            LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(selectionReceiver);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (loaderExecutor != null) {
            loaderExecutor.shutDown();
        }
        super.onDestroyView();
    }


    private void onLoadAlbumResponse(List<MediaMeta> mediaMetas) {
        if (adapter != null) {
            adapter.setNewData(mediaMetas);
        }
    }

    private void updateSelectionView() {
        if (selectionConfirmView != null) {
            if (selectionCollection.isEmpty()) {
                selectionConfirmView.setVisibility(View.GONE);
            } else {
                selectionConfirmView.setVisibility(View.VISIBLE);
            }

            if (selectionConfirmView.getVisibility() != View.VISIBLE) {
                return;
            }
            selectionConfirmView.update(selectionCollection.asListOfUri());
        }
    }


    /**
     * selection
     */
    class SelectionReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

            Log.w("123", "SelectionReceiver");
            selectionCollection.onCreate(intent.getBundleExtra(SelectionCollection.KEY_SELECTION));

            MediaMeta mediaMeta = intent.getParcelableExtra(SelectionCollection.KEY_SELECTION_CHECKED);
            if (adapter != null) {
                adapter.notifyItemChange(mediaMeta);
            }
            updateSelectionView();
        }
    }
}
