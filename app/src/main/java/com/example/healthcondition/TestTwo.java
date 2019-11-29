package com.example.healthcondition;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class TestTwo extends AppCompatActivity  {

    float downx = 0;
    float downy = 0;
    float upx = 0;
    float upy = 0;


    Canvas canvas;
    Paint paint;
    ImageView imageView;
    Button repeatButton,nextButton;

    private ArrayList<ArrayList<Float>> data = new ArrayList<ArrayList<Float>>();
    private int timeStart,tempMilis=0,tempSecond=0,tempMin=0;
    private Timer t = new Timer();
    private boolean timerStart = true;

    private int ctr =0;
    private ArrayList<Float> pointsCtr = new ArrayList<Float>();
    private float [] times = new float[7];

    @Override
    protected void  onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testtwo);

        pointsCtr.add(0,(float)483);
        pointsCtr.add(1,(float)725);
        pointsCtr.add(2,(float)300);
        pointsCtr.add(3,(float)840);
        pointsCtr.add(4,(float)164);
        pointsCtr.add(5,(float)1000);
        pointsCtr.add(6,(float)56);

        repeatButton = findViewById(R.id.repeatButton_test2);
        nextButton = findViewById(R.id.nextButton_test2);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.add(new ArrayList<Float>());
                data.get(3).add(times[0]-timeStart);
                data.get(3).add(times[1]-timeStart);
                data.get(3).add(times[2]-timeStart);
                data.get(3).add(times[3]-timeStart);
                data.get(3).add(times[4]-timeStart);
                data.get(3).add(times[5]-timeStart);
                data.get(3).add(times[6]-timeStart);
                startActivity(new Intent(TestTwo.this, GoToTestThree.class).putExtra("data", data));
            }
        });
        repeatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(TestTwo.this, TestTwo.class).putExtra("data", data));
            }
        });


        nextButton.post(new Runnable() {
            @Override
            public void run() {
                nextButton.setEnabled(false);
            }
        });
        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            data = (ArrayList<ArrayList<Float>>) bundle.getSerializable("data");
        }

        imageView = findViewById(R.id.imageView);
        imageView.post(new Runnable() {
            @Override
            public void run() {
                setBitmap();
            }
        });
        imageView.setOnTouchListener(onTouchListener());
    }

    private void setBitmap(){

        int weidth = imageView.getWidth();
        int height = imageView.getHeight();
        Bitmap bitmap = Bitmap.createBitmap(weidth , height, Bitmap.Config.ARGB_8888);
        Bitmap bm2 = ((BitmapDrawable)getResources().getDrawable(R.drawable.spiral)).getBitmap();
        bitmap = Blend(bitmap,bm2, PorterDuff.Mode.SCREEN);
        canvas = new Canvas(bitmap);
        paint = new Paint();
        paint.setColor(ContextCompat.getColor(TestTwo.this, R.color.grey_font));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(20);

        imageView.setImageBitmap(bitmap);

    }

    public Bitmap Blend(Bitmap topImage1, Bitmap bottomImage1, PorterDuff.Mode Type) {

        Bitmap workingBitmap = Bitmap.createBitmap(topImage1);
        Bitmap topImage = workingBitmap.copy(Bitmap.Config.ARGB_8888, true);

        Bitmap workingBitmap2 = Bitmap.createBitmap(bottomImage1);
        Bitmap bottomImage = workingBitmap2.copy(Bitmap.Config.ARGB_8888, true);

        Rect dest = new Rect(0, 0, bottomImage.getWidth(), bottomImage.getHeight());
        new BitmapFactory.Options().inPreferredConfig = Bitmap.Config.ARGB_8888;
        bottomImage.setHasAlpha(true);
        Canvas canvas = new Canvas(bottomImage);
        Paint paint = new Paint();

        paint.setXfermode(new PorterDuffXfermode(Type));

        paint.setFilterBitmap(true);
        canvas.drawBitmap(topImage, null, dest, paint);
        return bottomImage;
    }

    private View.OnTouchListener onTouchListener(){
        return new View.OnTouchListener(){
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                int action = event.getAction();
                switch (action){
                    case MotionEvent.ACTION_DOWN:
                        if(timerStart){
                            timeStart = (int) System.currentTimeMillis();
                            t.scheduleAtFixedRate(new TimerTask() {

                                @Override
                                public void run() {
                                    runOnUiThread(new Runnable() {

                                        @Override
                                        public void run() {
                                            long timeNow = System.currentTimeMillis();
                                            TextView testThreeTimer =  findViewById(R.id.test_two_timer);
                                            tempMin = (int) (timeNow-timeStart)/60000;
                                            tempSecond = (int)(timeNow-timeStart-tempMin*60000)/1000 ;
                                            tempMilis = (int)(timeNow-timeStart-tempMin*60000-tempSecond*1000) ;
                                            testThreeTimer.setText(String.valueOf(tempMin)+":"+String.valueOf(tempSecond)+":"+String.valueOf(tempMilis));
                                        }

                                    });
                                }

                            }, 0, 100);
                            timerStart=false;
                        }
                        downx = event.getX();
                        downy = event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if(!pointsCtr.isEmpty()){
                            if(Math.abs(event.getX()-pointsCtr.get(0))<50){
                                pointsCtr.remove(0);
                                times[ctr]=System.currentTimeMillis();
                                ctr++;
                            }
                        }
                        if(!(ctr<7)){
                            t.cancel();
                            t.purge();
                            nextButton.setEnabled(true);
                        }
                        upx = event.getX();
                        upy = event.getY();
                        canvas.drawLine(downx, downy, upx, upy, paint);
                        imageView.invalidate();
                        downx = upx;
                        downy = upy;
                        break;
                    case MotionEvent.ACTION_UP:
                        if(ctr<7){
                            Toast toastError = Toast.makeText(TestTwo.this, "Ups.. nie dokładnie rysowałeś/aś. Spróbuj ponownie (powtórz).", Toast.LENGTH_LONG);
                            View toastView =toastError.getView();
                            toastView.setBackgroundColor(Color.TRANSPARENT);
                            toastError.show();
                        }
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        break;
                    default:
                        break;

                }
                return true;
            }
        };
    }



}
