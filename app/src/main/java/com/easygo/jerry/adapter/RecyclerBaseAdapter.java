package com.easygo.jerry.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.easygo.jerry.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jerry on 2018/3/14.
 */

/**
 * 适用于RecyclerView的抽象Adapter，封装了数据集、ViewHolder的创建与绑定过程,简化子类的操作
 *
 * @param <D> 数据集中的类型
 * @param <V> viewHolder类型
 */
public abstract class RecyclerBaseAdapter<D, V extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<V> {

    /**
     * RecyclerView 中的数据集
     */
    protected final List<D> mDataSet = new ArrayList<>();

    /**
     * 点击事件处理回调
     */
    private OnItemClickListener<D> mItemClickListener;

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    protected D getItem(int position) {
        return mDataSet.get(position);
    }

    public void addItems(List<D> items) {
        items.removeAll(mDataSet); // 去除已经存在的数据
        mDataSet.addAll(items); // 更新数据
        notifyDataSetChanged();
    }

    public void clear() {
        mDataSet.clear();
        notifyDataSetChanged();
    }


    /*
     * 绑定数据,主要分为两步,绑定数据与设置每项的点击事件处理
     * @see
     * android.support.v7.widget.RecyclerView.Adapter#onBindViewHolder(android
     * .support.v7.widget.RecyclerView.ViewHolder, int)
     */
    @Override
    public void onBindViewHolder(V holder, int position) {
        final D item = getItem(position);
        bindDataToItemView(holder, item);
        setupItemViewClickListener(holder, item);
    }

    /**
     * temView的点击事件
     *
     * @param holder
     * @param item
     */
    private void setupItemViewClickListener(V holder, final D item) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemClickListener != null) {
                    mItemClickListener.onClick(item);
                }
            }
        });
    }

    public void setOnItemClickListener(OnItemClickListener<D> itemClickListener) {
        this.mItemClickListener = itemClickListener;
    }

    protected View inflateItemView(ViewGroup viewGroup, int layoutId) {
        return LayoutInflater.from(viewGroup.getContext()).inflate(layoutId, viewGroup, false);

    }


    /**
     * 将数据绑定到 ItemView 上
     *
     * @param viewHolder
     * @param item
     */
    protected abstract void bindDataToItemView(V viewHolder, D item);
}
