package com.easygo.jerry.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.easygo.jerry.R;
import com.easygo.jerry.data.bean.Poi;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jerry on 2018/3/19.
 */

public class PoiAdapter extends RecyclerBaseAdapter<Poi, PoiAdapter.PoiViewHolder> {

    @Override
    protected void bindDataToItemView(PoiViewHolder viewHolder, Poi item) {
        viewHolder.tvTitle.setText(item.getTitle());
        viewHolder.tvSnippet.setText(item.getSnippet());
    }

    @Override
    public PoiViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PoiViewHolder(inflateItemView(parent, R.layout.item_poi));
    }

    static class PoiViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_snippet)
        TextView tvSnippet;
        @BindView(R.id.ib_navigate)
        ImageView ibNavigate;

        public PoiViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
