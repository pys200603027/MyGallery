package com.sample.android.lib.model;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Nullable;

public class MediaMeta implements Parcelable {

    Uri uri;
    String path;
    long date;
    String mimeType;

    public MediaMeta() {
    }

    protected MediaMeta(Parcel in) {
        uri = in.readParcelable(Uri.class.getClassLoader());
        path = in.readString();
        date = in.readLong();
        mimeType = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(uri, flags);
        dest.writeString(path);
        dest.writeLong(date);
        dest.writeString(mimeType);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MediaMeta> CREATOR = new Creator<MediaMeta>() {
        @Override
        public MediaMeta createFromParcel(Parcel in) {
            return new MediaMeta(in);
        }

        @Override
        public MediaMeta[] newArray(int size) {
            return new MediaMeta[size];
        }
    };

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    @Override
    public int hashCode() {
        return uri.hashCode();
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj instanceof MediaMeta) {
            MediaMeta mediaMeta = (MediaMeta) obj;
            return mediaMeta.uri.equals(this.uri);
        }

        return false;
    }
}
