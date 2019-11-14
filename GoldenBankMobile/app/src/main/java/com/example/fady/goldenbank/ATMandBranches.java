package com.example.fady.goldenbank;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.google.android.gms.maps.GoogleMap.*;

public class ATMandBranches extends FragmentActivity implements OnMapReadyCallback, OnMapLongClickListener {

    private GoogleMap mMap;

    ATMandBranchesClass atmAndBranches;
    private ArrayList<ATMandBranchesClass> atmAndBranchesList;

    TextView txtTitre, txtDes;

    Connection conx = new Connection();
    final String ip = conx.getIp();

    int id, icon;
    double longitude, latitude;
    LatLng destination;
    String name,description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atmand_branches);

        setTitle("Location");

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        txtTitre = (TextView) findViewById(R.id.txtTitre);
        txtDes = (TextView) findViewById(R.id.txtDes);
    }

    @Override
    public void onMapLongClick(LatLng point) {
        mMap.clear();
        mMap.addMarker(new MarkerOptions()
                .position(point)
                .title("You are here")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapLongClickListener(this);
        sendProductListRequest();

        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(33.8843204, 35.4995336);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Beirut"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
//        mMap.clear();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);

    }

    private void sendProductListRequest() {
        try {
            SimpleRequestQueueFactory factory = SimpleRequestQueueFactory.getInstance(this.getApplicationContext());
            RequestQueue queue = factory.getQueueInstance();

            com.android.volley.Response.Listener<String> onSuccess = new com.android.volley.Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    try {
                        JSONArray productJsonList = new JSONArray(response);
                        atmAndBranchesList = new ArrayList<>();

                        for (int i = 0; i < productJsonList.length(); i++) {
                            JSONObject jsonAtmAndBranches = productJsonList.getJSONObject(i);
                            id = jsonAtmAndBranches.getInt("id");

                            name = jsonAtmAndBranches.getString("name");
                            longitude = jsonAtmAndBranches.getDouble("longitude");
                            latitude = jsonAtmAndBranches.getDouble("latitude");
                            description = jsonAtmAndBranches.getString("description");

                            atmAndBranches = new ATMandBranchesClass();

                            atmAndBranches.setId(id);
                            atmAndBranches.setLatitude(latitude);
                            atmAndBranches.setLongitude(longitude);
                            atmAndBranches.setDescription(description);
                            atmAndBranches.setName(name);

                            destination = new LatLng(atmAndBranches.getLatitude(), atmAndBranches.getLongitude());
                            mMap.addMarker(new MarkerOptions().position(destination).title(name));
                            Log.e("test %s", String.valueOf(atmAndBranches.getLatitude()));
                            // atmAndBranches.setIcon(icon);
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(destination, 7));

                            atmAndBranchesList.add(atmAndBranches);
                        }

                        mMap.setOnMarkerClickListener(new OnMarkerClickListener() {
                            @Override
                            public boolean onMarkerClick(Marker marker) {
                                for (int i = 0 ; i < atmAndBranchesList.size(); i++){
                                    String s = marker.getTitle().toString();
                                    if(atmAndBranchesList.get(i).getName().equals(marker.getTitle().toString())){
                                        txtTitre.setText((atmAndBranchesList.get(i).getName()));
                                        txtDes.setText(((atmAndBranchesList.get(i).getDescription())));
                                    }
                                }

                                String id = marker.getId();
                                return false;
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

            String url = "http://" + ip + ":9090/GoldenBankAdmin/webresources/bankbranche/list";

            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, onSuccess, onError);

            queue.add(stringRequest);

        } catch (Exception e) {
            Log.e("Volley", e.getMessage());
        }
    }

}
