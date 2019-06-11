package com.example.quiz;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button bu_true;
    Button bu_false;
    TextView textView;
    TextView score;
    ProgressBar progressBar;
    int mindex=0;
    int scoreresult=0;
    int question;
    private TrueFalse[] mQuestionBank = new TrueFalse[] {
            new TrueFalse(R.string.question_1, true),
            new TrueFalse(R.string.question_2, true),
            new TrueFalse(R.string.question_3, true),
            new TrueFalse(R.string.question_4, true),
            new TrueFalse(R.string.question_5, true),
            new TrueFalse(R.string.question_6, false),
            new TrueFalse(R.string.question_7, true),
            new TrueFalse(R.string.question_8, false),
            new TrueFalse(R.string.question_9, true),
            new TrueFalse(R.string.question_10, true),
            new TrueFalse(R.string.question_11, false),
            new TrueFalse(R.string.question_12, false),
            new TrueFalse(R.string.question_13,true)
    };

    final int increment=(int)Math.ceil(100/mQuestionBank.length);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bu_true=findViewById(R.id.true_button);
        bu_false=findViewById(R.id.false_button);
        textView=findViewById(R.id.question_text_view);
        score=findViewById(R.id.score);
        progressBar=findViewById(R.id.progress_bar);
        if(savedInstanceState != null)
        {
            scoreresult = savedInstanceState.getInt("score");
            mindex =savedInstanceState.getInt("index");
        }
        else {
            scoreresult=0;
            mindex=0;
        }
        score.setText("Score "+scoreresult+"/"+mQuestionBank.length);


        Random random=new Random();
        question=mQuestionBank[random.nextInt(13)].getmQuestionID();
        final int questionold=question;
        textView.setText(question);


        View.OnClickListener listener=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId() == R.id.true_button)
                {
                    checkanswer(true);
                    updatequestion(questionold);
                }
                if (v.getId() == R.id.false_button)
                {
                    checkanswer(false);
                    updatequestion(questionold);
                }
            }
        };
        bu_true.setOnClickListener(listener);
        bu_false.setOnClickListener(listener);

    }
    private void updatequestion(int questionold)
    {
        mindex=(mindex+1)%mQuestionBank.length;
        if(mindex==0)
        {
            AlertDialog.Builder alertDialog=new AlertDialog.Builder(this);
            alertDialog.setTitle("Game Over");
            alertDialog.setCancelable(false);
            alertDialog.setMessage("Your Score "+scoreresult+" Point !");
            alertDialog.setPositiveButton("Close APP", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            alertDialog.show();
        }
        progressBar.incrementProgressBy(increment);

        if(questionold == mindex)
        {
            mindex=(mindex+1)%mQuestionBank.length;
            question=mQuestionBank[mindex].getmQuestionID();
            textView.setText(question);

        }
        else {


            question=mQuestionBank[mindex].getmQuestionID();
            textView.setText(question);
        }
        score.setText("Score "+scoreresult+"/"+mQuestionBank.length);


    }
    private void checkanswer(boolean userselection)
    {
        boolean correct=mQuestionBank[mindex].ismAnswer();
        if(userselection==correct)
        {
            scoreresult+=1;
            Toast.makeText(this, R.string.correct_toast, Toast.LENGTH_SHORT).show();
        }

        else {
            Toast.makeText(this, R.string.incorrect_toast, Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("score",scoreresult);
        outState.putInt("index",mindex);
    }
}
