package com.easygo.jerry.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.easygo.jerry.R;
import com.easygo.jerry.data.bean.Dynamic;
import com.easygo.jerry.util.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jerry on 2018/3/19.
 */

public class DynamicAdapter extends RecyclerBaseAdapter<Dynamic, DynamicAdapter.DynamicViewHolder> {

    @Override
    protected void bindDataToItemView(DynamicViewHolder viewHolder, Dynamic item) {
        viewHolder.tvContent.setText(item.getContent());
        viewHolder.tvDate.setText(item.getDate());
        viewHolder.tvLocation.setText(item.getLocation());
        viewHolder.tvName.setText(Constants.USER_NAME);
        viewHolder.tvRecommendLevel.setText(item.getRecommend_level());
    }

    @Override
    public DynamicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DynamicViewHolder(inflateItemView(parent, R.layout.item_dynamic));
    }

    static class DynamicViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_head_picture)
        ImageView ivHeadPicture;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.tv_date)
        TextView tvDate;
        @BindView(R.id.tv_recommend_level_title)
        TextView tvRecommendLevelTitle;
        @BindView(R.id.tv_recommend_level)
        TextView tvRecommendLevel;
        @BindView(R.id.tv_location)
        TextView tvLocation;

        public DynamicViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
