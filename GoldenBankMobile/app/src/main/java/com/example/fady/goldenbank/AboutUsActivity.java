package com.example.fady.goldenbank;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

public class AboutUsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView txtDesc = (TextView) findViewById(R.id.description);
        txtDesc.setText("GOLDEN BANK is one of the leading banks in Lebanon and " +
                "an invaluable partner for individuals and companies whose business takes them to other countries in Europe, " +
                "Asia and Africa. With a growing reputation for helping clients manage " +
                "their affairs in challenging environments, our record speaks for itself.");
    }

}

