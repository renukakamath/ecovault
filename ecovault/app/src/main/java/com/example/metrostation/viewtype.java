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

public class viewtype extends AppCompatActivity implements JsonResponse, AdapterView.OnItemClickListener {

    SharedPreferences sh;
    String[] type_id,type,image,value,factory_id;
    public static  String tid,fid;
    ListView l1;

    String search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewtype);





        l1=(ListView) findViewById(R.id.list);
        l1.setOnItemClickListener(this);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) viewtype.this;
        String q = "/viewtype?log_id=" +sh.getString("log_id", "")+"&fid="+userhome.fid;
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
//                    case R.id.recipe:
//                        startActivity(new Intent(getApplicationContext(), viewfactory.class));
//                        overridePendingTransition(0, 0);
//                        return true;
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
//                    case R.id.add:
//                        startActivity(new Intent(getApplicationContext(),other .class));
//                        overridePendingTransition(0, 0);
//                        return true;
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
                type_id = new String[ja1.length()];
                type= new String[ja1.length()];
                image= new String[ja1.length()];
                factory_id = new String[ja1.length()];



                value = new String[ja1.length()];





                for (int i = 0; i < ja1.length(); i++) {
                    type_id[i] = ja1.getJSONObject(i).getString("type_id");
                    factory_id[i] = ja1.getJSONObject(i).getString("factory_id");
                    type[i] = ja1.getJSONObject(i).getString("types");

                    image[i] = ja1.getJSONObject(i).getString("image");

                    value[i] =  "type:" + type[i]   ;

                }
//                ArrayAdapter<String> ar = new ArrayAdapter<String>(getApplicationContext(), R.layout.custtext, value);
                Custimage ar = new Custimage(this,type,image);
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
        tid=type_id[i];
        fid=factory_id[i];


        final CharSequence[] items = {"Send request"};

        AlertDialog.Builder builder = new AlertDialog.Builder(viewtype.this);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Send request")) {



                    startActivity(new Intent(getApplicationContext(), user_Sendrequest.class));
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
