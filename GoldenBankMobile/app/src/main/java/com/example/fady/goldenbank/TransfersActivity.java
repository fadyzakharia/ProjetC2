package com.example.fady.goldenbank;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TransfersActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner spAccountsFrom, spAccountsTo, spCurrencies;
    TextView lbl1, lbl2;
    EditText txtAmount;
    Button btnTransfer;

    private List<MyAccountsClass> lstAccountsFrom = new ArrayList<>();
    private List<MyAccountsClass> lstAllAccounts = new ArrayList<>();
    private List<Long> lstAccountsIds = new ArrayList<>();
    private List<JSONObject> lstCurrency = new ArrayList<>();

    private List<JSONObject> jsonList = new ArrayList<>();

    JSONObject jsTransaction = new JSONObject();

    String[] currencies = new String[] { "USD", "LL" };

    private int client_id;
    long account_id;
    double balance, credit, debit;
    String type, currency;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfers);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        txtAmount = (EditText) findViewById(R.id.txtAmount);
        lbl1 = (TextView) findViewById(R.id.lblFrom);
        lbl2 = (TextView) findViewById(R.id.lblTo);

        btnTransfer = (Button) findViewById(R.id.btnTransfer);

        spAccountsFrom = (Spinner) findViewById(R.id.spAccountsFrom);
        spAccountsTo = (Spinner) findViewById(R.id.spAccountsTo);
        spCurrencies = (Spinner) findViewById(R.id.spCurrencies);

        final ProgressBar bar = (ProgressBar) findViewById(R.id.loadingPanel);

        SharedPreferences pref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        client_id = pref.getInt("clientId", 0);

        Connection conx = new Connection();
        final String ip = conx.getIp();

        final RequestQueue requestQueue = Volley.newRequestQueue(this.getApplicationContext());

        final String urlCurr = "http://" + ip + ":9090/GoldenBankAdmin/webresources/currency/list";
        JsonArrayRequest getCurrencyObj = new JsonArrayRequest(Request.Method.GET, urlCurr, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            if (!response.equals("")){

                                for(int i=0;i<response.length();i++) {
                                    JSONObject objCurrency = response.getJSONObject(i);
                                    lstCurrency.add(objCurrency);
                                }

                            } else {
                                //
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
        requestQueue.add(getCurrencyObj);

        final String url = "http://" + ip + ":9090/GoldenBankAdmin/webresources/accounts/" + Integer.toString(client_id);
        JsonArrayRequest getMyAccounts = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            if (!response.equals("")){

                                for(int i=0;i<response.length();i++){
                                    JSONObject objAccounts = response.getJSONObject(i);
                                    jsonList.add(objAccounts);

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
                                    lstAccountsFrom.add(a);
                                }

                                for (MyAccountsClass accounts : lstAccountsFrom){
                                    lstAccountsIds.add(accounts.getAccountId());
                                }

                                ArrayAdapter<Long> adapter_accounts = new ArrayAdapter<Long>(getApplicationContext(),
                                        R.layout.spinner_item_custom, lstAccountsIds);
                                adapter_accounts.setDropDownViewResource(R.layout.spinner_item_custom);

                                spAccountsFrom.setAdapter(adapter_accounts);
                                spAccountsFrom.setOnItemSelectedListener(TransfersActivity.this);

                                spAccountsTo.setAdapter(adapter_accounts);
                                spAccountsFrom.setOnItemSelectedListener(TransfersActivity.this);

                                bar.setVisibility(View.GONE);
                                lbl1.setVisibility(View.VISIBLE);
                                spAccountsFrom.setVisibility(View.VISIBLE);
                                lbl2.setVisibility(View.VISIBLE);
                                spAccountsTo.setVisibility(View.VISIBLE);

                            } else {
                                Toast.makeText(TransfersActivity.this, "No Accounts found", Toast.LENGTH_LONG).show();
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

        requestQueue.add(getMyAccounts);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, currencies);

        spCurrencies.setAdapter(adapter);

        btnTransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String accountFrom = spAccountsFrom.getSelectedItem().toString();
                String accountTo = spAccountsTo.getSelectedItem().toString();
                String spcurrency = spCurrencies.getSelectedItem().toString();
                String amount = txtAmount.getText().toString();

                if (accountFrom.equals(accountTo)){
                    AlertDialog alertDialog = new AlertDialog.Builder(TransfersActivity.this).create();
                    alertDialog.setTitle("Error");
                    alertDialog.setMessage("Transfer to the same account is not possible, Please choose another account to transfer to");

                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();

                } else if (amount.trim().equals("")){
                    txtAmount.setError("Field Required");
                } else {
                    try {

                        JSONObject jsAccountFrom = new JSONObject();
                        JSONObject jsAccountTo = new JSONObject();

                        for(JSONObject account: jsonList) {
                            Long lgId = account.getLong("id");
                            String id = lgId.toString();

                            if (id.equals(accountFrom)) {
                                jsAccountFrom = account;
                            } else if (id.equals(accountTo)){
                                jsAccountTo = account;
                            }
                        }

                        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

                        Date date = new Date();
                        String dateStr = dateFormat.format(date);

                        jsTransaction.put("amount", amount);
                        jsTransaction.put("cardId", null);
                        jsTransaction.put("location", "From Mobile");
                        jsTransaction.put("account1", jsAccountFrom);
                        jsTransaction.put("account2", jsAccountTo);
                        jsTransaction.put("type", "Transfer");
                        jsTransaction.put("date", dateStr);

                        JSONObject currencyObj = null;
                        if (spcurrency.equals("USD")){
                            for (JSONObject curr: lstCurrency){
                                if (curr.get("name").equals("Dollar")){
                                    currencyObj = curr;
                                }
                            }
                        }
                        if (spcurrency.equals("LL")){
                            for (JSONObject curr: lstCurrency){
                                if (curr.get("name").equals("Lebanese Lira")){
                                    currencyObj = curr;
                                }
                            }
                        }

                        jsTransaction.put("currencyId", currencyObj);


                        final String urlPost = "http://" + ip + ":9090/GoldenBankAdmin/webresources/transactions";
                        JsonObjectRequest addTransaction = new JsonObjectRequest (Request.Method.POST, urlPost, jsTransaction,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response2) {
                                        //Do Something
                                    }},

                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Log.d("Volley Error", error.getMessage());
                                    }
                                });

                        requestQueue.add(addTransaction);



                        Double oldDebit = jsAccountFrom.getDouble("debit");
                        Double oldCredit = jsAccountTo.getDouble("credit");

                        JSONObject fromCurr = jsAccountFrom.getJSONObject("currencyId");
                        Integer fromCurrId = fromCurr.getInt("id");
                        JSONObject toCurr = jsAccountTo.getJSONObject("currencyId");
                        Integer toCurrId = toCurr.getInt("id");

                        if (fromCurrId == currencyObj.getInt("id")){
                            Double newDebit = (oldDebit) + Double.valueOf(amount);
                            jsAccountFrom.put("debit", newDebit);
                        } else {
                            if (fromCurrId == 101 && currencyObj.getInt("id") == 102){
                                Double amountd = Double.valueOf(amount) / currencyObj.getDouble("rate");
                                Double newDebit = (oldDebit) + Double.valueOf(amountd);
                                jsAccountFrom.put("debit", newDebit);
                            } else if (fromCurrId == 102 && currencyObj.getInt("id") == 101) {
                                Double amountd = Double.valueOf(amount) * currencyObj.getDouble("rate");
                                Double newDebit = (oldDebit) + Double.valueOf(amountd);
                                jsAccountFrom.put("debit", newDebit);
                            }
                        }

                        if (toCurrId == currencyObj.getInt("id")){
                            Double newCredit = (oldCredit) + Double.valueOf(amount);
                            jsAccountTo.put("credit", newCredit);
                        } else {
                            if (toCurrId == 101 && currencyObj.getInt("id") == 102){
                                Double amountd = Double.valueOf(amount) / currencyObj.getDouble("rate");
                                Double newCredit = (oldCredit) + Double.valueOf(amountd);
                                jsAccountTo.put("credit", newCredit);
                            } else if (toCurrId == 102 && currencyObj.getInt("id") == 101) {
                                Double amountd = Double.valueOf(amount) * currencyObj.getDouble("rate");
                                Double newCredit = (oldCredit) + Double.valueOf(amountd);
                                jsAccountTo.put("credit", newCredit);
                            }
                        }



                        final String urlPut = "http://" + ip + ":9090/GoldenBankAdmin/webresources/accounts/updateaccount";

                        JsonObjectRequest changeAccount1 = new JsonObjectRequest (Request.Method.POST, urlPut, jsAccountFrom,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response2) {
                                        System.out.println("Ana hon");
                                    }},

                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        String err = (error.getMessage()==null)?" VolleyError ":error.getMessage();
                                        Log.e("Volley Error: ", err);
                                    }
                                });

                        requestQueue.add(changeAccount1);


                        JsonObjectRequest changeAccount2 = new JsonObjectRequest (Request.Method.POST, urlPut, jsAccountTo,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response2) {
                                        //Do Something
                                    }},

                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        String err = (error.getMessage()==null)?" VolleyError ":error.getMessage();
                                        Log.e("Volley Error: ", err);
                                    }
                                });

                        requestQueue.add(changeAccount2);

                        Intent intent = new Intent(getApplicationContext(), MyBankActivity.class);
                        startActivity(intent);
                        Toast.makeText(TransfersActivity.this, "Money successfully transfered", Toast.LENGTH_LONG).show();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        parent.setSelection(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
