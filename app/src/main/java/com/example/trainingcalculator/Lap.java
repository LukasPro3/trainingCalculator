package com.example.trainingcalculator;


public class Lap {
    int number;
    private String lapTime;
    private String elapsedTime;
    private String paceOfLap;

    public Lap() {
    }

    public Lap(int number,String lapTime, String elapsedTime, String paceOfLap) {
        this.number = number;
        this.lapTime = lapTime;
        this.elapsedTime = elapsedTime;
        this.paceOfLap = paceOfLap;
    }
    public Lap(int number,String lapTime, String elapsedTime) {
        this.number = number;
        this.lapTime = lapTime;
        this.elapsedTime = elapsedTime;
    }
    public String getLapTime() {
        return lapTime;
    }

    public void setLapTime(String lapTime) {
        this.lapTime = lapTime;
    }

    public String getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(String elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public String getPaceOfLap() {
        return paceOfLap;
    }

    public void setPaceOfLap(String paceOfLap) {
        this.paceOfLap = paceOfLap;
    }

    @Override
    public String toString() {
        if(this.paceOfLap!=null) {
            return "LAP " + number + " " + lapTime + " " + elapsedTime + " " + paceOfLap + " min/km";
        }
        else{
            return "LAP " + number +" " + lapTime + " " + elapsedTime;

        }
    }
}

