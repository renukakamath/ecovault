package com.example.metrostation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONObject;

public class trackdeliveryboy extends AppCompatActivity implements JsonResponse, AdapterView.OnItemClickListener {

    SharedPreferences sh;
    String[] uname, uplace, uphone, types, image, ass, date, uemail, ulatitude, ulongitude, assign_id, value;
    public static String stat, aid;
    ListView l1;

    String search;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trackdeliveryboy);



        l1 = (ListView) findViewById(R.id.list);





        l1 = findViewById(R.id.list);
        l1.setOnItemClickListener(this);


        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) trackdeliveryboy.this;
        String q = "/tracking?log_id=" + sh.getString("log_id", "");
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
                uname = new String[ja1.length()];
                uplace = new String[ja1.length()];
                uphone = new String[ja1.length()];
                types = new String[ja1.length()];
                ass = new String[ja1.length()];
                image = new String[ja1.length()];
                date = new String[ja1.length()];
                uemail = new String[ja1.length()];
                image = new String[ja1.length()];
                ulatitude = new String[ja1.length()];
                ulongitude = new String[ja1.length()];
                assign_id = new String[ja1.length()];
                value = new String[ja1.length()];


                for (int i = 0; i < ja1.length(); i++) {
                    uname[i] = ja1.getJSONObject(i).getString("uname");
                    uplace[i] = ja1.getJSONObject(i).getString("uplace");
                    uphone[i] = ja1.getJSONObject(i).getString("uphone");
                    types[i] = ja1.getJSONObject(i).getString("types");
                    ass[i] = ja1.getJSONObject(i).getString("ass");
                    date[i] = ja1.getJSONObject(i).getString("date");

                    uemail[i] = ja1.getJSONObject(i).getString("uemail");
                    ulatitude[i] = ja1.getJSONObject(i).getString("ulatitude");
                    ulongitude[i] = ja1.getJSONObject(i).getString("ulongitude");
                    assign_id[i] = ja1.getJSONObject(i).getString("assing_id");

                    image[i] = ja1.getJSONObject(i).getString("image");




                    value[i] = "name:" + uname[i] + "\nplace:" + uplace[i] + "\nphone:" + uphone[i] + "\ntype:" + types[i] + "\nstatus:" + ass[i] + "\ndate:" + date[i] ;

                }


                if (ja1.length() > 0) {
                    aid = assign_id[0]; // First Order ID
                    stat = ass[0]; // First Status

                    // Set the statusTextView and deliveryDetailsTextView dynamically
                    TextView statusTextView = findViewById(R.id.statusTextView);
                    TextView deliveryDetailsTextView = findViewById(R.id.deliveryDetailsTextView);

                    // Update the text views with the first delivery's status
                    statusTextView.setText("Delivery Boy Status: " + stat);
                    deliveryDetailsTextView.setText("Order ID: " + aid + "\nDelivery Boy Status: " + stat);

                    // Optionally show a Toast message for debugging
                    Toast.makeText(getApplicationContext(), "Order ID: " + aid, Toast.LENGTH_LONG).show();
                    Toast.makeText(getApplicationContext(), "Status: " + stat, Toast.LENGTH_LONG).show();
                }
//                ArrayAdapter<String> ar = new ArrayAdapter<String>(getApplicationContext(), R.layout.custtext, value);
                Custimage1 ar = new Custimage1(this, uname, uplace, uphone, types, ass, date, image);
                l1.setAdapter(ar);

            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();

        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}