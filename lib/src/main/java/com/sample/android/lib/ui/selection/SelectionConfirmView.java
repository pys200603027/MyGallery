package com.sample.android.lib.ui.selection;

import android.content.Context;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sample.android.lib.R;
import com.sample.android.lib.utils.DpUtils;
import com.sample.android.lib.utils.OffsetItemDecoration;

import java.util.List;


public class SelectionConfirmView extends LinearLayout implements View.OnClickListener {

    /**
     * preview list
     */
    private RecyclerView recyclerView;
    private SelectionConfirmPreviewAdapter adapter;

    /**
     * confirm & send
     */
    View sendView;
    ViewGroup rootContentView;

    public SelectionConfirmView(Context context) {
        super(context);
        initView(context);
    }

    public SelectionConfirmView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        inflate(context, R.layout.photo_selection_confirm_item, this);

        setOrientation(HORIZONTAL);
        int left = DpUtils.dp2px(getContext(), 10);
        int top = DpUtils.dp2px(getContext(), 6);
        int bottom = DpUtils.dp2px(getContext(), 12);
        setPadding(left, top, left, bottom);

        recyclerView = findViewById(R.id.ph_selection_list);
        sendView = findViewById(R.id.ph_selection_send);
        sendView.setOnClickListener(this);

        rootContentView = findViewById(R.id.root_ll);

        initRecyclerView();
    }

    @Override
    public void setBackgroundColor(int color) {
        super.setBackgroundColor(color);
        rootContentView.setBackgroundColor(color);
    }

    private void initRecyclerView() {
        adapter = new SelectionConfirmPreviewAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.addItemDecoration(new OffsetItemDecoration(getContext(), 0, 12, 0, 0));
        recyclerView.setAdapter(adapter);
    }

    public void update(List<Uri> list) {
        adapter.setContentUriList(list);
    }

    public void setSendBtnEnable(boolean enable) {
        sendView.setEnabled(enable);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ph_selection_send) {
            if (onViewClickListener != null) {
                onViewClickListener.onSendClick();
            }
        }
    }


    OnViewClickListener onViewClickListener;

    public SelectionConfirmView setOnViewClickListener(OnViewClickListener onViewClickListener) {
        this.onViewClickListener = onViewClickListener;
        return this;
    }

    public interface OnViewClickListener {
        void onSendClick();
    }
}
