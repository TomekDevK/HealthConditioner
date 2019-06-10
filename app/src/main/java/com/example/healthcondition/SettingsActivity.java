package com.example.healthcondition;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Button returnButton;
        Button checkDbConnection;


        checkDbConnection = findViewById(R.id.checkDbConnection);
        checkDbConnection.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v){
                checkDbConnection();
            }
        });


        returnButton = findViewById(R.id.returnButton);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View v){
                startActivity(new Intent(SettingsActivity.this, MainActivity.class));
            }
        });

    }


    private void checkDbConnection() {

    }
}
