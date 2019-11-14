package com.example.fady.goldenbank;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class LoanSimulator extends AppCompatActivity {

    SeekBar seekBarMonths, seekbarYears;
    double finalMonthlyPayment, rate;
    String input, des;

    Button btnSimulate;
    TextView seekBarMonthValue, seekBarYearValue, txtDescription, txtTitle, txtRate;
    EditText ETDownpayment;
    NumberPicker numberPickerAmount;

    int range = 10000;
    int max_value = 10;

    String values[];
    String[] listnumbers;

    LoansClass lc = new LoansClass();
    int value, amount, years, months, downpayment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_simulator);

        lc = (LoansClass) getIntent().getSerializableExtra("loan");

        btnSimulate = (Button) findViewById(R.id.btnSimulate);
        txtTitle = (TextView) findViewById(R.id.txtTitle);

        seekBarMonths = (SeekBar) findViewById(R.id.seekBarMonths);
        seekbarYears = (SeekBar) findViewById(R.id.seekBarYears);

        seekBarMonthValue = (TextView) findViewById(R.id.txtMonths);
        seekBarYearValue = (TextView) findViewById(R.id.txtYears);

        ETDownpayment = (EditText) findViewById(R.id.ETDownpayment);
        ETDownpayment.setText("0");

        numberPickerAmount = (NumberPicker) findViewById(R.id.amount);
        //numberPickerAmount.setMinValue(50000);

        final int maxAmount = (int) lc.getAmount();

        listnumbers = new String[(maxAmount/1000)];

        int n=0;
        for (int i = 1000; i < maxAmount; i=i+1000) {
            listnumbers[n] = Integer.toString(i);
            n++;
        }
        listnumbers[n] = Integer.toString(maxAmount);

        numberPickerAmount.setMaxValue(listnumbers.length - 1);
        numberPickerAmount.setDisplayedValues(listnumbers);

        txtTitle.setText(lc.getTitle());

        seekbarYears.setMax(25);
        seekBarMonths.setMax(12);

        seekbarYears.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                years = seekBar.getProgress();
                seekBarYearValue.setText("Years : " + String.valueOf(years));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        seekBarMonths.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                months = seekBar.getProgress();
                seekBarMonthValue.setText("Months : " + String.valueOf(months));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        btnSimulate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = numberPickerAmount.getValue();
                String s = listnumbers[index];
                int amount = Integer.parseInt(s);

                String w = ETDownpayment.getText().toString();
                downpayment = Integer.parseInt(w);

                int remainingAmount = amount - downpayment;
                months = months + (years * 12);

                AlertDialog alertDialog = new AlertDialog.Builder(LoanSimulator.this).create();
                alertDialog.setTitle("Simulation Result");

                if (months == 0) {
                    alertDialog.setMessage("Please set the time by scrolling the bar.");
                } else if (downpayment >= amount){
                    alertDialog.setMessage("Your Down Payment should not exceed the required amount.");
                } else {
                    NumberFormat formatter = new DecimalFormat("###,###");
                    Double interest = lc.getRate()/100;
                    int amount_fees = remainingAmount + 135;

                    finalMonthlyPayment = (amount_fees + (amount_fees * interest))/months;

                    alertDialog.setMessage("Following are the result of your simulation based on the information you entered: " +
                                            System.lineSeparator() + System.lineSeparator() +
                                            "Insurance fees:             20     USD" +
                                            System.lineSeparator() +
                                            "File fees:                        10     USD" +
                                            System.lineSeparator() +
                                            "Expert fees:                  100    USD" +
                                            System.lineSeparator() +
                                            "Stamp  fees:                   5       USD" +
                                            System.lineSeparator() +
                                            "Interest:                         " + String.valueOf(lc.getRate()) + "     %" +
                                            System.lineSeparator() + System.lineSeparator() +
                                            "Your monthly payment: "  + formatter.format(finalMonthlyPayment) + " USD"
                    );
                }

                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
        });

    }

}
