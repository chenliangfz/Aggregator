package com.chenl.widgets.scale;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.chenl.widgets.R;

/**
 * 比例
 */
public class SquareLayout extends RelativeLayout {

    public static final int PROPORTION = 1;
    private int mProportionH = 1, mProportionW = 1;

    public SquareLayout(Context context) {
        super(context);
    }

    public SquareLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.SquareLayout);
        try {
            mProportionH = a.getInteger(R.styleable.SquareLayout_ProportionH, PROPORTION);
            mProportionW = a.getInteger(R.styleable.SquareLayout_ProportionW, PROPORTION);
        } finally {
            a.recycle();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(getDefaultSize(0, widthMeasureSpec), getDefaultSize(0, heightMeasureSpec));
        int childWidthSize;
        int childHeightSize;
        if (mProportionW >= mProportionH) {
            childWidthSize = getMeasuredWidth();
            double ratio = 1.0f * mProportionH / mProportionW;
            childHeightSize = (int) (childWidthSize * ratio);
        } else {
            childHeightSize = getMeasuredHeight();
            double ratio = 1.0f * mProportionW / mProportionH;
            childWidthSize = (int) (childHeightSize * ratio);
        }
        widthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidthSize, MeasureSpec.EXACTLY);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(childHeightSize, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

}
