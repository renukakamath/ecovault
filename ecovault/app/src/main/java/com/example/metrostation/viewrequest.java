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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONObject;

public class viewrequest extends AppCompatActivity  implements JsonResponse {
    SharedPreferences sh;
    String[] quantity, uplace, uphone, types, image, ass, date, uemail, ulatitude, ulongitude, assign_id, value;
    public static String stat, aid;
    ListView l1;

    String search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewrequest);

        l1 = (ListView) findViewById(R.id.list);





        l1 = findViewById(R.id.list);



        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) viewrequest.this;
        String q = "/viewrequest?log_id=" + sh.getString("log_id", "");
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
                        startActivity(new Intent(getApplicationContext(), viewrequest.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.mihome:
                        startActivity(new Intent(getApplicationContext(),userhome .class));
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
                        startActivity(new Intent(getApplicationContext(),trackdeliveryboy .class));
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

                quantity = new String[ja1.length()];
                types = new String[ja1.length()];
                ass = new String[ja1.length()];
                value = new String[ja1.length()];



                for (int i = 0; i < ja1.length(); i++) {

                    quantity[i] = ja1.getJSONObject(i).getString("quantity");
                    types[i] = ja1.getJSONObject(i).getString("types");
                    ass[i] = ja1.getJSONObject(i).getString("status");




                    value[i] = "quantity:" + quantity[i] + "\ntypes:" + types[i] + "\nstatus:" + ass[i];
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