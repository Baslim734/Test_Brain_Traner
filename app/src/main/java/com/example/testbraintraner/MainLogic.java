package com.example.testbraintraner;

import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

class mainLogic implements MainLogicInterface {
    private String question;
    private int rightAnswer;
    private int rightAnswerPosition;
    private boolean isPositive;
    private int max = 10;

    public int generateQuestion(TextView textViewQuestion) {
        int a = (int) (Math.random() * (max + 1));
        int b = (int) (Math.random() * (max + 1));
        int mark = (int) (Math.random() * 2);
        isPositive = mark == 1;
        if (isPositive) {
            rightAnswer = a + b;
            question = String.format("%s + %s", a, b);
        } else {
            rightAnswer = a - b;
            question = String.format("%s - %s", a, b);
        }
        textViewQuestion.setText(question);
        rightAnswerPosition = (int) (Math.random() * 4);
        return rightAnswer;
    }

    public int generateWrongAnswer() {
        int result;
        do {
            result = (int) (Math.random() * max * 2 + 1) - (max);
        } while (result == rightAnswer);
        return result;
    }

    public int playNext(ArrayList<TextView> options, TextView textViewQuestion,int countOfRightAnswers, int countOfQuestions,TextView textViewScore) {
        rightAnswer = generateQuestion(textViewQuestion);
        for(
    int i = 0;i<options.size();i++)
    {
        if (i == rightAnswerPosition) {
            options.get(i).setText(Integer.toString(rightAnswer));
        } else {
            options.get(i).setText(Integer.toString(generateWrongAnswer()));
        }
    }
        String score = String.format("%s / %s",countOfRightAnswers, countOfQuestions);
        textViewScore.setText(score);
        return rightAnswer;
    }
}
