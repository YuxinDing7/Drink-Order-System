package com.example.drink_order_system;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class BillAdapter extends RecyclerView.Adapter {
    private final ArrayList<Order> mList;
    private final LayoutInflater mLayoutInflater;

    BillAdapter(LayoutInflater layoutInflater, ArrayList<Order> list) {
        this.mList = list;
        mLayoutInflater = layoutInflater;
        System.out.println("BillAdapter used");
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new BillAdapter.BillViewHolder(
                mLayoutInflater.inflate(R.layout.order_item, viewGroup, false));
    }
    private Order getItem(int position) {
        return mList.get(position);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Order target = getItem(position);
        if (holder instanceof BillAdapter.BillViewHolder) {
            ((BillAdapter.BillViewHolder) holder).bindBean(target);
        } else {
            throw new IllegalStateException("Illegal state Exception onBindviewHolder");
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
    private class BillViewHolder extends RecyclerView.ViewHolder {
        private final TextView TV_number;
        private final TextView TV_time;
        private final TextView TV_takeAway;
        private final TextView TV_cost;
        BillViewHolder(View itemView) {
            super(itemView);
            TV_number = (TextView) itemView.findViewById(R.id.textView_number);
            TV_time = (TextView) itemView.findViewById(R.id.textView_time);
            TV_takeAway = (TextView) itemView.findViewById(R.id.textView_takeAway);
            TV_cost = (TextView) itemView.findViewById(R.id.textView_cost);
        }

        void bindBean(final Order bean) {
            TV_number.setText("订单编号："+bean.getOrder_number());
            TV_time.setText(bean.getTime());
            if(bean.getTakeAway().equals("1"))
            {
                TV_takeAway.setText("外带");
            }
            else
            {
                TV_takeAway.setText("堂食");
            }
            TV_cost.setText("总价：￥ "+bean.getCost());
        }
    }
}
