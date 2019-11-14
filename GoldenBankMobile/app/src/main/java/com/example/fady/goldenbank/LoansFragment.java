package com.example.fady.goldenbank;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class LoansFragment extends Fragment {

    private LoansAdapter myLoansAdapter;
    private ListView listView;
    private ArrayList<LoansClass> LoansList;

    Connection conx = new Connection();
    final String ip = conx.getIp();

    Button simulator;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sendProductListRequest();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_loans, container, false);
        listView =(ListView) view.findViewById(R.id.list);

        return view;
    }

    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);

        Activity a;

        if (context instanceof Activity) {
            a = (Activity) context;
        }
    }

    private void sendProductListRequest() {
        try {
            SimpleRequestQueueFactory factory = SimpleRequestQueueFactory.getInstance(super.getContext());
            RequestQueue queue = factory.getQueueInstance();

            com.android.volley.Response.Listener<String> onSuccess = new com.android.volley.Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    try {
                        JSONArray productJsonList = new JSONArray(response);
                        LoansList = new ArrayList<>();

                        for (int i = 0; i < productJsonList.length(); i++) {
                            JSONObject jsonLoan = productJsonList.getJSONObject(i);

                            int id = jsonLoan.getInt("id");
                            double rate = jsonLoan.getDouble("rate");
                            String title = jsonLoan.getString("name");
                            String description = jsonLoan.getString("description");
                            Double amount =jsonLoan.getDouble("amount");

                            LoansClass lc = new LoansClass();

                            lc.setId(id);
                            lc.setRate(rate);
                            lc.setDescription(description);
                            lc.setTitle(title);
                            lc.setAmount(amount);

                            if (title.equalsIgnoreCase("Travel Loan")){
                                lc.setIcon(R.drawable.travel);
                            } else if (title.equalsIgnoreCase("School Loan")) {
                                lc.setIcon(R.drawable.school);
                            } else if (title.equalsIgnoreCase("Housing Loan")) {
                                lc.setIcon(R.drawable.housing);
                            } else {
                                lc.setIcon(R.drawable.loans);
                            }

                            LoansList.add(lc); 
                        }

                        myLoansAdapter = new LoansAdapter(LoansFragment.this.getActivity(), LoansList);
                        listView.setAdapter(myLoansAdapter);

                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                LoansClass lc = LoansList.get(position);

                                Intent intent = new Intent(LoansFragment.this.getContext(), LoanActivity.class);
                                intent.putExtra("loan", lc);
                                startActivity(intent);
                            }

                        });

                        } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };

            com.android.volley.Response.ErrorListener onError = new com.android.volley.Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("Volley", error.getMessage());
                }
            };

            String url = "http://" + ip + ":9090/GoldenBankAdmin/webresources/productsloans/list";

            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, onSuccess, onError);
            queue.add(stringRequest);

        } catch (Exception e) {
            Log.e("Volley", e.getMessage());
        }
    }
}
