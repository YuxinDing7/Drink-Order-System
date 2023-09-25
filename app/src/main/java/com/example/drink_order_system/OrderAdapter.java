package com.example.drink_order_system;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter {
    private final ArrayList<Ordered_drinks> mList;
    private final LayoutInflater mLayoutInflater;
    private OrderAdapter.MyClickListener mListener;
    OrderAdapter(LayoutInflater layoutInflater, ArrayList<Ordered_drinks> list) {
        this.mList = list;
        mLayoutInflater = layoutInflater;
        System.out.println("OrderAdapter used");
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new OrderViewHolder(
                mLayoutInflater.inflate(R.layout.bill_item, viewGroup, false));
    }
    private Ordered_drinks getItem(int position) {
        return mList.get(position);
    }

    public void buttonSetOnClick(OrderAdapter.MyClickListener mListener)
    {
        this.mListener=mListener;
    }

    public interface MyClickListener{
        public void onAddClick(View v, int position);
        public void onSubClick(View v, int position);
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Ordered_drinks target = getItem(position);
        if (holder instanceof OrderViewHolder) {
            ((OrderViewHolder) holder).bindBean(target);
            ((OrderViewHolder) holder).BT_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener!=null)
                    {
                        mListener.onAddClick(v, holder.getAdapterPosition());
                    }
                }
            });
            ((OrderViewHolder) holder).BT_subtract.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener!=null)
                    {
                        mListener.onSubClick(v, holder.getAdapterPosition());
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
    private class OrderViewHolder extends RecyclerView.ViewHolder {
        private final TextView drinkName;
        private final TextView drinkIntro;
        private final TextView drinkPrice;
        private ImageButton addBt;
        private ImageButton subBt;
        private final ImageView drinkImg;
        private TextView drinkNumber;
        private ImageButton BT_add;
        private ImageButton BT_subtract;

        OrderViewHolder(View itemView) {
            super(itemView);
            drinkName = (TextView) itemView.findViewById(R.id.Text_drinkName);
            drinkIntro = (TextView) itemView.findViewById(R.id.Text_drinkIntro);
            drinkPrice = (TextView) itemView.findViewById(R.id.Text_drinkPrice);
            drinkImg = (ImageView) itemView.findViewById(R.id.img_drink);
            addBt = (ImageButton) itemView.findViewById(R.id.button_add);
            subBt = (ImageButton) itemView.findViewById(R.id.button_subtract);
            drinkNumber = (TextView) itemView.findViewById(R.id.textView_drinkNumber);
            BT_add = (ImageButton) itemView.findViewById(R.id.button_add);
            BT_subtract = (ImageButton) itemView.findViewById(R.id.button_subtract);
        }

        void bindBean(final Ordered_drinks bean) {
            drinkName.setText(bean.get_drink().get_name()+"  #"+(bean.get_drink().get_number()+1));
            drinkImg.setImageResource(bean.get_drink().getImageResId());
            if(bean.get_flavor().getSize().equals("中杯"))
            {
                drinkPrice.setText(String.format("￥ %.0f", (bean.get_drink().get_price())*bean.get_drink_number()));
            }
            else if(bean.get_flavor().getSize().equals("小杯"))
            {
                drinkPrice.setText(String.format("￥ %.0f", (bean.get_drink().get_price()-2)*bean.get_drink_number()));
            }
            else
            {
                drinkPrice.setText(String.format("￥ %.0f", (bean.get_drink().get_price()+2)*bean.get_drink_number()));
            }
            drinkIntro.setText(bean.get_flavor().toString());
            drinkNumber.setText(String.valueOf(bean.get_drink_number()));
        }
    }
}
