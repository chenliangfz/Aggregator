package com.aggregator.card.ui.custom.recycle.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.aggregator.card.util.L;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by ChenLiang on 16/9/23.
 */

public abstract class ArrayRecyclerAdapter<T, V extends ArrayRecyclerAdapter.ItemViewHolder> extends RecyclerView.Adapter<V> {

    private ArrayList<T> mDataSet = new ArrayList<>();

    private OnItemClickListener mItemClickListener;

    private OnItemLongClickListener mItemLongClickListener;

    public void add(T data) {
        add(mDataSet.size(), data);
    }

    public void add(int location, T data) {
        mDataSet.add(location, data);
        notifyItemInserted(location);
    }

    public void addAll(List<T> list) {
        addAll(mDataSet.size(), list);
    }

    public void addAll(int location, List<T> list) {
        mDataSet.addAll(location, list);
        notifyItemRangeInserted(location, location + list.size());
    }

    public boolean exist(T data) {
        return mDataSet.indexOf(data) != -1;
    }

    public void remove(T data) {
        int position = mDataSet.indexOf(data);
        if (position != -1) {
            remove(position);
        }
    }

    public void remove(int position) {
        mDataSet.remove(position);
        notifyItemRemoved(position);
    }

    public void removeAll(List<T> list) {
        mDataSet.removeAll(list);
        notifyDataSetChanged();
    }

    public void clear() {
        mDataSet.clear();
        notifyDataSetChanged();
    }

    public List<T> getDateSet() {
        return mDataSet;
    }

    public void replace(List<T> list) {
        mDataSet.clear();
        mDataSet.addAll(list);
        notifyDataSetChanged();
    }

    public T getItem(int position) {
        return mDataSet.get(position);
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        mItemLongClickListener = onItemLongClickListener;
    }

    @Override
    public V onCreateViewHolder(ViewGroup parent, int viewType) {
        V viewHolder = onCreateItemViewHolder(parent, viewType);
        if (viewHolder != null) {
            viewHolder.setOnItemClickListener(mItemClickListener);
            viewHolder.setOnItemLongClickListener(mItemLongClickListener);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(V holder, int position) {
        holder.bindPosition(position);
        onBindItemViewHolder(holder,position);
    }

    protected abstract void onBindItemViewHolder(V holder, int position);

    protected abstract V onCreateItemViewHolder(ViewGroup parent, int viewType);

    public static interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public static interface OnItemLongClickListener {
        void onItemLongClick(View view, int position);
    }

    public static abstract class BaseViewHolder extends RecyclerView.ViewHolder {

        public BaseViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


    public static class ItemViewHolder extends BaseViewHolder {

        private int mPosition;

        public ItemViewHolder(View view) {
            super(view);
        }

        public void bindPosition(int position){
            mPosition = position;
        }

        public void setOnItemClickListener(final OnItemClickListener itemClickListener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (itemClickListener != null) {
                        itemClickListener.onItemClick(view, mPosition);
                    }
                }
            });
        }

        public void setOnItemLongClickListener(final OnItemLongClickListener itemLongClickListener) {
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if (itemLongClickListener != null) {
                        itemLongClickListener.onItemLongClick(view, mPosition);
                    }
                    return true;
                }
            });
        }
    }
}
