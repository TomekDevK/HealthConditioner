package com.example.healthcondition;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class TestFour extends AppCompatActivity {

    private ArrayList<ArrayList<Float>> data = new ArrayList<ArrayList<Float>>();

    private ViewGroup mainLayout;
    private ImageView image;
    private ImageView imageToReach1,imageNotExisting;
    private Button nextButton,repeatButton;
    private int xDelta;
    private int yDelta;
    private int timeStart,tempMilis=0,tempSecond=0,tempMin=0;
    private int completedCircles=0;
    private Timer t = new Timer();
    private boolean timerStart = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testfour);
        mainLayout = (RelativeLayout) findViewById(R.id.main);
        image = findViewById(R.id.image_test4);
        imageToReach1 = findViewById(R.id.image_to_reach1_test4);
        imageNotExisting = findViewById(R.id.image_not_existing_test4);

        nextButton = findViewById(R.id.nextButton_test4);
        repeatButton = findViewById(R.id.repeatButton_test4);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            data = (ArrayList<ArrayList<Float>>) bundle.getSerializable("data");
        }
        data.add(new ArrayList<Float>());
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TestFour.this, GoToTestFive.class).putExtra("data", data));
            }
        });
        repeatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(TestFour.this, TestFour.class).putExtra("data", data));
            }
        });
        nextButton.setEnabled(false);

        image.setOnTouchListener(onTouchListener());
        image.post(new Runnable() {
            @Override
            public void run() {
                generateRandomView();
            }
        });

    }



    private View.OnTouchListener onTouchListener() {
        return new View.OnTouchListener() {

            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent event) {

                final int x = (int) event.getRawX();
                final int y = (int) event.getRawY();

                switch (event.getAction() & MotionEvent.ACTION_MASK) {

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
                                            TextView testThreeTimer =  findViewById(R.id.test_four_timer);
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
                        RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams)
                                view.getLayoutParams();

                        xDelta = x - lParams.leftMargin;
                        yDelta = y - lParams.topMargin;
                        break;

                    case MotionEvent.ACTION_MOVE:
                        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view
                                .getLayoutParams();
                        layoutParams.leftMargin = x - xDelta;
                        layoutParams.topMargin = y - yDelta;
                        layoutParams.rightMargin = 0;
                        layoutParams.bottomMargin = 0;
                        view.setLayoutParams(layoutParams);

                        float currentX = x - xDelta;
                        float currentY = y - yDelta;
                        int xToReach = imageToReach1.getLeft();
                        int yToReach = imageToReach1.getTop();
                        if (currentX > (xToReach - 30) && currentX < (xToReach + 30) && currentY > (yToReach - 30) && currentY < (yToReach + 30))
                        {
                            completedCircles++;
                            if(!(completedCircles<6)){
                                imageToReach1.setVisibility(View.GONE);
                                t.cancel();
                                t.purge();
                                float timeFinish = System.currentTimeMillis();
                                data.get(5).add(timeFinish-timeStart);
                                nextButton.setEnabled(true);
                            }else{
                                generateRandomView();
                            }
                        }
                        break;
                }
                mainLayout.invalidate();
                return true;
            }
        };
    }

    private void generateRandomView (){

        int[]circleArray = new int[2];

        circleArray[0] = getRandom(179,901);
        circleArray[1] = getRandom(250,1500);

        setCirclePosition(circleArray);
    }

    private void setCirclePosition(int [] circleArray){

        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) imageToReach1.getLayoutParams();
        int i;
        for(i=0;i<6;i++){
            layoutParams.leftMargin = 0;
            layoutParams.topMargin = 0;
            layoutParams.rightMargin = circleArray[0];
            layoutParams.bottomMargin = circleArray[1];
            imageToReach1.setLayoutParams(layoutParams);
        }

    }

    public static int getRandom(int min, int max){
        int x = (int)(Math.random()*((max-min)+1))+min;
        return x;
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(TestFour.this,GoToTestFour.class));
    }
}
