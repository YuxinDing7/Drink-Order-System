package com.example.drink_order_system;

import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BillFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BillFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private String userName;
    private ArrayList<Order> orders = new ArrayList<>();
    private RecyclerView order_listView;
    private View view;
    private LinearLayoutManager llM;

    private LayoutInflater layoutInflater;
    public BillFragment() {
        // Required empty public constructor
    }

    public static BillFragment newInstance(String userName) {
        BillFragment fragment = new BillFragment();
        Bundle args = new Bundle();
        System.out.println("Username in newInstance"+userName);
        args.putString("userName", userName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userName = getArguments().getString("userName");
            System.out.println("USERNAME: "+userName);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        layoutInflater = inflater;
        try
        {
            FileOutputStream fos1;
            FileInputStream fis;
            if(getContext() != null) {
                fis = getContext().openFileInput(userName + "bill.txt");
                Reader in = new InputStreamReader(fis, StandardCharsets.UTF_8);
                LineNumberReader reader = new LineNumberReader(in);
                String s;
                while ((s = reader.readLine()) != null) {
                    System.out.println(s);
                    s = s.replace("/n", "");
                    orders.add(0, new Order(s));
                }
                reader.close();
                in.close();
            }
        }
        catch (IOException e)
        {
            System.out.println("read error");
        }
        view = inflater.inflate(R.layout.fragment_bill, container, false);
        order_listView = (RecyclerView) view.findViewById(R.id.RV_bill);
        llM = new LinearLayoutManager(this.getActivity());
        order_listView.setLayoutManager(llM);
        BillAdapter billAdapter = new BillAdapter(inflater, orders);
        order_listView.setAdapter(billAdapter);
        return view;
    }

    private void refresh() {
        orders.clear();
        try
        {
            FileOutputStream fos1;
            FileInputStream fis;
            if(getContext() != null) {
                fis = getContext().openFileInput(userName + "bill.txt");
                Reader in = new InputStreamReader(fis, StandardCharsets.UTF_8);
                LineNumberReader reader = new LineNumberReader(in);
                String s;
                while ((s = reader.readLine()) != null) {
                    System.out.println(s);
                    s = s.replace("/n", "");
                    orders.add(0, new Order(s));
                }
                reader.close();
                in.close();
            }
        }
        catch (IOException e)
        {
            System.out.println("read error");
        }
        llM = new LinearLayoutManager(this.getActivity());
        order_listView.setLayoutManager(llM);
        BillAdapter billAdapter = new BillAdapter(layoutInflater, orders);
        order_listView.setAdapter(billAdapter);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        refresh();
    }
}