package com.easygo.jerry.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.easygo.jerry.R;
import com.easygo.jerry.data.bean.Diary;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jerry on 2018/3/19.
 */

public class DiaryAdapter extends RecyclerBaseAdapter<Diary, DiaryAdapter.DiaryViewHolder> {

    @Override
    protected void bindDataToItemView(DiaryViewHolder viewHolder, Diary item) {
        viewHolder.tvDate.setText(item.getDate());
        viewHolder.tvContent.setText(item.getContent());
        viewHolder.tvTitle.setText(item.getTitle());
    }

    @Override
    public DiaryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DiaryViewHolder(inflateItemView(parent, R.layout.item_diary));
    }

    static class DiaryViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title_title)
        TextView tvTitleTitle;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.tv_date_title)
        TextView tvDateTitle;
        @BindView(R.id.tv_date)
        TextView tvDate;

        public DiaryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
