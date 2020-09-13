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

public class VideoLoader implements Loader {

    @Override
    public List<MediaMeta> load(Context context) {

        List<MediaMeta> mediaMetas = new ArrayList<>();
        Cursor cursor = createCursor(context);
        try {
            while (cursor.moveToNext()) {
                String path = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));

                if (TextUtils.isEmpty(path)) {
                    continue;
                }

                long id = cursor.getLong(cursor.getColumnIndex(MediaStore.Video.Media._ID));
                Uri uri = Uri.withAppendedPath(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, String.valueOf(id));
                String mimeType = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.MIME_TYPE));
                long addTime = cursor.getLong(cursor.getColumnIndex(MediaStore.Video.Media.DATE_ADDED));

                MediaMeta mediaMeta = new MediaMeta();
                mediaMeta.setUri(uri);
                mediaMeta.setDate(addTime);
                mediaMeta.setMimeType(mimeType);
                mediaMeta.setPath(path);

                mediaMetas.add(mediaMeta);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return mediaMetas;
    }

    private Cursor createCursor(Context context) {
        ContentResolver contentResolver = context.getContentResolver();

        String[] projection = new String[]{
                MediaStore.Video.Media._ID,
                MediaStore.Video.Media.MIME_TYPE,
                MediaStore.Video.Media.DATA,
                MediaStore.Video.Media.DATE_ADDED,
        };

        String selection = MediaStore.Images.Media.MIME_TYPE + "=? or " +
                MediaStore.Video.Media.MIME_TYPE + "=? or " +
                MediaStore.Video.Media.MIME_TYPE + "=? or " +
                MediaStore.Video.Media.MIME_TYPE + "=? or " +
                MediaStore.Video.Media.MIME_TYPE + "=? or " +
                MediaStore.Video.Media.MIME_TYPE + "=? or " +
                MediaStore.Video.Media.MIME_TYPE + "=? or " +
                MediaStore.Video.Media.MIME_TYPE + "=? or " +
                MediaStore.Video.Media.MIME_TYPE + "=?";
        String[] selectionArgs = new String[]{
                MIME_TYPE_MP4,
                MIME_TYPE_3GP,
                MIME_TYPE_AIV,
                MIME_TYPE_RMVB,
                MIME_TYPE_VOB,
                MIME_TYPE_FLV,
                MIME_TYPE_MKV,
                MIME_TYPE_MOV,
                MIME_TYPE_MPG,
        };
        String sortOrder = MediaStore.Video.Media.DATE_ADDED + " DESC";
        return contentResolver.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, projection, selection, selectionArgs, sortOrder);
    }
}
