package com.example.healthcondition;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button settingsButton;
        Button checkSensorsButton;
        Button startTestButton;

        //Settings
        settingsButton = findViewById(R.id.settingsButton);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View v){
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
            }
        });
        //CheckSensors
        checkSensorsButton = findViewById(R.id.checkSensorsButton);
        checkSensorsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View v){
                startActivity(new Intent(MainActivity.this, CheckSensorsActivity.class));
            }
        });
        //StartTest
        startTestButton = findViewById(R.id.startTestButton);
        startTestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View v){
                startActivity(new Intent(MainActivity.this, StartTestActivity.class));
            }
        });

    }
}
