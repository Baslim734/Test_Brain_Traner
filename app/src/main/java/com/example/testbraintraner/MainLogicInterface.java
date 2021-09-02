package com.example.testbraintraner;

import android.widget.TextView;

import java.util.ArrayList;

public interface MainLogicInterface {
    int generateQuestion(TextView textViewQuestion);
    int generateWrongAnswer();
    int playNext(ArrayList<TextView> options, TextView textViewQuestion,int countOfRightAnswers, int countOfQuestions,TextView textViewScore);
}
