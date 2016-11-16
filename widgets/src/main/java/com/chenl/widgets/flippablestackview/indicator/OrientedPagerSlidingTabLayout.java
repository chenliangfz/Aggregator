/*
 * Copyright (C) 2013 Andreas Stuetz <andreas.stuetz@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.chenl.widgets.flippablestackview.indicator;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chenl.widgets.R;
import com.chenl.widgets.flippablestackview.OrientedViewPager;

import java.util.Locale;


public class OrientedPagerSlidingTabLayout extends HorizontalScrollView {

    public interface IconTabProvider {
        public int getPageIconResId(int position);
    }


    private LinearLayout.LayoutParams defaultTabLayoutParams;
    private LinearLayout.LayoutParams expandedTabLayoutParams;

    private final PageListener pageListener = new PageListener();
    public OnPageChangeListener delegatePageListener;

    private LinearLayout tabsContainer;
    private OrientedViewPager mPager;

    private int tabCount;

    private int mCurrentPosition = 0;
    private float currentPositionOffset = 0f;

    private Paint rectPaint;
    private Paint dividerPaint;

    private int indicatorColor = 0xFF666666;
    private int underlineColor = 0x1A000000;
    private int dividerColor = 0x1A000000;

    private boolean shouldExpand = false;
    private boolean textAllCaps = true;

    private int scrollOffset = 52;
    private int indicatorHeight = 8;
    private int underlineHeight = 2;
    private int dividerPadding = 12;
    private int tabMargins = 0;
    private int tabPadding = 24;
    private int dividerWidth = 1;

    private int tabTextSize = 12;
    private ColorStateList tabTextColor = ColorStateList.valueOf(android.R.color.white);
    private Typeface tabTypeface = null;
    private int tabTypefaceStyle = Typeface.BOLD;

    private int lastScrollX = 0;

    private int tabBackgroundResId = -1;

    private StateListDrawable mTabBackgroundRes = createStateDrawable();

    private Locale locale;

    public OrientedPagerSlidingTabLayout(Context context) {
        this(context, null);
    }

    public OrientedPagerSlidingTabLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OrientedPagerSlidingTabLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        setFillViewport(true);
        setWillNotDraw(false);

        tabsContainer = new LinearLayout(context);
        tabsContainer.setOrientation(LinearLayout.HORIZONTAL);
        tabsContainer.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        addView(tabsContainer);

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        scrollOffset = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, scrollOffset, displayMetrics);
        indicatorHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, indicatorHeight, displayMetrics);
        underlineHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, underlineHeight, displayMetrics);
        dividerPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dividerPadding, displayMetrics);
        tabPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, tabPadding, displayMetrics);
        dividerWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dividerWidth, displayMetrics);
        tabTextSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, tabTextSize, displayMetrics);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.OrientedPagerSlidingTabLayout);
        tabTextSize = typedArray.getDimensionPixelSize(R.styleable.OrientedPagerSlidingTabLayout_tabLayoutTextSize, tabTextSize);
        tabTextColor = typedArray.getColorStateList(R.styleable.OrientedPagerSlidingTabLayout_tabLayoutTextColor);
        indicatorColor = typedArray.getColor(R.styleable.OrientedPagerSlidingTabLayout_tabLayoutIndicatorColor, indicatorColor);
        underlineColor = typedArray.getColor(R.styleable.OrientedPagerSlidingTabLayout_tabLayoutUnderlineColor, underlineColor);
        dividerColor = typedArray.getColor(R.styleable.OrientedPagerSlidingTabLayout_tabLayoutDividerColor, dividerColor);
        indicatorHeight = typedArray.getDimensionPixelSize(R.styleable.OrientedPagerSlidingTabLayout_tabLayoutIndicatorHeight, indicatorHeight);
        underlineHeight = typedArray.getDimensionPixelSize(R.styleable.OrientedPagerSlidingTabLayout_tabLayoutUnderlineHeight, underlineHeight);
        dividerPadding = typedArray.getDimensionPixelSize(R.styleable.OrientedPagerSlidingTabLayout_tabLayoutDividerPadding, dividerPadding);
        tabPadding = typedArray.getDimensionPixelSize(R.styleable.OrientedPagerSlidingTabLayout_tabLayoutPaddingLeftRight, tabPadding);
        tabBackgroundResId = typedArray.getResourceId(R.styleable.OrientedPagerSlidingTabLayout_tabLayoutBackground, tabBackgroundResId);
        shouldExpand = typedArray.getBoolean(R.styleable.OrientedPagerSlidingTabLayout_tabLayoutShouldExpand, shouldExpand);
        scrollOffset = typedArray.getDimensionPixelSize(R.styleable.OrientedPagerSlidingTabLayout_tabLayoutScrollOffset, scrollOffset);
        textAllCaps = typedArray.getBoolean(R.styleable.OrientedPagerSlidingTabLayout_tabLayoutTextAllCaps, textAllCaps);
        tabMargins = typedArray.getDimensionPixelSize(R.styleable.OrientedPagerSlidingTabLayout_tabLayoutMargins,tabMargins);
        typedArray.recycle();


        rectPaint = new Paint();
        rectPaint.setAntiAlias(true);
        rectPaint.setStyle(Style.FILL);

        dividerPaint = new Paint();
        dividerPaint.setAntiAlias(true);
        dividerPaint.setStrokeWidth(dividerWidth);

        defaultTabLayoutParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        expandedTabLayoutParams = new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 1.0f);

        if (locale == null) {
            locale = getResources().getConfiguration().locale;
        }
    }

    public void setOrientedViewPager(OrientedViewPager pager) {
        this.mPager = pager;
        if (pager.getAdapter() == null) {
            throw new IllegalStateException("ViewPager does not have adapter instance.");
        }
        pager.setOnPageChangeListener(pageListener);
        notifyDataSetChanged();
    }

    public void setOnPageChangeListener(OnPageChangeListener listener) {
        this.delegatePageListener = listener;
    }

    public void notifyDataSetChanged() {
        tabsContainer.removeAllViews();
        tabCount = mPager.getAdapter().getCount();
        for (int i = 0; i < tabCount; i++) {
            if (mPager.getAdapter() instanceof IconTabProvider) {
                addIconTab(i, ((IconTabProvider) mPager.getAdapter()).getPageIconResId(i));
            } else {
                addTextTab(i, mPager.getAdapter().getPageTitle(i).toString());
            }
        }
        updateTabStyles();
        getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

            @SuppressWarnings("deprecation")
            @SuppressLint("NewApi")
            @Override
            public void onGlobalLayout() {

                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                    getViewTreeObserver().removeGlobalOnLayoutListener(this);
                } else {
                    getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }

                mCurrentPosition = mPager.getCurrentItem();
                scrollToChild(mCurrentPosition, 0);
            }
        });

    }

    private void addTextTab(final int position, String title) {
        TextView tab = new TextView(getContext());
        tab.setText(title);
        tab.setGravity(Gravity.CENTER);
        tab.setMaxLines(2);
        addTab(position, tab);
    }

    private void addIconTab(final int position, int resId) {
        ImageButton tab = new ImageButton(getContext());
        tab.setImageResource(resId);
        addTab(position, tab);
    }

    private void addTab(final int position, View tab) {
        tab.setFocusable(true);
        tab.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mPager.setCurrentItem(position);
            }
        });
        tab.setPadding(tabPadding, 0, tabPadding, 0);
        LinearLayout.LayoutParams params = shouldExpand ? expandedTabLayoutParams : defaultTabLayoutParams;
        params.setMargins(tabMargins,tabMargins,tabMargins,tabMargins);
        tabsContainer.addView(tab, position, params);
    }

    private void updateTabStyles() {
        for (int i = 0; i < tabCount; i++) {
            View childView = tabsContainer.getChildAt(i);
            if (tabBackgroundResId == -1) {
                childView.setBackgroundDrawable(mTabBackgroundRes);
            } else {
                childView.setBackgroundResource(tabBackgroundResId);
            }
            if (childView instanceof TextView) {
                TextView tab = (TextView) childView;
                tab.setTextSize(tabTextSize);
                tab.setTextColor(tabTextColor);
                if (textAllCaps) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                        tab.setAllCaps(true);
                    } else {
                        tab.setText(tab.getText().toString().toUpperCase(locale));
                    }
                }
            }
        }

    }

    private void scrollToChild(int position, int offset) {

        if (tabCount == 0) {
            return;
        }

        int newScrollX = tabsContainer.getChildAt(position).getLeft() + offset;

        if (position > 0 || offset > 0) {
            newScrollX -= scrollOffset;
        }

        if (newScrollX != lastScrollX) {
            lastScrollX = newScrollX;
            scrollTo(newScrollX, 0);
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (isInEditMode() || tabCount == 0) {
            return;
        }

        if (underlineHeight > 0) {
            final int height = getHeight();

            // draw divider

            dividerPaint.setColor(dividerColor);
            for (int i = 0; i < tabCount - 1; i++) {
                View tab = tabsContainer.getChildAt(i);
                canvas.drawLine(tab.getRight(), dividerPadding, tab.getRight(), height - dividerPadding, dividerPaint);
            }

            // draw underline
            rectPaint.setColor(underlineColor);
            canvas.drawRect(0, height - underlineHeight, tabsContainer.getWidth(), height, rectPaint);
        }

        if (indicatorHeight > 0) {
            final int height = getHeight();
            // draw indicator line
            rectPaint.setColor(indicatorColor);
            // default: line below current tab
            View currentTab = tabsContainer.getChildAt(mCurrentPosition);
            float lineLeft = currentTab.getLeft();
            float lineRight = currentTab.getRight();

            // if there is an offset, start interpolating left and right coordinates between current and next tab
            if (currentPositionOffset > 0f && mCurrentPosition < tabCount - 1) {

                View nextTab = tabsContainer.getChildAt(mCurrentPosition + 1);
                final float nextTabLeft = nextTab.getLeft();
                final float nextTabRight = nextTab.getRight();

                lineLeft = (currentPositionOffset * nextTabLeft + (1f - currentPositionOffset) * lineLeft);
                lineRight = (currentPositionOffset * nextTabRight + (1f - currentPositionOffset) * lineRight);
            }
            canvas.drawRect(lineLeft, height - indicatorHeight, lineRight, height, rectPaint);
        }
    }

    private class PageListener implements OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            tabsContainer.getChildAt(mCurrentPosition).setSelected(false);
            mCurrentPosition = position;
            tabsContainer.getChildAt(mCurrentPosition).setSelected(true);
            currentPositionOffset = positionOffset;

            scrollToChild(position, (int) (positionOffset * tabsContainer.getChildAt(position).getWidth()));

            invalidate();

            if (delegatePageListener != null) {
                delegatePageListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if (state == ViewPager.SCROLL_STATE_IDLE) {
                scrollToChild(mPager.getCurrentItem(), 0);
            }

            if (delegatePageListener != null) {
                delegatePageListener.onPageScrollStateChanged(state);
            }
        }

        @Override
        public void onPageSelected(int position) {
            if (delegatePageListener != null) {
                delegatePageListener.onPageSelected(position);
            }
        }

    }

    public void setIndicatorColor(int indicatorColor) {
        this.indicatorColor = indicatorColor;
        invalidate();
    }

    public void setIndicatorColorResource(int resId) {
        this.indicatorColor = getResources().getColor(resId);
        invalidate();
    }

    public int getIndicatorColor() {
        return this.indicatorColor;
    }

    public void setIndicatorHeight(int indicatorLineHeightPx) {
        this.indicatorHeight = indicatorLineHeightPx;
        invalidate();
    }

    public int getIndicatorHeight() {
        return indicatorHeight;
    }

    public void setUnderlineColor(int underlineColor) {
        this.underlineColor = underlineColor;
        invalidate();
    }

    public void setUnderlineColorResource(int resId) {
        this.underlineColor = getResources().getColor(resId);
        invalidate();
    }

    public int getUnderlineColor() {
        return underlineColor;
    }

    public void setDividerColor(int dividerColor) {
        this.dividerColor = dividerColor;
        invalidate();
    }

    public void setDividerColorResource(int resId) {
        this.dividerColor = getResources().getColor(resId);
        invalidate();
    }

    public int getDividerColor() {
        return dividerColor;
    }

    public void setUnderlineHeight(int underlineHeightPx) {
        this.underlineHeight = underlineHeightPx;
        invalidate();
    }

    public int getUnderlineHeight() {
        return underlineHeight;
    }

    public void setDividerPadding(int dividerPaddingPx) {
        this.dividerPadding = dividerPaddingPx;
        invalidate();
    }

    public int getDividerPadding() {
        return dividerPadding;
    }

    public void setScrollOffset(int scrollOffsetPx) {
        this.scrollOffset = scrollOffsetPx;
        invalidate();
    }

    public int getScrollOffset() {
        return scrollOffset;
    }

    public void setShouldExpand(boolean shouldExpand) {
        this.shouldExpand = shouldExpand;
        requestLayout();
    }

    public boolean getShouldExpand() {
        return shouldExpand;
    }

    public boolean isTextAllCaps() {
        return textAllCaps;
    }

    public void setAllCaps(boolean textAllCaps) {
        this.textAllCaps = textAllCaps;
    }

    public void setTextSize(int textSizePx) {
        this.tabTextSize = textSizePx;
        updateTabStyles();
    }

    public float getTextSize() {
        return tabTextSize;
    }

    public void setTypeface(Typeface typeface, int style) {
        this.tabTypeface = typeface;
        this.tabTypefaceStyle = style;
        updateTabStyles();
    }

    public void setTabBackground(int resId) {
        this.tabBackgroundResId = resId;
    }

    public int getTabBackground() {
        return tabBackgroundResId;
    }

    public void setTabPaddingLeftRight(int paddingPx) {
        this.tabPadding = paddingPx;
        updateTabStyles();
    }

    public int getTabPaddingLeftRight() {
        return tabPadding;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        SavedState savedState = (SavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());
        mCurrentPosition = savedState.currentPosition;
        requestLayout();
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState savedState = new SavedState(superState);
        savedState.currentPosition = mCurrentPosition;
        return savedState;
    }


    private static final StateListDrawable createStateDrawable() {
        StateListDrawable stateListDrawable = new StateListDrawable();
        ColorDrawable normal = new ColorDrawable(Color.TRANSPARENT);
        ColorDrawable pressed = new ColorDrawable(Color.parseColor("#66000000"));
        stateListDrawable.addState(new int[]{android.R.attr.state_pressed, android.R.attr.state_enabled}, pressed);
        stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, pressed);
        stateListDrawable.addState(new int[]{android.R.attr.state_enabled}, normal);
        stateListDrawable.addState(new int[]{}, normal);
        return stateListDrawable;
    }

    static class SavedState extends BaseSavedState {
        int currentPosition;

        public SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            currentPosition = in.readInt();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeInt(currentPosition);
        }

        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            @Override
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            @Override
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }

}
