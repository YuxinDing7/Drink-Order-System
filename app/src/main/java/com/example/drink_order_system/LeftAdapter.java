package com.example.drink_order_system;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class LeftAdapter extends RecyclerView.Adapter<LeftAdapter.LeftViewHolder> {

    private ArrayList<LeftBean> mList;
    private OnItemClickListener onItemClickListener;

    public LeftAdapter( ArrayList<LeftBean> list) {
        this.mList = list;

    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private LeftBean getItem(int position) {
        return mList.get(position);
    }

    @Override
    public LeftViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.left_list_item,parent,false);

        return new LeftViewHolder(view);

    }

    @Override
    public void onBindViewHolder(LeftViewHolder holder, int position) {
        LeftBean target = getItem(holder.getAdapterPosition());
        if (holder instanceof LeftAdapter.LeftViewHolder) {
            ((LeftAdapter.LeftViewHolder) holder).bindBean(target);
            //绑定监听事件
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Log.v("onClick",holder.getAdapterPosition()+"\t");
                    if (onItemClickListener != null){
                        onItemClickListener.onItemClicked(getItem(holder.getAdapterPosition()).getRightPosition());
                    }
                    for (LeftBean bean:mList){
                        bean.setSelect(false);
                    }
                    getItem(holder.getAdapterPosition()).setSelect(true);
                    notifyDataSetChanged();
                }
            });
        } else {
            throw new IllegalStateException("Illegal state Exception onBindviewHolder");
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class LeftViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvTitle;
        //private View cl_left_item;

        LeftViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.TV_drink_type);
        }

        void bindBean(LeftBean target)
        {
            tvTitle.setText(target.getTitle());
            if (target.isSelect()){
                itemView.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.gray));
            } else {
                itemView.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.white));
            }
        }
    }

    interface OnItemClickListener {
        void onItemClicked(int rightPosition);
    }

    public void setCurrentPosition(int rightPosition){
        for (int i = 0; i < mList.size(); i++){
            LeftBean bean = mList.get(i);
            if(i < mList.size()-1)
            {
                LeftBean nextBean = mList.get(i+1);
                if (bean.getRightPosition() <= rightPosition && rightPosition < nextBean.getRightPosition()){
                    bean.setSelect(true);
                } else {
                    bean.setSelect(false);
                }
            }
            else
            {
                if(bean.getRightPosition() <= rightPosition)
                {
                    bean.setSelect(true);
                }
                else {
                    bean.setSelect(false);
                }
            }
        }
        notifyDataSetChanged();
    }

    public String getCurrentTitle(){
        String currentTitle = "";
        for (LeftBean bean:mList){
            if (bean.isSelect()){
                currentTitle = bean.getTitle();
                break;
            }
        }
        return currentTitle;
    }
}