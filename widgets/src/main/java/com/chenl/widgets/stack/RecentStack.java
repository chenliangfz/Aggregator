package com.chenl.widgets.stack;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Scroller;

import com.nineoldandroids.view.ViewHelper;

/**
 * Created by Marcin on 2015-04-13.
 */
public class RecentStack extends FrameLayout implements GestureDetector.OnGestureListener {
    Scroller scroller;

    StackAdapter mAdapter;

    GestureDetector gestureDetector = new GestureDetector(getContext(), this);

    int scroll = 0;

    OnItemClickListener onItemClickListener;

    Rect childTouchRect[];

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public RecentStack(Context context) {
        super(context);
        initRecentsList();
    }

    public RecentStack(Context context, AttributeSet attrs) {
        super(context, attrs);
        initRecentsList();
    }

    public RecentStack(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initRecentsList();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public RecentStack(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initRecentsList();
    }

    private void initRecentsList() {
        scroller = new Scroller(getContext());
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public StackAdapter getAdapter() {
        return mAdapter;
    }

    public void setAdapter(StackAdapter mAdapter) {
        this.mAdapter = mAdapter;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (mAdapter == null)
            return;
        if (getChildCount() != mAdapter.getCount()) {
            initChildren();
        }
        childTouchRect = new Rect[getChildCount()];
        for (int i = 0; i < getChildCount(); i++) {
            getChildAt(i).layout(0, 0, getWidth() - getPaddingLeft() - getPaddingRight(), getWidth() - getPaddingLeft() - getPaddingRight());
            childTouchRect[i] = new Rect();
        }
    }

    private void initChildren() {
        removeAllViews();
        for (int i = 0; i < mAdapter.getCount(); i++) {
            final View childrenView = mAdapter.getView(i, this);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                childrenView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
            }
            addView(childrenView, i, generateDefaultLayoutParams());
            final int finalI = i;
            childrenView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClickListener != null)
                        onItemClickListener.onItemClick(childrenView, finalI);
                }
            });
        }
    }

    private int getMaxScroll() {
        return (getChildCount() - 1) * (getWidth() - getPaddingLeft() - getPaddingRight());
    }

    @Override
    protected void dispatchDraw(@NonNull Canvas canvas) {
        layoutChildren();
        requestLayout();
        super.dispatchDraw(canvas);
        doScrolling();
    }

    private void layoutChildren() {
        int width = getWidth() - getPaddingLeft() - getPaddingRight();
        int height = getHeight() - getPaddingTop() - getPaddingBottom();
        for (int i = 0; i < getChildCount(); i++) {
            float topSpace = height - width;
            int y = (int) (topSpace * Math.pow(2, (i * width - scroll) / (float) width));
            float scale = (float) (-Math.pow(2, -y / topSpace / 10.0f) + 19.0f / 10);
            childTouchRect[i].set(getPaddingLeft(), y + getPaddingTop(), (int) (scale * (getPaddingLeft() + getWidth() - getPaddingLeft() - getPaddingRight())), (int) (scale * (y + getPaddingTop() + getWidth() - getPaddingLeft() - getPaddingRight())));
            ViewHelper.setTranslationX(getChildAt(i), getPaddingLeft());
            ViewHelper.setTranslationY(getChildAt(i), y + getPaddingTop());
            ViewHelper.setScaleX(getChildAt(i), scale);
            ViewHelper.setScaleY(getChildAt(i), scale);
        }
    }

    @Override
    public boolean dispatchTouchEvent(@NonNull MotionEvent event) {
        if (gestureDetector.onTouchEvent(event)) {
            for (int i = getChildCount() - 1; i >= 0; i--) {
                MotionEvent e = MotionEvent.obtain(event);
                event.setAction(MotionEvent.ACTION_CANCEL);
                e.offsetLocation(-childTouchRect[i].left, -childTouchRect[i].top);
                getChildAt(i).dispatchTouchEvent(e);
            }
            return true;
        }

        if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
            forceFinished();
        }

        for (int i = getChildCount() - 1; i >= 0; i--) {
            if (childTouchRect[i].contains((int) event.getX(), (int) event.getY())) {
                MotionEvent e = MotionEvent.obtain(event);
                e.offsetLocation(-childTouchRect[i].left, -childTouchRect[i].top);
                if (getChildAt(i).dispatchTouchEvent(e))
                    break;
            }
        }

        return true;
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent event) {

        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float v, float v2) {
        scroll = (int) Math.max(0, Math.min(scroll + v2, getMaxScroll()));
        postInvalidate();
        return true;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    void startScrolling(float initialVelocity) {
        scroller.fling(0, scroll, 0, (int) initialVelocity, 0,
                0, Integer.MIN_VALUE, Integer.MAX_VALUE);

        postInvalidate();
    }

    private void doScrolling() {
        if (scroller.isFinished())
            return;

        boolean more = scroller.computeScrollOffset();
        int y = scroller.getCurrY();

        scroll = Math.max(0, Math.min(y, getMaxScroll()));

        if (more)
            postInvalidate();
    }

    boolean isFlinging() {
        return !scroller.isFinished();
    }

    void forceFinished() {
        if (!scroller.isFinished()) {
            scroller.forceFinished(true);
        }
    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float v, float v2) {
        startScrolling(-v2);
        return true;
    }

}
