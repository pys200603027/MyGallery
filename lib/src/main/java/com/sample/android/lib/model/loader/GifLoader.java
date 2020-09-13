package com.sample.android.lib.model.loader;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;

import com.sample.android.lib.model.MediaMeta;

import java.util.ArrayList;
import java.util.List;

public class GifLoader implements Loader {

    @Override
    public List<MediaMeta> load(Context context) {
        List<MediaMeta> mediaMetas = new ArrayList<>();

        Cursor cursor = createGifCursor(context);
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
                mediaMeta.setUri(uri);
                mediaMeta.setPath(path);
                mediaMeta.setMimeType(mimeType);
                mediaMeta.setDate(addTime);

                mediaMetas.add(mediaMeta);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return mediaMetas;
    }

    private Cursor createGifCursor(Context context) {

        ContentResolver contentResolver = context.getContentResolver();

        String[] projection = new String[]{
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.MIME_TYPE,
                MediaStore.Images.Media.DATE_ADDED
        };

        String selection = MediaStore.Images.Media.MIME_TYPE + "=?";
        String[] selectionArgs = new String[]{
                MIME_TYPE_GIF
        };
        String sortOrder = MediaStore.Images.Media.DATE_ADDED + " DESC";
        return contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, selection, selectionArgs, sortOrder);
    }
}
