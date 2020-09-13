package com.sample.android.lib.model.loader;

import android.content.Context;

import com.sample.android.lib.model.MediaMeta;

import java.util.List;

public interface Loader {

    String MIME_TYPE_JPEG = "image/jpeg";
    String MIME_TYPE_PNG = "image/png";
    String MIME_TYPE_WEBP = "image/webp";
    String MIME_TYPE_GIF = "image/gif";


    String MIME_TYPE_MP4 = "video/mp4";
    String MIME_TYPE_3GP = "video/3gp";
    String MIME_TYPE_AIV = "video/aiv";
    String MIME_TYPE_RMVB = "video/rmvb";
    String MIME_TYPE_VOB = "video/vob";
    String MIME_TYPE_FLV = "video/flv";
    String MIME_TYPE_MKV = "video/mkv";
    String MIME_TYPE_MOV = "video/mov";
    String MIME_TYPE_MPG = "video/mpg";

    List<MediaMeta> load(Context context);
}
