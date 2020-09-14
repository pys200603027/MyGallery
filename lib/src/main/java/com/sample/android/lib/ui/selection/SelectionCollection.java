package com.sample.android.lib.ui.selection;

import android.net.Uri;
import android.os.Bundle;

import com.sample.android.lib.model.MediaMeta;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class SelectionCollection {

    public static final String STATE_SELECTION = "state_selection";

    public static final String KEY_SELECTION = "key_selection";

    public static final String ACTION_SELECTION_BROADCAST = "action_selection_broadcast";

    public static final String KEY_SELECTION_CHECKED = "key_selection_check";

    private Set<MediaMeta> selected;

    public SelectionCollection() {

    }

    public int getCount() {
        return selected == null ? 0 : selected.size();
    }

    public boolean add(MediaMeta mediaMeta) {
        return selected.add(mediaMeta);
    }

    public boolean remove(MediaMeta mediaMeta) {
        return selected.remove(mediaMeta);
    }

    public boolean isSelected(MediaMeta item) {
        return selected.contains(item);
    }

    public boolean isEmpty() {
        return getCount() == 0;
    }

    public List<Uri> asListOfUri() {
        List<Uri> uris = new ArrayList<>();
        for (MediaMeta item : selected) {
            uris.add(item.getUri());
        }
        return uris;
    }

    public void onCreate(Bundle bundle) {
        if (bundle == null) {
            selected = new LinkedHashSet<>();
        } else {
            ArrayList<MediaMeta> temp = bundle.getParcelableArrayList(STATE_SELECTION);
            selected = new LinkedHashSet<>(temp);
        }
    }

    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(STATE_SELECTION, new ArrayList<>(selected));
    }

    public Bundle getExtraBundle() {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(STATE_SELECTION, new ArrayList<>(selected));
        return bundle;
    }
}
