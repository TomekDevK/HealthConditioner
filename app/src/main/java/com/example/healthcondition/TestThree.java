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

public class TestThree extends AppCompatActivity {

    private ArrayList<ArrayList<Float>> data = new ArrayList<ArrayList<Float>>();

    private ViewGroup mainLayout;
    private ImageView image;
    private ImageView imageToReach1,imageToReach2,imageToReach3,imageToReach4,imageToReach5,imageToReach6,imageNotExisting;
    private Button nextButton,repeatButton;
    private int xDelta;
    private int yDelta;
    private ImageView [] imageToReachId = new ImageView[6];
    private int timeStart,tempMilis=0,tempSecond=0,tempMin=0;
    private int completedCircles=0;
    private Timer t = new Timer();
    private boolean timerStart = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testthree);
        mainLayout = (RelativeLayout) findViewById(R.id.main);
        image = findViewById(R.id.image);
        imageToReach1 = findViewById(R.id.image_to_reach1);
        imageToReach2 = findViewById(R.id.image_to_reach2);
        imageToReach3 = findViewById(R.id.image_to_reach3);
        imageToReach4 = findViewById(R.id.image_to_reach4);
        imageToReach5 = findViewById(R.id.image_to_reach5);
        imageToReach6 = findViewById(R.id.image_to_reach6);
        imageNotExisting = findViewById(R.id.image_not_existing);

        imageToReachId[0] = imageToReach1;
        imageToReachId[1] = imageToReach2;
        imageToReachId[2] = imageToReach3;
        imageToReachId[3] = imageToReach4;
        imageToReachId[4] = imageToReach5;
        imageToReachId[5] = imageToReach6;

        nextButton = findViewById(R.id.nextButton);
        repeatButton = findViewById(R.id.repeatButton);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            data = (ArrayList<ArrayList<Float>>) bundle.getSerializable("data");
        }
        data.add(new ArrayList<Float>());
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TestThree.this, GoToTestFour.class).putExtra("data", data));
            }
        });
        repeatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(TestThree.this, TestThree.class).putExtra("data", data));
            }
        });
        nextButton.setEnabled(false);

        image.setOnTouchListener(onTouchListener());
        image.post(new Runnable() {
            @Override
            public void run() {
                generateRandomViews();
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
                                        TextView testThreeTimer =  findViewById(R.id.test_three_timer);
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
                        for (int i=0;i<6;i++){
                            int xToReach = imageToReachId[i].getLeft();
                            int yToReach = imageToReachId[i].getTop();
                            if (currentX > (xToReach - 30) && currentX < (xToReach + 30) && currentY > (yToReach - 30) && currentY < (yToReach + 30))
                            {
                                imageToReachId[i].setVisibility(View.GONE);
                                imageToReachId[i]=imageNotExisting;
                                completedCircles++;
                                if(!(completedCircles<6)){
                                    t.cancel();
                                    t.purge();
                                    float timeFinish = System.currentTimeMillis();
                                    data.get(4).add(timeFinish-timeStart);
                                    nextButton.setEnabled(true);
                                }
                            }
                        }
                        break;
                }
                mainLayout.invalidate();
                return true;
            }
        };
    }

    private void generateRandomViews (){

        int[][] circleArray = new int[6][2];
        int ctr =0;

        while(ctr <6){
            int testx = getRandom(179,901);
            int testy = getRandom(250,1500);
            if(!circleOverlay(circleArray,testx,testy)){
                circleArray[ctr][0] = testx;
                circleArray[ctr][1] = testy;
                ctr++;
            }
        }

        setCirclePosition(circleArray);
    }

    private void setCirclePosition(int [][] circleArray){

        RelativeLayout.LayoutParams [] layoutParams = new  RelativeLayout.LayoutParams [6];
        layoutParams[0] = (RelativeLayout.LayoutParams) imageToReach1.getLayoutParams();
        layoutParams[1] = (RelativeLayout.LayoutParams) imageToReach2.getLayoutParams();
        layoutParams[2] = (RelativeLayout.LayoutParams) imageToReach3.getLayoutParams();
        layoutParams[3] = (RelativeLayout.LayoutParams) imageToReach4.getLayoutParams();
        layoutParams[4] = (RelativeLayout.LayoutParams) imageToReach5.getLayoutParams();
        layoutParams[5] = (RelativeLayout.LayoutParams) imageToReach6.getLayoutParams();
        int i;
        for(i=0;i<6;i++){
            layoutParams[i].leftMargin = 0;
            layoutParams[i].topMargin = 0;
            layoutParams[i].rightMargin = circleArray[i][0];
            layoutParams[i].bottomMargin = circleArray[i][1];
            imageToReachId[i].setLayoutParams(layoutParams[i]);
        }

    }

    public boolean circleOverlay(final int[][] array, final int keyX, final int keyY) {
        int i;
        for ( i=0;i<array.length;i++) {
            if(Math.pow(Math.abs(array[i][0]-keyX),2) + Math.pow(Math.abs(array[i][1]-keyY),2) < Math.pow(imageToReach1.getWidth(),2)){
                return true;
            }
        }
        return false;
    }

    public static int getRandom(int min, int max){
        int x = (int)(Math.random()*((max-min)+1))+min;
        return x;
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(TestThree.this,GoToTestThree.class));
    }
}
