package com.example.fitness;

public class DataPoint {
    String xtime;
    String ystep;

    public DataPoint() {
    }

    public DataPoint(String xtime, String ystep) {
        this.xtime = xtime;
        this.ystep = ystep;
    }

    public String getXtime() {
        return xtime;
    }

    public String getYstep() {
        return ystep;
    }
}
