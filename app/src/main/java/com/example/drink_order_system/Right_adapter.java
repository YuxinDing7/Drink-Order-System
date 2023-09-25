package com.example.drink_order_system;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class Right_adapter extends RecyclerView.Adapter{

    private final ArrayList<Drinks> mList;
    private final LayoutInflater mLayoutInflater;
    private MyClickListener mListener;
    Right_adapter(LayoutInflater layoutInflater, ArrayList<Drinks> list) {
        this.mList = list;
        mLayoutInflater = layoutInflater;
        System.out.println("rightAdapter used");
    }

    public void buttonSetOnClick(MyClickListener mListener)
    {
        this.mListener=mListener;
    }

    public interface MyClickListener{
        public void onclick(View v, int position);
    }

    private Drinks getItem(int position) {
        return mList.get(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //绑定xml组件
        return new RightViewHolder(
                mLayoutInflater.inflate(R.layout.list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Drinks target = getItem(position);
        if (holder instanceof RightViewHolder) {
            ((RightViewHolder) holder).bindBean(target);
            ((RightViewHolder) holder).chooseBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener!=null)
                    {
                        mListener.onclick(v, holder.getAdapterPosition());
                    }
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

    private class RightViewHolder extends RecyclerView.ViewHolder {
        private final TextView drinkType;
        private final TextView drinkName;
        private final TextView drinkIntro;
        private final TextView drinkPrice;
        private Button chooseBt;
        private final ImageView drinkImg;

        RightViewHolder(View itemView) {
            super(itemView);
            drinkType = (TextView) itemView.findViewById(R.id.Text_drinkType);
            drinkName = (TextView) itemView.findViewById(R.id.Text_drinkName);
            drinkIntro = (TextView) itemView.findViewById(R.id.Text_drinkIntro);
            drinkPrice = (TextView) itemView.findViewById(R.id.Text_drinkPrice);
            drinkImg = (ImageView) itemView.findViewById(R.id.img_drink);
            chooseBt = (Button) itemView.findViewById(R.id.BT_choose);
        }

        void bindBean(final Drinks bean) {
            drinkName.setText(bean.get_name()+"  #"+(bean.get_number()+1));
            if(bean.get_type()!=null)
            {
                drinkType.setText(bean.get_type());
            }
            else
            {
                drinkType.setText(null);
            }
            drinkImg.setImageResource(bean.getImageResId());
            drinkPrice.setText(String.format("￥ %.0f", bean.get_price()));
            drinkIntro.setText(bean.get_introduction());
        }
    }
}
