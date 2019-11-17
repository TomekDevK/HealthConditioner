package com.example.healthcondition;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.view.View;
import java.util.ArrayList;

public class GoToTestTwo extends AppCompatActivity {


    private ArrayList<ArrayList<Float>> data = new ArrayList<ArrayList<Float>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gototesttwo);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            data = (ArrayList<ArrayList<Float>>) bundle.getSerializable("data");
        }

        Button startButton = findViewById(R.id.testTwoStartButton);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View v){
                startActivity(new Intent(GoToTestTwo.this, GoToTestThree.class).putExtra("data",data));
            }
        });

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(GoToTestTwo.this, MainActivity.class));
    }
}
