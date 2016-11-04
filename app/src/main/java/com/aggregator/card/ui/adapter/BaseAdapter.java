package com.aggregator.card.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by ChenLiang on 16/9/23.
 */

public abstract class BaseAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<T> mDataSet = new ArrayList<>();

    private LayoutInflater mInflater;

    private OnItemClickListener mClickListener;

    private OnItemLongClickListener mLongClickListener;

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

    public void setData(List<T> list) {
        mDataSet.clear();
        mDataSet.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    public T getItem(int position) {
        return mDataSet.get(position);
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        mLongClickListener = onItemLongClickListener;
    }

    public abstract RecyclerView.ItemDecoration getItemDecoration();

    public  interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(View view, int position);
    }

    public class BaseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        public BaseViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
            view.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mClickListener != null) {
                mClickListener.onItemClick(v, getAdapterPosition());
            }
        }

        @Override
        public boolean onLongClick(View v) {
            if (mLongClickListener != null) {
                mLongClickListener.onItemLongClick(v, getAdapterPosition());
            }
            return true;
        }
    }
}
