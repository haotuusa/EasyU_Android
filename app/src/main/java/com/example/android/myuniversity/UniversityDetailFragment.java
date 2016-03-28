package com.example.android.myuniversity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A placeholder fragment containing a simple view.
 */
public class UniversityDetailFragment extends Fragment {

    public UniversityDetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_university_detail, container, false);

        //get the intent package send in
        Intent intent = getActivity().getIntent();

        //validity check for intent has the info
        if(intent != null && intent.hasExtra(Intent.EXTRA_TEXT)){
            //get the text of university from intent package
            String universityStr = intent.getStringExtra(Intent.EXTRA_TEXT);
            //get the text view and set the string
            TextView universityTextView =
                    (TextView) rootView.findViewById(R.id.university_detail_text);
            universityTextView.setText(universityStr);
        }

        return rootView;
    }




    

}
