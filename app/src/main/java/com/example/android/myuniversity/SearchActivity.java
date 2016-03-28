package com.example.android.myuniversity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

//import static android.Manifest.permission.READ_CONTACTS;

//implements LoaderCallbacks<Cursor>
public class SearchActivity extends AppCompatActivity  {

    //check the mode for which one to use
    private enum radio_btn{ RANK, HIGHTOLOW, LOWTOHIGH, NONE }
    radio_btn sortMode = radio_btn.NONE;


    // UI references.
    private EditText score1View;
    private EditText score2View;
    private EditText score3View;

    private View progressView;
    private View scoreFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);



        // assign the EditText
        score1View = (EditText) findViewById(R.id.score1);
        score2View = (EditText) findViewById(R.id.score2);
        score3View = (EditText) findViewById(R.id.score3);

        //score 3 actions, validity check and search if radio button is selected
        score3View.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                attemptSearch();
                return true;
            }
        });

        // for buttons actions
        Button searchButton = (Button) findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptSearch();
            }
        });


        scoreFormView = findViewById(R.id.score_form);
        progressView = findViewById(R.id.search_progress);
    }


    private void attemptSearch(){

        //get all the string
        String score1Str = score1View.getText().toString();
        String score2Str = score2View.getText().toString();
        String score3Str = score3View.getText().toString();

        //validity check
        boolean score1_isValid = isScoreValid(score1Str);
        boolean score2_isValid = isScoreValid(score2Str);
        boolean score3_isValid = isScoreValid(score3Str);

        // validity check for score 1
        if(!score1_isValid) {
            score1View.setError(getString(R.string.error_invalid_score));
            score1View.requestFocus();
            return;
        }
        // validity check for score 2
        if(!score2_isValid) {
            score2View.setError(getString(R.string.error_invalid_score));
            score2View.requestFocus();
            return;
        }
        // validity check for score 3
        if(!score3_isValid) {
            score3View.setError(getString(R.string.error_invalid_score));
            score3View.requestFocus();
            return;
        }
        // condition for no radio button select
        if(sortMode == radio_btn.NONE) {
            //show warning toast for no selection
            Context context = getApplicationContext();
            String errMsg = getString(R.string.error_require_radio);
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, errMsg, duration);
            toast.show();
            return;
        }

        Intent intent = new Intent(getApplicationContext(), UniversityActivity.class);
        String[] info = {score1Str, score2Str, score3Str, sortMode.toString()};
        // use ACTION_ATTACH_DATA as key
        intent.putExtra(Intent.ACTION_ATTACH_DATA, info);
        startActivity(intent);



    }

    private boolean isScoreValid(String scoreStr) {
        //match a number with optional '-' and decimal.)
        final int SCORE_MIN = 0;
        final int SCORE_MAX = 800;
        int score;
        try{
            score = Integer.parseInt(scoreStr);
            if(score < SCORE_MIN || score > SCORE_MAX)
                return false;
            return true;
        }
        catch (NumberFormatException nfe){
            return false;
        }
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_sort_rank:
                if (checked) {
                    sortMode = radio_btn.RANK;
                    break;
                }
            case R.id.radio_sort_hightolow:
                if (checked)
                    sortMode = radio_btn.HIGHTOLOW;
                    break;
            case R.id.radio_sort_lowtohigh:
                if(checked)
                    sortMode = radio_btn.LOWTOHIGH;
                    break;
        }
    }


    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }




}
