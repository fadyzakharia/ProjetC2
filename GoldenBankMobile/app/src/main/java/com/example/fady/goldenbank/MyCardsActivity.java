package com.example.fady.goldenbank;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
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
import java.util.List;

public class MyCardsActivity extends AppCompatActivity {

    private int client_id;
    private long card_number,account_id;
    private double dayLimit, monthlyLimit;
    private String type;

    private List<MyCardsClass> debitCards = new ArrayList<>();
    private List<MyCardsClass> creditCards = new ArrayList<>();

    private MyCardsAdapter debitCardsListAdapter;
    private MyCardsAdapter creditCardsListAdapter;

    NumberFormat formatter = new DecimalFormat("###,###");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cards);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ListView lstDebitCards = (ListView) findViewById(R.id.lstDebitCards);
        final ListView lstCreditCards = (ListView) findViewById(R.id.lstCreditCards);
        final Button btnAccountTransaction = (Button) findViewById(R.id.btnAcountsTransactions);

        SharedPreferences pref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        client_id = pref.getInt("clientId", 0);
        account_id = pref.getLong("account_id", 0);

        Connection conx = new Connection();
        final String ip = conx.getIp();

        final RequestQueue requestQueue = Volley.newRequestQueue(this.getApplicationContext());

        final String url = "http://" + ip + ":8080/GoldenBankAdmin/webresources/clientcard/accounts/" + Long.toString(account_id);

        JsonArrayRequest getCards = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            if (!response.equals("")){

                                for(int i=0;i<response.length();i++){
                                    JSONObject objAccounts = response.getJSONObject(i);

                                    card_number = objAccounts.getLong("id");
                                    dayLimit = objAccounts.getDouble("dayLimit");
                                    monthlyLimit = objAccounts.getDouble("monthlyLimit");
                                    type = objAccounts.getString("type");

                                    MyCardsClass a = new MyCardsClass(card_number, formatter.format(monthlyLimit), formatter.format(dayLimit), type);

                                    if (type.trim().toLowerCase().equals("debit")){
                                        debitCards.add(a);
                                    } else if (type.trim().toLowerCase().equals("credit")){
                                        creditCards.add(a);
                                    }

                                }

                                if (!debitCards.isEmpty()){
                                    debitCardsListAdapter = new MyCardsAdapter(MyCardsActivity.this, debitCards);
                                    lstDebitCards.setAdapter(debitCardsListAdapter);
                                }

                                if (!creditCards.isEmpty()){
                                    creditCardsListAdapter = new MyCardsAdapter(MyCardsActivity.this, creditCards);
                                    lstCreditCards.setAdapter(creditCardsListAdapter);
                                }

                                findViewById(R.id.loadingPanel).setVisibility(View.GONE);

                                ListUtils.setDynamicHeight(lstCreditCards);
                                ListUtils.setDynamicHeight(lstDebitCards);

                                lstCreditCards.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        MyCardsClass clickedCard = creditCards.get(position);

                                        Intent intent = new Intent(getApplicationContext(), TransactionsActivity.class);
                                        intent.putExtra("card_id", clickedCard.getCardNumber());
                                        intent.putExtra("from", "list");

                                        startActivity(intent);
                                    }
                                });

                                lstDebitCards.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        MyCardsClass clickedCard = debitCards.get(position);

                                        Intent intent = new Intent(getApplicationContext(), TransactionsActivity.class);
                                        intent.putExtra("card_id", clickedCard.getCardNumber());
                                        intent.putExtra("from", "list");

                                        startActivity(intent);
                                    }
                                });

                            } else {
                                Toast.makeText(MyCardsActivity.this, "No Cards found", Toast.LENGTH_LONG).show();
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
        requestQueue.add(getCards);

        btnAccountTransaction.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TransactionsActivity.class);
                intent.putExtra("from", "account");

                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), AccountsActivity.class);
        startActivity(intent);
    }
}
