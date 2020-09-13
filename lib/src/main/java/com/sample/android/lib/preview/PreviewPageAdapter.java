package com.sample.android.lib.preview;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.sample.android.lib.model.MediaMeta;

import java.util.ArrayList;
import java.util.List;

public class PreviewPageAdapter extends FragmentPagerAdapter {

    List<? extends MediaMeta> data;

    public PreviewPageAdapter(@NonNull FragmentManager fm) {
        super(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return PreviewFragment.newInstance(data.get(position));
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    public void addNewData(List newData) {
        if (this.data == null) {
            this.data = new ArrayList();
        }
        data.clear();
        data.addAll(newData);
        notifyDataSetChanged();
    }
}
