package com.sample.android.lib.ui.list;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sample.android.lib.R;
import com.sample.android.lib.model.MediaMeta;
import com.sample.android.lib.ui.selection.SelectionCollection;

import java.util.ArrayList;
import java.util.List;

public class PhotoListAdapter extends RecyclerView.Adapter<PhotoListAdapter.ViewHolder> {

    private OnItemClickListener onItemClickListener;
    private List<? extends MediaMeta> data;

    private SelectionCollection selectionCollection;

    public PhotoListAdapter(SelectionCollection selectionCollection) {
        this.selectionCollection = selectionCollection;
    }

    public void setNewData(List newData) {
        if (data == null) {
            data = new ArrayList();
        }
        data.clear();
        data.addAll(newData);
        notifyDataSetChanged();
    }

    public void notifyItemChange(MediaMeta mediaMeta) {
        boolean contains = data.contains(mediaMeta);
        int i = data.indexOf(mediaMeta);
        Log.w("123", "notifyItemChange position:" + i + ",contains:" + contains);
        if (i < 0 || i >= data.size()) {
            return;
        }
        notifyItemChanged(i);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public PhotoListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoListAdapter.ViewHolder holder, int position) {
        MediaMeta mediaMeta = data.get(position);
        if (mediaMeta != null) {
            Glide.with(holder.imageView.getContext()).load(mediaMeta.getUri()).into(holder.imageView);

            if (selectionCollection.isSelected(mediaMeta)) {
                holder.checkBox.setChecked(true);
            } else {
                holder.checkBox.setChecked(false);
            }


            holder.imageView.setOnClickListener(v -> {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(mediaMeta);
                }
            });

            holder.checkBox.setOnClickListener(v -> {
                boolean isChecked = holder.checkBox.isChecked();
                if (isChecked) {
                    selectionCollection.add(mediaMeta);
                } else {
                    selectionCollection.remove(mediaMeta);
                }
                if (onItemClickListener != null) {
                    onItemClickListener.onItemSelection(isChecked, mediaMeta);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        CheckBox checkBox;

        private ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.image);
            checkBox = itemView.findViewById(R.id.checkbox);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(MediaMeta mediaMeta);

        void onItemSelection(boolean isChecked, MediaMeta mediaMeta);
    }
}
