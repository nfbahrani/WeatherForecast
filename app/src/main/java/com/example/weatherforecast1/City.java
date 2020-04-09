package com.example.weatherforecast1;

public class City
{
    private String date;
    private String max_temperature;
    private String min_temperature;
    private String condition;

    public City(String date, String max_temperature, String min_temperature, String condition) {
        this.date = date;
        this.max_temperature = max_temperature;
        this.min_temperature = min_temperature;
        this.condition = condition;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMax_temperature() {
        return max_temperature;
    }

    public void setMax_temperature(String max_temperature) {
        this.max_temperature = max_temperature;
    }

    public String getMin_temperature() {
        return min_temperature;
    }

    public void setMin_temperature(String min_temperature) {
        this.min_temperature = min_temperature;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }
}
