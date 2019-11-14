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
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CardsFragment extends Fragment {

    private List<CardsClass> itemList = new ArrayList<>();
    private CardsAdapter myCardsAdapter;
    private ArrayList<CardsClass> cardsList ;

    Connection conx = new Connection();
    final String ip = conx.getIp();

    private ListView listView;

    int[] icons = new int[]{R.drawable.army, R.drawable.transfer_logo, R.drawable.settings};

    public CardsFragment() {   }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sendProductListRequest();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cards, container, false);
        listView =(ListView) view.findViewById(R.id.list);
        return view;


    }
    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);

        Activity a;

        if (context instanceof Activity){
            a=(Activity) context;
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
                        cardsList = new ArrayList<>();

                        for (int i = 0; i < productJsonList.length(); i++) {
                            JSONObject jsonCard = productJsonList.getJSONObject(i);

                            int id = jsonCard.getInt("id");
                            String description = jsonCard.getString("description");
                            String title = jsonCard.getString("name");
                            Double dayLimit = jsonCard.getDouble("dayLimit");
                            Double monthLimit = jsonCard.getDouble("monthlyLimit");

                            CardsClass cc = new CardsClass();

                            cc.setId(id);
                            cc.setDescription(description);
                            cc.setTitle(title);
                            cc.setDayLimit(dayLimit);
                            cc.setMonthlyLimit(monthLimit);

                            if (title.equalsIgnoreCase("Army Card")){
                                cc.setIcon(R.drawable.army);
                            } else if (title.equalsIgnoreCase("Students Card")){
                                cc.setIcon(R.drawable.credit_card);
                            } else {
                                cc.setIcon(R.drawable.card);
                            }

                            cardsList.add(cc);
                        }

                        myCardsAdapter = new CardsAdapter(CardsFragment.this.getActivity(), cardsList);

                        listView.setAdapter(myCardsAdapter);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                CardsClass cc = cardsList.get(position);
                                Intent intent = new Intent(CardsFragment.this.getContext(), CardActivity.class);
                                intent.putExtra("ID", cc.getId());
                                intent.putExtra("card", cc);
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

            String url = "http://" + ip + ":9090/GoldenBankAdmin/webresources/productscards/list";

            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, onSuccess, onError);

            queue.add(stringRequest);
        } catch (Exception e) {
            Log.e("Volley", e.getMessage());
        }
    }
}
