package com.example.healthcondition;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class StartTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starttest);

        Button returnButton,startTestButton;

        //StartTest
        startTestButton = findViewById(R.id.startTestButton);
        startTestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View v){
                startActivity(new Intent(StartTestActivity.this, TestOne.class));
            }
        });
        returnButton = findViewById(R.id.returnButton);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View v){
                startActivity(new Intent(StartTestActivity.this, MainActivity.class));
            }
        });

    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(StartTestActivity.this, MainActivity.class));
    }
}
