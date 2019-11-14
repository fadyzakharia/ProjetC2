package com.example.fady.goldenbank;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class LoanActivity extends AppCompatActivity {

    LoansClass lc = new LoansClass();
    TextView txtDescription, txtTitle;
    ImageView img;
    Button simulator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan);

        txtDescription = (TextView) findViewById(R.id.textDescription);
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        simulator = (Button) findViewById(R.id.simulator);
        img = (ImageView) findViewById(R.id.imageView2);

        lc = (LoansClass) getIntent().getSerializableExtra("loan");

        txtDescription.setText(lc.getDescription());
        txtTitle.setText(lc.getTitle());

        if (lc.getTitle().equalsIgnoreCase("Travel Loan")){
            img.setImageResource(R.drawable.travel);
        } else if (lc.getTitle().equalsIgnoreCase("School Loan")) {
            img.setImageResource(R.drawable.school);
        } else if (lc.getTitle().equalsIgnoreCase("Housing Loan")) {
            img.setImageResource(R.drawable.housing);
        } else {
            img.setImageResource(R.drawable.loans);
        }

        simulator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoanActivity.this, LoanSimulator.class);
                intent.putExtra("loan", lc);
                startActivity(intent);
            }
        });
    }
}
