package com.example.fady.goldenbank;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TransactionsActivity extends AppCompatActivity {

    private long card_id;
    private String date, from;
    private String location, type;
    private double amount;
    private String currency;

    private List<TransactionsClass> transactions = new ArrayList<>();
    private TransactionsAdapter transactionsListAdapter;

    private String ip;

    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ProgressBar bar = (ProgressBar) findViewById(R.id.loadingPanel);
        final ListView lstTransactions = (ListView) findViewById(R.id.lstTransactions);
        final TextView txtNothing= (TextView) findViewById(R.id.nothing);

        Intent intent = getIntent();
        Bundle bndle = intent.getExtras();
        if (bndle != null) {
            from = bndle.getString("from");
            card_id = bndle.getLong("card_id");
        }

        Connection conx = new Connection();
        ip = conx.getIp();

        requestQueue  = Volley.newRequestQueue(this.getApplicationContext());

        if (from.equals("list")){
            final String url = "http://" + ip + ":9090/GoldenBankAdmin/webresources/transactions/cardid/" + Long.toString(card_id);

            JsonArrayRequest getTransactions = new JsonArrayRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            try {
                                if (!response.equals("")){

                                    for(int i=0;i<response.length();i++){
                                        JSONObject objTrans = response.getJSONObject(i);

                                        date = objTrans.getString("date");
                                        date = date.substring(0, date.indexOf("T"));
                                        location = objTrans.getString("location");
                                        amount = objTrans.getDouble("amount");
                                        type = objTrans.getString("type");

                                        JSONObject objCurrency = objTrans.getJSONObject("currencyId");
                                        currency = objCurrency.getString("name");

                                        if (currency.toLowerCase().equals("lebanese lira")){
                                            currency = "LL";
                                        } else if (currency.toLowerCase().equals("dollar")){
                                            currency = "USD";
                                        }

                                        NumberFormat formatter = new DecimalFormat("###,###");

                                        TransactionsClass a = new TransactionsClass(date, type, location, formatter.format(amount), currency);
                                        transactions.add(a);
                                    }

                                    transactionsListAdapter = new TransactionsAdapter(TransactionsActivity.this, transactions);
                                    lstTransactions.setAdapter(transactionsListAdapter);

                                    bar.setVisibility(View.GONE);

                                } else {
                                    Toast.makeText(TransactionsActivity.this, "No Transactions found", Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //Log.d("Volley Error", error.getMessage());
                            bar.setVisibility(View.GONE);
                            txtNothing.setVisibility(View.VISIBLE);
                        }
                    }
            );
            requestQueue.add(getTransactions);

        } else if(from.equals("account")){
            SharedPreferences pref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
            final Long account_id = pref.getLong("account_id", 0);

            final String url = "http://" + ip + ":9090/GoldenBankAdmin/webresources/transactions/accounts1/" + Long.toString(account_id);

            JsonArrayRequest getTransForAccounts = new JsonArrayRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            try {
                                if (!response.equals("")) {

                                    for (int i = 0; i < response.length(); i++) {
                                        JSONObject jsTrans = response.getJSONObject(i);

                                        date = jsTrans.getString("date");
                                        date = date.substring(0, date.indexOf("T"));
                                        location = jsTrans.getString("location");
                                        amount = jsTrans.getDouble("amount");
                                        type = jsTrans.getString("type");

                                        JSONObject objCurrency = jsTrans.getJSONObject("currencyId");

                                        currency = objCurrency.getString("name");
                                        if (currency.toLowerCase().equals("lebanese lira")) {
                                            currency = "LL";
                                        } else if (currency.toLowerCase().equals("dollar")) {
                                            currency = "USD";
                                        }

                                        NumberFormat formatter = new DecimalFormat("###,###");

                                        TransactionsClass a = new TransactionsClass(date, type, location, formatter.format(amount), currency);
                                        transactions.add(a);
                                    }

                                    Collections.sort(transactions);
                                    Collections.reverse(transactions);

                                    transactionsListAdapter = new TransactionsAdapter(TransactionsActivity.this, transactions);
                                    lstTransactions.setAdapter(transactionsListAdapter);

                                    bar.setVisibility(View.GONE);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            String err = (error.getMessage()==null)?" VolleyError ":error.getMessage();
                            Log.e("Volley Error: ", err);
                        }
                    }
            );

            requestQueue.add(getTransForAccounts);


            final String url2 = "http://" + ip + ":9090/GoldenBankAdmin/webresources/transactions/accounts2/" + Long.toString(account_id);

            JsonArrayRequest getTransForAccounts2 = new JsonArrayRequest(Request.Method.GET, url2, null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            try {
                                if (!response.equals("")) {

                                    for (int i = 0; i < response.length(); i++) {
                                        JSONObject jsTrans = response.getJSONObject(i);

                                        date = jsTrans.getString("date");
                                        date = date.substring(0, date.indexOf("T"));
                                        location = jsTrans.getString("location");
                                        amount = jsTrans.getDouble("amount");
                                        type = jsTrans.getString("type");

                                        JSONObject objCurrency = jsTrans.getJSONObject("currencyId");

                                        currency = objCurrency.getString("name");
                                        if (currency.toLowerCase().equals("lebanese lira")) {
                                            currency = "LL";
                                        } else if (currency.toLowerCase().equals("dollar")) {
                                            currency = "USD";
                                        }

                                        NumberFormat formatter = new DecimalFormat("###,###");

                                        TransactionsClass a = new TransactionsClass(date, type, location, formatter.format(amount), currency);
                                        transactions.add(a);
                                    }

                                    Collections.sort(transactions);
                                    Collections.reverse(transactions);

                                    bar.setVisibility(View.GONE);

                                    if (transactionsListAdapter == null) {
                                        transactionsListAdapter = new TransactionsAdapter(TransactionsActivity.this, transactions);
                                        lstTransactions.setAdapter(transactionsListAdapter);
                                    } else {
                                        transactionsListAdapter.notifyDataSetChanged();
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            String err = (error.getMessage()==null)?" VolleyError ":error.getMessage();
                            Log.e("Volley Error: ", err);

                            if (transactions == null){
                                bar.setVisibility(View.GONE);
                                txtNothing.setVisibility(View.VISIBLE);
                            }
                        }
                    }
            );

            requestQueue.add(getTransForAccounts2);
        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), MyCardsActivity.class);
        startActivity(intent);
    }
}