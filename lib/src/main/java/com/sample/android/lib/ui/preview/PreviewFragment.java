package com.sample.android.lib.ui.preview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.sample.android.lib.R;
import com.sample.android.lib.model.MediaMeta;

public class PreviewFragment extends Fragment {

    ImageView imageView;

    public static Fragment newInstance(MediaMeta mediaMeta) {
        PreviewFragment fragment = new PreviewFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("mediaMeta", mediaMeta);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.photo_preview_fragment_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageView = view.findViewById(R.id.ph_preview_image);
        initData();
    }

    private void initData() {
        MediaMeta mediaMeta = getArguments().getParcelable("mediaMeta");
        if (mediaMeta == null) {
            return;
        }
        Glide.with(this).load(mediaMeta.getUri()).into(imageView);
    }
}
