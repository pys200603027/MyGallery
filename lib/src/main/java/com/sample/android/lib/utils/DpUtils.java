package com.sample.android.lib.utils;

import android.content.Context;
import android.util.TypedValue;

public class DpUtils {

    public static int dp2px(Context context, float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, context.getResources().getDisplayMetrics());
    }
}
