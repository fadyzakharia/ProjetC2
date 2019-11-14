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
import android.widget.ProgressBar;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MyProfileActivity extends AppCompatActivity {

    private List<ProfileClass> itemList = new ArrayList<>();
    private ProfileAdapter profileListAdapter;

    private ListView listView;
    private int client_id;
    private String first_name, last_name, username, password, email, image;
    private Date date_birth;
    private Long phone_number;

    private List<ProfileClass> profileList = new ArrayList<>();

    int[] icons = new int[]{R.drawable.phone, R.drawable.message, R.drawable.home, R.drawable.password};
    String[] line1 = null;
    String[] line2 = new String[]{"Work", "Personal", "Username", "Password"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        final ListView lstData = (ListView) findViewById(R.id.lstProfile);
        final TextView clickedTxt = (TextView) lstData.findViewById(R.id.lblvar);
        final TextView clientname = (TextView) findViewById(R.id.clientname);
        final ProgressBar bar = (ProgressBar) findViewById(R.id.loadingPanel);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SharedPreferences pref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        client_id = pref.getInt("clientId", 0);

        Connection conx = new Connection();
        final String ip = conx.getIp();

        final RequestQueue requestQueue = Volley.newRequestQueue(this.getApplicationContext());

        final String url = "http://" + ip + ":8080/GoldenBankAdmin/webresources/clients/" + Integer.toString(client_id);

        JsonObjectRequest getAccounts = new JsonObjectRequest(Request.Method.GET, url, null,
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

                                line1 = new String[]{Long.toString(phone_number), email, username, "********"};

                                String fn = first_name.substring(0, 1).toUpperCase() + first_name.substring(1);
                                String ln = last_name.substring(0, 1).toUpperCase() + last_name.substring(1);
                                clientname.setText(fn + " " + ln);

                                for (int i = 0; i < icons.length; i++) {
                                    ProfileClass pc = new ProfileClass(icons[i], line1[i], line2[i]);
                                    profileList.add(pc);
                                }

                                profileListAdapter = new ProfileAdapter(MyProfileActivity.this, profileList);
                                lstData.setAdapter(profileListAdapter);

                                bar.setVisibility(View.GONE);

                                lstData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        ProfileClass clicked = profileList.get(position);

                                        Intent intent = new Intent(getApplicationContext(), Pop.class);
                                        intent.putExtra("field", clicked.getLine2());

                                        startActivity(intent);
                                    }
                                });

                            } else {
                                Toast.makeText(MyProfileActivity.this, "No Profile found", Toast.LENGTH_LONG).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley Error", error.getMessage());
                    }
                }
        );

        requestQueue.add(getAccounts);
        clientname.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), MyBankActivity.class);
        startActivity(intent);
    }
}