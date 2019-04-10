package com.easygo.jerry.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.easygo.jerry.R;
import com.easygo.jerry.data.bean.MenuItem;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jerry on 2018/3/19.
 */

public class MenuAdapter extends RecyclerBaseAdapter<MenuItem, MenuAdapter.MenuViewHolder> {

    @Override
    protected void bindDataToItemView(MenuViewHolder viewHolder, MenuItem item) {
        viewHolder.tvItemDescribe.setText(item.getText());
        viewHolder.imageItemIcon.setImageResource(item.getIconResId());
    }

    @Override
    public MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MenuViewHolder(inflateItemView(parent, R.layout.item_meun_item));
    }

    static class MenuViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image_item_icon)
        ImageView imageItemIcon;
        @BindView(R.id.tv_item_describe)
        TextView tvItemDescribe;
        @BindView(R.id.image_item_arrow)
        ImageView imageItemArrow;

        public MenuViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
