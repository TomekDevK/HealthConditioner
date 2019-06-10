package com.example.healthcondition;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


public class SensorActivity extends AppCompatActivity implements SensorEventListener {

    private TextView sensorHeader;
    private Integer layoutPicker;
    private TextView title1,title2,title3,title4,output1View,output2View,output3View,output4View;
    private LinearLayout sensor4;
    private static final String TAG = "executed";
    private SensorManager sensorManager;
    private long lastUpdate;
    private static final String EXTRA_CONTEXT = "context";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);

        Bundle bundle = getIntent().getExtras();
        Button returnButton;

        if (bundle != null) {
            layoutPicker = bundle.getInt(EXTRA_CONTEXT);
        }
        lastUpdate = System.currentTimeMillis();

        sensorHeader = findViewById(R.id.sensorHeader);

        title1 = findViewById(R.id.title1);
        output1View = findViewById(R.id.output1View);

        title2 = findViewById(R.id.title2);
        output2View = findViewById(R.id.output2View);

        title3 = findViewById(R.id.title3);
        output3View = findViewById(R.id.output3View);

        title4 = findViewById(R.id.title4);
        output4View = findViewById(R.id.output4View);

        sensor4 = findViewById(R.id.sensor4);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        switch(layoutPicker) {
            case 1:
                getGravityLayout();
                break;
            case 2:
                getLinearAccelerometerLayout();
                break;
            case 3:
                getRotationVectorLayout();
                break;
            case 4:
                getAccelerometerLayout();
                break;
            case 5:
                getGyroscope();
                break;
            default:
                break;
        }

        returnButton = findViewById(R.id.returnButton);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View v){
                startActivity(new Intent(SensorActivity.this, CheckSensorsActivity.class));
            }
        });

    }

    private void getGravityLayout(){
        sensorHeader.setText(R.string.gravity_sensor);
        title1.setText(R.string.gravity_x);
        title2.setText(R.string.gravity_y);
        title3.setText(R.string.gravity_z);
        if (sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY) != null){
            sensorManager.registerListener(SensorActivity.this,
                    sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY),
                    SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            output1View.setText(R.string.no_sensor_available);
            output1View.setTextColor(ContextCompat.getColor(this ,R.color.red));
            output2View.setText(R.string.no_sensor_available);
            output2View.setTextColor(ContextCompat.getColor(this ,R.color.red));
            output3View.setText(R.string.no_sensor_available);
            output3View.setTextColor(ContextCompat.getColor(this ,R.color.red));
        }
    }

    private void getLinearAccelerometerLayout(){
        sensorHeader.setText(R.string.linear_accelerometer);
        title1.setText(R.string.linear_accelerometer_x);
        title2.setText(R.string.linear_accelerometer_y);
        title3.setText(R.string.linear_accelerometer_z);
        if (sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION) != null){
            sensorManager.registerListener(SensorActivity.this,
                    sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION),
                    SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            output1View.setText(R.string.no_sensor_available);
            output1View.setTextColor(ContextCompat.getColor(this ,R.color.red));
            output2View.setText(R.string.no_sensor_available);
            output2View.setTextColor(ContextCompat.getColor(this ,R.color.red));
            output3View.setText(R.string.no_sensor_available);
            output3View.setTextColor(ContextCompat.getColor(this ,R.color.red));
        }
    }

    private void getRotationVectorLayout(){
        sensorHeader.setText(R.string.rotation_vector_sensor);
        title1.setText(R.string.rotation_vector_x);
        title2.setText(R.string.rotation_vector_y);
        title3.setText(R.string.rotation_vector_z);
        sensor4.setVisibility(View.VISIBLE);
        title4.setText(R.string.rotation_vector_scalar_component);
        if (sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR) != null){
            sensorManager.registerListener(SensorActivity.this,
                    sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR),
                    SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            output1View.setText(R.string.no_sensor_available);
            output1View.setTextColor(ContextCompat.getColor(this ,R.color.red));
            output2View.setText(R.string.no_sensor_available);
            output2View.setTextColor(ContextCompat.getColor(this ,R.color.red));
            output3View.setText(R.string.no_sensor_available);
            output3View.setTextColor(ContextCompat.getColor(this ,R.color.red));
            output4View.setText(R.string.no_sensor_available);
            output4View.setTextColor(ContextCompat.getColor(this ,R.color.red));
        }
    }

    private void getAccelerometerLayout(){
        sensorHeader.setText(R.string.accelerometer);
        title1.setText(R.string.accelerometer_x);
        title2.setText(R.string.accelerometer_y);
        title3.setText(R.string.accelerometer_z);
        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null){
            sensorManager.registerListener(SensorActivity.this,
                    sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                    SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            output1View.setText(R.string.no_sensor_available);
            output1View.setTextColor(ContextCompat.getColor(this ,R.color.red));
            output2View.setText(R.string.no_sensor_available);
            output2View.setTextColor(ContextCompat.getColor(this ,R.color.red));
            output3View.setText(R.string.no_sensor_available);
            output3View.setTextColor(ContextCompat.getColor(this ,R.color.red));
        }
    }

    private void getGyroscope(){
        sensorHeader.setText(R.string.gyroscope);
        title1.setText(R.string.gyroscope_x);
        title2.setText(R.string.gyroscope_y);
        title3.setText(R.string.gyroscope_z);
        if (sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE) != null){
            sensorManager.registerListener(SensorActivity.this,
                    sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE),
                    SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            output1View.setText(R.string.no_sensor_available);
            output1View.setTextColor(ContextCompat.getColor(this ,R.color.red));
            output2View.setText(R.string.no_sensor_available);
            output2View.setTextColor(ContextCompat.getColor(this ,R.color.red));
            output3View.setText(R.string.no_sensor_available);
            output3View.setTextColor(ContextCompat.getColor(this ,R.color.red));
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event){
        long actualTime = System.currentTimeMillis();
        if (actualTime - lastUpdate > 200) {
            lastUpdate = actualTime;
            getSensorValue(event);
        }
    }

    private void getSensorValue(SensorEvent event){
        float [] values = event.values;
        float x = values[0];
        float y = values[1];
        float z = values[2];

        output1View.setText(String.format("%.4f",Math.abs(x)));
        output2View.setText(String.format("%.4f",Math.abs(y)));
        output3View.setText(String.format("%.4f",Math.abs(z)));

        if (event.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR) {
            float r = values[3];
            output4View.setText(String.format("%.4f",Math.abs(r)));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        sensorManager.unregisterListener(this);
    }

}
