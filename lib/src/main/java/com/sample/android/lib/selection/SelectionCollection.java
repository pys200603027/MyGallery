package com.sample.android.lib.selection;

import android.net.Uri;

import com.sample.android.lib.model.MediaMeta;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class SelectionCollection {

    Set<MediaMeta> selected;

    public SelectionCollection() {
        if (selected == null) {
            selected = new LinkedHashSet<>();
        }
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
}
