package com.example.a31c;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class QuizActivity extends AppCompatActivity {

    private TextView tvWelcome, tvQuestion;
    private Button btnAnswer1, btnAnswer2, btnAnswer3, btnSubmit;
    private ProgressBar progressBar;
    private int questionIndex = 0;
    private int score = 0;
    private int selectedAnswer = -1;

    private String userName;
    private String[] questions = {
            "Question 1: What is ...?",
            "Question 2: How many ...?",
            "Question 3: Where is ...?",
            "Question 4: When did ...?",
            "Question 5: Why does ...?"
    };

    private String[][] answers = {
            {"Answer 1.1", "Answer 1.2", "Answer 1.3"},
            {"Answer 2.1", "Answer 2.2", "Answer 2.3"},
            {"Answer 3.1", "Answer 3.2", "Answer 3.3"},
            {"Answer 4.1", "Answer 4.2", "Answer 4.3"},
            {"Answer 5.1", "Answer 5.2", "Answer 5.3"}
    };

    private int[] correctAnswers = {0, 1, 2, 0, 1};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        tvWelcome = findViewById(R.id.tv_welcome);
        tvQuestion = findViewById(R.id.tv_question);
        btnAnswer1 = findViewById(R.id.btn_answer1);
        btnAnswer2 = findViewById(R.id.btn_answer2);
        btnAnswer3 = findViewById(R.id.btn_answer3);
        btnSubmit = findViewById(R.id.btn_submit);
        progressBar = findViewById(R.id.progressBar);

        userName = getIntent().getStringExtra("USER_NAME");
        tvWelcome.setText("Welcome " + userName + "!");

        loadQuestion();

        btnAnswer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectAnswer(0);
            }
        });

        btnAnswer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectAnswer(1);
            }
        });

        btnAnswer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectAnswer(2);
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnSubmit.getText().equals("Submit")) {
                    checkAnswer();
                } else {
                    nextQuestion();
                }
            }
        });
    }

    private void loadQuestion() {
        tvQuestion.setText(questions[questionIndex]);
        btnAnswer1.setText(answers[questionIndex][0]);
        btnAnswer2.setText(answers[questionIndex][1]);
        btnAnswer3.setText(answers[questionIndex][2]);
        btnAnswer1.setBackgroundColor(Color.TRANSPARENT);
        btnAnswer2.setBackgroundColor(Color.TRANSPARENT);
        btnAnswer3.setBackgroundColor(Color.TRANSPARENT);
        btnSubmit.setText("Submit");
        selectedAnswer = -1;
    }

    private void selectAnswer(int index) {
        selectedAnswer = index;
        btnAnswer1.setBackgroundColor(index == 0 ? Color.LTGRAY : Color.TRANSPARENT);
        btnAnswer2.setBackgroundColor(index == 1 ? Color.LTGRAY : Color.TRANSPARENT);
        btnAnswer3.setBackgroundColor(index == 2 ? Color.LTGRAY : Color.TRANSPARENT);
    }

    private void checkAnswer() {
        if (selectedAnswer == -1) return;

        if (selectedAnswer == correctAnswers[questionIndex]) {
            score++;
            btnAnswer1.setBackgroundColor(selectedAnswer == 0 ? Color.GREEN : Color.TRANSPARENT);
            btnAnswer2.setBackgroundColor(selectedAnswer == 1 ? Color.GREEN : Color.TRANSPARENT);
            btnAnswer3.setBackgroundColor(selectedAnswer == 2 ? Color.GREEN : Color.TRANSPARENT);
        } else {
            btnAnswer1.setBackgroundColor(selectedAnswer == 0 ? Color.RED : (correctAnswers[questionIndex] == 0 ? Color.GREEN : Color.TRANSPARENT));
            btnAnswer2.setBackgroundColor(selectedAnswer == 1 ? Color.RED : (correctAnswers[questionIndex] == 1 ? Color.GREEN : Color.TRANSPARENT));
            btnAnswer3.setBackgroundColor(selectedAnswer == 2 ? Color.RED : (correctAnswers[questionIndex] == 2 ? Color.GREEN : Color.TRANSPARENT));
        }

        btnSubmit.setText("Next");
    }

    private void nextQuestion() {
        questionIndex++;
        progressBar.setProgress((questionIndex * 100) / questions.length);

        if (questionIndex < questions.length) {
            loadQuestion();
        } else {
            Intent intent = new Intent(QuizActivity.this, ResultActivity.class);
            intent.putExtra("SCORE", score);
            intent.putExtra("USER_NAME", userName);
            startActivity(intent);
            finish();
        }
    }
}

