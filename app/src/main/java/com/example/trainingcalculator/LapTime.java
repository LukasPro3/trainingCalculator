package com.example.trainingcalculator;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

import java.text.ParseException;

public class LapTime extends Fragment implements View.OnClickListener{

    public static LapTime newInstance(String param1, String param2) {
        LapTime fragment = new LapTime();
        Bundle args = new Bundle();
        return fragment;
    }

    public LapTime() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lap_time, container, false);
        Button btn = (Button) view.findViewById(R.id.calculateBtn);
        btn.setOnClickListener(this);

        return view;

    }

    @Override
    public void onClick(View v) {
        View view = v.getRootView();
        if(view != null) {
            TextView paceField = view.findViewById(R.id.paceField);
            System.out.println(paceField);
            TextView lapDistanceField = view.findViewById(R.id.lapDistanceField);
            TextView lapTimeText = view.findViewById(R.id.lapPaceText);
            String pace = paceField.getText().toString();
            System.out.println(pace);
            int paceInSec = 0;
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    paceInSec = Calculation.convertToSeconds(pace);
                }
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            System.out.println(paceInSec);
            Double lapTime = Calculation.getLapTime(paceInSec, Integer.parseInt(lapDistanceField.getText().toString()));
            System.out.println(lapTime);
            lapTimeText.setText("LAP time is " + lapTime);
            lapTimeText.setVisibility(View.VISIBLE);
        }
    }

}