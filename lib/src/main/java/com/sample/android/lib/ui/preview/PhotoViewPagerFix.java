package com.sample.android.lib.ui.preview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

/**
 * <p>
 * Description : PhotoView 图片放大缩小bug fix 参考 https://github.com/chrisbanes/PhotoView/issues/31
 * <p>
 * 解决/ 部分机型 java.lang.IllegalArgumentException: pointerIndex out of range pointerIndex=-1 pointerCount=1 问题
 */
public class PhotoViewPagerFix extends ViewPager {

    public PhotoViewPagerFix(@NonNull Context context) {
        super(context);
    }

    public PhotoViewPagerFix(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        try {
            return super.onTouchEvent(ev);
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        try {
            return super.onInterceptTouchEvent(ev);
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
