package com.example.healthcondition;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import java.util.ArrayList;

public class Result extends AppCompatActivity {

    private ArrayList<ArrayList<Float>> data = new ArrayList<ArrayList<Float>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            data = (ArrayList<ArrayList<Float>>) bundle.getSerializable("data");
        }

        final Button finishButton = findViewById(R.id.finishButton);

        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Result.this, MainActivity.class));
            }
        });

        finishButton.setEnabled(false);
        final CheckBox chbox1 = findViewById(R.id.chbox1);
        final CheckBox chbox2 = findViewById(R.id.chbox2);

        chbox1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(chbox1.isChecked()) {
                    chbox2.setChecked(false);
                    finishButton.setEnabled(true);
                }else{
                    finishButton.setEnabled(false);
                }
            }
        });
        chbox2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(chbox2.isChecked()) {
                    chbox1.setChecked(false);
                    finishButton.setEnabled(true);
                }else{
                    finishButton.setEnabled(false);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(Result.this, MainActivity.class));
    }
}
