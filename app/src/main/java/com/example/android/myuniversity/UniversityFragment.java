package com.example.android.myuniversity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class UniversityFragment extends Fragment {

    //initiate the adapter for list of university
    private ArrayAdapter<String> universityAdapter;



    public UniversityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_university, container, false);


        final String[] universityArray = {
                "University of California - Hao TU",
                "University of Washington - Devin Stoen"
        };


        // transform a array into a arraylist
        List<String> universityList = new ArrayList<String>(Arrays.asList(universityArray));

        /* initiate the adapter with context, list item layout, text view in list item
           arraylist of info */
        universityAdapter =
                new ArrayAdapter<String>(
                        getActivity(),
                        R.layout.list_item_university,
                        R.id.list_item_university_textview,
                        universityList );

        // Get a reference to the ListView, and attach this adapter to it.
        ListView universityListView = (ListView) rootView.findViewById(R.id.listview_university);
        universityListView.setAdapter(universityAdapter);


        universityListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView adapterView, View view, int position, long l){
                String universityStr = universityAdapter.getItem(position);
                // open new intent of detail
                Intent intentDetail = new Intent(getActivity(), UniversityDetailActivity.class);
                intentDetail.putExtra(Intent.EXTRA_TEXT, universityStr);
                startActivity(intentDetail);

            }
        });


        return rootView;
    }




    public class FetchUniversityTask extends AsyncTask<String, Void, String[]> {


        @Override
        protected String[] doInBackground(String... params){




            return null;
        }

        @Override
        protected void onPostExecute(String[] result){
            if(result != null)
            {
                universityAdapter.clear();
                for(String dayForecastStr: result){
                    universityAdapter.add(dayForecastStr);
                }
            }
        }

    }

}
