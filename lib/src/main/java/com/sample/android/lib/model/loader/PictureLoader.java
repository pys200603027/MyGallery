package com.sample.android.lib.model.loader;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;

import com.sample.android.lib.model.MediaMeta;

import java.util.ArrayList;
import java.util.List;

public class PictureLoader implements Loader {

    private Cursor createPictureCursor(Context context) {
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = new String[]{
                MediaStore.Video.Media._ID,
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.DATE_ADDED,
                MediaStore.Video.Media.MIME_TYPE
        };
        String selection = MediaStore.Images.Media.MIME_TYPE + "=? or " +
                MediaStore.Images.Media.MIME_TYPE + "=? or " +
                MediaStore.Images.Media.MIME_TYPE + "=?";

        String[] selectionArgs = new String[]{
                MIME_TYPE_JPEG,
                MIME_TYPE_PNG,
                MIME_TYPE_WEBP
        };
        String sortOrder = MediaStore.Images.Media.DATE_ADDED + " DESC";
        return context.getContentResolver().query(uri, projection, selection, selectionArgs, sortOrder);
    }

    @Override
    public List<MediaMeta> load(Context context) {
        List<MediaMeta> mediaMetas = new ArrayList<>();
        Cursor cursor = createPictureCursor(context);
        try {
            while (cursor.moveToNext()) {
                String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                if (TextUtils.isEmpty(path)) {
                    continue;
                }

                long id = cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media._ID));
                Uri uri = Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, String.valueOf(id));
                String mimeType = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.MIME_TYPE));
                long addTime = cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media.DATE_ADDED));

                MediaMeta mediaMeta = new MediaMeta();
                mediaMeta.setDate(addTime);
                mediaMeta.setUri(uri);
                mediaMeta.setPath(path);
                mediaMeta.setMimeType(mimeType);

                mediaMetas.add(mediaMeta);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return mediaMetas;
    }
}
