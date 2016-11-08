package com.chenl.widgets.scale;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.chenl.widgets.R;

/**
 * 正方形
 */
public class SquareImageView extends ImageView {
    private static int MODE_H = 0x1;
    private static int MODE_W = 0x2;

    int mMode = MODE_W;

    public SquareImageView(Context context) {
        super(context);
    }

    public SquareImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.SquareImage);
        try {
            mMode = a.getInt(R.styleable.SquareImage_mode, MODE_W);
        } finally {
            a.recycle();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(getDefaultSize(0, widthMeasureSpec), getDefaultSize(0, heightMeasureSpec));
        int childWidthSize;
        int childHeightSize;
        if (mMode == MODE_W) {
            childWidthSize = childHeightSize = getMeasuredWidth();
        } else {
            childWidthSize = childHeightSize = getMeasuredHeight();
        }
        widthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidthSize, MeasureSpec.EXACTLY);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(childHeightSize, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


}
