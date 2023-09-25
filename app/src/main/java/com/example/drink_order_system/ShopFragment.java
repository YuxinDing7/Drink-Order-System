package com.example.drink_order_system;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class ShopFragment extends Fragment {
    private RecyclerView bill_listView;
    private LinearLayoutManager llM;

    private LayoutInflater layoutInflater;
    private Context mContext;
    private TextView TV_cost;
    private EditText ET_people;
    private EditText ET_table;
    private CheckBox CB_takeAway;
    private ImageButton BT_delete;
    private Button BT_buy;
    private int drinkCost;
    private float serviceCost;

    private AlertDialog buyDialog = null;
    private AlertDialog.Builder builder = null;
    private View view_buy;
    private View view;
    private String userName;
    public ShopFragment() {
        // Required empty public constructor
    }
    public static ShopFragment newInstance(String userName) {
        ShopFragment myFragment = new ShopFragment();
        Bundle args = new Bundle();
        args.putString("userName", userName);
        myFragment.setArguments(args);
        return myFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userName = getArguments().getString("userName");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContext = getActivity();
        layoutInflater = inflater;
        view = inflater.inflate(R.layout.fragment_shop, container, false);
        bill_listView = (RecyclerView) view.findViewById(R.id.RV_bill);
        llM = new LinearLayoutManager(this.getActivity());
        bill_listView.setLayoutManager(llM);
        OrderAdapter orderAdapter = new OrderAdapter(inflater, Ordered_drinks.getOrdered_array());
        orderAdapter.buttonSetOnClick((new OrderAdapter.MyClickListener() {
            @Override
            public void onAddClick(View v, int position) {
                Ordered_drinks.addDrink(position);
                refresh();
            }

            public void onSubClick(View v, int position){
                Ordered_drinks.subtractDrink(position);
                refresh();
            }
        }));
        bill_listView.setAdapter(orderAdapter);
        TV_cost = view.findViewById(R.id.textView_cost);
        ET_people = view.findViewById(R.id.editText_people);
        ET_table = view.findViewById(R.id.editText_table);
        CB_takeAway = view.findViewById(R.id.checkBox);
        BT_delete = view.findViewById(R.id.button_delete);
        BT_buy = view.findViewById(R.id.button_buy);
        drinkCost = Ordered_drinks.getDrinkCost();
        serviceCost = 0.2f;
        if(!ET_people.getText().equals(""))
        {
            serviceCost = 0.2f*Integer.parseInt((String.valueOf(ET_people.getText())));
        }
        TV_cost.setText(String.format("饮料费：￥ %d \n服务费：￥ %.1f", drinkCost, serviceCost));
        ET_people.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().equals(""))
                {
                    s.append("1");
                }
                if(s.toString().equals("0"))
                {
                    s.replace(0, 1, "1");
                }
                serviceCost = 0.2f;
                if(!ET_people.getText().equals(""))
                {
                    serviceCost = 0.2f*Integer.parseInt((String.valueOf(ET_people.getText())));
                }
                TV_cost.setText(String.format("饮料费：￥ %d \n服务费：￥ %.1f", drinkCost, serviceCost));
            }
        });

        ET_people.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().equals(""))
                {
                    s.append("1");
                }
                if(s.toString().equals("0"))
                {
                    s.replace(0, 1, "1");
                }
                serviceCost = 0.2f;
                if(!ET_people.getText().equals(""))
                {
                    serviceCost = 0.2f*Integer.parseInt((String.valueOf(ET_people.getText())));
                }
                TV_cost.setText(String.format("饮料费：￥ %d \n服务费：￥ %.1f", drinkCost, serviceCost));
            }
        });

        ET_table.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().equals(""))
                {
                    s.append("1");
                }
                if(Integer.valueOf(s.toString())<=0)
                {
                    s.replace(0, 1, "1");
                }
                if(Integer.valueOf(s.toString())>30)
                {
                    s.replace(0, s.length(), "30");
                }
            }
        });

        BT_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "您已清空购物车！", Toast.LENGTH_SHORT).show();
                Ordered_drinks.clearOrdered_array();
                refresh();
            }
        });

        builder = new AlertDialog.Builder(this.getActivity());
        view_buy = inflater.inflate(R.layout.dialogue_buy, null, false);
        builder.setView(view_buy);
        builder.setCancelable(false);
        buyDialog = builder.create();
        view_buy.findViewById(R.id.button_quit).setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  buyDialog.dismiss();
              }
          }
        );
        view_buy.findViewById(R.id.button_bought).setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   Account temp = new Account(userName, mContext);
                   String takeAway = "0";
                   if(CB_takeAway.isChecked())
                   {
                       takeAway = "1";
                   }
                   String cost = String.format("%.1f",drinkCost + serviceCost);
                   temp.saveBill(takeAway, cost);
                   Ordered_drinks.clearOrdered_array();
                   refresh();
                   buyDialog.dismiss();
               }
           }
        );

        BT_buy.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v){
                if(drinkCost==0)
                {
                    Toast.makeText(getContext(), "请先选购饮品再结账！", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    buyDialog.show();
                    if(view_buy!=null) {
                        TextView TV_allCost = view_buy.findViewById(R.id.textView_allCost);
                        {
                            TV_allCost.setText(String.format("饮料费：￥ %d\n服务费：￥ %.1f\n总价：￥ %.1f\n请扫描以下二维码进行支付。"
                                    ,drinkCost, serviceCost, drinkCost+serviceCost));
                        }
                    }
                }
            }

        });
        return view;
    }



    private void refresh() {
        OrderAdapter orderAdapter = new OrderAdapter(layoutInflater, Ordered_drinks.getOrdered_array());
        orderAdapter.buttonSetOnClick((new OrderAdapter.MyClickListener() {
            @Override
            public void onAddClick(View v, int position) {
                Ordered_drinks.addDrink(position);
                refresh();
            }

            public void onSubClick(View v, int position){
                Ordered_drinks.subtractDrink(position);
                refresh();
            }
        }));
        bill_listView.setAdapter(orderAdapter);
        drinkCost = Ordered_drinks.getDrinkCost();
        serviceCost = 0.2f;
        ET_people.setText("1");
        if(!ET_people.getText().equals(""))
        {
            serviceCost = 0.2f*Integer.parseInt((String.valueOf(ET_people.getText())));
        }
        TV_cost.setText(String.format("饮料费：￥ %d \n服务费：￥ %.1f", drinkCost, serviceCost));
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        refresh();
    }
}