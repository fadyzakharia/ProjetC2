package com.example.fady.goldenbank;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class CardActivity extends AppCompatActivity {
    CardsClass cc = new CardsClass();

    TextView txtDescription, txtTitle, txtCeilingDay, txtCeilingMonth;
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crad);

        txtDescription = (TextView) findViewById(R.id.textDescription);
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        txtCeilingDay = (TextView) findViewById(R.id.txtceilingDay);
        txtCeilingMonth = (TextView) findViewById(R.id.txtceilingMonth);
        img = (ImageView) findViewById(R.id.imageView2);

        cc = (CardsClass) getIntent().getSerializableExtra("card");

        txtDescription.setText(cc.getDescription());
        txtTitle.setText(cc.getTitle());
        txtCeilingDay.setText(Double.toString(cc.getDayLimit()) + "    USD");
        txtCeilingMonth.setText(Double.toString(cc.getMonthlyLimit()) + "    USD");

        if (cc.getTitle().equalsIgnoreCase("Army Card")){
            img.setImageResource(R.drawable.army);
        } else if (cc.getTitle().equalsIgnoreCase("Students Card")){
            img.setImageResource(R.drawable.credit_card);
        } else {
            img.setImageResource(R.drawable.card);
        }
    }
}
