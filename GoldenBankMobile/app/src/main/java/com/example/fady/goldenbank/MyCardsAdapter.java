package com.example.fady.goldenbank;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class MyCardsAdapter extends BaseAdapter {
    private Activity activity;
    private List<MyCardsClass> itemList;
    private LayoutInflater inflater;

    public MyCardsAdapter(Activity activity, List<MyCardsClass> itemList){
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
            convertView = inflater.inflate(R.layout.item_card, null);
        }

        TextView cardNumber  = (TextView) convertView.findViewById(R.id.txtCardNumber);
        TextView monthCeiling  = (TextView) convertView.findViewById(R.id.txtMonthCeiling);
        TextView dayCeiling  = (TextView) convertView.findViewById(R.id.txtDayCeiling);

        MyCardsClass itemCard = itemList.get(position);

        cardNumber.setText(String.valueOf(itemCard.getCardNumber()));
        monthCeiling.setText(String.valueOf(itemCard.getMonthCeiling()));
        dayCeiling.setText(String.valueOf(itemCard.getDayCeiling()));

        return convertView;
    }
}