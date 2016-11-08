package com.aggregator.card.ui.custom.recycle;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.aggregator.card.ui.custom.recycle.adapter.HeaderAndFooterRecyclerViewAdapter;

/**
 * Created by ChenLiang on 16/11/8.
 */

public class HeaderAndFooterRecyclerView extends RecyclerView {

    private HeaderAndFooterRecyclerViewAdapter mHeaderAndFooterRecyclerViewAdapter;

    public HeaderAndFooterRecyclerView(Context context) {
        super(context);
    }

    public HeaderAndFooterRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public HeaderAndFooterRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setAdapter(Adapter adapter) {
        mHeaderAndFooterRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(adapter);
        super.setAdapter(mHeaderAndFooterRecyclerViewAdapter);
    }

    /**
     * 设置HeaderView
     */
    public void addHeaderView(View headerView) {
        mHeaderAndFooterRecyclerViewAdapter.addHeaderView(headerView);
    }

    /**
     * 设置FooterView
     */
    public void addFooterView(View footerView) {
        mHeaderAndFooterRecyclerViewAdapter.addFooterView(footerView);
    }

    /**
     * 移除FooterView
     */
    public void removeFooterView(View footerView) {
        mHeaderAndFooterRecyclerViewAdapter.removeFooterView(footerView);
    }

    /**
     * 移除HeaderView
     */
    public void removeHeaderView(View headerView) {
        mHeaderAndFooterRecyclerViewAdapter.removeHeaderView(headerView);
    }
}
