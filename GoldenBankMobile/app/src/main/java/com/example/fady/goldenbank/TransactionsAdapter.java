package com.example.fady.goldenbank;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class TransactionsAdapter extends BaseAdapter {
    private Activity activity;
    private List<TransactionsClass> itemList;
    private LayoutInflater inflater;

    public TransactionsAdapter(Activity activity, List<TransactionsClass> itemList){
        this.activity = activity;
        this.itemList = itemList;
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Object getItem(int position) {
        return itemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(inflater == null){
            inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }if(convertView == null){
            convertView = inflater.inflate(R.layout.item_transaction, null);
        }

        TextView txtDate  = (TextView) convertView.findViewById(R.id.date);
        TextView txtType  = (TextView) convertView.findViewById(R.id.type);
        TextView txtLocation  = (TextView) convertView.findViewById(R.id.location);
        TextView txtAmount  = (TextView) convertView.findViewById(R.id.amount);
        TextView txtCurrency  = (TextView) convertView.findViewById(R.id.currency);

        TransactionsClass trans = itemList.get(position);

        txtDate.setText(String.valueOf(trans.getDate()));
        txtType.setText(String.valueOf(trans.getType()));
        txtLocation.setText(String.valueOf(trans.getLocation()));
        txtAmount.setText(String.valueOf(trans.getAmount()));
        txtCurrency.setText(String.valueOf(trans.getCurrency()));

        return convertView;
    }
}