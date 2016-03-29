package com.example.android.myuniversity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class UniversityActivity extends AppCompatActivity {

//    //initiate the view for sleeping and appear form
//    private View myProgressView;
//    private View myUniversityFormView;
//
//    //initiate search async task for progress bar
//    private UserSearchTask mySearchTask = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_university);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);




//        //initiate the progress bar and main list form
//        myUniversityFormView = findViewById(R.id.listview_university);
//        myProgressView = findViewById(R.id.search_progress);

//        showProgress(true);
//        mySearchTask = new UserSearchTask();
//        mySearchTask.execute();

    }

//
//    /**
//     * Shows the progress UI and hides the login form.
//     */
//    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
//    private void showProgress(final boolean show) {
//        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
//        // for very easy animations. If available, use these APIs to fade-in
//        // the progress spinner.
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
//            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);
//
//            myUniversityFormView.setVisibility(show ? View.GONE : View.VISIBLE);
//            myUniversityFormView.animate().setDuration(shortAnimTime).alpha(
//                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
//                @Override
//                public void onAnimationEnd(Animator animation) {
//                    myUniversityFormView.setVisibility(show ? View.GONE : View.VISIBLE);
//                }
//            });
//
//            myProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
//            myProgressView.animate().setDuration(shortAnimTime).alpha(
//                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
//                @Override
//                public void onAnimationEnd(Animator animation) {
//                    myProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
//                }
//            });
//        } else {
//            // The ViewPropertyAnimator APIs are not available, so simply show
//            // and hide the relevant UI components.
//            myProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
//            myUniversityFormView.setVisibility(show ? View.GONE : View.VISIBLE);
//        }
//    }
//
//
//    /**
//     * Represents an asynchronous login/registration task used to authenticate
//     * the user.
//     */
//    public class UserSearchTask extends AsyncTask<Void, Void, Boolean> {
//
//        @Override
//        protected Boolean doInBackground(Void... params) {
//            try {
//                // Simulate network access.
//                Thread.sleep(2000);
//            } catch (InterruptedException e) {
//                return false;
//            }
//            return true;
//        }
//
//        @Override
//        protected void onPostExecute(final Boolean success) {
//            showProgress(false);
//        }
//
//        @Override
//        protected void onCancelled() {
//            showProgress(false);
//        }
//    }

}
