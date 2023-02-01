package com.example.trainingcalculator;

import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import androidx.annotation.RequiresApi;

import java.text.ParseException;
import java.util.Date;

public class Calculation {


    @RequiresApi(api = Build.VERSION_CODES.N)
    public static int convertToSeconds(String time) throws ParseException {
        DateFormat sdf = new SimpleDateFormat("HH:mm:sss");
        Date pace = sdf.parse(time);
        int seconds = pace.getHours() * 3600 + pace.getMinutes() * 60 + pace.getSeconds();
        return seconds;
    }

    public static Double getPace(double seconds, int distance){
        Double pace = ( seconds / ((double) distance * 0.001));
        return pace;
    }
    public int getTime(int pace, int distance){
        int seconds = pace * distance;
        return seconds;
    }
    public int getDistance(int seconds, int pace){
        int distance = seconds / pace;
        return distance;
    }

    public static Double getLapTime(int pace, int lapDistance){
        Double lapTime = (double) pace * (double) lapDistance / 1000;
        return lapTime;
    }
    public static String secondsToPace(Double seconds){
        int hours = (int) (seconds / 3600);
        int minutes = (int) ((seconds % 3600) / 60);
        int secs = (int) (seconds % 60);
        String pace = String.format("%02d:%02d:%02d", hours, minutes, secs);;
        return pace;
    }

}
