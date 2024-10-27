package com.example.metrostation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONObject;

public class deliveryboy_viewpayment extends AppCompatActivity implements JsonResponse {
    SharedPreferences sh;
    String[] factory, place, phone,date, amount,ass,distance, value;
    public static String stat, aid,otp;
    ListView l1;

    String method,phno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deliveryboy_viewpayment);

        l1 = (ListView) findViewById(R.id.list);

        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) deliveryboy_viewpayment.this;
        String q = "/viewpayment?log_id=" + sh.getString("log_id", "");
        q = q.replace(" ", "%20");
        JR.execute(q);


        // Initialize and assign variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomview);

        // Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.mihome);

        // Perform item selected listener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.recipe:
                        startActivity(new Intent(getApplicationContext(), deliveryboy_viewassign.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.mihome:
                        startActivity(new Intent(getApplicationContext(), deliveryboyaddvehicle.class));
                        overridePendingTransition(0, 0);
                        return true;
//                    case R.id.favourite:
//                        startActivity(new Intent(getApplicationContext(),manageshedule .class));
//                        overridePendingTransition(0, 0);
//                        return true;
                    case R.id.shop:
                        startActivity(new Intent(getApplicationContext(), login.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.add:
                        startActivity(new Intent(getApplicationContext(),deliveryboy_viewpayment .class));
                        overridePendingTransition(0, 0);
                        return true;
//                    case R.id.mihomee:
//                        startActivity(new Intent(getApplicationContext(),viewclass .class));
//                        overridePendingTransition(0, 0);
//                        return true;


                }
                return false;
            }

        });
    }

    @Override
    public void response(JSONObject jo) {
        try {

            String status = jo.getString("status");
            Log.d("pearl", status);






            if (status.equalsIgnoreCase("success")) {
                JSONArray ja1 = (JSONArray) jo.getJSONArray("data");

                factory = new String[ja1.length()];
                place = new String[ja1.length()];
                phone = new String[ja1.length()];
                date = new String[ja1.length()];
                amount = new String[ja1.length()];
                ass = new String[ja1.length()];
                distance = new String[ja1.length()];
                value = new String[ja1.length()];



                for (int i = 0; i < ja1.length(); i++) {

                    factory[i] = ja1.getJSONObject(i).getString("fname");
                    place[i] = ja1.getJSONObject(i).getString("fplace");
                    phone[i] = ja1.getJSONObject(i).getString("fphone");
                    date[i] = ja1.getJSONObject(i).getString("date");
                    amount[i] = ja1.getJSONObject(i).getString("amount");
                    ass[i] = ja1.getJSONObject(i).getString("ass");
                    distance[i] = ja1.getJSONObject(i).getString("total_distance");




                    value[i] = "factory:" + factory[i] + "\nplace:" + place[i] + "\nphone:" + phone[i]+ "\ndate:" + date[i] + "\namount:" + amount[i]+ "\nstatus:" + ass[i] + "\ndistance: (km)" + distance[i];
                }



                ArrayAdapter<String> ar = new ArrayAdapter<String>(getApplicationContext(), R.layout.custtext, value);

                l1.setAdapter(ar);

            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();

        }
    }
    }
