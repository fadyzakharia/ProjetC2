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

import java.util.ArrayList;
import java.util.List;

public class AccountsActivity extends AppCompatActivity {

    int client_id;
    long account_id;
    double balance, credit, debit;
    String type, currency;

    private List<MyAccountsClass> savingsAccounts = new ArrayList<>();
    private List<MyAccountsClass> depositAccounts = new ArrayList<>();

    private MyAccountsAdapter savingsAccountListAdapter;
    private MyAccountsAdapter depositAccountListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accounts);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final ListView lstDepositAccounts = (ListView) findViewById(R.id.lstDepositAccounts);
        final ListView lstSavingsAccounts = (ListView) findViewById(R.id.lstSavingsAccounts);

        SharedPreferences pref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        client_id = pref.getInt("clientId", 0);

        Connection conx = new Connection();
        final String ip = conx.getIp();

        final RequestQueue requestQueue = Volley.newRequestQueue(this.getApplicationContext());

        final String url = "http://" + ip + ":8080/GoldenBankAdmin/webresources/accounts/" + Integer.toString(client_id);

        JsonArrayRequest getAccounts = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            if (!response.equals("")){

                                for(int i=0;i<response.length();i++){
                                    JSONObject objAccounts = response.getJSONObject(i);

                                    account_id = objAccounts.getLong("id");
                                    credit = objAccounts.getDouble("credit");
                                    debit = objAccounts.getDouble("debit");
                                    type = objAccounts.getString("type");

                                    JSONObject objCurrency = objAccounts.getJSONObject("currencyId");
                                    currency = objCurrency.getString("name");

                                    if (currency.toLowerCase().equals("lebanese lira")){
                                        currency = "LL";
                                    } else if (currency.toLowerCase().equals("dollar")){
                                        currency = "USD";
                                    }

                                    balance = credit - debit;

                                    MyAccountsClass a = new MyAccountsClass(account_id, balance, type, currency);

                                    if (type.trim().toLowerCase().equals("savings")){
                                        savingsAccounts.add(a);
                                    } else if (type.trim().toLowerCase().equals("deposit")){
                                        depositAccounts.add(a);
                                    }

                                }

                                if (!savingsAccounts.isEmpty()){
                                    savingsAccountListAdapter = new MyAccountsAdapter(AccountsActivity.this, savingsAccounts);
                                    lstSavingsAccounts.setAdapter(savingsAccountListAdapter);
                                }

                                if (!depositAccounts.isEmpty()){
                                    depositAccountListAdapter = new MyAccountsAdapter(AccountsActivity.this, depositAccounts);
                                    lstDepositAccounts.setAdapter(depositAccountListAdapter);
                                }

                                findViewById(R.id.loadingPanel).setVisibility(View.GONE);

                                ListUtils.setDynamicHeight(lstSavingsAccounts);
                                ListUtils.setDynamicHeight(lstDepositAccounts);

                                lstDepositAccounts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        MyAccountsClass clickedAcc = depositAccounts.get(position);

                                        SharedPreferences prefs = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = prefs.edit();

                                        editor.putLong("account_id", clickedAcc.getAccountId());
                                        editor.commit();

                                        Intent intent = new Intent(getApplicationContext(), MyCardsActivity.class);
                                        startActivity(intent);
                                    }
                                });

                                lstSavingsAccounts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        MyAccountsClass clickedAcc = savingsAccounts.get(position);

                                        Intent intent = new Intent(getApplicationContext(), MyCardsActivity.class);

                                        SharedPreferences prefs = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = prefs.edit();

                                        editor.putLong("account_id", clickedAcc.getAccountId());
                                        editor.commit();

                                        startActivity(intent);
                                    }
                                });

                            } else {
                                Toast.makeText(AccountsActivity.this, "No Accounts found", Toast.LENGTH_LONG).show();
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

        requestQueue.add(getAccounts);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), MyBankActivity.class);
        startActivity(intent);
    }
}