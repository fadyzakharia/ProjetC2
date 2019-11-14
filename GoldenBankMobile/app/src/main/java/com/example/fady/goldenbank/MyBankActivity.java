package com.example.fady.goldenbank;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MyBankActivity extends HomeActivity {

    private List<MyBankClass> itemList = new ArrayList<>();
    private MyBankAdapter myBankListAdapter;

    private ListView listView;
    private ImageView imgLogout;

    String[] titles = new String[]{"My Accounts", "Transfers", "My Profile"};
    int[] icons = new int[]{R.drawable.accounts, R.drawable.transfer_logo, R.drawable.settings};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bank);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imgLogout = (ImageView) findViewById(R.id.logoutIcon);

        listView = (ListView) findViewById(R.id.list);
        myBankListAdapter = new MyBankAdapter(this, itemList);
        listView.setAdapter(myBankListAdapter);

        for (int i = 0; i < titles.length; i++) {
            MyBankClass item = new MyBankClass();
            item.setIcon(icons[i]);
            item.setTitle(titles[i]);
            itemList.add(item);
        }

        imgLogout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SharedPreferences prefs = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();

                editor.clear();
                editor.commit();

                Intent i = new Intent(MyBankActivity.this, HomeActivity.class);
                startActivity(i);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                MyBankClass clickedItem = itemList.get(position);
                Intent i = null;
                String title = clickedItem.getTitle().trim().toLowerCase().toString();

                if (title.equals("my accounts")) {
                    i = new Intent(MyBankActivity.this, AccountsActivity.class);
                } else if (title.equals("my profile")) {
                    i = new Intent(MyBankActivity.this, MyProfileActivity.class);
                } else if (title.equals("transfers")) {
                    i = new Intent(MyBankActivity.this, TransfersActivity.class);
                }

                startActivity(i);
            }
        });
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(intent);
    }
}