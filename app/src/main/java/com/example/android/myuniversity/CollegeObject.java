package com.example.android.myuniversity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by apple on 3/28/16.
 */
public class CollegeObject {


    private final int INDEX_SATVR25 = 0, INDEX_SATVR75 = 1,
                   INDEX_SATMT25 = 2, INDEX_SATMT75 = 3,
                   INDEX_SATWR25 = 4, INDEX_SATWR75 = 5,
                   INDEX_ACTCM25 = 6, INDEX_ACTCM75 = 7,
                   INDEX_ACTEM25 = 8, INDEX_ACTEM75 = 9,
                   INDEX_ACTMT25 = 10, INDEX_ACTMT75 = 11;

    private final String[] SCORE_ID = {"SATVR25 (ADM2014)", "SATVR75 (ADM2014)",
                            "SATMT25 (ADM2014)", "SATMT75 (ADM2014)",
                            "SATWR25 (ADM2014)", "SATWR75 (ADM2014)",
                            "ACTCM25 (ADM2014)", "ACTCM75 (ADM2014)",
                            "ACTEN25 (ADM2014)", "ACTEN75 (ADM2014)",
                            "ACTMT25 (ADM2014)", "ACTMT75 (ADM2014)"};
    private final String NAME_ID = "instnm";


    //data in CollegeObject
    private String collegeName;
    private final int[] scoreData = new int[12];

    //assign all the value
    public CollegeObject(JSONObject jsonCollege) {

        try {
            for (int i = 0; i < SCORE_ID.length; i++) {
                scoreData[i] = jsonCollege.getInt(SCORE_ID[i]);
            }
            collegeName = jsonCollege.getString(NAME_ID);
        } catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    public double getAverageACT25() {
        double total = (double) (scoreData[INDEX_ACTCM25] + scoreData[INDEX_ACTEM25]
                                + scoreData[INDEX_ACTMT25]);
        return total;
    }

    public double getAverageACT75() {
        double total = (double) (scoreData[INDEX_ACTCM75] + scoreData[INDEX_ACTEM75]
                + scoreData[INDEX_ACTMT75]);
        return total;
    }

    public double getAverageACT() {
        return (getAverageACT25() + getAverageACT75())/2;
    }


    public double getAverageSAT25() {
        double total = (double) (scoreData[INDEX_SATMT25] + scoreData[INDEX_SATVR25]
                + scoreData[INDEX_SATWR25]);
        return total;
    }

    public double getAverageSAT75() {
        double total = (double) (scoreData[INDEX_SATMT75] + scoreData[INDEX_SATVR75]
                + scoreData[INDEX_SATWR75]);
        return total;
    }

    public double getAverageSAT(){
        return (getAverageSAT25() + getAverageSAT75())/2;
    }

    public double getRequireSAT(){
        return getAverageSAT25() - (getAverageSAT() - getAverageSAT25());
    }

    public double getRequireACT(){
        return getAverageACT25() - (getAverageACT() - getAverageACT25());
    }




    public double getRequireSATReading(){

        double averageSATReading = (scoreData[INDEX_SATVR25] + scoreData[INDEX_SATVR75]) / 2;

        return scoreData[INDEX_SATVR25] - (averageSATReading - scoreData[INDEX_SATVR25]);
    }

    public double getRequireSATMath(){

        double averageSATMath = (scoreData[INDEX_SATMT25] + scoreData[INDEX_SATMT75]) / 2;

        return scoreData[INDEX_SATMT25] - (averageSATMath - scoreData[INDEX_SATMT25]);
    }

    public double getRequireSATWriting(){

        double averageSATWriting = (scoreData[INDEX_SATWR25] + scoreData[INDEX_SATWR75]) / 2;

        return scoreData[INDEX_SATWR25] - (averageSATWriting - scoreData[INDEX_SATWR25]);
    }


    public double getRequireACTComposite(){

        double averageACTComposite = (scoreData[INDEX_ACTCM25] + scoreData[INDEX_ACTCM75]) / 2;

        return scoreData[INDEX_ACTCM25] - (averageACTComposite - scoreData[INDEX_ACTCM25]);
    }

    public double getRequireACTMath(){

        double averageACTMath = (scoreData[INDEX_ACTMT25] + scoreData[INDEX_ACTMT75]) / 2;

        return scoreData[INDEX_ACTMT25] - (averageACTMath - scoreData[INDEX_ACTMT25]);
    }

    public double getRequireACTEnglish(){

        double averageACTEnglish = (scoreData[INDEX_ACTEM25] + scoreData[INDEX_ACTEM75]) / 2;

        return scoreData[INDEX_ACTEM25] - (averageACTEnglish - scoreData[INDEX_ACTEM25]);
    }


    public String getName(){
        return collegeName;
    }

    @Override
    public String toString(){
        return collegeName + "\n\nRequired SAT Reading Score : " + getRequireSATReading()
                + "\nRequired SAT Math Score : " + getRequireSATMath()
                + "\nRequired SAT Writing Score : " + getRequireSATWriting()
                + "\nAverage SAT Test Score: " + getAverageSAT()
                + "\nAverage lower 25 percent SAT Test Score: " + getAverageSAT25()
                + "\nAverage higher 75 percent SAT Test Score: " + getAverageSAT75()
                + "\n\nRequired ACT Composite Score : " + getRequireACTComposite()
                + "\nRequired ACT English Score : " + getRequireACTEnglish()
                + "\nRequired ACT Math Score : " + getRequireACTMath()
                + "\nAverage ACT Test Score: " + getAverageACT()
                + "\nAverage lower 25 percent ACT Test Score: " + getAverageACT25()
                + "\nAverage higher 75 percent ACT Test Score: " + getAverageACT75();

    }



}
