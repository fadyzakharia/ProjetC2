package com.example.fady.goldenbank;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LoginActivity extends AppCompatActivity {

    int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final TextView txtUsername = (TextView) findViewById(R.id.txtUsername);
        final TextView txtPassword = (TextView) findViewById(R.id.txtPassword);

        Connection conx = new Connection();
        final String ip = conx.getIp();

        Button btnSignIn = (Button) findViewById(R.id.btnSignIn);
        final RequestQueue requestQueue = Volley.newRequestQueue(this.getApplicationContext());

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String username = txtUsername.getText().toString();
                final String password = txtPassword.getText().toString();

                if (username.trim().length() == 0) {
                    txtUsername.setError("Field Required");
                } else if (password.trim().length() == 0) {
                    txtPassword.setError("Field Required");
                } else {

                    final String url = "http://" + ip + ":8080/GoldenBankAdmin/webresources/clients/auth/" + username + password;

                    JsonObjectRequest getAuthRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    try {
                                        if (response != null) {
                                            String clientPass = response.getString("password");
                                            int phoneNo = response.getInt("phoneNumber");

                                            if (clientPass.equals(password)) {
                                                int clientId = response.getInt("id");

                                                SharedPreferences prefs = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                                                SharedPreferences.Editor editor = prefs.edit();

                                                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:MM");
                                                Date date = new Date();
                                                String dateNow = dateFormat.format(date);

                                                editor.putString("last_login", dateNow);
                                                editor.putInt("clientId", clientId);
                                                editor.commit();

                                                Intent mainIntent = new Intent(LoginActivity.this, MyBankActivity.class);
                                                startActivity(mainIntent);

                                                finish();
                                            } else {
                                                if (counter >= 3) {
                                                    //sendSMS(phoneNo);
                                                    Toast.makeText(LoginActivity.this, "You've been blocked, please contact the bank", Toast.LENGTH_LONG).show();
                                                } else {
                                                    counter = counter + 1;
                                                    int left = 3 - counter;
                                                    Toast.makeText(LoginActivity.this, "Wrong Password, Attempts left: " + Integer.toString(left), Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        } else {
                                            Toast.makeText(LoginActivity.this, "Username not found", Toast.LENGTH_LONG).show();
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

                    requestQueue.add(getAuthRequest);
                }
            }
        });
    }

    public void open(View view){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Please contact the bank to fix your problem");

        alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                //Toast.makeText(LoginActivity.this,"You clicked yes button",Toast.LENGTH_LONG).show();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    /*protected void sendSMS(int phoneNo) {
        Log.i("Send SMS", "");
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(Integer.toString(phoneNo), null,
                    "Many invalid access on your online banking account has been identified. Please call support", null, null);
            Toast.makeText(getApplicationContext(), "SMS sent.", Toast.LENGTH_LONG).show();

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "SMS failed, please try again.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }*/
}