package com.hiddenhopes.guesstheword;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.support.annotation.ColorInt;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;
import java.lang.String;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    String hiddenWord="";
    int countMatched = 0;
    int countNotMatched = 0;
    int score = 0;
    private String [] wordArray = {"MANGO", "CLASS", "GREAT", "APPLE", "BUILD", "START", "PIZZA", "QUICK", "ENEMY", "CRAZY",
                                   "SEVEN", "ABOUT", "AGAIN", "HEART", "BREAD", "WATER", "HAPPY", "GREEN", "MUSIC", "THREE",
                                   "PARTY", "WOMAN", "DREAM", "LEARN", "TIGER", "RIVER", "MONEY", "HOUSE", "ALONE", "AFTER",
                                   "KNOCK", "THING", "LIGHT", "STORY", "NOBLE", "TODAY", "CANDY", "PUPPY", "ABOVE", "QUEEN",
                                   "PLANT", "BLACK", "ZEBRA", "TRAIN", "UNDER", "EIGHT", "PANDA", "TRUCK", "NEVER", "COLOR",
                                   "MOUSE", "PAPER", "DRESS", "TABLE", "WHITE", "GREAT", "SWEET", "BEACH", "PLATE", "RIGHT",
                                   "NOISE", "OFFER", "LOUGH", "LARGE", "DIRTY", "SMALL", "BREAK", "FALSE", "CLEAR", "SHIFT"};
    private Button buttonId;
    private Button buttonA;
    private Button buttonB;
    private Button buttonC;
    private Button buttonReset;

    private TextView textViewID;
    private TextView TV1;
    private TextView TV2;
    private TextView TV3;
    private TextView TV4;
    private TextView TV5;
    private TextView scoreTextView;
    private TextView attemptTextView;
    MediaPlayer resetSound;
    MediaPlayer buttonSound;
    MediaPlayer endSound;
    MediaPlayer wrongSound;
    MediaPlayer correctSound;

    private int resID;
    private char resChar;
    private Random random = new Random();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        hiddenWord = wordArray[random.nextInt(70)];
        countMatched = 0;
        for(int i=0; i<26; i++){
            String buttonIdString = "Button"+ Character.toString((char)(i+65)); // check if Character needed or not
            resID = getResources().getIdentifier(buttonIdString, "id", getPackageName());
            buttonId = (Button) findViewById(resID);
            buttonId.setOnClickListener(this);
        }

        buttonReset = findViewById(R.id.buttonReset);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetSoundStart();
                resetAll();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onClick(View v) {
        boolean matched = false;
        for(int i=0; i<26; i++){
            String buttonIdString = "Button"+ Character.toString((char)(i+65)); // check if Character needed or not
            int btnID = getResources().getIdentifier(buttonIdString, "id", getPackageName());
            if(v.getId()==btnID){
                resChar = (char)(i+65);
                Toast.makeText(this, ""+resChar, Toast.LENGTH_SHORT).show();
                matched = viewTextShow(""+resChar);
                buttonSoundStart();
                break;
            }

        }


        if(!matched) {
            if(countNotMatched<5) {
                countNotMatched = countNotMatched + 1;
                Toast.makeText(this, "NO", Toast.LENGTH_SHORT).show();
                attemptTextView = findViewById(R.id.attemptTextView);
                attemptTextView.setText("Attempts Left\n" + (5 - countNotMatched));
                wrongSoundStart();

            }
            if(countNotMatched==5){
                score--;
                Toast.makeText(this, "NO MORE ATTEMPTS LEFT",Toast.LENGTH_SHORT).show();
                setAnswerTextView(hiddenWord);
                countNotMatched = 99999;
                endSoundStart();
            }
        }

        if(countMatched == 5){
            Toast.makeText(this, "Correct", Toast.LENGTH_SHORT).show();
            score++;
            scoreTextView = (TextView) findViewById(R.id.scoreTextView);
            scoreTextView.setText("Score: "+score);
            countMatched = 0;
            countNotMatched = 99999;
            correctSoundStart();
        }
    }

    public void resetSoundStart() {
        resetSound = MediaPlayer.create(MainActivity.this, R.raw.buttonsound);
        resetSound.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.reset();
                mp.release();
                resetSound = null;
            }
        });
        resetSound.start();
    }
    public void buttonSoundStart(){
        buttonSound = MediaPlayer.create(MainActivity.this, R.raw.buttonsound);
        buttonSound.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.reset();
                mp.release();
                buttonSound = null;
            }
        });
        buttonSound.start();
    }
    public void endSoundStart(){
        endSound = MediaPlayer.create(MainActivity.this, R.raw.end);
        endSound.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.reset();
                mp.release();
                endSound = null;
            }
        });
        endSound.start();
    }
    public void wrongSoundStart(){
        wrongSound = MediaPlayer.create(MainActivity.this, R.raw.wrong);
        wrongSound.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.reset();
                mp.release();
                wrongSound = null;
            }
        });
        wrongSound.start();
    }
    public void correctSoundStart(){
        correctSound = MediaPlayer.create(MainActivity.this, R.raw.correct);
        correctSound.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.reset();
                mp.release();
                correctSound = null;
            }
        });
        correctSound.start();
    }

    public boolean viewTextShow(String ch){
        boolean matched = false;
        /*for(int i=1; i<=5; i++){
            if(hiddenWord.charAt(i-1)== ch.charAt(i-1)){
                countMatched = countMatched + 1;
                matched = true;
                // show in viewtext
                String textViewIdString = "TV"+i;
                int tvId = getResources().getIdentifier(textViewIdString, "id", getPackageName());
                textViewID = (TextView) findViewById(tvId);
                textViewID.setText(ch);
            }
        }*/
        if(hiddenWord.charAt(0)== ch.charAt(0)){
            matched = true;
            // show in viewtext
            TV1 = (TextView) findViewById(R.id.TV1);   // eykhane th
            if(TV1.getText().equals("")){
                countMatched = countMatched + 1;
                TV1.setText(ch);
                TV1.setBackgroundColor(Color.GREEN);
            }
        }
        if(hiddenWord.charAt(1)==ch.charAt(0)){
            matched = true;
            // show in viewtext
            TV2 = (TextView) findViewById(R.id.TV2);   // eykhane th
            if(TV2.getText().equals("")) {
                countMatched = countMatched + 1;
                TV2.setText(ch);
                TV2.setBackgroundColor(Color.GREEN);
            }
        }
        if(hiddenWord.charAt(2)==ch.charAt(0)){
            matched = true;
            // show in viewtext
            TV3 = (TextView) findViewById(R.id.TV3);   // eykhane th
            if(TV3.getText().equals("")) {
                countMatched = countMatched + 1;
                TV3.setText(ch);
                TV3.setBackgroundColor(Color.GREEN);
            }
        }
        if(hiddenWord.charAt(3)==ch.charAt(0)){
            matched = true;
            // show in viewtext
            TV4 = (TextView) findViewById(R.id.TV4);   // eykhane th
            if(TV4.getText().equals("")){
                countMatched = countMatched + 1;
                TV4.setText(ch);
                TV4.setBackgroundColor(Color.GREEN);
            }
        }
        if(hiddenWord.charAt(4)==ch.charAt(0)){
            matched = true;
            // show in viewtext
            TV5 = (TextView) findViewById(R.id.TV5);   // eykhane th
            if(TV5.getText().equals("")) {
                countMatched = countMatched + 1;
                TV5.setText(ch);
                TV5.setBackgroundColor(Color.GREEN);
            }
        }
        return matched;
    }

    public void resetAll(){
        countMatched = 0;
        countNotMatched = 0;
        hiddenWord = wordArray[random.nextInt(10)];
        //for(int i=1; i<=5; i++) {

        TV1 = (TextView) findViewById(R.id.TV1);
        TV1.setText("");
        TV1.setBackgroundColor(Color.LTGRAY);
        //}
        TV2 = (TextView) findViewById(R.id.TV2);
        TV2.setText("");
        TV2.setBackgroundColor(Color.LTGRAY);
        TV3 = (TextView) findViewById(R.id.TV3);
        TV3.setText("");
        TV3.setBackgroundColor(Color.LTGRAY);
        TV4 = (TextView) findViewById(R.id.TV4);
        TV4.setText("");
        TV4.setBackgroundColor(Color.LTGRAY);
        TV5 = (TextView) findViewById(R.id.TV5);
        TV5.setText("");
        TV5.setBackgroundColor(Color.LTGRAY);
        scoreTextView = (TextView) findViewById(R.id.scoreTextView);
        scoreTextView.setText("Score: "+score);
        attemptTextView = findViewById(R.id.attemptTextView);
        attemptTextView.setText("Attempts Left\n5");
    }

    public void setAnswerTextView(String hiddenWord){
        //for(int i=1; i<=5; i++) {
        TV1 = (TextView) findViewById(R.id.TV1);
        if(TV1.getText().equals(""))TV1.setBackgroundColor(Color.YELLOW);
        TV1.setText(""+hiddenWord.charAt(0));
        //}

        TV2 = (TextView) findViewById(R.id.TV2);
        if(TV2.getText().equals(""))TV2.setBackgroundColor(Color.YELLOW);
        TV2.setText(""+hiddenWord.charAt(1));

        TV3 = (TextView) findViewById(R.id.TV3);
        if(TV3.getText().equals(""))TV3.setBackgroundColor(Color.YELLOW);
        TV3.setText(""+hiddenWord.charAt(2));

        TV4 = (TextView) findViewById(R.id.TV4);
        if(TV4.getText().equals(""))TV4.setBackgroundColor(Color.YELLOW);
        TV4.setText(""+hiddenWord.charAt(3));

        TV5 = (TextView) findViewById(R.id.TV5);
        if(TV5.getText().equals("")) TV5.setBackgroundColor(Color.YELLOW);
        TV5.setText(""+hiddenWord.charAt(4));

        scoreTextView = (TextView) findViewById(R.id.scoreTextView);
        scoreTextView.setText("Score: "+score);
    }
}

