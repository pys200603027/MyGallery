package com.sample.android.lib.ui.selection;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sample.android.lib.R;

import java.util.ArrayList;
import java.util.List;

public class SelectionConfirmPreviewAdapter extends RecyclerView.Adapter {

    private List<Uri> contentUriList = new ArrayList<>();

    public SelectionConfirmPreviewAdapter() {

    }

    public void setContentUriList(List<Uri> list) {
        contentUriList.clear();
        contentUriList.addAll(list);
        notifyDataSetChanged();
    }


    public void addContentUri(String url) {
        contentUriList.add(Uri.parse(url));
        notifyItemInserted(contentUriList.size() - 1);
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_selection_preview_item, parent, false);
        return new SelectionPreviewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof SelectionPreviewHolder) {
            Uri uri = contentUriList.get(position);
            ((SelectionPreviewHolder) holder).bindMedia(uri);
        }
    }

    @Override
    public int getItemCount() {
        return contentUriList.size();
    }

    static class SelectionPreviewHolder extends RecyclerView.ViewHolder {

        ImageView mThumbnail;

        public SelectionPreviewHolder(View itemView) {
            super(itemView);
            this.mThumbnail = (ImageView) itemView;
        }

        private Context getContext() {
            return mThumbnail.getContext();
        }

//        private int getImageResize(Context context) {
//            float dimension = context.getResources().getDimension(R.dimen.selectionitem_size);
//            return (int) dimension;
//        }
//
//        private Drawable getPlaceHolder() {
//            return new ColorDrawable(0xff37474F);
//        }

        public void bindMedia(Uri uri) {
            Glide.with(getContext()).load(uri).into(mThumbnail);
        }
    }
}
