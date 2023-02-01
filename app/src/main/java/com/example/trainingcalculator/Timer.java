package com.example.trainingcalculator;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.fragment.app.Fragment;
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.base.Stopwatch;

import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class Timer extends Fragment {

    private long milliseconds = 0;
    private String time;
    private boolean running;
    private boolean wasRunning;
    private Stopwatch stopwatch;
    private Stopwatch lapWatch;
    private int lapCount =1;
    private final ArrayList<Lap> laps = new ArrayList<>();
    private ArrayAdapter adapter;
    private long elapsedTimeInSecs;
    public Timer() {
    }
    public static Timer newInstance(String param1, String param2) {
        Timer fragment = new Timer();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        stopwatch = Stopwatch.createUnstarted();
        lapWatch = Stopwatch.createUnstarted();
        if (savedInstanceState != null) {

            milliseconds
                    = savedInstanceState
                    .getLong("milliseconds");
            running
                    = savedInstanceState
                    .getBoolean("running");
            wasRunning
                    = savedInstanceState
                    .getBoolean("wasRunning");
        }

    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState)
    {
        savedInstanceState
                .putLong("milliseconds", milliseconds);
        savedInstanceState
                .putBoolean("running", running);
        savedInstanceState
                .putBoolean("wasRunning", wasRunning);
    }
    @Override
    public void onPause()
    {
        super.onPause();
        wasRunning = running;
        running = false;
    }
    @Override
    public void onResume()
    {
        super.onResume();
        if (wasRunning) {
            running = true;
        }
    }
    public void onClickStart(View view)
    {
        Button startBtn = view.findViewById(R.id.start_button);
        Button lapBtn = view.getRootView().findViewById(R.id.lap_button);
        if(!running) {
            running = true;
            stopwatch.start();
            lapWatch.start();
            startBtn.setText("Stop");
            lapBtn.setVisibility(View.VISIBLE);
        }
        else {
            running = false;
            stopwatch.stop();
            lapWatch.stop();
            startBtn.setText("Start");
            lapBtn.setVisibility(View.INVISIBLE);
        }

    }

    public void onClickReset(View view)
    {
        running = false;
        stopwatch.reset();
        lapWatch.reset();
        Button startBtn = view.getRootView().findViewById(R.id.start_button);
        startBtn.setText("Start");
        Button lapBtn = view.getRootView().findViewById(R.id.lap_button);
        lapBtn.setVisibility(View.INVISIBLE);
        ListView lapList = view.getRootView().findViewById(R.id.lapList);
        lapList.setVisibility(View.INVISIBLE);
        laps.clear();
        lapCount=1;
        ArrayList<Lap> laps = new ArrayList<>();

    }
    private void onClickLap(View view) {
        if(running) {
            TextView lapDistanceField = view.getRootView().findViewById(R.id.distanceField);
            int lapDistance=0;
             try{
                lapDistance = Integer.parseInt(lapDistanceField.getText().toString());
             }
             catch (NumberFormatException numberFormatException){
                 System.out.println(numberFormatException);
             };
            ListView lapList = view.getRootView().findViewById(R.id.lapList);
            lapList.setVisibility(View.VISIBLE);
            adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, laps);
            lapList.setAdapter(adapter);
            if (lapCount != 1) {
                milliseconds = lapWatch.elapsed(MILLISECONDS);
                lapWatch.reset();
                lapWatch.start();
                long seconds = MILLISECONDS.toSeconds(milliseconds);
                long hours = MILLISECONDS.toHours(milliseconds);
                milliseconds -= TimeUnit.HOURS.toMillis(hours);
                long minutes = MILLISECONDS.toMinutes(milliseconds);
                milliseconds -= TimeUnit.MINUTES.toMillis(minutes);
                long secs = MILLISECONDS.toSeconds(milliseconds);
                milliseconds -= TimeUnit.SECONDS.toMillis(secs);

                String lap = createTimeString(hours, minutes, secs, milliseconds/10);
                System.out.println("LAP " + lapCount + " : " + lap);
                if(lapDistance!=0) {
                    laps.add(new Lap(lapCount, lap, time, Calculation.secondsToPace(Calculation.getPace(seconds, lapDistance))));
                }
                else{
                    laps.add(new Lap(lapCount, lap, time));
                }
                }
            else {
                if(lapDistance!=0)
                {
                    laps.add(new Lap(lapCount, time, time, Calculation.secondsToPace(Calculation.getPace(elapsedTimeInSecs, lapDistance))));
                }
                else
                {
                    laps.add(new Lap(lapCount, time, time));
                }
                lapWatch.reset();
                lapWatch.start();
            }
            lapCount++;
        }

    }
    private void runTimer(View v)
    {
        View view = v.getRootView();
        final TextView timeView
                = view.findViewById(
                R.id.time_view);

        Handler handler = new Handler();

        handler.post(new Runnable() {
            @Override

            public void run()
            {

                milliseconds = stopwatch.elapsed(MILLISECONDS);
                elapsedTimeInSecs = MILLISECONDS.toSeconds(milliseconds);
                long hours =  MILLISECONDS.toHours(milliseconds);
                milliseconds -= TimeUnit.HOURS.toMillis(hours);
                long minutes = MILLISECONDS.toMinutes(milliseconds);
                milliseconds -= TimeUnit.MINUTES.toMillis(minutes);
                long secs = MILLISECONDS.toSeconds(milliseconds);
                milliseconds -= TimeUnit.SECONDS.toMillis(secs);


                time = createTimeString( hours, minutes, secs, milliseconds/10);

                timeView.setText(time);

                handler.postDelayed(this, 10);
            }
        });
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_timer, container, false);
        Button startBtn = view.findViewById(R.id.start_button);
        startBtn.setOnClickListener(this::onClickStart);
        Button lapBtn = view.findViewById(R.id.lap_button);
        lapBtn.setOnClickListener(this::onClickLap);
        Button resetBtn = view.findViewById(R.id.reset_button);
        resetBtn.setOnClickListener(this::onClickReset);
        runTimer(view);
        return view;
    }

    private String createTimeString( long hours,long minutes,long secs,long milliseconds){
        if(hours!=0) {
          return  String.format(Locale.getDefault(), "%d:%02d:%02d.%02d", hours, minutes, secs, milliseconds);
        }
        else{
           return String.format(Locale.getDefault(), "%01d:%02d.%02d", minutes, secs, milliseconds);
        }
    }
}