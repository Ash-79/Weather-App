package com.example.weatherapp;

public class Weather {
    private String temp, time, icon, wind_speed;

    public Weather(String temp, String time, String icon, String wind_speed) {
        this.temp = temp;
        this.time = time;
        this.icon = icon;
        this.wind_speed = wind_speed;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getWind_speed() {
        return wind_speed;
    }

    public void setWind_speed(String wind_speed) {
        this.wind_speed = wind_speed;
    }
}
