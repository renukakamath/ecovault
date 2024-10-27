package com.example.metrostation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageView;

public class signup extends AppCompatActivity {

    ImageView b1,b2;
    public static String user,pass,logid,usertype;
    SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        b1=(ImageView) findViewById(R.id.sendcomplaint);
        b2=(ImageView) findViewById(R.id.logout);
        startService(new Intent(getApplicationContext(),LocationService.class));


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),userregistration.class));
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),devilieryboyregistration.class));
            }
        });
    }
}