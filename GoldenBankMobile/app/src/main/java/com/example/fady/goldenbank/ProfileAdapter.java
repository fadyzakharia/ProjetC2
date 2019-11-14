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

public class ProfileAdapter extends BaseAdapter {

    private Activity activity;
    private List<ProfileClass> itemList;
    private LayoutInflater inflater;

    public ProfileAdapter(Activity activity, List<ProfileClass> itemList){
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
            convertView = inflater.inflate(R.layout.item_profile, null);
        }

        ImageView icon = (ImageView) convertView.findViewById(R.id.imgIcon);
        TextView lblVar  = (TextView) convertView.findViewById(R.id.lblvar);
        TextView lblFix  = (TextView) convertView.findViewById(R.id.lblfix);

        ProfileClass itemMyBank = itemList.get(position);

        icon.setImageResource(itemMyBank.getIcon());
        lblVar.setText(itemMyBank.getLine1());
        lblFix.setText(itemMyBank.getLine2());

        return convertView;
    }
}