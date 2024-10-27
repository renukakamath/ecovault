package com.example.metrostation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONObject;

public class userhome extends AppCompatActivity implements JsonResponse, AdapterView.OnItemClickListener {
    ListView l1;
    SharedPreferences sh;
    String[] factory_id,factory,phone,email,place,image,flongitude,flatitude,value;
    public static  String fid,lati,longi;
    EditText e1;
    String search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userhome);
        e1=(EditText)findViewById(R.id.search);


        l1=(ListView) findViewById(R.id.list);
        l1.setOnItemClickListener(this);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) userhome.this;
        String q = "/viewfactory?log_id=" +sh.getString("log_id", "");
        q = q.replace(" ", "%20");
        JR.execute(q);


        e1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                search=e1.getText().toString();

                JsonReq JR = new JsonReq();
                JR.json_response = (JsonResponse) userhome.this;
                String q = "/Searchfactory?&search=" + search;
                q = q.replace(" ", "%20");
                JR.execute(q);

            }
        });


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

                factory_id = new String[ja1.length()];
                factory= new String[ja1.length()];
                phone= new String[ja1.length()];
                email= new String[ja1.length()];
                place= new String[ja1.length()];
                image= new String[ja1.length()];
                flatitude= new String[ja1.length()];
                flongitude= new String[ja1.length()];



                value = new String[ja1.length()];



                String[] value = new String[ja1.length()];

                for (int i = 0; i < ja1.length(); i++) {
                    factory_id[i] = ja1.getJSONObject(i).getString("factory_id");
                    factory[i] = ja1.getJSONObject(i).getString("fname");
                    phone[i] = ja1.getJSONObject(i).getString("fphone");
                    email[i] = ja1.getJSONObject(i).getString("femail");
                    place[i] = ja1.getJSONObject(i).getString("fplace");
                    image[i] = ja1.getJSONObject(i).getString("image");
                    flatitude[i] = ja1.getJSONObject(i).getString("flatitude");
                    flongitude[i] = ja1.getJSONObject(i).getString("flongitude");


                    value[i] =  "factory:" + factory[i] + "phone:" + phone[i]+"email:" + email[i]+"place:" + place[i]+"image:" + image[i];

                }
//                ArrayAdapter<String> ar = new ArrayAdapter<String>(getApplicationContext(), R.layout.custtext, value);
                CustCatg ar = new CustCatg(this,factory,phone,email,place,image);
                l1.setAdapter(ar);

            }
            else {
                Toast.makeText(getApplicationContext(), "no data", Toast.LENGTH_LONG).show();

            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();

        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        fid=factory_id[i];

         lati=flatitude[i];
        longi=flongitude[i];
        final CharSequence[] items = {"Location","View type"};

        AlertDialog.Builder builder = new AlertDialog.Builder(userhome.this);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Location")) {



                    //                    startActivity(new Intent(getApplicationContext(),UserHotelRoomBooking.class));
                    String url = "http://www.google.com/maps?saddr=" + LocationService.lati + "" + "," + LocationService.logi + "" + "&&daddr=" + lati + "," + longi;

                    Intent in = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(in);



                }

                else if (items[item].equals("View type")) {


                    startActivity(new Intent(getApplicationContext(), viewtype.class));
                }
            }

        });
        builder.show();
    }
    public void onBackPressed()
    {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent b=new Intent(getApplicationContext(),userhome.class);
        startActivity(b);
    }
    }
