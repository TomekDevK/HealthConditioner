package com.example.healthcondition;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class GoToTestFour extends AppCompatActivity {

    private ArrayList<ArrayList<Float>> data = new ArrayList<ArrayList<Float>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gototestfour);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            data = (ArrayList<ArrayList<Float>>) bundle.getSerializable("data");
        }

        Button startButton = findViewById(R.id.testFourStartButton);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GoToTestFour.this, GoToTestFive.class).putExtra("data", data));
            }
        });

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(GoToTestFour.this, MainActivity.class));
    }
}
