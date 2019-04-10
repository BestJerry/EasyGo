package com.easygo.jerry.adapter;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.easygo.jerry.R;
import com.easygo.jerry.data.bean.TripPlan;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jerry on 2018/3/19.
 */

public class TripPlanAdapter extends RecyclerBaseAdapter<TripPlan, TripPlanAdapter.TripPlanViewHolder> {

    @BindView(R.id.tv_title_title)
    TextView tvTitleTitle;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_destination_title)
    TextView tvDestinationTitle;
    @BindView(R.id.tv_destination)
    TextView tvDestination;
    @BindView(R.id.tv_date_title)
    TextView tvDateTitle;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.tv_complete_state)
    TextView tvCompleteState;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void bindDataToItemView(TripPlanViewHolder viewHolder, TripPlan item) {
        viewHolder.tvDate.setText(item.getAdd_time());
        viewHolder.tvTitle.setText(item.getTitle());
        viewHolder.tvCompleteState.setText(item.getIs_complete() == 0 ? "待完成" : "已完成");
        viewHolder.tvCompleteState.setTextColor(item.getIs_complete() == 0 ? R.color.yellow : R.color.blue);
        viewHolder.tvDestination.setText(item.getLocation());
    }

    @Override
    public TripPlanViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TripPlanViewHolder(inflateItemView(parent, R.layout.item_trip_plan));
    }

    static class TripPlanViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_title_title)
        TextView tvTitleTitle;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_destination_title)
        TextView tvDestinationTitle;
        @BindView(R.id.tv_destination)
        TextView tvDestination;
        @BindView(R.id.tv_date_title)
        TextView tvDateTitle;
        @BindView(R.id.tv_date)
        TextView tvDate;
        @BindView(R.id.tv_complete_state)
        TextView tvCompleteState;

        public TripPlanViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
