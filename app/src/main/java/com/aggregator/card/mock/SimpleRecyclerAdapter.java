package com.aggregator.card.mock;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aggregator.card.ui.custom.recycle.adapter.ArrayRecyclerAdapter;

import javax.inject.Inject;

import butterknife.BindView;


/**
 * Created by ChenLiang on 16/11/8.
 */

public class SimpleRecyclerAdapter extends ArrayRecyclerAdapter<String, SimpleRecyclerAdapter.SimpleViewHolder> {

    @Inject
    SimpleRecyclerAdapter() {

    }

    @Override
    protected SimpleViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        return new SimpleViewHolder(LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false));
    }

    @Override
    protected void onBindItemViewHolder(SimpleViewHolder holder, int position) {
        holder.mTitleTxt.setText(getItem(position));
    }

    public static class SimpleViewHolder extends ArrayRecyclerAdapter.ItemViewHolder {
        @BindView(android.R.id.text1)
        TextView mTitleTxt;

        public SimpleViewHolder(View view) {
            super(view);
        }
    }
}
