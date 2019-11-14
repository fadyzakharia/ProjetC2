package com.example.fady.goldenbank;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MyBankAdapter extends BaseAdapter {

    private Activity activity;
    private List<MyBankClass> itemList;
    private LayoutInflater inflater;

    public MyBankAdapter(Activity activity, List<MyBankClass> itemList){
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
            convertView = inflater.inflate(R.layout.item_layout, null);
        }

        ImageView icon = (ImageView) convertView.findViewById(R.id.ivIcon);
        TextView title  = (TextView) convertView.findViewById(R.id.txtTitle);

        MyBankClass itemMyBank = itemList.get(position);
        icon.setImageResource(itemMyBank.getIcon());
        title.setText(itemMyBank.getTitle());

        return convertView;
    }
}
