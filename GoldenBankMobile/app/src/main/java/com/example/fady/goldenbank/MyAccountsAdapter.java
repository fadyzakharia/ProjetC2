package com.example.fady.goldenbank;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class MyAccountsAdapter extends BaseAdapter{
    private Activity activity;
    private List<MyAccountsClass> itemList;
    private LayoutInflater inflater;

    public MyAccountsAdapter(Activity activity, List<MyAccountsClass> itemList){
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
            convertView = inflater.inflate(R.layout.item_account, null);
        }

        TextView accountId  = (TextView) convertView.findViewById(R.id.txtAccountId);
        TextView balance  = (TextView) convertView.findViewById(R.id.txtBalance);
        TextView currency = (TextView) convertView.findViewById(R.id.txtCurrency);

        MyAccountsClass itemAccount = itemList.get(position);

        accountId.setText(String.valueOf(itemAccount.getAccountId()));
        balance.setText(String.valueOf(itemAccount.getBalance()));
        currency.setText(String.valueOf(itemAccount.getCurrency()));

        return convertView;
    }
}
