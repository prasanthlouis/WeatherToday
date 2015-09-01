package com.psiuol21.prasanth.weatherforecast;

/**
 * Created by Prasanth on 9/1/2015.
 */public class SearchResults {
    private String date = "";
    private String weather="";
    private String high="";
    private String low="";


    public void setDate(String date) {
        this.date = date;
    }
    public void setWeather(String weather)
    {
        this.weather=weather;
    }
    public void setHigh(String high)
    {
        this.high=high;
    }
    public void setLow(String low)
    {
        this.low=low;
    }
public String getDate()
{
    return date;
}
    public String getWeather()
    {
        return weather;
    }
    public String getHigh(){return high;}
    public String getLow(){return low;}

}
