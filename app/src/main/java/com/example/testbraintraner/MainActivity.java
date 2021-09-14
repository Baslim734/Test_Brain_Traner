package com.example.testbraintraner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView textViewQuestion;
    private TextView textViewTimer;
    private int countOfQuestions;
    private int countOfRightAnswers;
    private TextView textViewScore;
    private int rightAnswer;
    private TextView textViewOpinion0;
    private TextView textViewOpinion1;
    private TextView textViewOpinion2;
    private TextView textViewOpinion3;
    private boolean gameOver = false;
    private boolean success = false;
    ArrayList<TextView> options = new ArrayList<>();
    MainLogicInterface mainLogic1 = new mainLogic();
    CountDownTimer timer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewTimer = findViewById(R.id.textViewTimer);
        textViewQuestion = findViewById(R.id.textViewQuestion);
        textViewScore = findViewById(R.id.textViewScore);
        textViewOpinion0 = findViewById(R.id.textViewOpinion0);
        textViewOpinion1 = findViewById(R.id.textViewOpinion1);
        textViewOpinion2 = findViewById(R.id.textViewOpinion2);
        textViewOpinion3 = findViewById(R.id.textViewOpinion3);
        options.add(textViewOpinion0);
        options.add(textViewOpinion1);
        options.add(textViewOpinion2);
        options.add(textViewOpinion3);
        mainLogic1.playNext(options,textViewQuestion,countOfRightAnswers,countOfQuestions,textViewScore);
        timer = new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                textViewTimer.setText(getTime(millisUntilFinished));
                if (millisUntilFinished <= 6000) {
                    textViewTimer.setTextColor(Color.parseColor("#fd0006"));
                }
            }

            @Override
            public void onFinish() {
                if(!success){
                    gameOver = true;
                }
                  success = false;
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                int max = preferences.getInt("max", 0);
                if (countOfRightAnswers >= max) {
                    preferences.edit().putInt("max", countOfRightAnswers).apply();
                }
                Intent intent = new Intent(MainActivity.this, ActivityScore.class);
                intent.putExtra("result", countOfRightAnswers);
                startActivity(intent);
            }
        };
        timer.start();

    }



    public void onClickAnswer(View view) {
        if(!gameOver) {
            TextView textView = (TextView) view;
            String answer = textView.getText().toString();
            int chosenAnswer = Integer.parseInt(answer);
            if (chosenAnswer == rightAnswer) {
                countOfRightAnswers++;
                timer.cancel();
                timer.start();
                Toast.makeText(this, "Верно", Toast.LENGTH_SHORT).show();
                textViewTimer.setTextColor(Color.parseColor("#41A128"));
            } else {
                Toast.makeText(this, "Неверно", Toast.LENGTH_SHORT).show();
            }
            countOfQuestions++;
            rightAnswer = mainLogic1.playNext(options, textViewQuestion, countOfRightAnswers, countOfQuestions, textViewScore);
        }
    }

    private String getTime(long mils){
        int seconds = (int) (mils/1000);
        int minutes = seconds/60;
        seconds = seconds % 60;
        return String.format(Locale.getDefault(),"%02d:%02d",minutes,seconds);
    }





}