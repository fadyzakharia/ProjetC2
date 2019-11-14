package com.example.fady.goldenbank;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class Pop extends Activity {

    private String field;
    private Object oldField;

    private int client_id;
    private String first_name, last_name, username, password, notification_type, email, image, notification;
    private String date_birth;
    private Long phone_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_layout);
        setTitle("");

        this.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        TextView lblTitre = (TextView) findViewById(R.id.lblFieldToChange);
        final EditText txtOld = (EditText) findViewById(R.id.txtOld);
        final EditText txtNew = (EditText) findViewById(R.id.txtNew);
        Button btnCancel = (Button) findViewById(R.id.btnCancel);
        Button btnChange = (Button) findViewById(R.id.btnChange);

        SharedPreferences pref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        client_id = pref.getInt("clientId", 0);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels + 100;
        int height = dm.heightPixels + 200;

        getWindow().setLayout((int)(width*.8),(int)(height*.6));

        Intent intent = getIntent();
        Bundle profileBundle = intent.getExtras();
        if (profileBundle != null) {
            field = profileBundle.getString("field");
        }

        if (field.equals("Work")){
            field = "Phone Number";
            //android:inputType="numberSigned"
            txtOld.setInputType(InputType.TYPE_CLASS_NUMBER);
            txtNew.setInputType(InputType.TYPE_CLASS_NUMBER);
            lblTitre.setText(field);
        } else if (field.equals("Personal")){
            field = "Email";
            lblTitre.setText(field);
        } else if (field.equals("Username")){
            field = "Username";
            lblTitre.setText(field);
        } else if (field.equals("Password")){
            field = "Password";
            lblTitre.setText(field);
        }

        btnCancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MyProfileActivity.class);
                startActivity(intent);
            }
        });

        Connection conx = new Connection();
        final String ip = conx.getIp();

        final RequestQueue requestQueue = Volley.newRequestQueue(this.getApplicationContext());

        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String oldText = txtOld.getText().toString();
                final String newText = txtNew.getText().toString();

                if (oldText.trim().length() == 0){
                    txtOld.setError("Field Required");
                } else if (newText.trim().length() == 0){
                    txtNew.setError("Field Required");
                } else {

                    final String urlGet = "http://" + ip + ":9090/GoldenBankAdmin/webresources/clients/" + Integer.toString(client_id);

                    JsonObjectRequest getClient = new JsonObjectRequest(Request.Method.GET, urlGet, null,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    try {
                                        if (!response.equals("")) {

                                            first_name = response.getString("firstName");
                                            last_name = response.getString("lastName");
                                            username = response.getString("username");
                                            password = response.getString("password");
                                            email = response.getString("email");
                                            phone_number = response.getLong("phoneNumber");
                                            date_birth = response.getString("dateOfBirth");

                                            switch (field) {
                                                case "Phone Number":
                                                    oldField = response.get("phoneNumber");
                                                    break;
                                                case "Username":
                                                    oldField = response.get("username");
                                                    break;
                                                case "Password":
                                                    oldField = response.get("password");
                                                    break;
                                                case "Email":
                                                    oldField = response.get("email");
                                                    break;
                                            }

                                            if (!oldField.toString().equals(oldText)){
                                                txtOld.setError("Old Field doesn't match");
                                            } else {
                                                //post request
                                                final String urlPost = "http://" + ip + ":9090/GoldenBankAdmin/webresources/clients/" + Integer.toString(client_id);

                                                switch (field) {
                                                    case "Phone Number":
                                                        response.put("phoneNumber", txtNew.getText().toString());
                                                        response.put("username", username);
                                                        response.put("password", password);
                                                        response.put("email", email);
                                                        break;
                                                    case "Username":
                                                        response.put("username", txtNew.getText().toString());
                                                        response.put("phoneNumber", phone_number);
                                                        response.put("password", password);
                                                        response.put("email", email);
                                                        break;
                                                    case "Password":
                                                        response.put("password", txtNew.getText().toString());
                                                        response.put("username", username);
                                                        response.put("phoneNumber", phone_number);
                                                        response.put("email", email);
                                                        break;
                                                    case "Email":
                                                        response.put("email", txtNew.getText().toString());
                                                        response.put("username", username);
                                                        response.put("phoneNumber", phone_number);
                                                        response.put("password", password);
                                                        break;
                                                }

                                                JsonObjectRequest changeClientRequest = new JsonObjectRequest (Request.Method.PUT, urlPost, response,
                                                        new Response.Listener<JSONObject>() {
                                                            @Override
                                                            public void onResponse(JSONObject response2) {
                                                                //Do Something
                                                            }
                                                        },
                                                        new Response.ErrorListener() {
                                                            @Override
                                                            public void onErrorResponse(VolleyError error) {
                                                                Log.d("Volley Error", error.getMessage());
                                                            }
                                                        });
                                                requestQueue.add(changeClientRequest);

                                                Intent intent = new Intent(getApplicationContext(), MyProfileActivity.class);
                                                startActivity(intent);
                                            }

                                        } else {
                                            Toast.makeText(Pop.this, "No Profile found", Toast.LENGTH_LONG).show();
                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.d("Volley Error", error.getMessage());
                                }
                            }
                    );

                    requestQueue.add(getClient);
                }
            }
        });
    }
}
