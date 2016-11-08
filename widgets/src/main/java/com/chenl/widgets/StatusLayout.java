package com.chenl.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ChenLiang on 16/11/8.
 */

public class StatusLayout extends FrameLayout {

    private final static int NOT_SET = -1;

    private LayoutStatus mCurrentStatus = LayoutStatus.LOADING;

    private HashMap<LayoutStatus, Integer> mStatusAndLayoutIds = new HashMap<>();

    private HashMap<LayoutStatus, View> mStatusAndViews = new HashMap<>();

    private ArrayList<View> mContentViews = new ArrayList<>();

    private OnClickListener mFailedClickListener;

    public StatusLayout(Context context) {
        super(context);
    }

    public StatusLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()) {
            init(context, attrs);
        }
    }

    public StatusLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (!isInEditMode()) {
            init(context, attrs);
        }
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray =
                context.obtainStyledAttributes(attrs, R.styleable.StatusLayout);
        if (typedArray == null) {
            return;
        }
        try {
            int loadingLayoutId = typedArray.getResourceId(R.styleable.StatusLayout_layout_loading, NOT_SET);
            mStatusAndLayoutIds.put(LayoutStatus.LOADING, loadingLayoutId);
            int emptyLayoutId =
                    typedArray.getResourceId(R.styleable.StatusLayout_layout_empty, NOT_SET);
            mStatusAndLayoutIds.put(LayoutStatus.EMPTY, emptyLayoutId);
            int networkErrorLayoutId =
                    typedArray.getResourceId(R.styleable.StatusLayout_layout_network_error, NOT_SET);
            mStatusAndLayoutIds.put(LayoutStatus.NETWORK_ERROR, networkErrorLayoutId);
            int failedLayoutId =
                    typedArray.getResourceId(R.styleable.StatusLayout_layout_failed, NOT_SET);
            mStatusAndLayoutIds.put(LayoutStatus.FAILED, failedLayoutId);
        } finally {
            typedArray.recycle();
        }
        showLayout(mCurrentStatus);
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        super.addView(child, index, params);
        if (child.getTag() == null ||
                (!child.getTag().equals(LayoutStatus.LOADING.name()) &&
                        !child.getTag().equals(LayoutStatus.NETWORK_ERROR.name()) &&
                        !child.getTag().equals(LayoutStatus.EMPTY.name()) &&
                        !child.getTag().equals(LayoutStatus.FAILED.name()))) {
            mContentViews.add(child);
            if (!this.isInEditMode()) {
                setContentVisibility(false);
            }
        }
    }

    private void setFailedClickListener(OnClickListener clickListener) {
        mFailedClickListener = clickListener;
    }

    public void showContent(){
        setContentVisibility(true);
        mCurrentStatus = LayoutStatus.CONTENT;
    }

    public void showLoadingLayout() {
        showLayout(LayoutStatus.LOADING);
    }

    public void showEmptyLayout() {
        showLayout(LayoutStatus.EMPTY);
    }

    public void showFailedLayout() {
        showLayout(LayoutStatus.FAILED);
    }

    public void showNetworkErrorLayout() {
        showLayout(LayoutStatus.NETWORK_ERROR);
    }

    private void showLayout(LayoutStatus status) {
        hideLayout(mCurrentStatus);
        View layout = mStatusAndViews.get(status);
        if (layout == null) {
            int layoutId = mStatusAndLayoutIds.get(LayoutStatus.LOADING);
            if (layoutId == NOT_SET) {
                throw new IllegalStateException(
                        status + " value is -1");
            }
            layout = LayoutInflater.from(getContext()).inflate(layoutId, this, false);
            if (status == LayoutStatus.NETWORK_ERROR || status == LayoutStatus.FAILED || status == LayoutStatus.EMPTY) {
                layout.setOnClickListener(mFailedClickListener);
            }
            layout.setTag(status.name());
            LayoutParams layoutParams = (LayoutParams) layout.getLayoutParams();
            if (layoutParams == null) {
                layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            }
            mStatusAndViews.put(status, layout);
            addView(layout, layoutParams);
        } else {
            layout.setVisibility(VISIBLE);
        }
        mCurrentStatus = status;
    }


    public void hideContent(){
        setContentVisibility(false);
    }

    public void hideLoadingLayout() {
        hideLayout(LayoutStatus.LOADING);
    }

    public void hideEmptyLayout() {
        hideLayout(LayoutStatus.EMPTY);
    }

    public void hideFailedLayout() {
        hideLayout(LayoutStatus.FAILED);
    }

    public void hideNetworkErrorLayout() {
        hideLayout(LayoutStatus.NETWORK_ERROR);
    }

    private void hideLayout(LayoutStatus status) {
        showContent();
        View layout = mStatusAndViews.get(status);
        if (layout != null && layout.isShown()) {
            layout.setVisibility(GONE);
        }
    }

    public void setContentVisibility(boolean visibility) {
        for (View contentView : mContentViews) {
            contentView.setVisibility(visibility ? View.VISIBLE : View.GONE);
        }
    }

    public enum LayoutStatus {

        /**
         * 正在加载
         */
        LOADING,
        /**
         * 无内容
         */
        EMPTY,
        /**
         * 内容显示
         */
        CONTENT,
        /**
         * 网络错误
         */
        NETWORK_ERROR,
        /**
         * 加载失败
         */
        FAILED
    }

    public static interface OnClickEmptyLayoutListenter extends OnClickListener {
    }

    public static interface OnClickFailedLayoutListener extends OnClickListener {
    }

    public static interface OnCliclNetworkErrorLayoutListener extends OnClickListener {
    }
}
