package com.example.displayincidentapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

import cz.msebera.android.httpclient.Header;
import android.content.Intent;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private ListView lvTraffic;
    private ArrayList<TrafficClass> alTraffic;
    private ArrayAdapter<TrafficClass> aaTraffic;
    private AsyncHttpClient client;
    AlertDialog alertDialog;
    private FirebaseFirestore firestore;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        client = new AsyncHttpClient();
        lvTraffic = findViewById(R.id.listViewDetails);
        alTraffic = new ArrayList<TrafficClass>();
        aaTraffic = new TrafficAdapter(this, R.layout.custom_list_view, alTraffic);
        lvTraffic.setAdapter(aaTraffic);

        firestore = FirebaseFirestore.getInstance();


        lvTraffic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                intent.putExtra("lat", alTraffic.get(i).getLat());
                intent.putExtra("lng", alTraffic.get(i).getLng());
                intent.putExtra("title", alTraffic.get(i).getName());
                startActivity(intent);

            }
        });


    }


    @Override
    protected void onResume() {
        super.onResume();

        alTraffic = new ArrayList<TrafficClass>();

        client.addHeader("AccountKey", "cYsiznKuReChgmNVjkun9Q==");
        client.get("http://datamall2.mytransport.sg/ltaodataservice/TrafficIncidents", new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("value");
                    for(int i=0; i<jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String type = jsonObject.getString("Type");
                        double lat = jsonObject.getDouble("Latitude");
                        double lng = jsonObject.getDouble("Longitude");
                        String message = jsonObject.getString("Message");

                        TrafficClass newTraffic = new TrafficClass(type, message, lat, lng);
                        alTraffic.add(newTraffic);

                    }


                } catch (JSONException e) {

                }


                aaTraffic = new TrafficAdapter(getBaseContext(), R.layout.custom_list_view, alTraffic);
                lvTraffic.setAdapter(aaTraffic);
                aaTraffic.notifyDataSetChanged();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.bar_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        // Handle action bar item clicks here.
        int id = item.getItemId();

        if (id == R.id.upload) {
            final CollectionReference colRef = firestore.collection("incidents");

            new AlertDialog.Builder(this).setTitle("Do you want to upload this to Database?")
                    .setMessage("Upload").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    for (TrafficClass trafficClass : alTraffic) {
                        colRef.add(trafficClass);
                    }
                    Toast.makeText(getApplicationContext(), "Send successful", Toast.LENGTH_SHORT).show();
                }
            }).setNegativeButton("No", null).setIcon(android.R.drawable.ic_dialog_alert).show();
            return true;

        }
        return super.onOptionsItemSelected(item);

    }

    }


