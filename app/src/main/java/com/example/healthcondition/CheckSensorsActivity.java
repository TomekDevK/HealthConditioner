package com.example.healthcondition;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class CheckSensorsActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checksensors);

        Button returnButton;
        Button gravitySensorButton;
        Button linearAccelerometerButton;
        Button rotationVectorSensorButton;
        Button accelerometerButton;
        Button gyroscopeButton;
        final String EXTRA_CONTEXT = "context";

        //MainActivity
        returnButton = findViewById(R.id.returnButton);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View v){
                startActivity(new Intent(CheckSensorsActivity.this, MainActivity.class));
            }
        });

        gravitySensorButton = findViewById(R.id.gravitySensorButton);
        gravitySensorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View v){
                startActivity(new Intent( CheckSensorsActivity.this, SensorActivity.class).putExtra(EXTRA_CONTEXT , 1));
            }
        });

        linearAccelerometerButton = findViewById(R.id.linearAccelerometerButton);
        linearAccelerometerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View v){
                startActivity(new Intent( CheckSensorsActivity.this, SensorActivity.class).putExtra(EXTRA_CONTEXT , 2));
            }
        });

        rotationVectorSensorButton = findViewById(R.id.rotationVectorSensorButton);
        rotationVectorSensorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View v){
                startActivity(new Intent( CheckSensorsActivity.this, SensorActivity.class).putExtra(EXTRA_CONTEXT , 3));
            }
        });

        accelerometerButton = findViewById(R.id.accelerometerButton);
        accelerometerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View v){
                startActivity(new Intent( CheckSensorsActivity.this, SensorActivity.class).putExtra(EXTRA_CONTEXT , 4));
            }
        });

        gyroscopeButton = findViewById(R.id.gyroscopeButton);
        gyroscopeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View v){
                startActivity(new Intent( CheckSensorsActivity.this, SensorActivity.class).putExtra(EXTRA_CONTEXT , 5));
            }
        });



    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(CheckSensorsActivity.this, MainActivity.class));
    }
}
