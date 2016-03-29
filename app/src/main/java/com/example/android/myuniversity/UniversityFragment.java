package com.example.android.myuniversity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class UniversityFragment extends Fragment {

    //initiate the adapter for list of university
    private ArrayAdapter<String> universityAdapter;
    private ArrayList<CollegeObject> universityList;
    private ArrayList<CollegeObject> filteredList;
    private double userTotal;
    private int userScore[] = new int[3];
    final int INDEX_SCORE1 = 0;
    final int INDEX_SCORE2 = 1;
    final int INDEX_SCORE3 = 2;


    public UniversityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onResume(){
        super.onResume();
        Log.v("onResume", "yeah");
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.sort_order, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_harderToEasier) {
            Collections.sort(filteredList, new CompareBySATAverageHighToLow());
            updateAdapter();

            return true;
        }
        else if(id == R.id.action_easierToHarder){
            Collections.sort(filteredList, new CompareBySATAverageLowToHigh());
            updateAdapter();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void setUserTotal(){

        String[] scoreStrArray = getActivity().getIntent()
                                .getStringArrayExtra(Intent.ACTION_ATTACH_DATA);

        double scoreTotal = 0;

        for(int i = 0; i < scoreStrArray.length; i++) {
            int score = Integer.parseInt(scoreStrArray[i]);
            userScore[i] = score;
            scoreTotal += score;
        }
        userTotal = scoreTotal;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_university, container, false);


        //get the score from last intent package
        setUserTotal();



        List universityStrList = new ArrayList<String>();

        /* initiate the adapter with context, list item layout, text view in list item
           arraylist of info */
        universityAdapter =
                new ArrayAdapter<String>(
                        getActivity(),
                        R.layout.list_item_university,
                        R.id.list_item_university_textview,
                        universityStrList ){
                    @Override
                    public View getView(int position, View view, ViewGroup viewGroup)
                    {
                        View v = super.getView(position, view, viewGroup);

                        TextView tv = ((TextView)v);
                        if(userTotal <= filteredList.get(position).getAverageSAT25())
                            tv.setBackgroundResource(R.color.colorLoAccept);
                        else if(userTotal <= filteredList.get(position).getAverageSAT())
                            tv.setBackgroundResource(R.color.colorMiloAccept);
                        else if(userTotal <= filteredList.get(position).getAverageSAT75())
                            tv.setBackgroundResource(R.color.colorMiAccept);
                        else
                            tv.setBackgroundResource(R.color.colorHiAccept);

                        return tv;
                    }
                };

        //for updating the list
        FetchUniversityTask myTask = new FetchUniversityTask();
        myTask.execute("CA");

        // Get a reference to the ListView, and attach this adapter to it.
        ListView universityListView = (ListView) rootView.findViewById(R.id.listview_university);
        universityListView.setAdapter(universityAdapter);



        universityListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView adapterView, View view, int position, long l){
//                String universityStr = universityAdapter.getItem(position);
                String universityStr = filteredList.get(position).toString();
                // open new intent of detail
                Intent intentDetail = new Intent(getActivity(), UniversityDetailActivity.class);
                intentDetail.putExtra(Intent.EXTRA_TEXT, universityStr);
                startActivity(intentDetail);

            }

        });


        return rootView;
    }


    // the inner class to get the university's data
    public class FetchUniversityTask extends AsyncTask<String, Void, ArrayList<CollegeObject>> {

        //for debug log
        private final String LOG_TAG = FetchUniversityTask.class.getSimpleName();

        private ArrayList<CollegeObject> getUniversityDataFromJson(String universityJsonstr)
                throws JSONException
        {

            final String UNIVERSITY_NAME = "instnm";

            ArrayList universities = new ArrayList<CollegeObject>();
            JSONArray universityArray = new JSONArray(universityJsonstr);

            for(int i = 0; i < universityArray.length(); i++) {
//
//                String universityName = universityArray.getJSONObject(i).getString(UNIVERSITY_NAME);
//                result.add(universityName);
                universities.add(new CollegeObject(universityArray.getJSONObject(i)));
            }
//            return result;
            return universities;
        }



        @Override
        protected ArrayList<CollegeObject> doInBackground(String... params){

            // If there's no state's name, there's nothing to look up.  Verify size of params.
            if (params.length == 0) {
                return null;
            }

            // These two need to be declared outside the try catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String universityJsonStr = null;

            try{
                final String UNIVERSITY_BASE_URL =
                        "http://haotuusa.github.io/collegeData/university";

                //for later update .state
                final String STATE = ".state";

                // params indicate the state region provide by user
                // is html for now, later update to my own API
                String urlStr = UNIVERSITY_BASE_URL + STATE + ":" + params[0] + ".html";


                URL url = new URL(urlStr);

                // create request to open the university url and get the string
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                //read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();

                // buffer for storing the university data string from internet
                StringBuffer buffer = new StringBuffer();


                //empty input stream nothing to do
                if(inputStream == null)
                    return null;

                // reader for read in the data from internet's input stream
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                // nothing in the string, not data so do nothing
                if(buffer.length()==0)
                    return null;

                // assign buffer to Json String
                universityJsonStr = buffer.toString();

            }
            catch(IOException e) //doesn't succesfully get the data
            {
                return null;
            } finally{
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                    }
                }
            }
            try {
                return getUniversityDataFromJson(universityJsonStr);
            } catch (JSONException e) {
                e.printStackTrace();
            }


            //only happen when there is error
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<CollegeObject> universities){
            if(universities != null) {
                universityList = universities;
                FilterList();
                Collections.sort(filteredList, new CompareBySATAverageHighToLow());
                updateAdapter();
            }
        }

    }

    private void FilterList(){
        filteredList = universityList;
        for(int i = 0; i < filteredList.size(); i++)
        {
            CollegeObject temp_College = filteredList.get(i);
            if((userScore[INDEX_SCORE1] < temp_College.getRequireSATReading() ||
                    userScore[INDEX_SCORE2] < temp_College.getRequireSATMath() ||
                    userScore[INDEX_SCORE3] < temp_College.getRequireSATWriting())||
                    (temp_College.getRequireSAT()==0)) {
                filteredList.remove(i);
                i--;
            }
        }
    }

    //method for update the list, also after the sort can call this
    private void updateAdapter(){
        universityAdapter.clear();
        for(int i = 0; i < filteredList.size(); i++){
            universityAdapter.add(filteredList.get(i).getName());
        }

    }


    private class CompareBySATAverageHighToLow implements Comparator<CollegeObject>
    {
        public CompareBySATAverageHighToLow(){}

        @Override
        public int compare(CollegeObject school1, CollegeObject school2)
        {
            int result = 0;
            if(userTotal <= school2.getAverageSAT25())
                result =  (int)school2.getRequireSAT() - (int)school1.getRequireSAT();
            else if(userTotal<= school2.getAverageSAT())
                result =  (int)school2.getAverageSAT25() - (int)school1.getAverageSAT25();
            else if(userTotal <= school2.getAverageSAT75())
                result =  (int)school2.getAverageSAT() - (int)school1.getAverageSAT();
            else
                result =  (int)school2.getAverageSAT75() - (int)school1.getAverageSAT75();
            return result;
        }
    }


    private class CompareBySATAverageLowToHigh implements Comparator<CollegeObject>
    {
        public CompareBySATAverageLowToHigh(){}

        @Override
        public int compare(CollegeObject school2, CollegeObject school1)
        {
            int result = 0;
            if(userTotal <= school2.getAverageSAT25())
                result =  (int)school2.getRequireSAT() - (int)school1.getRequireSAT();
            else if(userTotal<= school2.getAverageSAT())
                result =  (int)school2.getAverageSAT25() - (int)school1.getAverageSAT25();
            else if(userTotal <= school2.getAverageSAT75())
                result =  (int)school2.getAverageSAT() - (int)school1.getAverageSAT();
            else
                result =  (int)school2.getAverageSAT75() - (int)school1.getAverageSAT75();
            return result;
        }
    }



}
