package com.sample.android.lib.utils;

import android.content.Context;
import android.graphics.Rect;
import android.util.TypedValue;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class OffsetItemDecoration extends RecyclerView.ItemDecoration {

    int mOffsetTop, mOffsetRight, mOffsetBottom, mOffsetLeft;
    Context context;

    public OffsetItemDecoration(Context context, int top, int right, int bot, int left) {
        this.context = context;
        mOffsetTop = top;
        mOffsetRight = right;
        mOffsetBottom = bot;
        mOffsetLeft = left;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (mOffsetTop != 0) {
            outRect.top = dp2px(context, mOffsetTop);
        }
        if (mOffsetRight != 0) {
            outRect.right = dp2px(context, mOffsetRight);
        }
        if (mOffsetBottom != 0) {
            outRect.bottom = dp2px(context, mOffsetBottom);
        }
        if (mOffsetLeft != 0) {
            outRect.left = dp2px(context, mOffsetLeft);
        }
    }

    public int dp2px(Context context, float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, context.getResources().getDisplayMetrics());
    }
}