package com.example.healthcondition;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TestOne extends AppCompatActivity implements SensorEventListener {

    private LinearLayout descriptionLinearLayout, testOneTestLinearLayout;
    AnimationDrawable bendingAnimation;
    private SensorManager sensorManager;
    private long lastUpdate;

    private TextView testOneInProgressDescription;

    class lastSensorData {
        private float x;
        private float y;
        private float z;

        public float getX() {
            return x;
        }

        public float getY() {
            return y;
        }

        public float getZ() {
            return z;
        }

        public void setX(float x) {
            this.x = x;
        }

        public void setY(float y) {
            this.y = y;
        }

        public void setZ(float z) {
            this.z = z;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testone);

        Button testOneButton = findViewById(R.id.startTestButton);


        testOneInProgressDescription = findViewById(R.id.testOneInProgressDescription);
        descriptionLinearLayout = findViewById(R.id.testOneDescriptionLayout);
        testOneTestLinearLayout = findViewById(R.id.testOneTestLayout);

        ImageView imageView = findViewById(R.id.bendingAnimation);
        //imageView.setBackgroundResource(R.drawable.bending_animation);
        //bendingAnimation = (AnimationDrawable) imageView.getBackground();

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        lastUpdate = System.currentTimeMillis();

        testOneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                descriptionLinearLayout.setVisibility(View.GONE);
                testOneTestLinearLayout.setVisibility(View.VISIBLE);
                getGyroscope();
                getLinearAccelerometerLayout();
            }
        });

    }

    private void isThresholdExceeded() {

    }

    private void getLinearAccelerometerLayout() {
        if (sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION) != null) {
            sensorManager.registerListener(TestOne.this,
                    sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION),
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    private void getGyroscope() {
        if (sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE) != null) {
            sensorManager.registerListener(TestOne.this,
                    sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE),
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        long actualTime = System.currentTimeMillis();
        if (actualTime - lastUpdate > 200) {
            lastUpdate = actualTime;
            //Flaga ze akwizycja danych się zaczela
            checkSensorValue(event);
        }

    }

    private void checkSensorValue(SensorEvent event) {
        float[] values = event.values;

        if (event.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION) {
            lastSensorData lastAccData = new lastSensorData();
            isThresholdExceeded();
            lastAccData.setX(values[0]);
            lastAccData.setZ(values[2]);
            lastAccData.setY(values[1]);
            testOneInProgressDescription.setTextColor(Color.RED);

        }
        if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            lastSensorData lastGyroscopeData = new lastSensorData();
            lastGyroscopeData.setX(values[0]);
            lastGyroscopeData.setY(values[1]);
            lastGyroscopeData.setZ(values[2]);
            testOneInProgressDescription.setTextColor(Color.YELLOW);
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(TestOne.this, MainActivity.class));
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        bendingAnimation.start();
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