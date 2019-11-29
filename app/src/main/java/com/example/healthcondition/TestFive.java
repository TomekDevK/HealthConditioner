package com.example.healthcondition;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;

public class TestFive extends AppCompatActivity {

    private ArrayList<ArrayList<Float>> data = new ArrayList<ArrayList<Float>>();

    private int [] shapeArray = new int[3];
    private int [] colorArray = new int[10];
    private String [] shapeNameArray = new String[3];
    private String [] colorNameArray = new String[10];

    private int [] testShape = new int [3];
    private int [] testColor = new int [3];
    private int answerNumber;
    private int [] answerColors = new int [3];
    private ImageView customShape;
    private TextView answerA, answerB, answerC;
    private LinearLayout buttonA, buttonB, buttonC;
    private float correctAnswers = 0;
    private float wrongAnswers = 0;
    private int questionsCtr = 0;
    private float timeAnswers = 0;
    private float startTime,endTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testfive);
        answerA = findViewById(R.id.testFiveAnswerA);
        answerB = findViewById(R.id.testFiveAnswerB);
        answerC = findViewById(R.id.testFiveAnswerC);

        buttonA = findViewById(R.id.testFiveButtonA);
        buttonB = findViewById(R.id.testFiveButtonB);
        buttonC = findViewById(R.id.testFiveButtonC);

        customShape = findViewById(R.id.testFiveImage);
        createShapeArrays();
        createColorArrays();

        generateTest();

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            data = (ArrayList<ArrayList<Float>>) bundle.getSerializable("data");
        }

        buttonA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(answerNumber == 0){
                    correctAnswers++;
                }else{
                    wrongAnswers++;
                }
                if(questionsCtr >4){
                    endTime = System.nanoTime();
                    sendDate();
                }else{
                    generateTest();
                }
            }
        });
        buttonB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(answerNumber == 1){
                    correctAnswers++;
                }else{
                    wrongAnswers++;
                }
                if(questionsCtr >4){
                    endTime = System.nanoTime();
                    sendDate();
                }else{
                    generateTest();
                }
            }
        });
        buttonC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(answerNumber == 2){
                    correctAnswers++;
                }else{
                    wrongAnswers++;
                }
                if(questionsCtr >4){
                    endTime = System.currentTimeMillis();
                    sendDate();
                }else{
                    generateTest();
                }
            }
        });
        startTime = System.currentTimeMillis();
    }

    private void createShapeArrays (){
        shapeArray[0]=getResources().getIdentifier("customcircle","drawable", TestFive.this.getPackageName());
        shapeArray[1]=getResources().getIdentifier("customsquare","drawable", TestFive.this.getPackageName());
        shapeArray[2]=getResources().getIdentifier("customtriangle","drawable", TestFive.this.getPackageName());
        shapeNameArray[0]="okrąg";
        shapeNameArray[1]="kwadrat";
        shapeNameArray[2]="trójkąt";
    }

    private void  createColorArrays (){
        colorArray[0]=getResources().getIdentifier("red1","color", TestFive.this.getPackageName());
        colorArray[1]=getResources().getIdentifier("blue","color", TestFive.this.getPackageName());
        colorArray[2]=getResources().getIdentifier("green","color", TestFive.this.getPackageName());
        colorArray[3]=getResources().getIdentifier("yellow","color", TestFive.this.getPackageName());
        colorArray[4]=getResources().getIdentifier("orange","color", TestFive.this.getPackageName());
        colorArray[5]=getResources().getIdentifier("white","color", TestFive.this.getPackageName());
        colorArray[6]=getResources().getIdentifier("black","color", TestFive.this.getPackageName());
        colorArray[7]=getResources().getIdentifier("brown","color", TestFive.this.getPackageName());
        colorArray[8]=getResources().getIdentifier("purple","color", TestFive.this.getPackageName());
        colorArray[9]=getResources().getIdentifier("pink","color", TestFive.this.getPackageName());
        colorNameArray[0]="Czerwony";
        colorNameArray[1]="Niebieski";
        colorNameArray[2]="Zielony";
        colorNameArray[3]="Żółty";
        colorNameArray[4]="Pomarańczowy";
        colorNameArray[5]="Biały";
        colorNameArray[6]="Czarny";
        colorNameArray[7]="Brązowy";
        colorNameArray[8]="Fioletowy";
        colorNameArray[9]="Różowy";

    }
    private void generateTest () {
        questionsCtr++;
        generateColorsShapes();
        customShape.setImageResource(shapeArray[testShape[answerNumber]]);
        customShape.setColorFilter(ContextCompat.getColor(TestFive.this, colorArray[testColor[answerNumber]]));

        setTexts();
    }
    private void generateColorsShapes () {
        int i=0;
        while(i<3 ){
            int shapeNumber = getRandom(0,2);
            int colorNumber = getRandom(0,9);
            if( !contains(testColor,colorNumber) ){
                testShape[i] = shapeNumber;
                testColor[i] = colorNumber;
                i++;
            }else{
                if( !contains(testShape,shapeNumber)){
                    testShape[i] = shapeNumber;
                    testColor[i] = colorNumber;
                    i++;
                }
            }
        }
        answerNumber = getRandom(0,2);
        i=0;
        while(i<3){
            int colorNumber = getRandom(0,9);
            if(colorNumber != testColor[answerNumber]){
                answerColors[i]=colorNumber;
                i++;
            }
        }
    }

    public boolean contains(final int[] array, final int key) {
        for (final int i : array) {
            if (i == key) {
                return true;
            }
        }
        return false;
    }

    public static int getRandom(int min, int max){
        int x = (int)(Math.random()*((max-min)+1))+min;
        return x;
    }

    private void setTexts(){
        answerA.setText(colorNameArray[testColor[0]]+" "+ shapeNameArray[testShape[0]]);
        answerB.setText(colorNameArray[testColor[1]]+" "+ shapeNameArray[testShape[1]]);
        answerC.setText(colorNameArray[testColor[2]]+" "+shapeNameArray[testShape[2]]);
        answerA.setTextColor(ContextCompat.getColor(TestFive.this,colorArray[answerColors[0]]));
        answerB.setTextColor(ContextCompat.getColor(TestFive.this,colorArray[answerColors[1]]));
        answerC.setTextColor(ContextCompat.getColor(TestFive.this,colorArray[answerColors[2]]));
    }

    private void sendDate () {
        timeAnswers=endTime-startTime;
        data.add(new ArrayList<Float>());
        data.get(6).add(timeAnswers);
        data.get(6).add(correctAnswers);
        data.get(6).add(wrongAnswers);
        goToResult();
    }

    private void goToResult(){
        startActivity(new Intent(TestFive.this, Result.class).putExtra("data",data));
    }

    @Override
    public void onBackPressed() {
    }
}
