package com.example.healthcondition;


import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class TestOne extends AppCompatActivity implements SensorEventListener {


    private AnimationDrawable bendingAnimation;
    private SensorManager sensorManager;
    private boolean measurementFlag = false;
    private TextView testOneHeader;
    private ArrayList<ArrayList<Float>> data = new ArrayList<ArrayList<Float>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testone);


        ImageView imageView = findViewById(R.id.bendingAnimation);
        imageView.setBackgroundResource(R.drawable.bendinganimation);
        bendingAnimation = (AnimationDrawable) imageView.getBackground();
        testOneHeader = findViewById(R.id.testOneHeader);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        data.add(new ArrayList<Float>());
        data.add(new ArrayList<Float>());
        data.add(new ArrayList<Float>());

        getGyroscope();
    }


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

    lastSensorData lastGyroscopeData = new lastSensorData();
    lastSensorData lastAccelerationData = new lastSensorData();

    private void isThresholdExceeded(SensorEvent event) {
        float[] values = event.values;
        float[] oldValues = new float [3];

        oldValues[0] = lastGyroscopeData.getX();
        oldValues[1] = lastGyroscopeData.getY();
        oldValues[2] = lastGyroscopeData.getZ();

        if(
            (Math.abs(oldValues[0]-values[0])+
            Math.abs(oldValues[1]-values[1])+
            Math.abs(oldValues[2]-values[2]))>1

        ){
            measurementFlag = true;
            testOneHeader.setText(R.string.test_one_test_start);
            testOneHeader.setTextColor(ContextCompat.getColor(this ,R.color.red));
            getLinearAccelerometer();
            getGravity();
            unregisterSensor("TYPE_GYROSCOPE");
        }

        lastGyroscopeData.setX(values[0]);
        lastGyroscopeData.setY(values[1]);
        lastGyroscopeData.setZ(values[2]);

    }
    private void isMovementStopped(SensorEvent event) {
        float[] values = event.values;

        if(
                values[1] > 8 &&
                lastAccelerationData.getX() < 0.6 &&
                lastAccelerationData.getY() < 0.6 &&
                lastAccelerationData.getZ() < 0.6
        ){
            measurementFlag = false;
            unregisterSensor("TYPE_LINEAR_ACCELERATION");
            unregisterSensor("TYPE_GRAVITY");
            checkData();
        }

    }

    private void getLinearAccelerometer() {
        if (sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION) != null) {
            sensorManager.registerListener(TestOne.this,
                    sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION),
                    SensorManager.SENSOR_DELAY_GAME);
        }
    }

    private void getGravity() {
        if (sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY) != null) {
            sensorManager.registerListener(TestOne.this,
                    sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY),
                    SensorManager.SENSOR_DELAY_GAME);
        }
    }

    private void unregisterSensor ( String type) {

        if( type.equals("TYPE_LINEAR_ACCELERATION")){
            sensorManager.unregisterListener(TestOne.this,sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION));
        }
        if( type.equals("TYPE_GYROSCOPE")){
            sensorManager.unregisterListener(TestOne.this,sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE));
        }
        if( type.equals("TYPE_GRAVITY")){
            sensorManager.unregisterListener(TestOne.this,sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY));
        }
    }

    private void getGyroscope() {
        if (sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE) != null) {
            sensorManager.registerListener(TestOne.this,
                    sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE),
                    SensorManager.SENSOR_DELAY_GAME);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if(event.sensor.getType() == Sensor.TYPE_GYROSCOPE && !measurementFlag){
            isThresholdExceeded(event);
        }
        if(event.sensor.getType() == Sensor.TYPE_GRAVITY && measurementFlag){
            isMovementStopped(event);
        }
        if(event.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION && measurementFlag){
            checkSensorValue(event);
        }

    }
    private void checkData () {
        testOneHeader.setText(R.string.test_one_data_check);
        testOneHeader.setTextColor(ContextCompat.getColor(this ,R.color.colorNextTwo));


        sendData();

    }
    private void sendData(){
        startActivity(new Intent(TestOne.this, GoToTestTwo.class).putExtra("data", data));
    }
    private void checkSensorValue(SensorEvent event) {
        float[] values = event.values;

        data.get(0).add(Math.abs(values[0]));
        data.get(1).add(Math.abs(values[1]));
        data.get(2).add(Math.abs(values[2]));

        lastAccelerationData.setX(values[0]);
        lastAccelerationData.setZ(values[2]);
        lastAccelerationData.setY(values[1]);

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